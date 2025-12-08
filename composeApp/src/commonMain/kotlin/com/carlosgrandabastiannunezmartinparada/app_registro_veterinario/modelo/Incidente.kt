package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo

import kotlin.time.Instant
@kotlin.time.ExperimentalTime
class Incidente (
    private val id: Int,
    private val idMascota: Int,
    private val idVeterinario: Int,
    private val nombre: String,
    private val fecha: Instant,
    private val tipoIncidente: String,
    private val descripcion: String,
    private val gravedad: String,
    private val observaciones: String
) {
    //Getter
    fun getId() = id
    fun getIdMascota() = idMascota
    fun getIdVeterinario() = idVeterinario
    fun getNombre() = nombre
    fun getFecha() = fecha
    fun getTipoIncidente() = tipoIncidente
    fun getDescripcion() = descripcion
    fun getGravedad() = gravedad
    fun getObservaciones() = observaciones
}