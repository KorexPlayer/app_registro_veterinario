package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Animales

import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class Perro (
    id: Int,
    idDueno: Int,
    nombre: String,
    edad: Int,
    fechaNacimiento: Instant,
    genero: Genero,
    raza: String,
    peso: Double,
    private val tipoHocico: String
): Mascota(
    id = id,
    idDueno = idDueno,
    nombre = nombre,
    edad = edad,
    fechaNacimiento = fechaNacimiento,
    genero = genero,
    raza = raza,
    peso = peso
) {
}