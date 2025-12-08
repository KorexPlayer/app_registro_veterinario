package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.GestorBaseDato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioDueno
import persistencia.AndroidDriverStorage

@kotlin.time.ExperimentalTime
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val rutaAndroid = filesDir.absolutePath
        val driver = AndroidDriverStorage(rutaAndroid)
        val persistencia = GestorBaseDato(driver)
        RepositorioDueno.init(persistencia)
        setContent {

            App()
        }
    }
}
@kotlin.time.ExperimentalTime
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}