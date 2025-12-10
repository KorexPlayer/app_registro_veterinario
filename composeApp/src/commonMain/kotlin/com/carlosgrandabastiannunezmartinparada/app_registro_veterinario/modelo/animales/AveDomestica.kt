package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales

import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class AveDomestica (
    id: Int,
    idDueno: Int,
    nombre: String,
    edad: Int,
    fechaNacimiento: Instant,
    genero: Genero,
    raza: String,
    peso: Double,
    private val formaPico: String
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
    fun getFormaPico() = formaPico
    override fun datosegundato(): String{
        val textoSample = "Una buena dieta para tu ave son "
        when (getFormaPico()) {
            ("Conico y Corto") -> {
                return textoSample + "semillas."
            }
            "Ganchudos" -> {
                return textoSample + "carne."
            }
            "Largo y delgado" -> {
                return textoSample + "nectar."
            }
            "Pinzas" -> {
                return textoSample + "insectos."
            }
            "Lanza" -> {
                return textoSample + "peces."
            }
            "Peine" -> {
                return textoSample + "alimentos del agua."
            }
            else -> {
                return textoSample + "algo que no puedo determinar."
            }
        }
    }
}