package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.AppDropdown
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CardItem
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ComprobarDato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ExpandableMediaCard
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.AveDomestica
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Conejo
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Gato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Genero
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Hamster
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Perro
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioAnimal
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.UsuarioActual
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.time.Instant


enum class Type {AGREGAR, LISTADO}

@Composable
@Preview
@kotlin.time.ExperimentalTime
fun PaginaResumen() {
    var vistaActual by remember { mutableStateOf(Type.LISTADO) }

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        if (vistaActual == Type.AGREGAR) {
            AgregarMascota(
                onCancelar = { vistaActual = Type.LISTADO },
                onRegistroExitoso = { vistaActual = Type.LISTADO }
            )
        } else {
            Listado()

            Spacer(modifier = Modifier.height(20.dp))

            FloatingActionButton(
                onClick = { vistaActual = Type.AGREGAR },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Agregar Mascota")
            }
        }
    }
}

@kotlin.time.ExperimentalTime
@Composable
private fun Listado() {
    // Título de Bienvenida
    Text(
        text = "Bienvenido ${UsuarioActual.nombreActual}",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "Estas son las mascotas que tienes actualmente a tu cargo:",
        style = MaterialTheme.typography.bodyLarge
    )

    for (mascota in RepositorioAnimal.repositorio) {
        val sample = CardItem(
            mascota.getNombre(),
            "${mascota::class.simpleName}",
            mascota.getRaza(),
            "Genero: ${mascota.getGenero()} y Edad: ${mascota.getEdad()}"
        )
        ExpandableMediaCard(item = sample, onDeleteClick = { RepositorioAnimal.eliminarMascota(mascota.getId()) })
    }
}


@Composable
@kotlin.time.ExperimentalTime
@Preview
private fun AgregarMascota(onCancelar: () -> Unit, onRegistroExitoso: () -> Unit) {
    val scrollState = rememberScrollState()
    val tipo = listOf("Ave", "Conejo", "Gato", "Hamster", "Perro")
    val tgenero = listOf<Genero>(Genero.MACHO, Genero.HEMBRA)
    var nombre by remember {mutableStateOf("")}
    var edad by remember {mutableStateOf("")}
    var fechaNacimiento by remember {mutableStateOf("")}
    var raza by remember {mutableStateOf("")}
    var peso by remember {mutableStateOf("")}
    var especialstr by remember {mutableStateOf("")}
    var especialnum by remember {mutableStateOf(0.0)}
    var selectedtype by remember { mutableStateOf<String>(tipo[0]) }
    var selectedgenre by remember { mutableStateOf<Genero>(tgenero[0])}
    var error by remember {mutableStateOf<String?>(null)}

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registro Mascota", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))
        CampoTextField("Nombre Completo", value = nombre, onChange = { nombre = it })
        CampoTextField("Edad", value = edad, onChange = { edad = it })
        AppDropdown("Genero", tgenero, selectedgenre, onOptionSelected = {nuevogenero -> selectedgenre = nuevogenero})
        AppDropdown("Tipo de Animal", tipo, selectedtype, onOptionSelected = { nuevoTipo -> selectedtype = nuevoTipo })
        CampoTextField("Raza", raza, onChange = { raza = it })
        CampoTextField("Peso", peso, onChange = { peso = it })
        CampoTextField("Fecha de Nacimiento (AAAA-MM-DD) ", fechaNacimiento, onChange = { fechaNacimiento = it })
        when (selectedtype) {
            "Ave" -> {
                CampoTextField("Forma de Pico", especialstr, onChange = { especialstr = it })
            }
            "Conejo" -> {
                CampoTextField("Tipo de Oreja", especialstr, onChange = { especialstr = it })
            }
            "Gato" -> {
                CampoTextField("Longitud de Bigotes", especialstr, onChange = { especialstr = it })
            }
            "Hamster" -> {
                CampoTextField("Capacidad Abazones (grs)", especialstr, onChange = { especialstr = it })
            }
            "Perro" -> {
                CampoTextField("Tipo de Hocico", especialstr, onChange = { especialstr = it })
            }
        }
        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(onClick = {
            try {
                if (nombre.isEmpty() || edad.isEmpty() || fechaNacimiento.isEmpty() || peso.isEmpty() || raza.isEmpty() || especialstr.isEmpty()) {
                    error = "Rellene todos los campos"
                } else if (!(ComprobarDato(nombre, "nonum") xor (!(ComprobarDato(edad, "onlynum") or !(ComprobarDato(peso, "onlynum")))))) {
                    error = "El nombre solo letras o Solo rellene con lo solicitado"
                } else {
                    error = null
                    val idAnimal = Random.nextInt(99999)
                    val fechaNacimientoo = Instant.parse("${fechaNacimiento}T00:00:00Z")
                    when (selectedtype) {
                        "Ave" -> {
                            RepositorioAnimal.crearMascota(
                                AveDomestica(
                                    idAnimal,
                                    UsuarioActual.usuarioActual!!,
                                    nombre,
                                    edad.toInt(),
                                    fechaNacimientoo,
                                    selectedgenre,
                                    raza,
                                    peso.toDouble(),
                                    especialstr
                                )
                            )
                        }

                        "Conejo" -> {
                            RepositorioAnimal.crearMascota(
                                Conejo(
                                    idAnimal,
                                    UsuarioActual.usuarioActual!!,
                                    nombre,
                                    edad.toInt(),
                                    fechaNacimientoo,
                                    selectedgenre,
                                    raza,
                                    peso.toDouble(),
                                    especialstr
                                )
                            )
                        }

                        "Gato" -> {
                            RepositorioAnimal.crearMascota(
                                Gato(
                                    idAnimal,
                                    UsuarioActual.usuarioActual!!,
                                    nombre,
                                    edad.toInt(),
                                    fechaNacimientoo,
                                    selectedgenre,
                                    raza,
                                    peso.toDouble(),
                                    especialnum.toFloat()
                                )
                            )
                        }

                        "Hamster" -> {
                            RepositorioAnimal.crearMascota(
                                Hamster(
                                    idAnimal,
                                    UsuarioActual.usuarioActual!!,
                                    nombre,
                                    edad.toInt(),
                                    fechaNacimientoo,
                                    selectedgenre,
                                    raza,
                                    peso.toDouble(),
                                    especialnum.toFloat()
                                )
                            )
                        }

                        "Perro" -> {
                            RepositorioAnimal.crearMascota(
                                Perro(
                                    idAnimal,
                                    UsuarioActual.usuarioActual!!,
                                    nombre,
                                    edad.toInt(),
                                    fechaNacimientoo,
                                    selectedgenre,
                                    raza,
                                    peso.toDouble(),
                                    especialstr
                                )
                            )
                        }
                    }
                    onRegistroExitoso()
                }
            }
            catch (e: Exception) {
                if (e.toString().contains("java.lang.NumberFormatException")) {
                    error = "Colocaste letras donden no debias"
                }
                else {
                    error = e.toString()
                }

            }
        }
            ) {
            Text("Registrarse")

        }
        TextButton(onClick = {onCancelar()}
        ) { Text("Cancelar") }
    }
}