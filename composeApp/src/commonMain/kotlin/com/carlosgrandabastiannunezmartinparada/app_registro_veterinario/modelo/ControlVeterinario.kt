package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

import kotlin.time.Instant
@kotlin.time.ExperimentalTime
class ControlVeterinario (
    private val id: Int,
    private val idMascota: Int,
    private val idVeterinario: Int,
    private val fechaConsulta : Instant,
    private val motivoControl : String,
    private val recomendaciones : String,
    private val necesidadExamen : Boolean,
    private val CantidadExamen : Int,
    private val nombreExamenes : List<String>
) {
    //Getter
    fun getId() = id
    fun getIdMascota() = idMascota
    fun getIdVeterinario() = idVeterinario
    fun getFechaConsulta() = fechaConsulta
    fun getMotivoControl() = motivoControl
    fun getRecomendaciones() = recomendaciones
    fun getNecesidadExamen() = necesidadExamen
    fun getCantidadExamen() = CantidadExamen
    fun getNombreExamenes() = nombreExamenes
}