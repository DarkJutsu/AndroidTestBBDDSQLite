package com.samllo.testsqlite.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Clase abstracta que representa la base de datos de tareas, extiende de RoomDatabase
 * Contiene una función abstracta para obtener el DAO de tareas y un companion object para obtener la instancia de la base de datos
 * @Database(entities = [Task::class], version = 1) indica que esta clase es una base de datos de Room, con la entidad Task y la versión 1
 */
@Database(entities = [Task::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
  /**
   * Función abstracta para obtener el DAO de tareas
   * @return TaskDAO el DAO de tareas
   */
  abstract fun taskDAO(): TaskDAO // Función abstracta para obtener el DAO de tareas

  companion object { // Companion object para obtener la instancia de la base de datos
    @Volatile // Anotación para indicar que esta variable puede ser accedida por varios hilos de forma segura
    private var INSTANCIA: TaskDataBase? =
      null // Variable para almacenar la instancia de la base de datos, inicialmente nula

    /**
     * Función para obtener la instancia de la base de datos, si no existe se crea una nueva instancia
     * @param context el contexto de la aplicación
     * @return TaskDataBase la instancia de la base de datos
     */
    fun getDataBase(context: Context): TaskDataBase {
      return INSTANCIA ?: synchronized(this) {
        Room.databaseBuilder(context.applicationContext, TaskDataBase::class.java, "task_db")
          .build() // Construye la base de datos utilizando Room.databaseBuilder, pasando el contexto de la aplicación, la clase de la base de datos y el nombre de la base de datos
          .also {
            INSTANCIA = it
          } // Asigna la instancia de la base de datos a la variable INSTANCIA y la devuelve
      }
    }
  }
}