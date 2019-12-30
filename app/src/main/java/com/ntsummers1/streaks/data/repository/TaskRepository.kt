package com.ntsummers1.streaks.data.repository

import androidx.lifecycle.LiveData
import com.ntsummers1.streaks.data.entity.Task


interface TaskRepository{
    fun findById(id: Int): LiveData<Task>

    fun findAll(): LiveData<List<Task>>

    fun insert(task: Task?): Long

    fun delete(task: Task?): Int

    fun deleteAll()
}