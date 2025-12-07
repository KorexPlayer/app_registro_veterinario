package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo

import kotlin.time.Instant
@kotlin.time.ExperimentalTime
class ControlVeterinario (
    private val id: Int,
    private val fechaConsulta : Instant,
    private val motivoControl : String,
    private val recomendaciones : String,
    private val necesidadExamen : Boolean,
    private val CantidadExamen : Int,
    private val nombreExamenes : List<String>
) {
}