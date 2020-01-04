package com.ntsummers1.streaks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Task constructor(
    private var title: String,
    private var startDate: Date = Calendar.getInstance().time
) {
    private var description: String ?= ""
    private var endDate: Date ?= null

    @PrimaryKey(autoGenerate = true)
    private var id = 0

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun setStartDate(startDate: Date) {
        this.startDate = startDate
    }

    fun getStartDate(): Date {
        return startDate
    }

    fun setEndDate(endDate: Date?) {
        this.endDate = endDate
    }

    fun getEndDate(): Date? {
        return endDate
    }

}