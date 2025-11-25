package com.hugin_munin.api.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Reporte(
    val id: Int? = null,
    val tipoReporteId: Int,
    val especimenId: Int?,
    val responsableId: Int,
    val asunto: String,
    val fechaReporte: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val contenido: String
)