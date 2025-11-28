package com.hugin_munin.api.domain.ports
import com.hugin_munin.api.domain.models.OrigenAlta
import com.hugin_munin.api.domain.models.Reporte
import com.hugin_munin.api.domain.models.ReporteTraslado

interface ReporteRepository {
    suspend fun findById(id: Int): Reporte?
    suspend fun save(reporte: Reporte): Reporte
    suspend fun saveTraslado(traslado: ReporteTraslado)
    suspend fun findTrasladoByReporteId(reporteId: Int): ReporteTraslado?
    suspend fun findOrigenAltaById(id: Int): OrigenAlta?
}