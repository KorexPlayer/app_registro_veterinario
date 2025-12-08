package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.componentes
/**
* "correo" para verificar correos
* "nonum" para verificar que no existan numeros en el dato
*/
fun ComprobarDato(dato: String, tipo: String): Boolean {

    when (tipo) {
        "correo" -> {
            if (dato.contains("@")){
                return false
            }
        }
        "nonum" -> {
            if (dato.any { it.isDigit() }) {
                return false
            }
        }
    }

    return true
}