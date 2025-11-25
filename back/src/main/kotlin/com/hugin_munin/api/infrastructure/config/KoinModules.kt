package com.hugin_munin.api.infrastructure.config

import com.hugin_munin.api.domain.ports.*
import com.hugin_munin.api.infrastructure.database.repositories.*
import com.hugin_munin.api.application.services.EspecimenService

import org.koin.dsl.module

val appModule = module {
    single<EspecieRepository> { EspecieRepositoryImpl() }
    single<EspecimenRepository> { EspecimenRepositoryImpl() }
    single<RegistroAltaRepository> { RegistroAltaRepositoryImpl() }
    single<ReporteRepository> { ReporteRepositoryImpl() }

    single {
        EspecimenService(
            especieRepository = get(),
            especimenRepository = get(),
            altaRepository = get(),
            reporteRepository = get()
        )
    }
}