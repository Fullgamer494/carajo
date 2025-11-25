package com.hugin_munin.api.infrastructure.database.repositories

import com.hugin_munin.api.domain.models.RegistroAlta
import com.hugin_munin.api.domain.ports.RegistroAltaRepository
import com.hugin_munin.api.infrastructure.database.DatabaseFactory.dbQuery
import com.hugin_munin.api.infrastructure.database.schemas.RegistroAltaTable
import org.jetbrains.exposed.sql.insert

class RegistroAltaRepositoryImpl : RegistroAltaRepository {

    override suspend fun save(alta: RegistroAlta): RegistroAlta = dbQuery {
        val insertStatement = RegistroAltaTable.insert {
            it[especimenId] = alta.especimenId
            it[origenAltaId] = alta.origenAltaId
            it[responsableId] = alta.responsableId
            it[fechaIngreso] = alta.fechaIngreso
            it[procedencia] = alta.procedencia
            it[observacion] = alta.observacion
            it[idReporteTraslado] = alta.idReporteTraslado
        }
        val id = insertStatement.resultedValues?.singleOrNull()?.get(RegistroAltaTable.id)
        alta.copy(id = id)
    }
}