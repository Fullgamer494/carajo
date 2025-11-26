package com.hugin_munin.api.infrastructure.api.dto

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class AltaEspecimenRequest(
    val genero: String,
    val especieNombre: String,

    val numInventario: String,
    val nombreEspecimen: String,

    val responsableId: Int,
    val fechaIngreso: LocalDate,

    val origenAltaId: Int,
    val procedencia: String?,
    val observacionAlta: String?,

    val areaDestino: String,
    val ubicacionDestino: String
)