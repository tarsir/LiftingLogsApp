package com.example.liftinglogs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.saveable.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.liftinglogs.ui.theme.LiftingLogsTheme

class WorkoutListViewModel(
    val workoutList: SnapshotStateList<Workout> = emptyList<Workout>().toMutableStateList(),
) : ViewModel() {
    val workouts: List<Workout>
        get() = workoutList

    fun remove(item: Workout) {
        workoutList.remove(item)
    }

    fun update(id: Int, item: Workout) {
        println("Updating workout $id: ${item.name}")
        var w = workouts.find { it.id == id }
        w = item
    }

    fun add(item: Workout) {
        workoutList.add(item)
        println("Adding workout id $item.id")
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiftingLogsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WorkoutsScreen()
                }
            }
        }
    }
}

class Workout(
    var name: String?,
    var reps: Int? = 0,
    var sets: Int? = 0,
    var weight: Int? = 0,
    val id: Int,
) {}

@Composable
fun WorkoutItem(
    workout: Workout,
    updateFn: (Int, Workout) -> Unit,
) {
    val kbNumOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    val wName = remember { mutableStateOf(workout.name) }
    val wWeight = remember { mutableStateOf(workout.weight) }
    val wReps = remember { mutableStateOf(workout.reps) }
    val wSets = remember { mutableStateOf(workout.sets) }
    Row {
        TextField(
            modifier = Modifier.weight(5f),
            value = wName.value?: "",
            onValueChange = {
                wName.value = it
                updateFn(workout.id, workout)
            },
            label = { Text("Workout") })
        TextField(
            modifier = Modifier.weight(3f),
            value = wWeight.value.toString(),
            keyboardOptions = kbNumOptions,
            onValueChange = {
                wWeight.value = it.toIntOrNull() ?: 0
                updateFn(workout.id, workout)
            },
            label = { Text("Weight") })
        TextField(
            modifier = Modifier.weight(2f),
            value = wReps.value.toString(),
            keyboardOptions = kbNumOptions,
            onValueChange = {
                wReps.value = it.toIntOrNull() ?: 0
                updateFn(workout.id, workout)
            },
            label = { Text("Reps") })
        TextField(
            modifier = Modifier.weight(2f),
            value = wSets.value.toString(),
            keyboardOptions = kbNumOptions,
            onValueChange = {
                wSets.value = it.toIntOrNull() ?: 0
                updateFn(workout.id, workout)
            },
            label = { Text("Sets") })
    }
}

@Composable
fun WorkoutList(workouts: List<Workout>, updateFn: (Int, Workout) -> Unit) {
    LazyColumn() {
        items(
            items = workouts,
            key = { workout -> workout.id }
        ) { item ->
            WorkoutItem(
                item,
                updateFn
            )
        }
    }
}

@Composable
fun WorkoutsScreen(workoutsViewModel: WorkoutListViewModel = viewModel()) {
    var workouts = workoutsViewModel.workouts
    val addItemFn = { workoutsViewModel.add(Workout("", id = workouts.size)) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Daily Log") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addItemFn ) {
                Text("+")
            }
        }
    ) {
        WorkoutList(workouts, updateFn = { a, b -> workoutsViewModel.update(a, b) })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkoutsScreen()
}