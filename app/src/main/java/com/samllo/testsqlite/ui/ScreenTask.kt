package com.samllo.testsqlite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samllo.testsqlite.data.Task

@Composable
fun ScreenTask(viewModel: TaskViewModel) {
  val listTasks by viewModel.tasks.collectAsState(initial = emptyList())
  var titleTask by remember { mutableStateOf("") }
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(text = "Task List", style = MaterialTheme.typography.titleLarge)
    Row(modifier = Modifier.padding(top = 16.dp)) {
      InputText(titleTask, { titleTask = it }, "Title task")
    }
    Row(modifier = Modifier.padding(top = 6.dp)) {
      BtnAdd("Add Task", Modifier.fillMaxWidth())
    }
    Row(modifier = Modifier.padding(top = 6.dp)) {
      ViewListTasks(listTasks)
    }
  }
}

@Composable
fun InputText(value: String, onValueChange: (String) -> Unit, placeholder: String) {
  Column(modifier = Modifier.fillMaxWidth()) {
    TextField(
      value = value,
      onValueChange = onValueChange,
      label = { Text(placeholder) },
      isError = value.length < 5,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun BtnAdd(text: String, modifier: Modifier) {
  Button(
    onClick = {},
    colors = ButtonDefaults.buttonColors(
      containerColor = Color.Black,
      contentColor = Color.White
    ),
    modifier = modifier
  ) {
    Text(text.uppercase())
  }
}

@Composable
fun ViewListTasks(listTasks: List<Task>) {
  LazyColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = if (listTasks.isEmpty()) Arrangement.Center else Arrangement.Top,
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 28.dp, end = 28.dp)
  ) {
    if (listTasks.isEmpty()) {
      item {
        Text("List empty", style = MaterialTheme.typography.bodyLarge)
      }
    } else {
      itemsIndexed(listTasks) { index, task ->
        Text("${index + 1}. ${task.title}", style = MaterialTheme.typography.bodyLarge)
      }
    }
  }
}