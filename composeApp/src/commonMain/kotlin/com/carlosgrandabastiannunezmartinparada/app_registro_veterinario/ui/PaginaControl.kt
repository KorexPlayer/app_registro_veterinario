package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Instant

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioAnimal
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioVeterinarios

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun PaginaControl() {

    var pantallaActual by remember { mutableStateOf("lista") }
    var mascotaSeleccionada by remember { mutableStateOf<Int?>(null) }

    when (pantallaActual) {
        "lista" -> PantallaListaControles(
            mascotaSeleccionada = mascotaSeleccionada,
            onMascotaSeleccionada = { mascotaSeleccionada = it },
            onAgregarControlClick = { pantallaActual = "formulario" }
        )

        "formulario" -> PantallaFormularioControl(
            mascotaId = mascotaSeleccionada,
            onVolver = { pantallaActual = "lista" }
        )
    }
}

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun PantallaListaControles(
    mascotaSeleccionada: Int?,
    onMascotaSeleccionada: (Int) -> Unit,
    onAgregarControlClick: () -> Unit
) {

    val mascotas = RepositorioAnimal.repositorio

    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Registro de Controles", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        Text("Seleccionar Mascota:")
        DropdownMenuMascotas(
            mascotas = mascotas,
            onSelect = { onMascotaSeleccionada(it) }
        )

        Spacer(Modifier.height(20.dp))

        if (mascotaSeleccionada != null) {

            val mascota = mascotas.first { it.getId() == mascotaSeleccionada }

            Text("Controles registrados:")
            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(mascota.listadoControlVeterinario) { control ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Fecha: ${control.getFechaConsulta().toString().substringBefore("T")}")
                            Text("Motivo: ${control.getMotivoControl()}")
                            Text("Veterinario: ${RepositorioVeterinarios.obtenerPorId(control.getIdVeterinario()) ?.getNombre()}")
                            Text("Recomendaciones: ${control.getRecomendaciones()}")
                            if (control.getNecesidadExamen()) {
                                Text("Exámenes: ${control.getNombreExamenes().joinToString()}")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onAgregarControlClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Nuevo Control")
            }
        }
    }
}

@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun PantallaFormularioControl(
    mascotaId: Int?,
    onVolver: () -> Unit
) {
    if (mascotaId == null) {
        onVolver()
        return
    }

    val mascotas = RepositorioAnimal.repositorio
    val mascota = mascotas.first { it.getId() == mascotaId }

    val scope = rememberCoroutineScope()

    var motivo by remember { mutableStateOf("") }
    var recomendaciones by remember { mutableStateOf("") }
    var fechaStr by remember { mutableStateOf("") }
    var veterinarioStr by remember { mutableStateOf("") }

    var necesidadExamen by remember { mutableStateOf(false) }
    var cantidadExamen by remember { mutableStateOf("0") }
    var examenActual by remember { mutableStateOf("") }
    var listaExamenes by remember { mutableStateOf(listOf<String>()) }

    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Text("Nuevo Control Veterinario", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            item {
                OutlinedTextField(
                    value = motivo,
                    onValueChange = { motivo = it },
                    label = { Text("Motivo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                OutlinedTextField(
                    value = recomendaciones,
                    onValueChange = { recomendaciones = it },
                    label = { Text("Recomendaciones") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                OutlinedTextField(
                    value = fechaStr,
                    onValueChange = { fechaStr = it },
                    label = { Text("Fecha (AAAA-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                OutlinedTextField(
                    value = veterinarioStr,
                    onValueChange = { veterinarioStr = it },
                    label = { Text("Veterinario") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
            }

            item {
                Row {
                    Checkbox(
                        checked = necesidadExamen,
                        onCheckedChange = { necesidadExamen = it }
                    )
                    Text("¿Necesita exámenes?")
                }
                Spacer(Modifier.height(12.dp))
            }

            if (necesidadExamen) {
                item {
                    OutlinedTextField(
                        value = cantidadExamen,
                        onValueChange = { cantidadExamen = it },
                        label = { Text("Cantidad de exámenes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    Row {
                        OutlinedTextField(
                            value = examenActual,
                            onValueChange = { examenActual = it },
                            label = { Text("Añadir Examen") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = {
                            if (examenActual.isNotBlank()) {
                                listaExamenes = listaExamenes + examenActual
                                examenActual = ""
                            }
                        }) { Text("Agregar") }
                    }
                }

                items(listaExamenes) { exam ->
                    Text("- $exam")
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch(Dispatchers.Default) {

                    val veterinario = RepositorioVeterinarios.listar().find {
                        it.getNombre().equals(veterinarioStr, ignoreCase = true)
                    }
                    if (veterinario == null) {
                        mensaje = "Nombre de veterinario invalido."
                        return@launch
                    }

                    val fecha = try {
                        // Formato ISO completo
                        Instant.parse(fechaStr)
                    } catch (e1: Exception) {
                        // Agrega hora por defecto T00:00:00Z
                        try {
                            Instant.parse("${fechaStr}T00:00:00Z")
                        } catch (e2: Exception) {
                            mensaje = "Fecha inválida."
                            return@launch
                    }
                    }

                    mascota.agregarControl(
                        id = Random.nextInt(99999),
                        idMascota = mascota.getId(),
                        idVeterinario = veterinario.getId(),
                        fechaConsulta = fecha,
                        motivoControl = motivo,
                        recomendaciones = recomendaciones,
                        necesidadExamen = necesidadExamen,
                        CantidadExamen = cantidadExamen.toIntOrNull() ?: 0,
                        nombreExamenes = listaExamenes
                    )

                    mensaje = "Control agregado correctamente."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Control")
        }

        Spacer(Modifier.height(10.dp))

        Text(mensaje, color = MaterialTheme.colorScheme.error)

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Volver") }
    }
}



@OptIn(kotlin.time.ExperimentalTime::class)
@Composable
fun DropdownMenuMascotas(
    mascotas: List<com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota>,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLabel by remember { mutableStateOf("Seleccione una mascota") }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedLabel)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            mascotas.forEach { mascota ->
                DropdownMenuItem(
                    text = { Text("${mascota.getNombre()} (ID: ${mascota.getId()})") },
                    onClick = {
                        selectedLabel = mascota.getNombre()
                        expanded = false
                        onSelect(mascota.getId())
                    }
                )
            }
        }
    }
}