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

    // Launcher especial para la cámara en Compose
    // (En este ejemplo solo abre la cámara y captura la miniatura, no guardamos la foto)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // Aquí recibirías la foto (bitmap) si quisieras mostrarla
        // Por ahora no hacemos nada, solo cumplimos el objetivo de abrirla
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
                // Cambia esta URL por la de tu instituto
                val url = "https://www.google.com"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. LLAMAR POR TELÉFONO
            ActionButton(
                text = "Llamar a Secretaría",
                icon = Icons.Filled.Phone,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                // ACTION_DIAL es mejor que CALL porque no requiere pedir permisos
                // simplemente abre el marcador con el número listo.
                val numero = "912345678"
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$numero"))
                context.startActivity(intent)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. VER UBICACIÓN EN MAPS
            ActionButton(
                text = "Ver en Google Maps",
                icon = Icons.Filled.Map,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                // "geo:0,0?q=" permite buscar por nombre o dirección
                val ubicacion = "Instituto de Educación Secundaria" // Pon aquí el nombre real o dirección
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$ubicacion"))
                // Opcional: forzar que se abra en Google Maps
                intent.setPackage("com.google.android.apps.maps")

                // Comprobamos si hay app de mapas instalada para evitar cierres
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else {
                    // Fallback: abrir en navegador si no hay maps instalado
                    val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=$ubicacion"))
                    context.startActivity(intentWeb)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. ENVIAR CORREO
            ActionButton(
                text = "Enviar Email",
                icon = Icons.Filled.Email,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                val email = "info@instituto.com"
                val asunto = "Consulta desde la App"

                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:") // Solo apps de correo
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, asunto)
                }
                context.startActivity(intent)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5. ABRIR CÁMARA
            ActionButton(
                text = "Hacer Foto",
                icon = Icons.Filled.CameraAlt,
                color = buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                // Lanzamos el contrato de la cámara definido arriba
                cameraLauncher.launch(null)
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
