package com.carlosgrandabastiannunezmartinparada.app_registro_veterinario

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform