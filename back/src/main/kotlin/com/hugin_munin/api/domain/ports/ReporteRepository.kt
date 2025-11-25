package com.hugin_munin.api.domain.ports
import com.hugin_munin.api.domain.models.Reporte
import com.hugin_munin.api.domain.models.ReporteTraslado

interface ReporteRepository {
    suspend fun save(reporte: Reporte): Reporte
    suspend fun saveTraslado(traslado: ReporteTraslado)
}