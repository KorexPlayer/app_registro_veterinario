package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota

@kotlin.time.ExperimentalTime
interface AnimalRepositorio {
    fun crearMascota(m: Mascota): Mascota
    fun actualizarMascota(m: Mascota): Mascota
    fun eliminarMascota(id: Int): Boolean
    fun listar(filtro: String = ""): List<Mascota>

}