package cl.ameza.android.serv_basicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.Fireplace
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Medicion(val tipo: String, val valor: String, val fecha: String)

@Composable
fun MedicionListScreen(mediciones: List<Medicion>, onAddClick: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(mediciones) { medicion ->
                Boletas(medicion)
            }
        }
    }
}

@Composable
fun Boletas(medicion: Medicion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = when (medicion.tipo) {
                "Agua" -> Icons.Default.WaterDrop
                "Luz" -> Icons.Rounded.ElectricBolt
                "Gas" -> Icons.Rounded.Fireplace
                else -> Icons.Default.Add
            },
            contentDescription = "Ícono ${medicion.tipo}"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "${medicion.tipo}: ${medicion.valor} - ${medicion.fecha}")
    }
}

@Composable
fun RegistroMedicionScreen(onSaveClick: (Medicion) -> Unit) {
    var valor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Agua") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = valor, onValueChange = { valor = it }, label = { Text("Valor") })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") })
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            listOf("Agua", "Luz", "Gas").forEach { opcion ->
                RadioButton(selected = tipo == opcion, onClick = { tipo = opcion })
                Text(text = opcion)
            }
        }

        Button(onClick = {
            onSaveClick(Medicion(tipo, valor, fecha))
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrar")
        }
    }
}

@Composable
fun NuevaApk() {
    var mediciones by remember { mutableStateOf(listOf<Medicion>()) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    if (mostrarFormulario) {
        RegistroMedicionScreen { nuevaMedicion ->
            mediciones = mediciones + nuevaMedicion
            mostrarFormulario = false
        }
    } else {
        MedicionListScreen(mediciones = mediciones) {
            mostrarFormulario = true
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { NuevaApk() }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    NuevaApk()
}