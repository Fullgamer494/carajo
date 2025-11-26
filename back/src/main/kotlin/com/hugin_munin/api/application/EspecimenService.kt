package com.hugin_munin.api.application.services

import com.hugin_munin.api.domain.models.*
import com.hugin_munin.api.domain.ports.*

class EspecimenService(
    private val especieRepository: EspecieRepository,
    private val especimenRepository: EspecimenRepository,
    private val altaRepository: RegistroAltaRepository,
    private val reporteRepository: ReporteRepository
) {
    // ID Fijo para "Tipo Reporte: Traslado"
    private val TIPO_REPORTE_TRASLADO_ID = 5

    suspend fun getAllRegister(): List<EspecimenDetalle> {
        return especimenRepository.findAll()
    }

    suspend fun getRegisterById(id: Int): EspecimenDetalle? {
        return especimenRepository.findById(id)
    }

    suspend fun registerEspecimen(
        especieData: Especie,
        especimenData: Especimen,
        altaData: RegistroAlta,
        trasladoData: ReporteTraslado
    ): Especimen {
        // 1. Manejar Especie
        val especieExistente = especieRepository.findByGeneroAndEspecie(especieData.genero, especieData.especie)
        val especieFinal = especieExistente ?: especieRepository.save(especieData)

        // 2. Crear Espécimen
        val especimenParaGuardar = especimenData.copy(especieId = especieFinal.id!!)
        val nuevoEspecimen = especimenRepository.save(especimenParaGuardar)

        // 3. Crear Reporte Padre (Debe ir ANTES del RegistroAlta)
        val reportePadre = Reporte(
            tipoReporteId = TIPO_REPORTE_TRASLADO_ID,
            especimenId = nuevoEspecimen.id,
            responsableId = altaData.responsableId,
            asunto = "Ingreso inicial: ${nuevoEspecimen.nombre}",
            contenido = "Espécimen ingresado y ubicado en ${trasladoData.ubicacionDestino}"
        )
        val nuevoReportePadre = reporteRepository.save(reportePadre) // Genera la ID del reporte

        // 4. Crear Reporte de Traslado
        val trasladoFinal = trasladoData.copy(reporteId = nuevoReportePadre.id!!)
        reporteRepository.saveTraslado(trasladoFinal)

        // 5. Crear Registro de Alta (Debe ir DESPUÉS, usando la ID generada)
        val altaParaGuardar = altaData.copy(
            especimenId = nuevoEspecimen.id!!,
            idReporteTraslado = nuevoReportePadre.id
        )
        altaRepository.save(altaParaGuardar)

        return nuevoEspecimen
    }


}