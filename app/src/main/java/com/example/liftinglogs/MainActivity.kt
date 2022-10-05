package com.example.liftinglogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.example.liftinglogs.ui.theme.LiftingLogsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiftingLogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Workout() {
    var workout by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf(0)}
    var reps by remember { mutableStateOf(0) }
    var sets by remember { mutableStateOf(0) }

    Row {
        TextField(
            modifier = Modifier.weight(3f),
            value = workout,
            onValueChange = { workout = it },
            label = { Text("Workout") })
        TextField(
            modifier = Modifier.weight(1f),
            value = weight.toString(),
            onValueChange = { weight = it.toInt()  },
            label = { Text("Weight")})
        TextField(
            modifier = Modifier.weight(1f),
            value = reps.toString(),
            onValueChange = { reps = it.toInt()  },
            label = { Text("Reps")})
        TextField(
            modifier = Modifier.weight(1f),
            value = sets.toString(),
            onValueChange = { sets = it.toInt()  },
            label = { Text("Sets")})
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Workout()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting("Android")
}