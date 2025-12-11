package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.AppDropdown
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CardItem
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ExpandableMediaCard
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.AveDomestica
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Conejo
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Gato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Hamster
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Mascota
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.animales.Perro
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Veterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioAnimal
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioObjetoVeterinario
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioVeterinarios
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

private enum class VistaTratamiento { AGREGAR, LISTADO }

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun PaginaTratamientos() {
    var vistaActual by remember { mutableStateOf(VistaTratamiento.LISTADO) }

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        if (vistaActual == VistaTratamiento.AGREGAR) {
            AgregarTratamiento(
                onCancelar = { vistaActual = VistaTratamiento.LISTADO },
                onRegistroExitoso = { vistaActual = VistaTratamiento.LISTADO }
            )
        } else {
            FloatingActionButton(
                onClick = { vistaActual = VistaTratamiento.AGREGAR },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Registrar Tratamientos")
            }

            Spacer(modifier = Modifier.height(20.dp))

            ListadoTratamientos()
        }
    }
}

@kotlin.time.ExperimentalTime
@Composable
private fun ListadoTratamientos() {
    val scrollState = rememberScrollState()

    val tiposAnimales = listOf("Todos", "Perro", "Gato", "Conejo", "Hamster", "Ave")
    var filtroTipo by remember { mutableStateOf("Todos") }

    val mascotasDisponibles by remember(filtroTipo) {
        derivedStateOf {
            val filtradas = if (filtroTipo == "Todos") {
                RepositorioAnimal.repositorio
            } else {
                RepositorioAnimal.repositorio.filter { mascota ->
                    when (filtroTipo) {
                        "Perro" -> mascota is Perro
                        "Gato" -> mascota is Gato
                        "Conejo" -> mascota is Conejo
                        "Hamster" -> mascota is Hamster
                        "Ave" -> mascota is AveDomestica
                        else -> true
                    }
                }
            }
            filtradas.sortedBy { it.getNombre() }
        }
    }

    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }

    LaunchedEffect(mascotasDisponibles) {
        if (mascotaSeleccionada != null && !mascotasDisponibles.contains(mascotaSeleccionada)) {
            mascotaSeleccionada = null
        }
        if (mascotaSeleccionada == null && mascotasDisponibles.isNotEmpty()) {
            mascotaSeleccionada = mascotasDisponibles.first()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Tratamientos y Recetas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 5.dp)
        )
        Text(
            text = "Revise los tratamientos de su mascota:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            AppDropdown(
                label = "Filtrar por Tipo",
                options = tiposAnimales,
                selectedOption = filtroTipo,
                onOptionSelected = { filtroTipo = it }
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (mascotasDisponibles.isNotEmpty()) {
                val actual = mascotaSeleccionada ?: mascotasDisponibles.first()
                AppDropdown(
                    label = "Seleccionar Mascota",
                    options = mascotasDisponibles,
                    selectedOption = actual,
                    onOptionSelected = { mascotaSeleccionada = it },
                    itemLabel = { it.getNombre() }
                )
            } else {
                Text("No hay mascotas registradas de este tipo.", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mascotaSeleccionada != null) {
            val tratamientosDeMascota = RepositorioObjetoVeterinario.listaTratamientos.filter {
                it.getIdMascota() == mascotaSeleccionada!!.getId()
            }

            if (tratamientosDeMascota.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
                    Text("No hay tratamientos activos para ${mascotaSeleccionada!!.getNombre()}.", color = Color.Gray)
                }
            } else {
                Text(
                    text = "Recetas de ${mascotaSeleccionada!!.getNombre()} (${tratamientosDeMascota.size}):",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                for (tratamiento in tratamientosDeMascota) {
                    val vet = RepositorioVeterinarios.obtenerPorId(tratamiento.getIdVeterinario())
                    val nombreVet = vet?.getNombre() ?: "No especificado"

                    val medicamentosStr = if (tratamiento.getMedicamentos().isEmpty()) "Ninguno"
                    else tratamiento.getMedicamentos().joinToString("\n") { med ->
                        val partes = med.split(", ")
                        if (partes.size >= 4) {
                            "- ${partes[0]}\n  ${partes[1]}\n  ${partes[2]}\n  ${partes[3]}"
                        } else {
                            "- $med" }
                    }

                    val itemCard = CardItem(
                        title = tratamiento.getNombreTratamiento(),
                        type = "Diagnóstico: ${tratamiento.getRazonTratamiento()}",
                        status = "Ver medicamentos al expandir",
                        extraInfo = "Medicamentos:\n$medicamentosStr\n\n" +
                                "Veterinario: $nombreVet\n" +
                                "Observaciones: ${tratamiento.getObservaciones()}"
                    )

                    ExpandableMediaCard(
                        item = itemCard,
                        onDeleteClick = {
                            RepositorioObjetoVeterinario.eliminarTratamiento(tratamiento.getId())
                        },
                        icon = Icons.Default.Delete,
                        tinte = Color.Red
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@kotlin.time.ExperimentalTime
@Composable
private fun AgregarTratamiento(onCancelar: () -> Unit, onRegistroExitoso: () -> Unit) {
    val scrollState = rememberScrollState()

    val tiposAnimales = listOf("Perro", "Gato", "Conejo", "Hamster", "Ave")
    var tipoSeleccionado by remember { mutableStateOf(tiposAnimales[0]) }

    val todasLasMascotas = RepositorioAnimal.repositorio
    val mascotasFiltradas by remember(tipoSeleccionado, todasLasMascotas) {
        derivedStateOf {
            todasLasMascotas.filter { mascota ->
                when (tipoSeleccionado) {
                    "Perro" -> mascota is Perro
                    "Gato" -> mascota is Gato
                    "Conejo" -> mascota is Conejo
                    "Hamster" -> mascota is Hamster
                    "Ave" -> mascota is AveDomestica
                    else -> false
                }
            }.sortedBy { it.getNombre() }
        }
    }

    var selectedMascota by remember { mutableStateOf<Mascota?>(null) }
    LaunchedEffect(mascotasFiltradas) {
        if (mascotasFiltradas.isNotEmpty()) {
            if (selectedMascota == null || !mascotasFiltradas.contains(selectedMascota)) {
                selectedMascota = mascotasFiltradas.first()
            }
        } else {
            selectedMascota = null
        }
    }

    val listaVeterinarios by remember {
        derivedStateOf { RepositorioVeterinarios.repositorio.sortedBy { it.getNombre() } }
    }
    var selectedVeterinario by remember { mutableStateOf<Veterinario?>(null) }
    LaunchedEffect(listaVeterinarios) {
        if (selectedVeterinario == null && listaVeterinarios.isNotEmpty()) {
            selectedVeterinario = listaVeterinarios.first()
        }
    }

    var nombreTratamiento by remember { mutableStateOf("") }
    var razonTratamiento by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var medNombre by remember { mutableStateOf("") }
    var medDosis by remember { mutableStateOf("") }
    var medFrecuencia by remember { mutableStateOf("") }
    var medDuracion by remember { mutableStateOf("") }
    var listaMedicamentos by remember { mutableStateOf(listOf<String>()) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Nuevo Tratamiento", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))

        AppDropdown(
            label = "Tipo de Animal",
            options = tiposAnimales,
            selectedOption = tipoSeleccionado,
            onOptionSelected = { tipoSeleccionado = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (mascotasFiltradas.isNotEmpty()) {
            val actual = selectedMascota ?: mascotasFiltradas.first()
            AppDropdown(
                label = "Nombre de la Mascota",
                options = mascotasFiltradas,
                selectedOption = actual,
                onOptionSelected = { selectedMascota = it },
                itemLabel = { it.getNombre() }
            )
        } else {
            Text("No tienes $tipoSeleccionado registrados.", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))


        if (listaVeterinarios.isNotEmpty()) {
            val vetActual = selectedVeterinario ?: listaVeterinarios.first()
            AppDropdown(
                label = "Veterinario Responsable",
                options = listaVeterinarios,
                selectedOption = vetActual,
                onOptionSelected = { selectedVeterinario = it },
                itemLabel = { it.getNombre() }
            )
        } else {
            Text("No hay veterinarios registrados.", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))

        CampoTextField("Nombre Tratamiento", nombreTratamiento, { nombreTratamiento = it })
        CampoTextField("Diagnóstico / Razón", razonTratamiento, { razonTratamiento = it })

        Divider(modifier = Modifier.padding(vertical = 10.dp))
        Text("Agregar Medicamento:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        CampoTextField("Nombre Medicamento", medNombre, { medNombre = it })

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = medDosis,
                onValueChange = { medDosis = it },
                label = { Text("Dosis (ej: 10mg)") },
                modifier = Modifier.weight(1f).padding(end = 4.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = medFrecuencia,
                onValueChange = { medFrecuencia = it },
                label = { Text("Frec. (ej: 8 hrs)") },
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = medDuracion,
                onValueChange = { medDuracion = it },
                label = { Text("Duración (ej: 5 días)") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            IconButton(
                onClick = {
                    if (medNombre.isNotEmpty() && medDosis.isNotEmpty()) {
                        val medicamentoFormateado = "$medNombre, $medDosis, $medFrecuencia, $medDuracion"

                        listaMedicamentos = listaMedicamentos + medicamentoFormateado
                        medNombre = ""
                        medDosis = ""
                        medFrecuencia = ""
                        medDuracion = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar a lista", tint = MaterialTheme.colorScheme.primary)
            }
        }
        Text("Presiona + para añadir Medicamentos", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)

        if (listaMedicamentos.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Medicamentos añadidos:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    listaMedicamentos.forEachIndexed { index, med ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("• $med", fontSize = 14.sp, modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = {
                                    val nuevaLista = listaMedicamentos.toMutableList()
                                    nuevaLista.removeAt(index)
                                    listaMedicamentos = nuevaLista
                                },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Borrar", tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
        Divider(modifier = Modifier.padding(vertical = 10.dp))
        CampoTextField("Observaciones Generales", observaciones, { observaciones = it })
        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            if (nombreTratamiento.isEmpty() || razonTratamiento.isEmpty()) {
                error = "El nombre del tratamiento y el diagnóstico son obligatorios."
            } else if (selectedMascota == null) {
                error = "Seleccione una mascota válida."
            } else if (listaVeterinarios.isNotEmpty() && selectedVeterinario == null) {
                error = "Seleccione un veterinario."
            } else if (listaMedicamentos.isEmpty()) {
                error = "Debe agregar al menos un medicamento a la lista."
            } else {
                error = null
                val idNuevo = Random.nextInt(999999)

                selectedMascota!!.agregarTratamiento(
                    id = idNuevo,
                    idMascota = selectedMascota!!.getId(),
                    idVeterinario = selectedVeterinario?.getId() ?: 0,
                    nombreTratamiento = nombreTratamiento,
                    razonTratamiento = razonTratamiento,
                    Medicamentos = listaMedicamentos,
                    observaciones = if (observaciones.isEmpty()) "Sin observaciones" else observaciones
                )

                onRegistroExitoso()
            }
        }) {
            Text("Guardar Tratamiento")
        }

        TextButton(onClick = { onCancelar() }) {
            Text("Cancelar")
        }
    }
}