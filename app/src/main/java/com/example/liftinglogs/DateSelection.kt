package com.example.liftinglogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DateList(goToWorkout: () -> Unit) {
    Column() {
        Button(
            onClick = {
                goToWorkout()
            }
        ) {
            Text(text = "Add workout")
        }
    }
}