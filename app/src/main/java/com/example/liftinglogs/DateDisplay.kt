package com.example.liftinglogs

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun DateDisplay() {
    val calendar = Calendar.getInstance().time
    Text(SimpleDateFormat.getDateInstance(DateFormat.LONG).format(calendar))
}