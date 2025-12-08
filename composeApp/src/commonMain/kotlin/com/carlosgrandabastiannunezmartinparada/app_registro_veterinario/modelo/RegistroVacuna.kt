package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class RegistroVacuna (
    private val id : Int,
    private val nombreVacuna : String,
    private val tipo : String,
    private val cantidadDosis : Int,
    private val fechaVacunacion : Instant,
    private val observaciones : String
) {
}