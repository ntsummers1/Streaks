package com.ntsummers1.streaks.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ntsummers1.streaks.data.entity.Task
import com.ntsummers1.streaks.data.repository.TaskRepository
import com.ntsummers1.streaks.ui.todo.TodoViewModel
import javax.inject.Inject

class CreateTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    suspend fun insertTask(task: Task) {
        taskRepository.insert(task)
    }
}

class CreateTaskViewModelFactory @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateTaskViewModel(taskRepository) as T
    }
}