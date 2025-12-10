package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person2
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CardItem
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ComprobarDato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ExpandableMediaCard
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Veterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioVeterinarios
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

 enum class TypeV {AGREGAR, LISTADO}

@Composable
@Preview
fun VeterinariosVisitados() {
    var vistaActual by remember { mutableStateOf(TypeV.LISTADO) }
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
    ) {

        Text("Contactos de los Veterinarios", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))

        if (vistaActual == TypeV.AGREGAR) {
            AgregarVeterinario(
                onRegresar = { vistaActual = TypeV.LISTADO }
            )
        }
        else {
            for (veterinarios in RepositorioVeterinarios.listar()) {
                val sampleItem = CardItem(
                    title = veterinarios.getNombre(),
                    type = veterinarios.getEspecialidad(),
                    veterinarios.getHorarioAtencion(),
                    veterinarios.getDescripcionServicio() + "Numero Telfonico: ${veterinarios.getTelefono()} y Correo Electronico: ${veterinarios.getEmail()}, Nos pueden encontrar en: ${veterinarios.getDireccion()}")
                ExpandableMediaCard(
                    item = sampleItem,
                    onDeleteClick = { RepositorioVeterinarios.eliminarVeterinario(veterinarios.getId())},
                    icon = Icons.Default.Person2,
                    tinte = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            FloatingActionButton(
                onClick = { vistaActual = TypeV.AGREGAR },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Agregar Mascota")
            }
        }
    }
}

private @Composable
@Preview
fun AgregarVeterinario (
    onRegresar: () -> Unit
) {
    var descServicio by remember {mutableStateOf("")}
    var especialidad by remember {mutableStateOf("")}
    var horarioAtencion by remember {mutableStateOf("")}
    var nombre by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var direccion by remember {mutableStateOf("")}
    var telefono by remember {mutableStateOf("")}
    var error by remember {mutableStateOf<String?>(null)}
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.padding(10.dp).verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Registro", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))
        CampoTextField("Nombre Completo", value = nombre, onChange = { nombre = it })
        CampoTextField("Correo Electronico (xxxx@xxx.com)", value = email, onChange = { email = it })
        CampoTextField("Direccion", value = direccion, onChange = { direccion = it })
        CampoTextField("Numero de Telefono (Solo Numero: 987654321)", value = telefono, onChange = { telefono = it })
        CampoTextField("Especialidad", value = especialidad, onChange = { especialidad = it })
        CampoTextField("Horario de Atencion", value = horarioAtencion, onChange = { horarioAtencion = it })
        CampoTextField("Descripcion del Servicio", value = descServicio, onChange = { descServicio = it }, modifier1 = Modifier.padding(5.dp).height(50.dp))
        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(onClick = {
            try {
                if (especialidad.isEmpty() || nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || descServicio.isEmpty() || horarioAtencion.isEmpty()) {
                    error = "Rellene todos los campos"
                }
                else if (!ComprobarDato(nombre, "nonum")) {
                    error = "El nombre solo puede contener letras"
                }
                else if (!ComprobarDato(email, "correo")) {
                    error = "El correo debe ser de tipo xxxx@xxx.xxx"
                }
                else if (!ComprobarDato(telefono, "onlynum")) {
                    error = "El numero de telefono solo puede ser: 987654321"
                    }
                else {
                    error = null
                    val id = Random.nextInt(99999)
                    if(( if (RepositorioVeterinarios.listar().any() { it.getNombre() == nombre}) false else true )) {
                        val nuevoveterinario = Veterinario(
                            id = id,
                            nombreCompleto = nombre,
                            telefono = telefono,
                            email = email,
                            direccion = direccion,
                            especialidad = especialidad,
                            descripcionServicio = descServicio,
                            horarioAtencion = horarioAtencion
                        )
                        RepositorioVeterinarios.crearVeterinario(nuevoveterinario)
                        onRegresar()
                    }
                    else {
                        error = "Ya existe el Veterinario"
                    }
                }
            } catch (e: Exception) {
                if (e.toString().contains("java.lang.NumberFormatException")) {
                    error = "Hay letras en algun dato que solo recibe numeros."
                } else {
                    error = e.toString()
                }
            }
        }
        ) {
            Text("Registrar nuevo Veterinario")

        }
        TextButton(onClick = onRegresar) { Text("Cancelar") }
    }
}