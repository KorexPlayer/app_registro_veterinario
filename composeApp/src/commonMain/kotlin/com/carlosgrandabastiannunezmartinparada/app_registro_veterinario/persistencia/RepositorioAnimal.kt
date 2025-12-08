package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.AnimalRepositorio

@kotlin.time.ExperimentalTime
object RepositorioAnimal : AnimalRepositorio {
    override fun crearMascota(m: Mascota): Mascota {
        TODO("Not yet implemented")
    }

    override fun actualizarMascota(m: Mascota): Mascota {
        TODO("Not yet implemented")
    }

    override fun eliminarMascota(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun listar(filtro: String): List<Mascota> {
        TODO("Not yet implemented")
    }

}