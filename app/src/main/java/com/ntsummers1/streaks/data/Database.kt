package com.ntsummers1.streaks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ntsummers1.streaks.data.dao.TaskDao
import com.ntsummers1.streaks.data.entity.Task

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile private var instance: com.ntsummers1.streaks.data.Database? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                com.ntsummers1.streaks.data.Database::class.java,
                "database.db"
            ).build()
    }

}