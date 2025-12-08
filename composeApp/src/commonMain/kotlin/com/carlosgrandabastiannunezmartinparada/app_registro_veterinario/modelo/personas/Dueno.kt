package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas

class Dueno (
    id: Int,
    nombreCompleto: String,
    telefono: String,
    email: String,
    direccion: String,
    private val rut: String,
    private val contrasena: String
): Persona(
    id = id,
    nombreCompleto = nombreCompleto,
    telefono = telefono,
    email = email,
    direccion = direccion
){
    //Getter
    fun getRut() = rut
    fun getContrasena() = contrasena

}