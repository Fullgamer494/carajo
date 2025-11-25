package com.hugin_munin.api.infrastructure.database.repositories

import com.hugin_munin.api.domain.models.Especimen
import com.hugin_munin.api.domain.models.EspecimenDetalle
import com.hugin_munin.api.domain.ports.EspecimenRepository
import com.hugin_munin.api.infrastructure.database.DatabaseFactory.dbQuery
import com.hugin_munin.api.infrastructure.database.schemas.*
import org.jetbrains.exposed.sql.*

class EspecimenRepositoryImpl : EspecimenRepository {

    override suspend fun findAll(): List<EspecimenDetalle> = dbQuery {
        EspecimenTable
            .innerJoin(EspecieTable, { especieId }, { EspecieTable.id })
            .innerJoin(RegistroAltaTable, { EspecimenTable.id }, { especimenId })
            .innerJoin(OrigenAltaTable, { RegistroAltaTable.origenAltaId }, { OrigenAltaTable.id })
            .innerJoin(ReporteTable, { RegistroAltaTable.idReporteTraslado }, { ReporteTable.id })
            .innerJoin(ReporteTrasladoTable, { ReporteTable.id }, { reporteId })
            .selectAll()
            .map { row ->
                EspecimenDetalle(
                    id = row[EspecimenTable.id],
                    numInventario = row[EspecimenTable.numInventario],
                    genero = row[EspecieTable.genero],
                    especieNombre = row[EspecieTable.especie],
                    activo = row[EspecimenTable.activo],
                    origenAltaNombre = row[OrigenAltaTable.nombre],
                    procedencia = row[RegistroAltaTable.procedencia],
                    observacion = row[RegistroAltaTable.observacion],
                    fechaIngreso = row[RegistroAltaTable.fechaIngreso],
                    areaDestino = row[ReporteTrasladoTable.areaDestino],
                    ubicacionDestino = row[ReporteTrasladoTable.ubicacionDestino]
                )
            }
    }

    override suspend fun findById(id: Int): EspecimenDetalle? = dbQuery {
        EspecimenTable
            .innerJoin(EspecieTable, { especieId }, { EspecieTable.id })
            .innerJoin(RegistroAltaTable, { EspecimenTable.id }, { especimenId })
            .innerJoin(OrigenAltaTable, { RegistroAltaTable.origenAltaId }, { OrigenAltaTable.id })
            .innerJoin(ReporteTable, { RegistroAltaTable.idReporteTraslado }, { ReporteTable.id })
            .innerJoin(ReporteTrasladoTable, { ReporteTable.id }, { reporteId })
            .selectAll()
            .andWhere { EspecimenTable.id eq id }
            .singleOrNull()
            ?.let { row ->
                EspecimenDetalle(
                    id = row[EspecimenTable.id],
                    numInventario = row[EspecimenTable.numInventario],
                    genero = row[EspecieTable.genero],
                    especieNombre = row[EspecieTable.especie],
                    activo = row[EspecimenTable.activo],
                    origenAltaNombre = row[OrigenAltaTable.nombre],
                    procedencia = row[RegistroAltaTable.procedencia],
                    observacion = row[RegistroAltaTable.observacion],
                    fechaIngreso = row[RegistroAltaTable.fechaIngreso],
                    areaDestino = row[ReporteTrasladoTable.areaDestino],
                    ubicacionDestino = row[ReporteTrasladoTable.ubicacionDestino]
                )
            }
    }

    override suspend fun save(especimen: Especimen): Especimen = dbQuery {
        val insertStatement = EspecimenTable.insert {
            it[numInventario] = especimen.numInventario
            it[especieId] = especimen.especieId
            it[nombre] = especimen.nombre
            it[activo] = especimen.activo
        }
        val id = insertStatement.resultedValues?.singleOrNull()?.get(EspecimenTable.id)
        especimen.copy(id = id)
    }
}