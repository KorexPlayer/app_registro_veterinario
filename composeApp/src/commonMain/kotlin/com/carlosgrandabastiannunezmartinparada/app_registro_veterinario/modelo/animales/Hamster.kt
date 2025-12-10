package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales

import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class Hamster (
    id: Int,
    idDueno: Int,
    nombre: String,
    edad: Int,
    fechaNacimiento: Instant,
    genero: Genero,
    raza: String,
    peso: Double,
    private val capacidadAbazonesGramos: Float
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
    //Getter
    fun getCapacidadAbazonesGramos() = capacidadAbazonesGramos

    override fun datosegundato(): String {
        if ((getPeso()*0.2) <= getCapacidadAbazonesGramos()) {
            return "LLeva tu Hamster al veterinario, puede tener problemas"
        }
        else {
            return "Tu hamster esta en buenas condiciones."
        }
    }
}