package com.hugin_munin.api.domain.ports
import com.hugin_munin.api.domain.models.RegistroAlta

interface RegistroAltaRepository {
    suspend fun save(alta: RegistroAlta): RegistroAlta
    suspend fun update(id: Int): RegistroAlta?
}