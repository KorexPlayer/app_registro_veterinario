package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import kotlin.time.Instant

private enum class VistaIncidente { AGREGAR, LISTADO }

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun PaginaIncidentes() {
    var vistaActual by remember { mutableStateOf(VistaIncidente.LISTADO) }

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        if (vistaActual == VistaIncidente.AGREGAR) {
            AgregarIncidente(
                onCancelar = { vistaActual = VistaIncidente.LISTADO },
                onRegistroExitoso = { vistaActual = VistaIncidente.LISTADO }
            )
        } else {
            FloatingActionButton(
                onClick = { vistaActual = VistaIncidente.AGREGAR },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Registrar Nuevo Incidente")
            }

            Spacer(modifier = Modifier.height(20.dp))

            ListadoIncidentes()
        }
    }
}

@kotlin.time.ExperimentalTime
@Composable
private fun ListadoIncidentes() {
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
            text = "Historial de Incidentes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 5.dp)
        )
        Text(
            text = "Busca a tu mascota para ver sus incidentes:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
        )

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            AppDropdown(
                label = "Filtrar por Tipo de Animal",
                options = tiposAnimales,
                selectedOption = filtroTipo,
                onOptionSelected = { filtroTipo = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (mascotasDisponibles.isNotEmpty()) {
                val seleccionActual = mascotaSeleccionada ?: mascotasDisponibles.first()
                AppDropdown(
                    label = "Seleccionar Mascota",
                    options = mascotasDisponibles,
                    selectedOption = seleccionActual,
                    onOptionSelected = { mascotaSeleccionada = it },
                    itemLabel = { it.getNombre() }
                )
            } else {
                Text("No hay mascotas de este tipo registradas.", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mascotaSeleccionada != null) {
            val incidentesDeMascota = RepositorioObjetoVeterinario.listaIncidentes.filter {
                it.getIdMascota() == mascotaSeleccionada!!.getId()
            }

            if (incidentesDeMascota.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
                    Text("No hay incidentes registrados para ${mascotaSeleccionada!!.getNombre()}.", color = Color.Gray)
                }
            } else {
                Text(
                    text = "Incidentes de ${mascotaSeleccionada!!.getNombre()} (${incidentesDeMascota.size}):",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.error
                )

                for (incidente in incidentesDeMascota) {
                    val vetEncontrado = RepositorioVeterinarios.obtenerPorId(incidente.getIdVeterinario())
                    val nombreVet = vetEncontrado?.getNombre() ?: "No especificado"

                    val itemIncidente = CardItem(
                        title = incidente.getNombre(),
                        type = "Tipo: ${incidente.getTipoIncidente()} (${incidente.getGravedad()})",
                        status = "Fecha: ${incidente.getFecha().toString().substringBefore('T')}",
                        extraInfo = "Descripción: ${incidente.getDescripcion()}\n" +
                                "Atendido por: $nombreVet\n" +
                                "Observaciones: ${incidente.getObservaciones()}"
                    )

                    ExpandableMediaCard(
                        item = itemIncidente,
                        onDeleteClick = {
                            RepositorioObjetoVeterinario.eliminarIncidente(incidente.getId())
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
private fun AgregarIncidente(onCancelar: () -> Unit, onRegistroExitoso: () -> Unit) {
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

    val gravedades = listOf("Leve", "Moderada", "Grave", "Crítica")
    var gravedadSeleccionada by remember { mutableStateOf(gravedades[0]) }

    var nombreIncidente by remember { mutableStateOf("") }
    var fechaIncidente by remember { mutableStateOf("") }
    var tipoIncidente by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registrar Incidente", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))

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
                label = "Mascota Afectada",
                options = mascotasFiltradas,
                selectedOption = actual,
                onOptionSelected = { selectedMascota = it },
                itemLabel = { it.getNombre() }
            )
        } else {
            Text("No tienes $tipoSeleccionado registrados.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (listaVeterinarios.isNotEmpty()) {
            val vetActual = selectedVeterinario ?: listaVeterinarios.first()
            AppDropdown(
                label = "Veterinario",
                options = listaVeterinarios,
                selectedOption = vetActual,
                onOptionSelected = { selectedVeterinario = it },
                itemLabel = { it.getNombre() }
            )
        } else {
            Text("No hay veterinarios registrados.", color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        CampoTextField("Título (Ej: Caída)", value = nombreIncidente, onChange = { nombreIncidente = it })
        CampoTextField("Tipo (Ej: Accidente, Enfermedad)", value = tipoIncidente, onChange = { tipoIncidente = it })
        CampoTextField("Fecha (AAAA-MM-DD)", value = fechaIncidente, onChange = { fechaIncidente = it })

        AppDropdown(
            label = "Nivel de Gravedad",
            options = gravedades,
            selectedOption = gravedadSeleccionada,
            onOptionSelected = { gravedadSeleccionada = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        CampoTextField("Descripción Detallada", value = descripcion, onChange = { descripcion = it })
        CampoTextField("Observaciones", value = observaciones, onChange = { observaciones = it })

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            try {
                if (nombreIncidente.isEmpty() || tipoIncidente.isEmpty() || fechaIncidente.isEmpty() || descripcion.isEmpty()) {
                    error = "Rellene los campos obligatorios."
                }
                else if (selectedMascota == null) {
                    error = "Debe seleccionar una mascota válida."
                }
                else {
                    error = null
                    val idNuevo = Random.nextInt(999999)
                    val fechaFinal = Instant.parse("${fechaIncidente}T00:00:00Z")

                    selectedMascota!!.agregarIncidente(
                        id = idNuevo,
                        idMascota = selectedMascota!!.getId(),
                        idVeterinario = selectedVeterinario?.getId() ?: 0,
                        nombre = nombreIncidente,
                        fecha = fechaFinal,
                        tipoIncidente = tipoIncidente,
                        descripcion = descripcion,
                        gravedad = gravedadSeleccionada,
                        observaciones = if (observaciones.isEmpty()) "Sin observaciones" else observaciones
                    )
                    onRegistroExitoso()
                }
            } catch (e: Exception) {
                if (e.toString().contains("InstantFormatException") || e.toString().contains("DateTimeParseException")) {
                    error = "Fecha inválida (Use AAAA-MM-DD)."
                } else {
                    error = "Error: ${e.message}"
                }
            }
        }) {
            Text("Registrar Incidente")
        }

        TextButton(onClick = { onCancelar() }) {
            Text("Cancelar")
        }
    }
}