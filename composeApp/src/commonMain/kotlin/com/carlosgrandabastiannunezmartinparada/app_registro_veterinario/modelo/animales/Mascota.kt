package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.ControlVeterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Incidente
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.RegistroVacuna
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.Tratamiento
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioObjetoVeterinario
import kotlin.time.Instant
@kotlin.time.ExperimentalTime
abstract class Mascota (
    private val id: Int,
    private val idDueno: Int,
    private val nombre: String,
    private val edad: Int,
    private val fechaNacimiento: Instant,
    private val genero: Genero,
    private val raza: String,
    private val peso: Double

) {
    val listadoControlVeterinario = mutableListOf<ControlVeterinario>()
    val listadoIncidente = mutableListOf<Incidente>()
    val listadoRegistroVacuna = mutableListOf<RegistroVacuna>()
    val listadoTratamiento = mutableListOf<Tratamiento>()

    fun agregarControl(  id: Int,  idMascota: Int,  idVeterinario: Int,  fechaConsulta : Instant,  motivoControl : String,  recomendaciones : String,  necesidadExamen : Boolean,  CantidadExamen : Int,  nombreExamenes : List<String>) {
        val control = ControlVeterinario(id, idMascota, idVeterinario, fechaConsulta, motivoControl, recomendaciones, necesidadExamen, CantidadExamen, nombreExamenes)
        listadoControlVeterinario.add(control)
        RepositorioObjetoVeterinario.anadirControlVeterinario(control)
    }
    fun agregarIncidente( id: Int, idMascota: Int, idVeterinario: Int, nombre: String, fecha: Instant, tipoIncidente: String, descripcion: String, gravedad: String, observaciones: String) {
        val incidente = Incidente(id, idMascota, idVeterinario, nombre, fecha, tipoIncidente, descripcion, gravedad, observaciones)
        listadoIncidente.add(incidente)
        RepositorioObjetoVeterinario.anadirIncidente(incidente)
    }
    fun agregarRegistro(id : Int, idMascota: Int, idVeterinario: Int, nombreVacuna : String, tipo : String, cantidadDosis : Int, fechaVacunacion : Instant, observaciones : String) {
        val registro = RegistroVacuna(id, idMascota, idVeterinario, nombreVacuna, tipo, cantidadDosis, fechaVacunacion, observaciones)
        listadoRegistroVacuna.add(registro)
        RepositorioObjetoVeterinario.anadirRegistroVacuna(registro)
    }
    fun agregarTratamiento(id : Int, idMascota: Int, idVeterinario: Int, nombreTratamiento: String, razonTratamiento: String, Medicamentos: List<List<String>>, observaciones : String) {
        val tratamiento = Tratamiento(id, idMascota, idVeterinario, nombreTratamiento, razonTratamiento, Medicamentos, observaciones)
        listadoTratamiento.add(tratamiento)
        RepositorioObjetoVeterinario.anadirTratamiento(tratamiento)

    }
    //Getter
    fun getId() = id
    fun getIdDueno() = idDueno
    fun getNombre() = nombre
    fun getEdad() = edad
    fun getFechaNacimiento() = fechaNacimiento
    fun getGenero() = genero
    fun getRaza() = raza
    fun getPeso() = peso
}