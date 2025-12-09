package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoPasswordField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioDueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.UsuarioActual
import org.jetbrains.compose.ui.tooling.preview.Preview

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun LoginPage(
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit
) {
    var rut by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    Box(contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(7.dp)
        ) {
            Text(
                text = "Inicio de Sesion",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            CampoTextField(label = "RUT", value = rut, onChange = { rut = it })
            CampoPasswordField(label = "Password", value = password, onChange = { password = it })
            error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(onClick = {
                val duenio = RepositorioDueno.autenticarUsuario(rut, password)
                if (duenio != null){
                    error = null
                    UsuarioActual.usuarioActual = rut
                    UsuarioActual.nombreActual = duenio.getNombre()
                    onLoginSuccess()
                }
                else if (rut.isEmpty() || password.isEmpty()){
                    error = "Rellene todos los campos."
                }
                else {
                    error = "RUT o Password incorrectos"
                }
            }) { Text(text = "Iniciar Sesion") }
            TextButton(onClick =
                onRegister
            ) { Text("No eres Usuario?, Registrate Ahora.") }
        }
    }
}