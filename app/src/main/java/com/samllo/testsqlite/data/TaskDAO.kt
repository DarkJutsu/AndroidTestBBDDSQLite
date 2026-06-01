package com.samllo.testsqlite.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao // Anotación para indicar que esta interfaz es un Data Access Object (DAO) para la entidad Task
interface TaskDAO {
  /**
   * Funcion que devuelve una lista de tareas ordenada por fecha de creación de forma descendente
   * @return Flow<List<Task>> una lista de tareas ordenada por fecha de creación de forma descendente
   */
  @Query("SELECT * FROM tasks ORDER BY initDate DESC") // Consulta SQL para obtener todas las tareas ordenadas por fecha de creación de forma descendente
  fun getTasks(): Flow<List<Task>>

  /**
   * Funcion que inserta una tarea en la base de datos
   * @param task la tarea a insertar
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE) // Anotación para indicar que se debe reemplazar la tarea si ya existe una con el mismo id o en caso de conflicto
  suspend fun insert(task: Task)

  /**
   * Funcion que elimina una tarea de la base de datos
   * @param task la tarea a eliminar
   */
  @Delete
  suspend fun delete(task: Task)
}