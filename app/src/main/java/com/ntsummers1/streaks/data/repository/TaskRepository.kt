package com.ntsummers1.streaks.data.repository

import androidx.lifecycle.LiveData
import com.ntsummers1.streaks.data.entity.Task
import java.util.*


interface TaskRepository{
    suspend fun findById(id: Int): LiveData<Task>

    suspend fun findAll(): LiveData<List<Task>>

    suspend fun findByDate(givenDate: Date): LiveData<List<Task>>

    suspend fun insert(task: Task?): Long

    suspend fun delete(task: Task?): Int

    suspend fun deleteAll()
}