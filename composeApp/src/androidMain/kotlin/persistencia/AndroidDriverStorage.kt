package persistencia

import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.interfacerepositorios.StorageDriver
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.IOException

class AndroidDriverStorage(private val directorioBase: String ) : StorageDriver {

    private val fileSystem = FileSystem.SYSTEM
    private val basePath: Path = directorioBase.toPath()

    // Bloque INIT: Lógica equivalente a directorioPadre.mkdirs()
    init {
        if (!fileSystem.exists(basePath)) {
            try {
                fileSystem.createDirectories(basePath)
            } catch (e: IOException) {
                println("Error creando directorio base: ${e.message}")
            }
        }
    }

    // Lógica auxiliar: Equivalente a obtenerNombreArchivo() con sanitización
    private fun obtenerRutaArchivo(key: String): Path {
        // Reemplazamos separadores para aplanar la estructura, igual que en tu lógica Desktop
        val nombreSanitizado = key
            .replace("/", "_")
            .replace("\\", "_")
            .replace(":", "_")

        // Unimos el directorio base con el nombre sanitizado
        return basePath / nombreSanitizado
    }

    override fun put(key: String, data: ByteArray): Boolean {
        return try {
            val ruta = obtenerRutaArchivo(key)
            // Equivalente a file.writeBytes(data)
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

        // Equivalente a: if (archivo.exists() && archivo.isFile)
        if (!fileSystem.exists(ruta)) return null

        // Nota: metadataOrNull ayuda a saber si es archivo o directorio,
        // pero generalmente en Okio si puedes leerlo, es un archivo.

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
        // Sanitizamos el prefijo igual que la key
        val prefijoSanitizado = prefix
            .replace("/", "_")
            .replace("\\", "_")
            .replace(":", "_")

        return try {
            // Equivalente a directorioPadre.listFiles { ... }
            fileSystem.list(basePath)
                .filter { path ->
                    // path.name te da solo el nombre del archivo sin la ruta completa
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
            // Equivalente a file.delete()
            fileSystem.delete(ruta)
            true
        } catch (e: IOException) {
            false
        }
    }
}