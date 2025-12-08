package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.darkScheme
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.lightScheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.LoginPage
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.MainPage
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.RegisterPage
private enum class Pantallas { LOGIN, REGISTRO, MAIN}

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun App() {
    var checked by remember { mutableStateOf(true) }
    MaterialTheme(colorScheme  = if(checked) {
        darkScheme
    } else {
        lightScheme
    }) {
            var pantalla by remember { mutableStateOf(Pantallas.LOGIN) }
            Surface(modifier = Modifier.fillMaxSize()) {
                Box (modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(modifier = Modifier.width(200.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text("TemaOscuro: ",modifier = Modifier.weight(1f), textAlign = TextAlign.Right)
                        Switch(checked = checked, onCheckedChange = { checked = it }, modifier = Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(12.dp))
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp))
                {

                    when (pantalla) {
                        Pantallas.LOGIN -> LoginPage(
                            onLoginSuccess = { pantalla = Pantallas.MAIN },
                            onRegister = { pantalla = Pantallas.REGISTRO },
                        )
                        Pantallas.REGISTRO -> RegisterPage(
                            onLogin = { pantalla = Pantallas.LOGIN }
                        )
                        Pantallas.MAIN -> MainPage(
                            onCerrarSesion = {pantalla = Pantallas.LOGIN}
                        )

                    }
                }

            }
    }
}