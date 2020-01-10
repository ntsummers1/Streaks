package com.ntsummers1.streaks.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ntsummers1.streaks.data.entity.Task
import java.util.*


@Dao
interface TaskDao {
    @Query("SELECT * FROM Task WHERE id=:id")
    fun findById(id: Int): LiveData<Task>

    @Query("SELECT * FROM Task")
    fun findAll(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE startDate <= :givenDate AND (endDate >=:givenDate OR endDate IS NULL)")
    fun findByDate(givenDate: Date): LiveData<List<Task>>

    @Query("UPDATE Task set title = :title, description = :description, startDate = :startDate, endDate = :endDate where id = :id")
    fun updateTask(id: Int, title: String, description: String?, startDate: Date, endDate: Date?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task?): Long

    @Delete
    fun delete(task: Task?): Int

    @Query("DELETE FROM Task")
    fun deleteAll()
}