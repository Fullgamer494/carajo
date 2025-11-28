package com.hugin_munin.api.application.services

import com.hugin_munin.api.infrastructure.api.dto.EspecimenDetalleResponse
import com.hugin_munin.api.infrastructure.api.dto.RegistroAltaInfo
import com.hugin_munin.api.infrastructure.api.dto.TrasladoInfo
import com.hugin_munin.api.domain.ports.*

class EspecimenQueryService(
    private val especimenRepository: EspecimenRepository,
    private val especieRepository: EspecieRepository,
    private val registroAltaRepository: RegistroAltaRepository,
    private val reporteRepository: ReporteRepository
) {

    suspend fun getEspecimenDetalleById(id: Int): EspecimenDetalleResponse? {
        val especimen = especimenRepository.findById(id) ?: return null
        val especie = especieRepository.findById(especimen.especieId) ?: return null
        val registroAlta = registroAltaRepository.findByEspecimenId(especimen.id!!) ?: return null

        val reporte = reporteRepository.findById(registroAlta.idReporteTraslado) ?: return null
        val traslado = reporteRepository.findTrasladoByReporteId(registroAlta.idReporteTraslado) ?: return null
        val origenAlta = reporteRepository.findOrigenAltaById(registroAlta.origenAltaId) ?: return null

        return EspecimenDetalleResponse(
            id = especimen.id,
            numInventario = especimen.numInventario,
            nombreEspecimen = especimen.nombre,
            genero = especie.genero,
            especieNombre = especie.especie,
            activo = especimen.activo,
            registroAlta = RegistroAltaInfo(
                id = registroAlta.id!!,
                origenAltaNombre = origenAlta.nombre,
                procedencia = registroAlta.procedencia,
                observacion = registroAlta.observacion,
                fechaIngreso = registroAlta.fechaIngreso,
                responsableId = registroAlta.responsableId,
                traslado = TrasladoInfo(
                    areaDestino = traslado.areaDestino,
                    ubicacionDestino = traslado.ubicacionDestino,
                    areaOrigen = traslado.areaOrigen,
                    ubicacionOrigen = traslado.ubicacionOrigen,
                    motivo = traslado.motivo
                )
            )
        )
    }

    suspend fun getAllEspecimenesDetalle(): List<EspecimenDetalleResponse> {
        val especimenes = especimenRepository.findAll()
        return especimenes.mapNotNull { especimen ->
            getEspecimenDetalleById(especimen.id!!)
        }
    }
}