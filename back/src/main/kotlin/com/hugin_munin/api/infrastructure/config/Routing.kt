package com.hugin_munin.api.infrastructure.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.hugin_munin.api.infrastructure.api.routes.especimenRouting
import com.hugin_munin.api.application.services.EspecimenService
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Serializable
data class ErrorResponse(val error: String, val message: String)

fun Application.configureRouting() {
    val especimenService by inject<EspecimenService>() // Inyección AQUÍ

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Internal Server Error", cause.message ?: "Error desconocido"))
        }
    }

    routing {
        get("/") { call.respondText("Hello World!") }
        get("/health") { call.respond(mapOf("status" to "OK")) }
        especimenRouting(especimenService) // Pasa el servicio como parámetro
    }
}