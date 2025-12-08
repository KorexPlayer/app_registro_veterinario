package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import app_registro_veterinario.composeapp.generated.resources.Res
import app_registro_veterinario.composeapp.generated.resources.compose_multiplatform
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.LoginPage
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.MainPage
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.RegisterPage

private enum class Pantallas { LOGIN, REGISTRO, MAIN}

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun App() {
    MaterialTheme {
            var pantalla by remember { mutableStateOf(Pantallas.LOGIN) }
            Surface(modifier = Modifier.fillMaxSize()) {
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