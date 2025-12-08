package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Personas

abstract class Persona(
    private val id: Int,
    private val nombreCompleto: String,
    private val telefono: String,
    private val email: String,
    private val direccion: String
    ){
}