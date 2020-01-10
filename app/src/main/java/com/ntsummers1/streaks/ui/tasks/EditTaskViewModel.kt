package com.ntsummers1.streaks.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ntsummers1.streaks.data.entity.Task
import com.ntsummers1.streaks.data.repository.TaskRepository
import com.ntsummers1.streaks.ui.todo.TodoViewModel
import com.ntsummers1.streaks.utils.lazyDeferred
import java.util.*
import javax.inject.Inject

class EditTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    suspend fun findTaskById(id: Int): LiveData<Task> {
        return taskRepository.findById(id)
    }

    suspend fun updateTask(id: Int, title: String, description: String?, startDate: Date, endDate: Date?) {
        taskRepository.updateTask(id, title, description, startDate, endDate)
    }
}

class EditTaskViewModelFactory @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditTaskViewModel(taskRepository) as T
    }
}