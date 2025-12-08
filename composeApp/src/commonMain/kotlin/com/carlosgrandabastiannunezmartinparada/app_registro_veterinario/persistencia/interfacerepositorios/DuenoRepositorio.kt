package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Dueno

@kotlin.time.ExperimentalTime
interface DuenoRepositorio {
    fun crearDueno (d: Dueno): Dueno
    fun actualizarDueno (d: Dueno): Dueno
    fun eliminarDueno (id: Int): Boolean
    fun obtenerMascotasDueno(idDueno: Int): List<Mascota>
}