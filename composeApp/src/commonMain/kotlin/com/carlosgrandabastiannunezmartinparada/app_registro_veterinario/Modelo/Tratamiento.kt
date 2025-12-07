package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo

class Tratamiento(
    private val id : Int,
    private val nombreTratamiento: String,
    private val razonTratamiento: String,
    private val nombreMedicamentos: List<String>,
    private val dosisMedicamento : String,
    private val frecuencia :  String,
    private val duracion : String,
    private val observaciones : String
) {
    // TODO:  ("PASAR MEDICAMENTOS A UN DICCIONARIO KEY=NOMBREMEDICAMENTO, VALUES = DOSIS, FRECUENCIA, DURACION")
}