package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.AppDropdown
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CardItem
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ComprobarDato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ExpandableMediaCard
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.RegistroVacuna
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

private enum class VistaVacuna { AGREGAR, LISTADO }

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun PaginaVacunas() {
    var vistaActual by remember { mutableStateOf(VistaVacuna.LISTADO) }

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        if (vistaActual == VistaVacuna.AGREGAR) {
            AgregarVacuna(
                onCancelar = { vistaActual = VistaVacuna.LISTADO },
                onRegistroExitoso = { vistaActual = VistaVacuna.LISTADO }
            )
        } else {
            FloatingActionButton(
                onClick = { vistaActual = VistaVacuna.AGREGAR },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Registrar Nueva Vacuna")
            }

            Spacer(modifier = Modifier.height(20.dp))

            ListadoVacunas()
        }
    }
}

@kotlin.time.ExperimentalTime
@Composable
private fun ListadoVacunas() {
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
            text = "Historial de Vacunación",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 5.dp)
        )
        Text(
            text = "Busca a tu mascota para ver sus vacunas:",
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

            val vacunasDeMascota = RepositorioObjetoVeterinario.listaVacunas.filter {
                it.getIdMascota() == mascotaSeleccionada!!.getId()
            }

            if (vacunasDeMascota.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center) {
                    Text("No hay vacunas registradas para ${mascotaSeleccionada!!.getNombre()}.", color = Color.Gray)
                }
            } else {
                Text(
                    text = "Historial de ${mascotaSeleccionada!!.getNombre()} (${vacunasDeMascota.size}):",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                for (vacuna in vacunasDeMascota) {
                    val vetEncontrado = RepositorioVeterinarios.obtenerPorId(vacuna.getIdVeterinario())
                    val nombreVet = vetEncontrado?.getNombre() ?: "No especificado"

                    val itemVacuna = CardItem(
                        title = vacuna.getNombreVacuna(),
                        type = "Tipo: ${vacuna.getTipo()}",
                        status = "Fecha: ${vacuna.getFechaVacunacion().toString().substringBefore('T')}",
                        extraInfo = "Dosis Aplicada: ${vacuna.getCantidadDosis()}\n" +
                                "Veterinario: $nombreVet\n" +
                                "Observaciones: ${vacuna.getObservaciones()}"
                    )

                    ExpandableMediaCard(
                        item = itemVacuna,
                        onDeleteClick = {
                            RepositorioObjetoVeterinario.eliminarRegistroVeterinario(vacuna.getId())
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
private fun AgregarVacuna(onCancelar: () -> Unit, onRegistroExitoso: () -> Unit) {
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
        derivedStateOf {
            RepositorioVeterinarios.repositorio.sortedBy { it.getNombre() }
        }
    }

    var selectedVeterinario by remember { mutableStateOf<Veterinario?>(null) }
    LaunchedEffect(listaVeterinarios) {
        if (selectedVeterinario == null && listaVeterinarios.isNotEmpty()) {
            selectedVeterinario = listaVeterinarios.first()
        }
    }

    var nombreVacuna by remember { mutableStateOf("") }
    var tipoVacuna by remember { mutableStateOf("") }
    var cantidadDosis by remember { mutableStateOf("") }
    var fechaVacunacion by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Registrar Vacuna", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))
        AppDropdown(
            label = "Tipo de Animal",
            options = tiposAnimales,
            selectedOption = tipoSeleccionado,
            onOptionSelected = { tipoSeleccionado = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (mascotasFiltradas.isNotEmpty()) {
            val mascotaActual = selectedMascota ?: mascotasFiltradas.first()
            AppDropdown(
                label = "Nombre de la Mascota",
                options = mascotasFiltradas,
                selectedOption = mascotaActual,
                onOptionSelected = { selectedMascota = it },
                itemLabel = { it.getNombre() }
            )
        } else {
            Text(
                "No tienes $tipoSeleccionado registrados.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (listaVeterinarios.isNotEmpty()) {
            val vetActual = selectedVeterinario ?: listaVeterinarios.first()

            AppDropdown(
                label = "Seleccionar Veterinario",
                options = listaVeterinarios,
                selectedOption = vetActual,
                onOptionSelected = { selectedVeterinario = it },
                itemLabel = { it.getNombre() }
            )
        } else {
            Text(
                "No hay veterinarios registrados. Registre uno primero.",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        CampoTextField("Nombre Vacuna", value = nombreVacuna, onChange = { nombreVacuna = it })
        CampoTextField("Tipo (Viral/Bacteriana)", value = tipoVacuna, onChange = { tipoVacuna = it })
        CampoTextField("Dosis (Numero)", value = cantidadDosis, onChange = { cantidadDosis = it })
        CampoTextField("Fecha (AAAA-MM-DD)", value = fechaVacunacion, onChange = { fechaVacunacion = it })
        CampoTextField("Observaciones", value = observaciones, onChange = { observaciones = it })

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            try {
                if (nombreVacuna.isEmpty() || tipoVacuna.isEmpty() || cantidadDosis.isEmpty() || fechaVacunacion.isEmpty()) {
                    error = "Rellene todos los campos"
                }
                else if (selectedMascota == null) {
                    error = "Debe seleccionar una mascota valida."
                }
                else if (listaVeterinarios.isNotEmpty() && selectedVeterinario == null) {
                    error = "Debe seleccionar un veterinario."
                }
                else if (!ComprobarDato(cantidadDosis, "onlynum")) {
                    error = "La dosis debe ser un numero entero sin letras."
                }
                else {
                    error = null
                    val idNuevo = Random.nextInt(999999)
                    val fechaFinal = Instant.parse("${fechaVacunacion}T00:00:00Z")

                    val nuevaVacuna = RegistroVacuna(
                        id = idNuevo,
                        idMascota = selectedMascota!!.getId(),
                        idVeterinario = selectedVeterinario?.getId() ?: 0,
                        nombreVacuna = nombreVacuna,
                        tipo = tipoVacuna,
                        cantidadDosis = cantidadDosis.toInt(),
                        fechaVacunacion = fechaFinal,
                        observaciones = if (observaciones.isEmpty()) "Sin observaciones" else observaciones
                    )

                    RepositorioObjetoVeterinario.anadirRegistroVacuna(nuevaVacuna)
                    onRegistroExitoso()
                }
            } catch (e: Exception) {
                if (e.toString().contains("InstantFormatException") || e.toString().contains("DateTimeParseException")) {
                    error = "Fecha inválida (Use AAAA-MM-DD)."
                }
                else if (e.toString().contains("NumberFormatException")) {
                    error = "Hay letras en algún dato, solo escribe numeros."
                }
                else {
                    error = e.toString()
                }
            }
        }) {
            Text("Guardar Vacuna")
        }

        TextButton(onClick = { onCancelar() }) {
            Text("Cancelar")
        }
    }
}