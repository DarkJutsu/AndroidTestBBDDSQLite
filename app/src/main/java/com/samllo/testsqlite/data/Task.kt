package com.samllo.testsqlite.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

/**
 * Clase de datos que representa una tarea en la base de datos
 * @param id el id de la tarea, es la clave primaria y se autogenera
 * @param title el título de la tarea
 * @param initDate la fecha de creación de la tarea
 */
@Entity(tableName = "tasks") // Anotación para indicar que esta clase es una entidad de la base de datos y se almacenará en la tabla "tasks"
data class Task(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val title: String,
  val initDate: Date
)