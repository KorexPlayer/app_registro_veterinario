package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoPasswordField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.CampoTextField
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes.ComprobarDato
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.modelo.personas.Dueno
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.RepositorioDueno
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun RegisterPage(
    onLogin: () -> Unit
) {
    var rut by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var password2 by remember {mutableStateOf("")}
    var nombre by remember {mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var direccion by remember {mutableStateOf("")}
    var telefono by remember {mutableStateOf("")}
    var error by remember {mutableStateOf<String?>(null)}
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.padding(10.dp).verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Registro", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(5.dp))
        CampoTextField("RUT (Sin digito Verificador)", value = rut, onChange = { rut = it })
        CampoTextField("Nombre Completo", value = nombre, onChange = { nombre = it })
        CampoTextField("Correo Electronico (ej: xxxx@xxx.com", value = email, onChange = { email = it })
        CampoTextField("Direccion", value = direccion, onChange = { direccion = it })
        CampoTextField("Numero de Telefono (Solo numeros: 987654321)", value = telefono, onChange = { telefono = it })
        CampoPasswordField("Contraseña", value = password, onChange = { password = it })
        CampoPasswordField("Ingrese nuevamente Contraseña", value = password2, onChange = { password2 = it })
        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(onClick = {
            if(rut.isEmpty() || nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                error = "Rellene todos los campos"
            }
            else if(!(ComprobarDato(nombre, "nonum"))) {
                error = "El nombre solo letras"
            }
            else if (!ComprobarDato(email, "correo")) {
                error = "El correo debe ser de tipo xxxx@xxx.com"
            }
            else if (!ComprobarDato(telefono, "onlynum")) {
                error = "El numero de telefono solo puede ser: 987654321"
            }
            else if (!ComprobarDato(rut, "onlynum")) {
                error = "El rut es sin el digito verificador y sin ninguna letra"
            }
            else {
                error = null
                val id = Random.nextInt(99999)
                if(RepositorioDueno.obtenerPorRut(rut) == null && !(RepositorioDueno.repositorio.any() {it.getId() == id})) {
                    val nuevodueno = Dueno(id = id, nombreCompleto = nombre, telefono = telefono, email = email, direccion = direccion, rut = rut, contrasena = password)
                    RepositorioDueno.crearDueno(nuevodueno)
                    onLogin()
                }
                else {
                    error = "Ya existe el usuario"
                }

            }
        }) {
            Text("Registrarse")

        }
        TextButton(onClick = onLogin) { Text("Ya tienes una cuenta?, Inicia Sesion.") }
    }
}