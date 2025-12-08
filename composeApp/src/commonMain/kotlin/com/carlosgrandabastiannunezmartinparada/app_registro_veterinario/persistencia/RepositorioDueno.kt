package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Dueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.DuenoRepositorio

@kotlin.time.ExperimentalTime
object RepositorioDueno : DuenoRepositorio {

    val repositorio: MutableList<Dueno> = mutableListOf()

    private var persistencia: GestorBaseDato? = null

    private const val PREFIJO_KEY = "Dueno_"
    private const val DELIMITADOR = "::"

    fun init(persistencia: GestorBaseDato) {
        this.persistencia = persistencia
        cargarDatos()
    }

    private fun cargarDatos() {

        persistencia?.list(PREFIJO_KEY)?.forEach { key ->
            val bytes = persistencia!!.read(key)
            if (bytes != null) {
                val dueno = textoADueno(bytes.decodeToString())
                if (dueno != null && obtenerPorRut(dueno.getRut()) == null) {
                    repositorio.add(dueno)
                }
            }
        }
    }

    private fun duenoATexto(dueno: Dueno): String {
        return "${dueno.getRut()}$DELIMITADOR" +
                "${dueno.getNombre()}$DELIMITADOR" +
                "${dueno.getEmail()}$DELIMITADOR" +
                "${dueno.getDireccion()}$DELIMITADOR" +
                "${dueno.getTelefono()}$DELIMITADOR" +
                "${dueno.getContrasena()}$DELIMITADOR" +
                "${dueno.getId()}"
    }

    private fun textoADueno(data: String): Dueno? {
        val parts = data.split(DELIMITADOR)
        val rut = parts.getOrNull(0)
        val nombre = parts.getOrNull(1)
        val email = parts.getOrNull(2)
        val direccion = parts.getOrNull(3)
        val telefono = parts.getOrNull(4)
        val contasena = parts.getOrNull(5)
        val id = parts.getOrNull(6)!!.toInt()

        if (rut == null || nombre == null || email == null || direccion == null || telefono == null || contasena == null || id == null) {
            return null
        }
        return Dueno(rut = rut, id = id, nombreCompleto = nombre, email = email, direccion = direccion, telefono = telefono, contrasena = contasena)
    }
    override fun crearDueno(d: Dueno): Dueno {
        for (cliente in repositorio) {
            if ()
        }
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

    override fun obtenerPorRut(rut: String): Dueno? {
        TODO("Not yet implemented")
    }
    fun autenticarUsuario(rut: String, password: String): Dueno? {
        return null
    }
}