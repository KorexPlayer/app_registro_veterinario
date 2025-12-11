import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.persistencia.UsuarioActual
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.PaginaResumen
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.PaginaControl
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.VeterinariosVisitados
import com.carlosgrandabastiannunezmartinparada.app_registro_veterinario.ui.PaginaVacunas
import org.jetbrains.compose.ui.tooling.preview.Preview

@kotlin.time.ExperimentalTime
@Composable
@Preview
fun MainPage(
    onCerrarSesion: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Principal", "Controles", "Medico Vet.", "Historial", "Vacunas")
    val icons = listOf(Icons.Default.Home, Icons.Default.List, Icons.Default.Person, Icons.Default.List, Icons.Default.Archive)

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onCerrarSesion() },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Cerrar Sesion")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedItem) {
                    0 -> PaginaResumen()
                    1 -> PaginaControl()
                    2 -> VeterinariosVisitados()
                    3 -> Text("Historial Médico")
                    4 -> PaginaVacunas()
                }
            }
        }
    }
}