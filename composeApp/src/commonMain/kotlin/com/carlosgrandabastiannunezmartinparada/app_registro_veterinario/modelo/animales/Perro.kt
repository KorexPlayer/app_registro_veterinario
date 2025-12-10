package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales

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
    //Getter
    fun getTipoHocico() = tipoHocico
    override fun datosegundato(): String {
        if (getTipoHocico() == "Largo") {
            return "Tu perro tiene buena respiracion y sensibilidad mayor, cuida su nariz"
        }
        else if (getTipoHocico() == "Mediano") {
            return "Tu perro tiene el balance perfecto entre sensibilidad y respiracion."
        }
        else if (getTipoHocico() == "Corto") {
            return "Tu perro tiene una nariz corta, asi que cuidalo de las enfermedades."
        }
        else {
            return "Tu perro tiene otro tipo de nariz."
        }
    }
}