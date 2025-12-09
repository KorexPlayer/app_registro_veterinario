package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDropdown(
    label: String,                // El texto de la etiqueta (ej: "Categoría")
    options: List<T>,             // La lista de opciones (puede ser Strings, o tus objetos)
    selectedOption: T,            // El valor que está seleccionado actualmente
    onOptionSelected: (T) -> Unit, // La función que "devuelve" el dato seleccionado
    itemLabel: (T) -> String = { it.toString() }, // (Opcional) Cómo convertir el objeto a texto
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            // Mostramos el texto usando la función itemLabel
            value = itemLabel(selectedOption),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = itemLabel(option)) },
                    onClick = {
                        // AQUÍ es donde "devolvemos" el dato al padre
                        onOptionSelected(option)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}