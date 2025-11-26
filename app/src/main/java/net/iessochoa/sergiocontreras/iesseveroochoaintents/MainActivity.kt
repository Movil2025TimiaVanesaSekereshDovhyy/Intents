package net.iessochoa.sergiocontreras.iesseveroochoaintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.iessochoa.sergiocontreras.iesseveroochoaintents.ui.theme.IESSeveroOchoaIntentsTheme
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IESSeveroOchoaIntentsTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IESSeveroOchoaIntents()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IESSeveroOchoaIntents() {
    val context = LocalContext.current

    // Launcher especial para la cámara en Compose
    // (En este ejemplo solo abre la cámara y captura la miniatura, no guardamos la foto)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // Aquí recibirías la foto (bitmap) si quisieras mostrarla
        // Por ahora no hacemos nada, solo cumplimos el objetivo de abrirla
    }

    val uriHandler = LocalUriHandler.current
    val url = "https://portal.edu.gva.es/03013224/"
    val phoneNumber = "+34966912260"
    val address = "IES Severo Ochoa, Elche"

    val latitude = "38.27532"
    val longitude = "-0.68652"

    // 2. Opcional: Añade una etiqueta al marcador usando el parámetro 'q'.
    // El formato es geo:lat,lng?q=Mi Etiqueta
    val label = "IES Severo Ochoa"
    val geoUriWithLabel = "geo:$latitude,$longitude?q=$label"

    val recipient = "info@iesseveroochoa.edu.es"
    val subject = "Consulta desde la App"
    val body = "Hola, me gustaría recibir información sobre..."

    // 2. Codifica los componentes para que sean seguros en un URI.
    val encodedSubject = URLEncoder.encode(subject, "UTF-8")
    val encodedBody = URLEncoder.encode(body, "UTF-8")



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel del Instituto") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->

        // Columna principal para organizar los botones
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.entrada_ies_severo_ochoa_elche),
                contentDescription = "Entrada del IES Severo Ochoa"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Acciones Rápidas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // 1. ABRIR WEB DEL INSTITUTO
            ActionButton(
                text = "Web del Instituto",
                icon = Icons.Filled.Language,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                uriHandler.openUri(url)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. LLAMAR POR TELÉFONO
            ActionButton(
                text = "Llamar a Secretaría",
                icon = Icons.Filled.Phone,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                uriHandler.openUri("tel:$phoneNumber")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. VER UBICACIÓN EN MAPS
            ActionButton(
                text = "Ver en Google Maps",
                icon = Icons.Filled.Map,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            )   {
                    val encodedAddress = URLEncoder.encode(address, "UTF-8")
                    val geoUri = "geo:0,0?q=$encodedAddress"
                    uriHandler.openUri(geoUri)
                    //uriHandler.openUri(geoUriWithLabel)
                }


            Spacer(modifier = Modifier.height(16.dp))

            // 4. ENVIAR CORREO
            ActionButton(
                text = "Enviar Email",
                icon = Icons.Filled.Email,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                val mailtoUri = "mailto:$recipient?subject=$encodedSubject&body=$encodedBody"
                uriHandler.openUri(mailtoUri)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. ABRIR CÁMARA
            ActionButton(
                text = "Hacer Foto",
                icon = Icons.Filled.CameraAlt,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
            ) {


            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. MANDAR WHATSAPP
            ActionButton(
                text = "Mandar Whastapp",
                icon = Icons.Filled.Whatsapp,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer, contentColor = MaterialTheme.colorScheme.onTertiaryContainer)
            ) {


            }

        }
    }
}


// Componente reutilizable para los botones (para no repetir código visual)
@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    color: ButtonColors,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = color,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun IESSeveroOchoaIntentsPreview() {
    IESSeveroOchoaIntentsTheme() {
        IESSeveroOchoaIntents()
    }
}
