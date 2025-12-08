package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Veterinario

interface VeterinarioRepositorio {
    fun crearVeterinario (v: Veterinario): Veterinario
    fun actualizarVeterinario (v: Veterinario): Veterinario
    fun eliminarVeterinario (id: Int): Boolean
    fun obtenerPorId(id: Int): Veterinario?
}