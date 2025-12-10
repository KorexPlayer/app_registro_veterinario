package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import androidx.compose.runtime.mutableStateListOf
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Perro
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Gato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Conejo
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Hamster
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.AveDomestica
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Genero
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.AnimalRepositorio
import kotlin.time.Instant

@kotlin.time.ExperimentalTime
object RepositorioAnimal : AnimalRepositorio {

    val repositorio: MutableList<Mascota> = mutableStateListOf()

    private var persistencia: GestorBaseDato? = null

    private const val PREFIJO_KEY = "Mascota_"
    private const val DELIMITADOR = "::"

    fun init(persistencia: GestorBaseDato) {
        this.persistencia = persistencia
        cargarDatos()
    }

    private fun cargarDatos() {
        persistencia?.list(PREFIJO_KEY)?.forEach { key ->
            val bytes = persistencia!!.read(key)
            if (bytes != null) {
                val mascota = textoAMascota(bytes.decodeToString())
                if (mascota != null) {
                    var existe = false
                    for (m in repositorio) {
                        if (m.getId() == mascota.getId()) {
                            existe = true
                            break
                        }
                    }
                    if (!existe) {
                        repositorio.add(mascota)
                    }
                }
            }
        }
    }

    private fun mascotaATexto(m: Mascota): String {
        val common = "${m.getId()}$DELIMITADOR${m.getIdDueno()}$DELIMITADOR${m.getNombre()}$DELIMITADOR" +
                "${m.getEdad()}$DELIMITADOR${m.getFechaNacimiento()}$DELIMITADOR${m.getGenero()}$DELIMITADOR" +
                "${m.getRaza()}$DELIMITADOR${m.getPeso()}"

        return when(m) {
            is Perro -> "PERRO$DELIMITADOR$common$DELIMITADOR${m.getTipoHocico()}"
            is Gato -> "GATO$DELIMITADOR$common$DELIMITADOR${m.getLongitudBigotes()}"
            is Conejo -> "CONEJO$DELIMITADOR$common$DELIMITADOR${m.getTipoOrejas()}"
            is Hamster -> "HAMSTER$DELIMITADOR$common$DELIMITADOR${m.getCapacidadAbazonesGramos()}"
            is AveDomestica -> "AVE$DELIMITADOR$common$DELIMITADOR${m.getFormaPico()}"
            else -> "UNKNOWN$DELIMITADOR$common"
        }
    }

    private fun textoAMascota(data: String): Mascota? {
        val parts = data.split(DELIMITADOR)
        if (parts.size < 10) return null

        val tipo = parts[0]
        val id = parts[1].toInt()
        val idDueno = parts[2].toInt()
        val nombre = parts[3]
        val edad = parts[4].toInt()

        val fecha = try { Instant.parse(parts[5]) } catch (_: Exception) { return null }
        val genero = try { Genero.valueOf(parts[6]) } catch (_: Exception) { Genero.MACHO }

        val raza = parts[7]
        val peso = parts[8].toDouble()
        val attr = parts[9]

        return when (tipo) {
            "PERRO" -> Perro(id, idDueno, nombre, edad, fecha, genero, raza, peso, tipoHocico = attr)
            "GATO" -> Gato(id, idDueno, nombre, edad, fecha, genero, raza, peso, longitudBigotes = attr.toFloat())
            "CONEJO" -> Conejo(id, idDueno, nombre, edad, fecha, genero, raza, peso, tipoOrejas = attr)
            "HAMSTER" -> Hamster(id, idDueno, nombre, edad, fecha, genero, raza, peso, capacidadAbazonesGramos = attr.toFloat())
            "AVE" -> AveDomestica(id, idDueno, nombre, edad, fecha, genero, raza, peso, formaPico = attr)
            else -> null
        }
    }

    override fun crearMascota(m: Mascota): Mascota {
        for (mascota in repositorio) {
            if (mascota.getId() == m.getId()) {
                println("Ya existe Mascota con este ID")
                return mascota
            }
        }
        repositorio.add(m)
        println("Mascota creada correctamente.")

        persistencia?.let { p ->
            val key = "$PREFIJO_KEY${m.getId()}"
            val data = mascotaATexto(m).encodeToByteArray()
            p.save(key, data)
        }
        return m
    }

    override fun actualizarMascota(m: Mascota): Mascota {
        for (i in repositorio.indices) {
            if (repositorio[i].getId() == m.getId()) {
                repositorio[i] = m
                println("Mascota actualizada correctamente")

                persistencia?.let { p ->
                    val key = "$PREFIJO_KEY${m.getId()}"
                    val data = mascotaATexto(m).encodeToByteArray()
                    p.save(key, data)
                }
                return m
            }
        }
        println("No se encontro la Mascota")
        return m
    }

    override fun eliminarMascota(id: Int): Boolean {
        for (mascota in repositorio) {
            if (mascota.getId() == id) {
                repositorio.remove(mascota)
                println("Se ha eliminado la Mascota del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_KEY$id"
                    p.delete(key)
                }
                return true
            }
        }
        return false
    }

    override fun listar(filtro: String): List<Mascota> {
        if (filtro.isEmpty()) return repositorio

        val listaFiltrada = mutableListOf<Mascota>()
        for (m in repositorio) {
            if (m.getNombre().contains(filtro, ignoreCase = true)) {
                listaFiltrada.add(m)
            }
        }
        return listaFiltrada
    }
}