package com.samllo.testsqlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samllo.testsqlite.ui.ScreenTask
import com.samllo.testsqlite.ui.TaskViewModel
import com.samllo.testsqlite.ui.theme.TestBBDDSQLiteTheme

class MainActivity : ComponentActivity() {
  val viewModel: TaskViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      TestBBDDSQLiteTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          AppTask(
            viewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun AppTask(viewModel: TaskViewModel, modifier: Modifier = Modifier) {
  Surface(modifier = modifier.padding(horizontal = 16.dp)) {
    ScreenTask(viewModel)
  }
}

@Preview(showBackground = true)
@Composable
fun AppTaskPreview() {
  TestBBDDSQLiteTheme {
//    AppTask()
  }
}