package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}