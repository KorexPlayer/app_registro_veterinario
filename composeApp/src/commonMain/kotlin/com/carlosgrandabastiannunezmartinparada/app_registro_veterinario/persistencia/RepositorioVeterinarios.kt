package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Veterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.VeterinarioRepositorio

object RepositorioVeterinarios : VeterinarioRepositorio{
    override fun crearVeterinario(v: Veterinario): Veterinario {
        TODO("Not yet implemented")
    }

    override fun actualizarVeterinario(v: Veterinario): Veterinario {
        TODO("Not yet implemented")
    }

    override fun eliminarVeterinario(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}