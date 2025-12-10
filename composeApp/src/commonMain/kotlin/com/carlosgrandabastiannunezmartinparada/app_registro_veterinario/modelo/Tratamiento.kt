package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

class Tratamiento(
    private val id : Int,
    private val idMascota: Int,
    private val idVeterinario: Int,
    private val nombreTratamiento: String,
    private val razonTratamiento: String,
    private val Medicamentos: List<String>, // "nombre, dosis, frecuencia, duracion"
    private val observaciones : String
) {
    //Getter
    fun getId() = id
    fun getIdMascota() = idMascota
    fun getIdVeterinario() = idVeterinario
    fun getNombreTratamiento() = nombreTratamiento
    fun getRazonTratamiento() = razonTratamiento
    fun getObservaciones() = observaciones
}