package com.samllo.testsqlite.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.samllo.testsqlite.data.Task
import com.samllo.testsqlite.data.TaskDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Clase ViewModel que extiende de AndroidViewModel para manejar la lógica de la aplicación relacionada con las tareas
 * Contiene una referencia al DAO de tareas para acceder a la base de datos, una propiedad que devuelve la lista de
 * tareas como un Flow y funciones para agregar y eliminar tareas
 * @param application el contexto de la aplicación, necesario para obtener la instancia de la base de datos
 */
class TaskViewModel(application: Application) : AndroidViewModel(application) {
  private val dao = TaskDataBase.getDataBase(application).taskDAO()
  val tasks: Flow<List<Task>> = dao.getTasks()

  /**
   * Función para agregar una tarea a la base de datos, se ejecuta en un coroutine dentro del viewModelScope
   * para evitar bloquear el hilo principal
   * @param text el título de la tarea a agregar
   */
  fun appTask(text: String) {
    viewModelScope.launch { dao.insert(Task(title = text)) }
  }

  /**
   * Función para eliminar una tarea de la base de datos, se ejecuta en un coroutine dentro del viewModelScope
   * para evitar bloquear el hilo principal
   * @param task la tarea a eliminar
   */
  fun deleteTask(task: Task) {
    viewModelScope.launch { dao.delete(task) }
  }
}