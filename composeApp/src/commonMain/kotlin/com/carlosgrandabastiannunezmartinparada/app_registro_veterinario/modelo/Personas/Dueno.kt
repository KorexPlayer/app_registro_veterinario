package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Personas

class Dueno (
    id: Int,
    nombreCompleto: String,
    telefono: String,
    email: String,
    direccion: String,
    private val rut: String
): Persona(
    id = id,
    nombreCompleto = nombreCompleto,
    telefono = telefono,
    email = email,
    direccion = direccion
){
}