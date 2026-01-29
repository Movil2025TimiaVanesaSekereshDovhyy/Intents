package net.iessochoa.sergiocontreras.iesseveroochoaintents

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
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
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import net.iessochoa.sergiocontreras.iesseveroochoaintents.ui.theme.IESSeveroOchoaIntentsTheme
import java.net.URLEncoder
import java.util.Locale

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
    // Preparamos el escuchador de permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Este código se ejecuta CUANDO el usuario responde al diálogo
        val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (isGranted) {
            Toast.makeText(context, "Permiso concedido. Calculando...", Toast.LENGTH_SHORT).show()
            // ¡Llamamos a la función que calcula!
            calculateAndShowDistance(context, latitude, longitude)
        } else {
            Toast.makeText(context, "Se necesita permiso para calcular la distancia", Toast.LENGTH_LONG).show()
        }
    }

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
                uriHandler.openUri(webUrl)
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
                // El formato debe ser "tel:+3496..."
                uriHandler.openUri("tel:$phoneNumber")
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
                // 1. Codificamos el nombre para que sea seguro (los espacios se convierten en %20, etc.)
                // IMPORTANTE: Necesitarás importar java.net.URLEncoder
                val encodedLabel = URLEncoder.encode(mapLabel, "UTF-8")

                // 2. Construimos la URI compleja concatenando las variables
                val geoUri = "geo:0,0?q=$latitude,$longitude($encodedLabel)&z=$mapZoom"

                // 3. Lanzamos
                uriHandler.openUri(geoUri)
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
                // Codificamos asunto y cuerpo
                val encodedSubject = URLEncoder.encode(emailSubject, "UTF-8")
                val encodedBody = URLEncoder.encode(emailBody, "UTF-8")

                // Construimos la URI
                val mailtoUri = "mailto:$emailRecipient?subject=$encodedSubject&body=$encodedBody"

                uriHandler.openUri(mailtoUri)
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

                // En el onClick del botón:
                permissionLauncher.launch( // <--- ¡A trabajar!
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )

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

            Spacer(modifier = Modifier.height(16.dp))

            //CALENDARIO
            ActionButton(
                text = "Añadir cita al calendario",
                icon = Icons.Filled.Event,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.scrim)
            ) {
                addCalendarEvent(context)
            }


        }
    }
}

fun addCalendarEvent(context: Context) {
    // Insertar un evento en el calendario
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = android.provider.CalendarContract.Events.CONTENT_URI //Lo que se va a insertar

        //El título del evento
        putExtra(android.provider.CalendarContract.Events.TITLE, "Visita al IES Severo Ochoa")
       //El lugar
        putExtra(android.provider.CalendarContract.Events.EVENT_LOCATION, "Elche")
       //La descripción
        putExtra(android.provider.CalendarContract.Events.DESCRIPTION, "Reunión informativa")

        //Empieza en 1h
        val startTime = System.currentTimeMillis() + 60 * 60 * 1000
        //Dura 1h el evento
        val endTime = startTime + 60 * 60 * 1000

        //Le pongo las horas
        putExtra(android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
        putExtra(android.provider.CalendarContract.EXTRA_EVENT_END_TIME, endTime)
    }

    //Se lanza el intent
    context.startActivity(intent)
}


fun sendWhatsAppMessage(context: Context, phoneNumber: String, message: String) {
    // TODO: Paso 6. Implementar lógica de WhatsApp
    // 1. Formatear número (quitar espacios y +).
    // 2. Codificar mensaje con URLEncoder.
    // 3. Crear URI "https://wa.me/..."
    // 4. Crear Intent ACTION_VIEW.
    // 5. Configurar package "com.whatsapp" (opcional, para forzar app).
    // 6. Lanzar activity dentro de un try-catch para evitar cierres si no está instalado.
    // 1. Limpieza: WhatsApp quiere el número sin '+' ni espacios (ej: 34666...)
    val cleanNumber = phoneNumber.replace("+", "").replace(" ", "")

    // 2. Codificamos el mensaje
    val encodedMessage = URLEncoder.encode(message, "UTF-8")

    // 3. Creamos la URI "mágica" de WhatsApp
    val uri = "https://wa.me/$cleanNumber?text=$encodedMessage"

    // 4. Creamos un Intent nativo ACTION_VIEW
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = android.net.Uri.parse(uri)

    /* TRUCO PRO: Si descomentas la siguiente línea, obligas a abrir WhatsApp oficial.
       Si la dejas comentada, Android preguntará al usuario (útil si usa WhatsApp Business) */
    // intent.setPackage("com.whatsapp")

    // 5. INTENTAMOS abrir la actividad
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // Si entramos aquí, es que no tiene WhatsApp instalado
        Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
    }


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
    // 1. Obtenemos el cliente de ubicación de Google
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // 2. IMPORTANTE: Aunque el usuario haya dicho "Sí" antes,
    // Android exige por seguridad volver a verificar el permiso antes de llamar a la API.
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        // 3. Pedimos la ubicación ACTUAL
        // PRIORITY_HIGH_ACCURACY: Usa GPS (gasta más batería, pero es exacto)
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
            .addOnSuccessListener { userLocation: Location? ->
                if (userLocation != null) {
                    // A. Creamos la ubicación del Instituto
                    val instituteLocation = Location("provider").apply {
                        latitude = instituteLat.toDouble()
                        longitude = instituteLon.toDouble()
                    }

                    // B. Calculamos la distancia (magia matemática de Android)
                    val distanceInMeters = userLocation.distanceTo(instituteLocation)
                    val distanceInKm = distanceInMeters / 1000f

                    // C. Mostramos resultado
                    val msg = String.format(Locale.getDefault(), "Estás a %.2f km del instituto", distanceInKm)
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "No se pudo obtener la ubicación (GPS desactivado?)", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al obtener ubicación", Toast.LENGTH_SHORT).show()
            }
    }
}


@Preview(showBackground = true)
@Composable
private fun IESSeveroOchoaIntentsPreview() {
    IESSeveroOchoaIntentsTheme() {
        IESSeveroOchoaIntents()
    }
}