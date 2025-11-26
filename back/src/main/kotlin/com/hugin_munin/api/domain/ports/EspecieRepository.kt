package com.hugin_munin.api.domain.ports
import com.hugin_munin.api.domain.models.Especie

interface EspecieRepository {
    suspend fun findByGeneroAndEspecie(genero: String, especie: String): Especie?
    suspend fun save(especie: Especie): Especie
}