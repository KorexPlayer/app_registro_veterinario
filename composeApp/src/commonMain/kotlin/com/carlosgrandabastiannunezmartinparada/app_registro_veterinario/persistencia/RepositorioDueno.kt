package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import androidx.compose.runtime.mutableStateListOf
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Dueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.AnimalRepositorio
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.DuenoRepositorio

@kotlin.time.ExperimentalTime
object RepositorioDueno : DuenoRepositorio {
    private var animalRepo: AnimalRepositorio? = null
    val repositorio: MutableList<Dueno> = mutableStateListOf()

    private var persistencia: GestorBaseDato? = null

    private const val PREFIJO_KEY = "Dueno_"
    private const val DELIMITADOR = "::"

    fun init(persistencia: GestorBaseDato, animalRepo: AnimalRepositorio) {
        this.persistencia = persistencia
        this.animalRepo = animalRepo
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

        if (rut == null || nombre == null || email == null || direccion == null || telefono == null || contasena == null) {
            return null
        }
        return Dueno(rut = rut, id = id, nombreCompleto = nombre, email = email, direccion = direccion, telefono = telefono, contrasena = contasena)
    }
    override fun crearDueno(d: Dueno): Dueno {
        for (dueno in repositorio) {
            if (dueno.getRut() == d.getRut()) {
                println("Ya existe Dueño en la base de datos")
                return dueno
            }
        }
        repositorio.add(d)
        println("Dueno creado correctamente.")

        persistencia?.let { p ->
            val key = "$PREFIJO_KEY${d.getRut()}"
            val data = duenoATexto(d).encodeToByteArray()
            p.save(key, data)
        }
        return d
    }

    override fun actualizarDueno(d: Dueno): Dueno {
        for (i in repositorio.indices) {
            if (repositorio[i].getRut() == d.getRut()) {
                repositorio[i] = d
                println("Dueno actualizado correctamente")

                persistencia?.let { p ->
                    val key = "$PREFIJO_KEY${d.getRut()}"
                    val data = duenoATexto(d).encodeToByteArray()
                    p.save(key, data)
                }
                return d
            }
        }
        println("No se encontro el dueno")
        return d
    }

    override fun eliminarDueno(rut: String): Boolean {
        for (dueno in repositorio) {
            if (dueno.getRut() == rut) {
                repositorio.remove(dueno)
                println("Se ha eliminado el Dueno del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_KEY$rut"
                    p.delete(key)
                }

                return true
            }
        }
        return false
    }

    override fun obtenerMascotasDueno(idDueno: Int): List<Mascota> {
        TODO("Not yet implemented")
    }

    override fun obtenerPorRut(rut: String): Dueno? {
        for (dueno in repositorio) {
            if (dueno.getRut() == rut) {
                println("Dueno encontrado: ${dueno.getNombre()}")
                return dueno
            }
        }
        println("No se encontro el Dueno")
        return null
    }
    fun autenticarUsuario(rut: String, password: String): Dueno? {
        val usuario = obtenerPorRut(rut)
        if (usuario != null) {
            if (usuario!!.getRut() == rut && usuario!!.getContrasena() == password) {
                return usuario
            }
        }
        return null
    }
}