package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Animales

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Animales.Mascota
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class Hamster (
    id: Int,
    idDueno: Int,
    nombre: String,
    edad: Int,
    fechaNacimiento: Instant,
    genero: String,
    especie: String,
    raza: String,
    peso: Double
): Mascota(
    id = id,
    idDueno = idDueno,
    nombre = nombre,
    edad = edad,
    fechaNacimiento = fechaNacimiento,
    genero = genero,
    especie = especie,
    raza = raza,
    peso = peso
) {
}