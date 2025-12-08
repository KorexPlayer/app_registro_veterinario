package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales

import kotlin.time.Instant
@kotlin.time.ExperimentalTime
abstract class Mascota (
    private val id: Int,
    private val idDueno: Int,
    private val nombre: String,
    private val edad: Int,
    private val fechaNacimiento: Instant,
    private val genero: Genero,
    private val raza: String,
    private val peso: Double

) {
    //Getter
    fun getId() = id
    fun getIdDueno() = idDueno
    fun getNombre() = nombre
    fun getEdad() = edad
    fun getFechaNacimiento() = fechaNacimiento
    fun getGenero() = genero
    fun getRaza() = raza
    fun getPeso() = peso
}