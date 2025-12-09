package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CardItem
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ExpandableMediaCard
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioVeterinarios
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun VeterinariosVisitados() {
    Column(
        modifier = Modifier// Fondo blanco de la pantalla
            .padding(top = 20.dp)
    ) {
        for (veterinarios in RepositorioVeterinarios.listar()) {
            val sampleItem = CardItem(title = veterinarios.getNombre(), type = veterinarios.getEspecialidad(), veterinarios.getHorarioAtencion(), veterinarios.getDescripcionServicio() + "Numero Telfonico: ${veterinarios.getTelefono()} y Correo Electronico: ${veterinarios.getEmail()}, Nos pueden encontrar en: ${veterinarios.getDireccion()}")
            ExpandableMediaCard(
                item = sampleItem,
                onDeleteClick = {
                    RepositorioVeterinarios.eliminarVeterinario(veterinarios.getId())
                    // Lógica para borrar el item
                    println("Borrar item")
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}