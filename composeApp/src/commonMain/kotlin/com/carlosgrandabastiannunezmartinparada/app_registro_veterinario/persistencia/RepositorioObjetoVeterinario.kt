package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.ControlVeterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Incidente
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.RegistroVacuna
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Tratamiento
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.ObjetoVeterinarioRepositorio

@kotlin.time.ExperimentalTime
object RepositorioObjetoVeterinario : ObjetoVeterinarioRepositorio {
    override fun anadirControlVeterinario(c: ControlVeterinario): ControlVeterinario {
        TODO("Not yet implemented")
    }

    override fun anadirIncidente(i: Incidente): Incidente {
        TODO("Not yet implemented")
    }

    override fun anadirRegistroVacuna(i: RegistroVacuna): RegistroVacuna {
        TODO("Not yet implemented")
    }

    override fun anadirTratamiento(t: Tratamiento): Tratamiento {
        TODO("Not yet implemented")
    }

    override fun eliminarControlVeterinario(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarIncident(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarRegistroVeterinario(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun eliminarTratamiento(id: Int): Boolean {
        TODO("Not yet implemented")
    }

}