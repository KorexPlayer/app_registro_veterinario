package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales

import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class Conejo (
    id: Int,
    idDueno: Int,
    nombre: String,
    edad: Int,
    fechaNacimiento: Instant,
    genero: Genero,
    raza: String,
    peso: Double,
    private val tipoOrejas: String
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
    fun getTipoOrejas() = tipoOrejas

    override fun datosegundato(): String {
        if (tipoOrejas == "Largo") {
            return "Cuida las orejas de tu conejo porque puede acumular infecciones, suciedad, etc."
        }
        else if (tipoOrejas == "Corta") {
            return "Limpia las orejas de tu conejo, este tiene buenas orejas para moverse y detectar seres en su entorno."
        }
        else {
            return "Cuida las orejas de tu conejo, no puedo determinar ahora que tipo de consejo darte."
        }
    }
}