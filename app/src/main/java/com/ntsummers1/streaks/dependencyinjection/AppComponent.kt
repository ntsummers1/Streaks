package com.ntsummers1.streaks.dependencyinjection

import android.app.Application

import com.ntsummers1.streaks.MainActivity
import com.ntsummers1.streaks.data.Database
import com.ntsummers1.streaks.data.dao.TaskDao
import com.ntsummers1.streaks.data.repository.TaskRepository
import com.ntsummers1.streaks.ui.tasks.CreateTaskFragment
import com.ntsummers1.streaks.ui.tasks.EditTaskFragment
import com.ntsummers1.streaks.ui.todo.TodoFragment
import dagger.Component

import javax.inject.Singleton


@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity?)
    fun inject(target: TodoFragment)
    fun inject(target: CreateTaskFragment)
    fun inject(target: EditTaskFragment)
    fun taskDao(): TaskDao?
    fun database(): Database?
    fun taskrepository(): TaskRepository?
    fun application(): Application?
}