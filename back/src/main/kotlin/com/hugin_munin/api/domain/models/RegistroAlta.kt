package com.hugin_munin.api.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RegistroAlta(
    val id: Int? = null,
    val especimenId: Int,
    val origenAltaId: Int,
    val responsableId: Int,
    val fechaIngreso: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val procedencia: String?,
    val observacion: String?,
    val idReporteTraslado: Int
)