package com.ntsummers1.streaks.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Task constructor(private var title: String, private var description: String?) {

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

}