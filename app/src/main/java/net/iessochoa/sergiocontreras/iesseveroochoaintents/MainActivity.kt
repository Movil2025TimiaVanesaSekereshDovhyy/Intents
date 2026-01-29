package net.iessochoa.sergiocontreras.iesseveroochoaintents

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Route
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
    // Helper de Compose que facilita abrir URIs básicas (http, tel, etc.)
    val uriHandler = LocalUriHandler.current

    // --- DATOS DEL INSTITUTO ---
    val webUrl = "https://portal.edu.gva.es/03013224/"
    val phoneNumber = "+34966912260"
    val address = "IES Severo Ochoa, Elche"

    // Coordenadas exactas
    val latitude = "38.2794548"
    val longitude = "-0.7180523"
    val mapZoom = "16.5"
    val mapLabel = "IES Severo Ochoa" // Etiqueta para el marcador

    val emailRecipient = "info@iesseveroochoa.edu.es"
    val emailSubject = "Consulta desde la App"
    val emailBody = "Hola, me gustaría recibir información sobre..."


    // --- 5. CONFIGURACIÓN DE PERMISOS (Hacer al final) ---
    // TODO: Paso 5. Crear el permissionLauncher.
    // Debe usar ActivityResultContracts.RequestMultiplePermissions()
    // Si se conceden los permisos -> llamar a calculateAndShowDistance()
    // Si se deniegan -> mostrar un Toast informativo


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
                // TODO: Paso 1. Abrir la URL del instituto usando uriHandler
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. LLAMAR POR TELÉFONO
            ActionButton(
                text = "Llamar a Secretaría",
                icon = Icons.Filled.Phone,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                // TODO: Paso 2. Abrir el marcador telefónico.
                // Recuerda el esquema "tel:"
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. VER UBICACIÓN EN MAPS
            ActionButton(
                text = "Ver en Google Maps",
                icon = Icons.Filled.Map,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            )   {
                // TODO: Paso 3. Construir la geoURI compleja.
                // Formato: "geo:0,0?q=lat,lng(label)&z=zoom"
                // IMPORTANTE: Recuerda codificar la etiqueta (label) con URLEncoder para caracteres especiales.

            }


            Spacer(modifier = Modifier.height(16.dp))

            // 4. ENVIAR CORREO
            ActionButton(
                text = "Enviar Email",
                icon = Icons.Filled.Email,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                // TODO: Paso 4. Construir la URI mailto.
                // Formato: "mailto:email?subject=...&body=..."
                // IMPORTANTE: Codificar asunto y cuerpo con URLEncoder.
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. Calcular distancia al instituto
            ActionButton(
                text = "Calcula distancia al Instituto",
                icon = Icons.Filled.Route,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                // TODO: Paso 5 (Botón). Lanzar el permissionLauncher solicitando:
                // Manifest.permission.ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. MANDAR WHATSAPP
            ActionButton(
                text = "Mandar Whastapp",
                icon = Icons.Filled.Whatsapp,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer, contentColor = MaterialTheme.colorScheme.onTertiaryContainer)
            ) {
                sendWhatsAppMessage(
                    context,
                    phoneNumber,
                    "Me gustaría matricularme en vuestros ciclos"
                )
            }

        }
    }
}

fun sendWhatsAppMessage(context: Context, phoneNumber: String, message: String) {
    // TODO: Paso 6. Implementar lógica de WhatsApp
    // 1. Formatear número (quitar espacios y +).
    // 2. Codificar mensaje con URLEncoder.
    // 3. Crear URI "https://wa.me/..."
    // 4. Crear Intent ACTION_VIEW.
    // 5. Configurar package "com.whatsapp" (opcional, para forzar app).
    // 6. Lanzar activity dentro de un try-catch para evitar cierres si no está instalado.
}


// Componente reutilizable para los botones
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

// NUEVA FUNCIÓN para obtener ubicación y calcular distancia
private fun calculateAndShowDistance(context: Context, instituteLat: String, instituteLon: String) {
    // TODO: Paso 7. Lógica de Geolocalización (API LocationServices)

    // 1. Obtener FusedLocationProviderClient (Necesitarás añadir dependencia en Gradle y el import).
    // 2. Verificar permiso selfPermission (aunque ya lo hayamos pedido, es buena práctica).
    // 3. Obtener ubicación actual (getCurrentLocation) con prioridad alta.
    // 4. En el listener de éxito: crear objeto Location del instituto, calcular distancia (distanceTo) y mostrar Toast.
}

@Preview(showBackground = true)
@Composable
private fun IESSeveroOchoaIntentsPreview() {
    IESSeveroOchoaIntentsTheme() {
        IESSeveroOchoaIntents()
    }
}