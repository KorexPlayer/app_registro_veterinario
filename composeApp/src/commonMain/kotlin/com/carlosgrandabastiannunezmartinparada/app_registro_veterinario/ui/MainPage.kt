package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun MainPage(
    onCerrarSesion: () -> Unit
) {
    var selected by remember { mutableStateOf(2) }
    val options = listOf("Hist. Medico", "Vacunas", "Principal", "Controles", "Veterinarios", )
    MaterialTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                Box {
                    Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick =  {onCerrarSesion()} ) {Text(text = "Cerrar Sesion") }
                    }
                    Spacer(Modifier.height(30.dp)) }
                when (selected) {
                    0 -> {
                        //Historial Medico
                    }

                    1 -> {
                        //Vacunas que ha tenido
                    }

                    2 -> {
                        //Datos de Mascotas y seleccion de mascota a ver
                    }

                    3 -> {
                        //Controles Veterinarios y sus tratamientos
                    }

                    4 -> {
                        VeterinariosVisitados()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box { SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, option ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ), onClick = { selected = index },
                            selected = index == selected,
                            label = { Text(option) }
                            )
                        }
                    }
                }
            }
        }
    }
}