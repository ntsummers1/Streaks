package com.ntsummers1.streaks.dependencyinjection

import android.app.Application
import android.provider.ContactsContract
import androidx.room.Room
import com.ntsummers1.streaks.data.Database
import com.ntsummers1.streaks.data.dao.TaskDao
import com.ntsummers1.streaks.data.repository.TaskDataSource
import com.ntsummers1.streaks.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application?) {

    private val database: Database

    @Singleton
    @Provides
    fun providesRoomDatabase(): Database {
        return database
    }

    @Singleton
    @Provides
    fun providesTaskDao(database: Database): TaskDao {
        return database.getTaskDao()
    }

    @Singleton
    @Provides
    fun taskRepository(taskDao: TaskDao): TaskRepository {
        return TaskDataSource(taskDao)
    }

    init {
        database =
            Room.databaseBuilder(mApplication!!, Database::class.java, "database").build()
    }
}