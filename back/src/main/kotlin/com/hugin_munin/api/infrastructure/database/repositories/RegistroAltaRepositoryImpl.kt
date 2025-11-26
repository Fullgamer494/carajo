package com.hugin_munin.api.infrastructure.database.repositories

import com.hugin_munin.api.domain.models.RegistroAlta
import com.hugin_munin.api.domain.ports.RegistroAltaRepository
import com.hugin_munin.api.infrastructure.database.schemas.RegistroAltaTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class RegistroAltaRepositoryImpl : RegistroAltaRepository {
    fun ResultRow.toRegistroAlta() = RegistroAlta(
        id = this[RegistroAltaTable.id],
        especimenId = this[RegistroAltaTable.especimenId],
        origenAltaId = this[RegistroAltaTable.origenAltaId],
        responsableId = this[RegistroAltaTable.responsableId],
        fechaIngreso = this[RegistroAltaTable.fechaIngreso],
        procedencia = this[RegistroAltaTable.procedencia],
        observacion = this[RegistroAltaTable.observacion],
        idReporteTraslado = this[RegistroAltaTable.idReporteTraslado],
    )

    override suspend fun save(alta: RegistroAlta): RegistroAlta {
        transaction {
            val insertStatement = RegistroAltaTable.insert {
                it[especimenId] = alta.especimenId
                it[origenAltaId] = alta.origenAltaId
                it[responsableId] = alta.responsableId
                it[fechaIngreso] = alta.fechaIngreso
                it[procedencia] = alta.procedencia
                it[observacion] = alta.observacion
                it[idReporteTraslado] = alta.idReporteTraslado
            }

           val insertedRow = insertStatement.resultedValues?.singleOrNull()?: throw IllegalStateException("No se pudo crear el registro de alta")

            insertedRow.toRegistroAlta()
        }
    }

    override suspend fun update(id: Int): RegistroAlta? {
        transaction {
            val rowAffected = RegistroAltaTable.update({ RegistroAltaTable.id eq id }) {
                it[ ]
            }
        }
    }
}