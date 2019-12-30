package com.ntsummers1.streaks.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ntsummers1.streaks.data.entity.Task
import com.ntsummers1.streaks.data.repository.TaskRepository
import com.ntsummers1.streaks.utils.lazyDeferred
import javax.inject.Inject

class TodoViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val tasks by lazyDeferred {
        taskRepository.findAll()
    }

    suspend fun deleteTasks() {
        taskRepository.deleteAll()
    }

    suspend fun insertTask(task: Task) {
        taskRepository.insert(task)
    }
}


class ToDoViewModelFactory @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(taskRepository) as T
    }
}
