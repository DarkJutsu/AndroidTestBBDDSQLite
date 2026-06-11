package com.samllo.testsqlite.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samllo.testsqlite.data.Task
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun ScreenTask(viewModel: TaskViewModel) {
  val listTasks by viewModel.tasks.collectAsState(initial = emptyList())
  var titleTask by remember { mutableStateOf("") }

  Column(modifier = Modifier.fillMaxWidth()) {
    Text(text = "Task list", style = MaterialTheme.typography.titleLarge)
    Row(modifier = Modifier.padding(top = 16.dp)) {
      InputText(titleTask, { titleTask = it }, "Title task")
    }
    Row(modifier = Modifier.padding(top = 6.dp)) {
      BtnAdd("Add Task", {
        if (titleTask.isNotEmpty()) {
          viewModel.addTask(titleTask)
          titleTask = ""
        }
      }, Modifier.fillMaxWidth())
    }
    Row(modifier = Modifier.padding(top = 6.dp)) {
      ViewListTasks(listTasks) { viewModel.deleteTask(it) }
    }
  }
}

@Composable
fun InputText(value: String, onValueChange: (String) -> Unit, placeholder: String) {
  Column(modifier = Modifier.fillMaxWidth()) {
    OutlinedTextField(
      value = value,
      onValueChange = onValueChange,
      label = { Text(placeholder) },
      isError = value.length < 5,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
fun BtnAdd(text: String, onClick: () -> Unit, modifier: Modifier) {
  Button(
    onClick = onClick,
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
fun ViewListTasks(listTasks: List<Task>, clickable: (Task) -> Unit) {
  LazyColumn(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = if (listTasks.isEmpty()) Arrangement.Center else Arrangement.Top,
    modifier = Modifier
      .fillMaxSize()
  ) {
    if (listTasks.isEmpty()) {
      item {
        Text("List empty", style = MaterialTheme.typography.bodyLarge)
      }
    } else {
      items(listTasks) { task ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              task.title,
              style = MaterialTheme.typography.bodyLarge
            )
            DeleteBtn { clickable(task) }
          }
        }
      }
    }
  }
}

@Composable
fun DeleteBtn(clickable: () -> Unit) {
  val interactionSource =
    remember { MutableInteractionSource() } // Crear un MutableInteractionSource para rastrear las interacciones del botón
  val isPressed by interactionSource.collectIsPressedAsState() // Obtener el estado de si el botón está siendo presionado
  val pressedListener by rememberUpdatedState(clickable) // Recordar la función de clic actualizada para usarla dentro del efecto de lanzamiento

  /**
   * Efecto de lanzamiento que se ejecuta cada vez que cambia el estado de isPressed. Mientras el
   * botón esté siendo presionado, se ejecutará un bucle que llama a la función de clic cada 100 milisegundos,
   * lo que permite realizar acciones repetitivas mientras el botón esté presionado.
   * El delay se asegura de que la función de clic no se ejecute demasiado rápido, evitando así un
   * comportamiento no deseado o sobrecarga de la función de clic.
   * El uso de rememberUpdatedState garantiza que siempre se utilice la función de clic más reciente,
   * incluso si cambia durante la ejecución del efecto de lanzamiento.
   */
  LaunchedEffect(isPressed) {
    while (isPressed) {
      delay(100L.coerceIn(1L, Long.MAX_VALUE).milliseconds)
      pressedListener()
    }
  }

  IconButton(
    onClick = clickable,
    interactionSource = interactionSource
  ) {
    Icon(
      imageVector = Icons.Default.Delete,
      "Icon delete",
      tint = if (isPressed) Color.Red else Color(253, 109, 109, 255)
    )
  }
}