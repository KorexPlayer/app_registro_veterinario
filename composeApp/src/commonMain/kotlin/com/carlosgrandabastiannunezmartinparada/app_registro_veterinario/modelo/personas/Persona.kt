package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas

abstract class Persona(
    private val id: Int,
    private val nombreCompleto: String,
    private val telefono: String,
    private val email: String,
    private val direccion: String
    ){
    //Getter
    open fun getId() = id
    open fun getNombre(): String = nombreCompleto
    open fun getTelefono(): String = telefono
    open fun getEmail(): String = email
    open fun getDireccion(): String = direccion
}