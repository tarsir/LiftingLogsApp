package com.example.liftinglogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

class WorkoutListViewModel(
    val workoutList: SnapshotStateList<Workout> = emptyList<Workout>().toMutableStateList(),
) : ViewModel() {
    val workouts: List<Workout>
        get() = workoutList

    init {
        if (workoutList.size == 0) {
            workoutList.add(Workout("", id = 0))
        }
    }

    fun remove(item: Workout) {
        workoutList.remove(item)
    }

    fun update(id: Int, item: Workout) {
        println("Updating workout $id: ${item.name}")
        workouts.find { it.id == id }
    }

    fun add(item: Workout) {
        workoutList.add(item)
        println("Adding workout id $item.id")
    }
}

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
fun WorkoutsScreen(goBackHome: () -> Unit, workoutsViewModel: WorkoutListViewModel = viewModel()) {
    val workouts = workoutsViewModel.workouts
    val addItemFn = { workoutsViewModel.add(Workout("", id = workouts.size)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { DateDisplay() },
                navigationIcon = {
                    IconButton(onClick = { goBackHome() }) {
                        Icon(Icons.Rounded.ArrowBack, "")
                    }
                }
            )
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

