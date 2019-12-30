package com.ntsummers1.streaks.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.ntsummers1.streaks.data.dao.TaskDao
import com.ntsummers1.streaks.data.entity.Task
import javax.inject.Inject


class TaskDataSource @Inject constructor(taskDao: TaskDao) : TaskRepository {
    
    private val taskDao: TaskDao

    override fun findById(id: Int): LiveData<Task> {
        return taskDao.findById(id)
    }


    override fun findAll(): LiveData<List<Task>> {
        return taskDao.findAll()
    }

    override fun insert(task: Task?): Long {
        return taskDao.insert(task)
    }

    override fun delete(task: Task?): Int {
        return taskDao.delete(task)
    }

    override fun deleteAll() {
        deleteAllTasks(taskDao).execute()
    }

    private class deleteAllTasks internal constructor(dao: TaskDao) : AsyncTask<Void?, Void?, Void?>() {

        private val mAsyncTaskDao: TaskDao

        override fun doInBackground(vararg params: Void?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }

        init {
            mAsyncTaskDao = dao
        }
    }

    init {
        this.taskDao = taskDao
    }
}