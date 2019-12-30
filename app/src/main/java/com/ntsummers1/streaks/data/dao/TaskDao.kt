package com.ntsummers1.streaks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ntsummers1.streaks.data.entity.Task


@Dao
interface TaskDao {
    @Query("SELECT * FROM Task WHERE id=:id")
    fun findById(id: Int): LiveData<Task>

    @Query("SELECT * FROM Task")
    fun findAll(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task?): Long

    @Delete
    fun delete(task: Task?): Int

    @Query("DELETE FROM Task")
    fun deleteAll()
}