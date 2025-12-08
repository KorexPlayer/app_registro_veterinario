package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas

class Veterinario(
    id: Int,
    nombreCompleto: String,
    telefono: String,
    email: String,
    direccion: String,
    private val especialidad: String,
    private val horarioAtencion: String,
    private val descripcionServicio: String
): Persona(
    id = id,
    nombreCompleto = nombreCompleto,
    telefono = telefono,
    email = email,
    direccion = direccion
) {
    //Getter
    fun getEspecialidad(): String = especialidad
    fun getHorarioAtencion(): String = horarioAtencion
    fun getDescripcionServicio(): String = descripcionServicio
}