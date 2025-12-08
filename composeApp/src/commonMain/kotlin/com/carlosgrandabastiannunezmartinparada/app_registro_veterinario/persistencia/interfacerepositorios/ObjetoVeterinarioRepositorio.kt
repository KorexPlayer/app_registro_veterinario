package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.ControlVeterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Incidente
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.RegistroVacuna
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Tratamiento

@kotlin.time.ExperimentalTime
interface ObjetoVeterinarioRepositorio {
    fun anadirControlVeterinario(c: ControlVeterinario): ControlVeterinario
    fun anadirIncidente(i: Incidente): Incidente
    fun anadirRegistroVacuna(i: RegistroVacuna): RegistroVacuna
    fun anadirTratamiento(t: Tratamiento): Tratamiento

    fun eliminarControlVeterinario(id: Int): Boolean
    fun eliminarIncident(id: Int): Boolean
    fun eliminarRegistroVeterinario(id: Int): Boolean
    fun eliminarTratamiento(id: Int): Boolean

}