package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CardItem
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ExpandableMediaCard
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioAnimal
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.UsuarioActual
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.typeOf

@Composable
@Preview
@kotlin.time.ExperimentalTime
fun PaginaResumen() {
    Column {
        Text("Bienvenido ${UsuarioActual.nombreActual}")
        Text("Estas son las mascotas que tienes actualmente a tu cargo: ")
        for (mascota in RepositorioAnimal.repositorio) {
            val sample = CardItem(
                mascota.getNombre(),
                "${mascota::class.simpleName}",
                mascota.getRaza(),
                "${mascota.getGenero()}"
            )
            ExpandableMediaCard(item = sample, onDeleteClick = { RepositorioAnimal.eliminarMascota(mascota.getId()) })
        }
        FloatingActionButton(onClick = { }) {
            Text("Agregar Mascota")
        }
    }
}