package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.Persistencia

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.IOException
class GestorBaseDato {
    fun getInstance(): GestorBaseDato {
        return GestorBaseDato()
    }
    fun getDuenoDato(): String {
        return ""
    }
    fun getMascotaDato(): String {
        return ""
    }
    fun getVeterinarioDato(): String {
        return ""
    }
}