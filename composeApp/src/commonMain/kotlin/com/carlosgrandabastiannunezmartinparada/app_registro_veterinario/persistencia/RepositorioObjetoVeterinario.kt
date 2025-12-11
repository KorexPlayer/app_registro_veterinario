package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

import androidx.compose.runtime.mutableStateListOf
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.ControlVeterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Incidente
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.RegistroVacuna
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Tratamiento
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.ObjetoVeterinarioRepositorio
import kotlin.time.Instant

@kotlin.time.ExperimentalTime
object RepositorioObjetoVeterinario : ObjetoVeterinarioRepositorio {

    val listaControles: MutableList<ControlVeterinario> = mutableStateListOf()
    val listaIncidentes: MutableList<Incidente> = mutableStateListOf()
    val listaVacunas: MutableList<RegistroVacuna> = mutableStateListOf()
    val listaTratamientos: MutableList<Tratamiento> = mutableStateListOf()

    private var persistencia: GestorBaseDato? = null

    private const val DELIMITADOR = "::"
    private const val PREFIJO_CTRL = "Control_"
    private const val PREFIJO_INC = "Incidente_"
    private const val PREFIJO_VAC = "Vacuna_"
    private const val PREFIJO_TRAT = "Tratamiento_"

    fun init(persistencia: GestorBaseDato) {
        this.persistencia = persistencia
        cargarDatos()
    }

    private fun cargarDatos() {
        // Cargar Controles
        persistencia?.list(PREFIJO_CTRL)?.forEach { key ->
            val bytes = persistencia!!.read(key)
            if (bytes != null) {
                val obj = textoAControl(bytes.decodeToString())
                if (obj != null) {
                    var existe = false
                    for (item in listaControles) {
                        if (item.getId() == obj.getId()) {
                            existe = true
                            break
                        }
                    }
                    if (!existe) listaControles.add(obj)
                }
            }
        }
        // Cargar Incidentes
        persistencia?.list(PREFIJO_INC)?.forEach { key ->
            val bytes = persistencia!!.read(key)
            if (bytes != null) {
                val obj = textoAIncidente(bytes.decodeToString())
                if (obj != null) {
                    var existe = false
                    for (item in listaIncidentes) {
                        if (item.getId() == obj.getId()) {
                            existe = true
                            break
                        }
                    }
                    if (!existe) listaIncidentes.add(obj)
                }
            }
        }
        // Cargar Vacunas
        persistencia?.list(PREFIJO_VAC)?.forEach { key ->
            val bytes = persistencia!!.read(key)
            if (bytes != null) {
                val obj = textoAVacuna(bytes.decodeToString())
                if (obj != null) {
                    var existe = false
                    for (item in listaVacunas) {
                        if (item.getId() == obj.getId()) {
                            existe = true
                            break
                        }
                    }
                    if (!existe) listaVacunas.add(obj)
                }
            }
        }
        // Cargar Tratamientos
        persistencia?.list(PREFIJO_TRAT)?.forEach { key ->
            val bytes = persistencia!!.read(key)
            if (bytes != null) {
                val obj = textoATratamiento(bytes.decodeToString())
                if (obj != null) {
                    var existe = false
                    for (item in listaTratamientos) {
                        if (item.getId() == obj.getId()) {
                            existe = true
                            break
                        }
                    }
                    if (!existe) listaTratamientos.add(obj)
                }
            }
        }
    }

    // CONTROL
    private fun controlATexto(c: ControlVeterinario): String {
        val examenes = c.getNombreExamenes().joinToString(",")
        return "${c.getId()}$DELIMITADOR${c.getIdMascota()}$DELIMITADOR${c.getIdVeterinario()}$DELIMITADOR" +
                "${c.getFechaConsulta()}$DELIMITADOR${c.getMotivoControl()}$DELIMITADOR${c.getRecomendaciones()}$DELIMITADOR" +
                "${c.getNecesidadExamen()}$DELIMITADOR${c.getCantidadExamen()}$DELIMITADOR$examenes"
    }

    private fun textoAControl(data: String): ControlVeterinario? {
        val parts = data.split(DELIMITADOR)

        val id = parts.getOrNull(0)!!.toInt()
        val idMascota = parts.getOrNull(1)!!.toInt()
        val idVeterinario = parts.getOrNull(2)!!.toInt()

        val fechaStr = parts.getOrNull(3)
        val fecha = try { Instant.parse(fechaStr!!) } catch (_: Exception) { null }

        val motivo = parts.getOrNull(4)
        val recomendaciones = parts.getOrNull(5)
        val necesidadExamen = parts.getOrNull(6)!!.toBoolean()
        val cantidadExamen = parts.getOrNull(7)!!.toInt()
        val examenesStr = parts.getOrNull(8)

        if (fecha == null || motivo == null || recomendaciones == null) {
            return null
        }

        val listExamenes = if (examenesStr == null || examenesStr == "") emptyList() else examenesStr.split(",")

        return ControlVeterinario(id, idMascota, idVeterinario, fecha, motivo, recomendaciones, necesidadExamen, cantidadExamen, listExamenes)
    }

    // INCIDENTE
    private fun incidenteATexto(i: Incidente): String {
        return "${i.getId()}$DELIMITADOR${i.getIdMascota()}$DELIMITADOR${i.getIdVeterinario()}$DELIMITADOR" +
                "${i.getNombre()}$DELIMITADOR${i.getFecha()}$DELIMITADOR${i.getTipoIncidente()}$DELIMITADOR" +
                "${i.getDescripcion()}$DELIMITADOR${i.getGravedad()}$DELIMITADOR${i.getObservaciones()}"
    }

    private fun textoAIncidente(data: String): Incidente? {
        val parts = data.split(DELIMITADOR)

        val id = parts.getOrNull(0)!!.toInt()
        val idMascota = parts.getOrNull(1)!!.toInt()
        val idVeterinario = parts.getOrNull(2)!!.toInt()

        val nombre = parts.getOrNull(3)
        val fechaStr = parts.getOrNull(4)
        val fecha = try { Instant.parse(fechaStr!!) } catch (_: Exception) { null }

        val tipo = parts.getOrNull(5)
        val desc = parts.getOrNull(6)
        val gravedad = parts.getOrNull(7)
        val obs = parts.getOrNull(8)

        if (nombre == null || fecha == null || tipo == null || desc == null || gravedad == null || obs == null) {
            return null
        }
        return Incidente(id, idMascota, idVeterinario, nombre, fecha, tipo, desc, gravedad, obs)
    }

    // VACUNA
    private fun vacunaATexto(v: RegistroVacuna): String {
        return "${v.getId()}$DELIMITADOR${v.getIdMascota()}$DELIMITADOR${v.getIdVeterinario()}$DELIMITADOR" +
                "${v.getNombreVacuna()}$DELIMITADOR${v.getTipo()}$DELIMITADOR${v.getCantidadDosis()}$DELIMITADOR" +
                "${v.getFechaVacunacion()}$DELIMITADOR${v.getObservaciones()}"
    }

    private fun textoAVacuna(data: String): RegistroVacuna? {
        val parts = data.split(DELIMITADOR)

        val id = parts.getOrNull(0)!!.toInt()
        val idMascota = parts.getOrNull(1)!!.toInt()
        val idVeterinario = parts.getOrNull(2)!!.toInt()

        val nombre = parts.getOrNull(3)
        val tipo = parts.getOrNull(4)
        val dosis = parts.getOrNull(5)!!.toInt()

        val fechaStr = parts.getOrNull(6)
        val fecha = try { Instant.parse(fechaStr!!) } catch (_: Exception) { null }

        val obs = parts.getOrNull(7)

        if (nombre == null || tipo == null || fecha == null || obs == null) {
            return null
        }
        return RegistroVacuna(id, idMascota, idVeterinario, nombre, tipo, dosis, fecha, obs)
    }

    // TRATAMIENTO
    private fun tratamientoATexto(t: Tratamiento): String {
        val medicamentos = t.getMedicamentos().joinToString(",")

        return "${t.getId()}$DELIMITADOR${t.getIdMascota()}$DELIMITADOR${t.getIdVeterinario()}$DELIMITADOR" +
                "${t.getNombreTratamiento()}$DELIMITADOR${t.getRazonTratamiento()}$DELIMITADOR${t.getObservaciones()}$DELIMITADOR$medicamentos"
    }

    private fun textoATratamiento(data: String): Tratamiento? {
        val parts = data.split(DELIMITADOR)

        val id = parts.getOrNull(0)!!.toInt()
        val idMascota = parts.getOrNull(1)!!.toInt()
        val idVeterinario = parts.getOrNull(2)!!.toInt()

        val nombre = parts.getOrNull(3)
        val razon = parts.getOrNull(4)
        val obs = parts.getOrNull(5)
        val medicamentosStr = parts.getOrNull(6)

        if (nombre == null || razon == null || obs == null) {
            return null
        }
        val listMedicamentos = if (medicamentosStr.isNullOrEmpty()) emptyList() else medicamentosStr.split(",")

        return Tratamiento(id, idMascota, idVeterinario, nombre, razon, listMedicamentos, obs)
    }


    override fun anadirControlVeterinario(c: ControlVeterinario): ControlVeterinario {
        for (item in listaControles) {
            if (item.getId() == c.getId()) {
                println("Ya existe este Control")
                return item
            }
        }
        listaControles.add(c)
        println("Control creado correctamente.")

        persistencia?.let { p ->
            val key = "$PREFIJO_CTRL${c.getId()}"
            val data = controlATexto(c).encodeToByteArray()
            p.save(key, data)
        }
        return c
    }

    override fun anadirIncidente(i: Incidente): Incidente {
        for (item in listaIncidentes) {
            if (item.getId() == i.getId()) {
                println("Ya existe este Incidente")
                return item
            }
        }
        listaIncidentes.add(i)
        println("Incidente creado correctamente.")

        persistencia?.let { p ->
            val key = "$PREFIJO_INC${i.getId()}"
            val data = incidenteATexto(i).encodeToByteArray()
            p.save(key, data)
        }
        return i
    }

    override fun anadirRegistroVacuna(i: RegistroVacuna): RegistroVacuna {
        for (item in listaVacunas) {
            if (item.getId() == i.getId()) {
                println("Ya existe esta Vacuna")
                return item
            }
        }
        listaVacunas.add(i)
        println("Vacuna creada correctamente.")

        persistencia?.let { p ->
            val key = "$PREFIJO_VAC${i.getId()}"
            val data = vacunaATexto(i).encodeToByteArray()
            p.save(key, data)
        }
        return i
    }

    override fun anadirTratamiento(t: Tratamiento): Tratamiento {
        for (item in listaTratamientos) {
            if (item.getId() == t.getId()) {
                println("Ya existe este Tratamiento")
                return item
            }
        }
        listaTratamientos.add(t)
        println("Tratamiento creado correctamente.")

        persistencia?.let { p ->
            val key = "$PREFIJO_TRAT${t.getId()}"
            val data = tratamientoATexto(t).encodeToByteArray()
            p.save(key, data)
        }
        return t
    }


    override fun eliminarControlVeterinario(id: Int): Boolean {
        for (item in listaControles) {
            if (item.getId() == id) {
                listaControles.remove(item)
                println("Se ha eliminado el Control del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_CTRL$id"
                    p.delete(key)
                }
                return true
            }
        }
        return false
    }

    override fun eliminarIncidente(id: Int): Boolean {
        for (item in listaIncidentes) {
            if (item.getId() == id) {
                listaIncidentes.remove(item)
                println("Se ha eliminado el Incidente del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_INC$id"
                    p.delete(key)
                }
                return true
            }
        }
        return false
    }

    override fun eliminarRegistroVeterinario(id: Int): Boolean {
        for (item in listaVacunas) {
            if (item.getId() == id) {
                listaVacunas.remove(item)
                println("Se ha eliminado la Vacuna del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_VAC$id"
                    p.delete(key)
                }
                return true
            }
        }
        return false
    }

    override fun eliminarTratamiento(id: Int): Boolean {
        for (item in listaTratamientos) {
            if (item.getId() == id) {
                listaTratamientos.remove(item)
                println("Se ha eliminado el Tratamiento del repositorio.")

                persistencia?.let { p ->
                    val key = "$PREFIJO_TRAT$id"
                    p.delete(key)
                }
                return true
            }
        }
        return false
    }
}