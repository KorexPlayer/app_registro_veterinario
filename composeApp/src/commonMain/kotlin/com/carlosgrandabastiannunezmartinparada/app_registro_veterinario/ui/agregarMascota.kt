package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.AppDropdown
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoPasswordField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ComprobarDato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Genero
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Dueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioDueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.UsuarioActual
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.time.Instant
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
@kotlin.time.ExperimentalTime
@Preview
fun AgregarMascota(
    //onRegresar: () -> Unit
) {
    var tipo = listOf("Ave", "Conejo", "Gato", "Hamster", "Perro")
    var tgenero = listOf<Genero>(Genero.MACHO, Genero.HEMBRA)
    var nombre by remember {mutableStateOf("")}
    var edad by remember {mutableStateOf("")}
    var fechaNacimiento by remember {mutableStateOf("")}
    var genero by remember {mutableStateOf(Genero.MACHO)}
    var raza by remember {mutableStateOf("")}
    var peso by remember {mutableStateOf("")}
    val especial by remember {mutableStateOf("")}
    var selectedtype by remember { mutableStateOf<String>(tipo[0]) }
    var selectedgenre by remember { mutableStateOf<Genero>(tgenero[0])}
    var error by remember {mutableStateOf<String?>(null)}
    //val idDueno = RepositorioDueno.obtenerPorRut(UsuarioActual.usuarioActual!!)!!.getId()

    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Registro Mascota", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))
        CampoTextField("Nombre Completo", value = nombre, onChange = { nombre = it })
        CampoTextField("Edad", value = edad.toString(), onChange = { edad = it })
        AppDropdown("Genero", tgenero, selectedgenre, onOptionSelected = {nuevogenero -> genero = nuevogenero})
        AppDropdown("Tipo de Animal", tipo, selectedtype, onOptionSelected = { nuevoTipo -> selectedtype = nuevoTipo })
        CampoTextField("Raza", raza, onChange = { raza = it })
        CampoTextField("Peso", peso.toString(), onChange = { peso = it })
        CampoTextField("Fecha de Nacimiento (AAAA-MM-DD) ", fechaNacimiento, onChange = { fechaNacimiento = it })
//        when (selectedtype {
//            "Ave" -> {
//
//            }
//            "Conejo" -> {
//
//            }
//            "Gato" -> {
//
//            }
//            "Hamster" -> {
//
//            }
//            "Perro" -> {
//
//            }
//        }
            error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(onClick = {
            if(false) {
                error = "Rellene todos los campos"
            }
            else if(!(ComprobarDato(nombre, "nonum"))) {
                error = "El nombre solo letras o El correo debe ser de tipo xxxx@xxx.xxx"
            }

            else {
                /*error = null
                val id = Random.nextInt(99999)
                if(RepositorioDueno.obtenerPorRut(rut) == null && !(RepositorioDueno.repositorio.any() {it.getId() == id})) {
                    val nuevodueno = Dueno(id = id, nombreCompleto = nombre, telefono = telefono, email = email, direccion = direccion, rut = rut, contrasena = password)
                    RepositorioDueno.crearDueno(nuevodueno)
                    onRegresar()
                }
                else {
                    error = "Ya existe el usuario"
                }*/

            }
        }) {
            Text("Registrarse")

        }
        TextButton(onClick = {nombre = nombre}
            //onRegresar
        ) { Text("Cancelar") }
    }
}