package com.hugin_munin.api.infrastructure.database.repositories

import com.hugin_munin.api.domain.models.Reporte
import com.hugin_munin.api.domain.models.ReporteTraslado
import com.hugin_munin.api.domain.ports.ReporteRepository
import com.hugin_munin.api.infrastructure.database.DatabaseFactory.dbQuery
import com.hugin_munin.api.infrastructure.database.schemas.ReporteTable
import com.hugin_munin.api.infrastructure.database.schemas.ReporteTrasladoTable
import org.jetbrains.exposed.sql.insert

class ReporteRepositoryImpl : ReporteRepository {

    override suspend fun save(reporte: Reporte): Reporte = dbQuery {
        val insertStatement = ReporteTable.insert {
            it[tipoReporteId] = reporte.tipoReporteId
            it[especimenId] = reporte.especimenId!!
            it[responsableId] = reporte.responsableId
            it[asunto] = reporte.asunto
            it[fechaReporte] = reporte.fechaReporte
            it[contenido] = reporte.contenido
        }

        val id = insertStatement.resultedValues?.singleOrNull()?.get(ReporteTable.id)
            ?.let { it as? Int }
        reporte.copy(id = id)
    }

    override suspend fun saveTraslado(traslado: ReporteTraslado): Unit = dbQuery {
        ReporteTrasladoTable.insert {
            it[reporteId] = traslado.reporteId
            it[areaOrigen] = traslado.areaOrigen
            it[areaDestino] = traslado.areaDestino
            it[ubicacionOrigen] = traslado.ubicacionOrigen
            it[ubicacionDestino] = traslado.ubicacionDestino
            it[motivo] = traslado.motivo
        }
    }
}