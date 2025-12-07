package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Personas

class Dueno (
    id: Int,
    nombreCompleto: String,
    telefono: String,
    email: String,
    direccion: String
): Persona(
    id = id,
    nombreCompleto = nombreCompleto,
    telefono = telefono,
    email = email,
    direccion = direccion
){
}