package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

import kotlin.time.Instant
@kotlin.time.ExperimentalTime
class Incidente (
    private val id: Int,
    private val nombre: String,
    private val fecha: Instant,
    private val tipoIncidente: String,
    private val descripcion: String,
    private val gravedad: String,
    private val observaciones: String
) {
}