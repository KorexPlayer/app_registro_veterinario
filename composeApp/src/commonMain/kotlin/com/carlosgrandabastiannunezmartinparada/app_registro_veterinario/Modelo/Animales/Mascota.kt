package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Animales

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

abstract class Mascota @OptIn(ExperimentalTime::class) constructor(
    private val id: Int,
    private val idDueno: Int,
    private val nombre: String,
    private val edad: Int,
    private val fechaNacimiento: Instant,
    private val genero: String,
    private val especie: String,
    private val raza: String,
    private val peso: Double

) {
}