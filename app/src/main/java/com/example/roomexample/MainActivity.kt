package com.example.roomexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roomexample.ui.compose.ComposeNavActivity
import com.example.roomexample.ui.theme.RoomExampleTheme
import com.example.roomexample.xml.XmlNavigationActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LandingScreen(
                        onOpenCompose = {
                            startActivity(Intent(this, ComposeNavActivity::class.java))
                        },
                        onOpenXml = {
                            startActivity(Intent(this, XmlNavigationActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LandingScreen(
    onOpenCompose: () -> Unit,
    onOpenXml: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Room Example",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onOpenCompose) {
            Text(text = "Abrir Compose")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onOpenXml) {
            Text(text = "Abrir XML")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LandingScreenPreview() {
    RoomExampleTheme {
        LandingScreen(onOpenCompose = {}, onOpenXml = {})
    }
}
