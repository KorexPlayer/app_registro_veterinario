package persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.StorageDriver
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.IOException

class AndroidDriverStorage(private val directorioBase: String ) : StorageDriver {

    private val fileSystem = FileSystem.SYSTEM
    private val basePath: Path = directorioBase.toPath()

    init {
        if (!fileSystem.exists(basePath)) {
            try {
                fileSystem.createDirectories(basePath)
            } catch (e: IOException) {
                println("Error creando directorio base: ${e.message}")
            }
        }
    }

    private fun obtenerRutaArchivo(key: String): Path {

        val nombreSanitizado = key
            .replace("/", "_")
            .replace("\\", "_")
            .replace(":", "_")

        return basePath / nombreSanitizado
    }

    override fun put(key: String, data: ByteArray): Boolean {
        return try {
            val ruta = obtenerRutaArchivo(key)
            fileSystem.write(ruta) {
                write(data)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    override fun get(key: String): ByteArray? {
        val ruta = obtenerRutaArchivo(key)

        if (!fileSystem.exists(ruta)) return null

        return try {
            // Equivalente a file.readBytes()
            fileSystem.read(ruta) {
                readByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun keys(prefix: String): List<String> {
        val prefijoSanitizado = prefix
            .replace("/", "_")
            .replace("\\", "_")
            .replace(":", "_")

        return try {
            fileSystem.list(basePath)
                .filter { path ->
                    path.name.startsWith(prefijoSanitizado)
                }
                .map { it.name }
        } catch (e: IOException) {
            emptyList()
        }
    }

    override fun remove(key: String): Boolean {
        return try {
            val ruta = obtenerRutaArchivo(key)

            fileSystem.delete(ruta)
            true
        } catch (e: IOException) {
            false
        }
    }
}