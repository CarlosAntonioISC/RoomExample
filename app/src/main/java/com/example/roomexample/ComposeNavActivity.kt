package com.example.roomexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roomexample.ui.theme.RoomExampleTheme

class ComposeNavActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ComposeNavigation()
                }
            }
        }
    }
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "composeA") {
        composable("composeA") {
            ComposeScreenA(onNavigate = { navController.navigate("composeB") })
        }
        composable("composeB") {
            ComposeScreenB(onBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun ComposeScreenA(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla A (Compose)", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onNavigate, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Ir a Pantalla B")
        }
    }
}

@Composable
fun ComposeScreenB(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla B (Compose)", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Regresar")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComposeScreenPreview() {
    RoomExampleTheme {
        ComposeNavigation()
    }
}
