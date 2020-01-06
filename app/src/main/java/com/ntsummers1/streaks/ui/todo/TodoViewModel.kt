package com.ntsummers1.streaks.ui.todo

import android.util.EventLogTags
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ntsummers1.streaks.data.entity.Task
import com.ntsummers1.streaks.data.repository.TaskRepository
import com.ntsummers1.streaks.utils.lazyDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TodoViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val tasks by lazyDeferred {
        taskRepository.findAll()
    }

    suspend fun findByDate(givenDate: Date): Deferred<LiveData<List<Task>>> {
        return GlobalScope.async {
            taskRepository.findByDate(givenDate)
        }
    }

    suspend fun deleteTasks() {
        taskRepository.deleteAll()
    }
}


class ToDoViewModelFactory @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(taskRepository) as T
    }
}
