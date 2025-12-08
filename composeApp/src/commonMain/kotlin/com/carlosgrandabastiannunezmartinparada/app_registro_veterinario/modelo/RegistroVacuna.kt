package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

import kotlin.time.Instant

@kotlin.time.ExperimentalTime
class RegistroVacuna (
    private val id : Int,
    private val idMascota: Int,
    private val idVeterinario: Int,
    private val nombreVacuna : String,
    private val tipo : String,
    private val cantidadDosis : Int,
    private val fechaVacunacion : Instant,
    private val observaciones : String
) {
    //Getter
    fun getId() = id
    fun getIdMascota() = idMascota
    fun getIdVeterinario() = idVeterinario
    fun getNombreVacuna() = nombreVacuna
    fun getTipo() = tipo
    fun getCantidadDosis() = cantidadDosis
    fun getFechaVacunacion() = fechaVacunacion
    fun getObservaciones() = observaciones
}