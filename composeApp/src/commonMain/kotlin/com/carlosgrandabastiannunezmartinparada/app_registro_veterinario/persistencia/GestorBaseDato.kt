package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia

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