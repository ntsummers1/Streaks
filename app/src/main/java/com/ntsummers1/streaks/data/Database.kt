package com.ntsummers1.streaks.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ntsummers1.streaks.data.dao.TaskDao
import com.ntsummers1.streaks.data.entity.Task

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

}