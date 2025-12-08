package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

class Tratamiento(
    private val id : Int,
    private val nombreTratamiento: String,
    private val razonTratamiento: String,
    private val Medicamentos: List<List<String>>, // [nombre, dosis, frecuencia, duracion]
    private val observaciones : String
) {

}