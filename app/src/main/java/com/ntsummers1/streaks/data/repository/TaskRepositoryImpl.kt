package com.ntsummers1.streaks.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.ntsummers1.streaks.data.dao.TaskDao
import com.ntsummers1.streaks.data.entity.Task
import java.util.*
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor(taskDao: TaskDao) : TaskRepository {
    
    private val taskDao: TaskDao

    override suspend fun findById(id: Int): LiveData<Task> {
        return taskDao.findById(id)
    }

    override suspend fun findAll(): LiveData<List<Task>> {
        return taskDao.findAll()
    }

    override suspend fun findByDate(givenDate: Date): LiveData<List<Task>> {
        return taskDao.findByDate(givenDate)
    }

    override suspend fun updateTask(id: Int, title: String, description: String?,
                                    startDate: Date, endDate: Date?) {
        taskDao.updateTask(id, title, description, startDate, endDate)
    }

    override suspend fun insert(task: Task?): Long {
        return taskDao.insert(task)
    }

    override suspend fun delete(task: Task?): Int {
        return taskDao.delete(task)
    }

    override suspend fun deleteAll() {
        taskDao.deleteAll()
    }

    init {
        this.taskDao = taskDao
    }
}