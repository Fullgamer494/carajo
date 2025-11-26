package com.hugin_munin.api.domain.ports
import com.hugin_munin.api.domain.models.Especimen
import com.hugin_munin.api.domain.models.EspecimenDetalle

interface EspecimenRepository {
    suspend fun findAll(): List<EspecimenDetalle>
    suspend fun findById(id: Int): EspecimenDetalle?
    suspend fun save(especimen: Especimen): Especimen
}