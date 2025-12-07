package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Personas.Dueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.Personas.Persona
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Modelo.RegistroVacuna

@kotlin.time.ExperimentalTime
class RepositorioVeterinaria(
    private val db: GestorBaseDato
) {
    fun registrarDueno (d: Dueno): Long {
        return 0
    }
    fun autenticarUsuario (email: String, password: String)/*: Persona*/ {

    }
    fun obtenerMascotasDeDueno(idDueno: Int): List<Mascota> {
        return listOf()
    }
    fun insertarMascota(m: Mascota): Long {
        return 0
    }
    fun insertarVacuna(v: RegistroVacuna): Long {
        return 0
    }
    fun listarVeterinarios(): List<RegistroVacuna> {
        return listOf()
    }
}