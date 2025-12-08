package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Dueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.DuenoRepositorio

@kotlin.time.ExperimentalTime
object RepositorioDueno : DuenoRepositorio {
    override fun crearDueno(d: Dueno): Dueno {
        TODO("Not yet implemented")
    }

    override fun actualizarDueno(d: Dueno): Dueno {
        TODO("Not yet implemented")
    }

    override fun eliminarDueno(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun obtenerMascotasDueno(idDueno: Int): List<Mascota> {
        TODO("Not yet implemented")
    }

    fun autenticarUsuario(rut: String, password: String): Dueno? {
        return null
    }
}