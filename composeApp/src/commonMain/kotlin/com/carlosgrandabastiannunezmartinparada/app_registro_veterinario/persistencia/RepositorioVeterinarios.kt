package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Veterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.VeterinarioRepositorio

object RepositorioVeterinarios : VeterinarioRepositorio{

    val repositorio: MutableList<Veterinario> = mutableListOf()

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
                val veterinario = textoAVeterinario(bytes.decodeToString())
                if (veterinario != null && obtenerPorId(veterinario.getId()) == null) {
                    RepositorioVeterinarios.repositorio.add(veterinario)
                }
            }
        }
    }

    private fun veterinarioATexto(veterinario: Veterinario): String {
        return "${veterinario.getId()}${DELIMITADOR}" +
                "${veterinario.getNombre()}${DELIMITADOR}" +
                "${veterinario.getEmail()}${DELIMITADOR}" +
                "${veterinario.getDireccion()}${DELIMITADOR}" +
                "${veterinario.getTelefono()}${DELIMITADOR}" +
                "${veterinario.getDescripcionServicio()}${DELIMITADOR}" +
                "${veterinario.getEspecialidad()}${DELIMITADOR}" +
                "${veterinario.getHorarioAtencion()}${DELIMITADOR}"
    }

    private fun textoAVeterinario(data: String): Veterinario? {
        val parts = data.split(DELIMITADOR)
        val id = parts.getOrNull(0)?.toInt()
        val nombre = parts.getOrNull(1)
        val email = parts.getOrNull(2)
        val direccion = parts.getOrNull(3)
        val telefono = parts.getOrNull(4)
        val descripcion = parts.getOrNull(5)
        val especialidad = parts.getOrNull(6)
        val horarioAtencion = parts.getOrNull(7)

        if (id == null || nombre == null || email == null || direccion == null
            || telefono == null || descripcion == null || especialidad == null || horarioAtencion == null) {
            return null
        }
        return Veterinario(
            id = id,
            nombreCompleto = nombre,
            email = email,
            direccion = direccion,
            telefono = telefono,
            descripcionServicio = descripcion,
            especialidad = especialidad,
            horarioAtencion = horarioAtencion,

        )
    }
    override fun crearVeterinario(v: Veterinario): Veterinario {
        for (veterinario in repositorio) {
            if (veterinario.getId() == v.getId()) {
                println("Ya existe veterinario en la base de datos")
                return veterinario
            }
        }
        repositorio.add(v)
        println("Veterinario creado correctamente.")

        persistencia?.let { p ->
            val key = "${PREFIJO_KEY}${v.getId()}"
            val data = veterinarioATexto(v).encodeToByteArray()
            p.save(key, data)
        }
        return v
    }

    override fun actualizarVeterinario(v: Veterinario): Veterinario {
        for (i in repositorio.indices) {
            if (repositorio[i].getId() == v.getId()) {
                repositorio[i] = v
                println("Veterinario actualizado correctamente")

                persistencia?.let { p ->
                    val key = "${PREFIJO_KEY}${v.getId()}"
                    val data = veterinarioATexto(v).encodeToByteArray()
                    p.save(key, data)
                }
                return v
            }
        }
        println("No se encontro el veterinario")
        return v

    }

    override fun eliminarVeterinario(id: Int): Boolean {
        for (veterinario in repositorio) {
            if (veterinario.getId() == id) {
                repositorio.remove(veterinario)
                println("Se ha eliminado el Veterinario del repositorio.")

                persistencia?.let { p ->
                    val key = "${PREFIJO_KEY}$id"
                    p.delete(key)
                }

                return true
            }
        }
        return false
    }
    override fun obtenerPorId(id: Int): Veterinario? {
        for (veterinario in repositorio) {
            if (veterinario.getId() == id) {
                println("Veterinario encontrado: ${veterinario.getNombre()}")
                return veterinario
            }
        }
        println("No se encontro el Veterinario")
        return null
    }

    override fun listar(): List<Veterinario> {
        val lista: MutableList<Veterinario> = mutableListOf()
        for (veterinario in repositorio) {
                lista.add(veterinario)
        }
        return lista
    }
}