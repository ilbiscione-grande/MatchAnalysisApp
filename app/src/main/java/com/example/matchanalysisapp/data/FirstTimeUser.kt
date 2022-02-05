package com.example.matchanalysisapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FirstTimeUser(
    @PrimaryKey(autoGenerate = true)
    val userId: Int?,
    val FirstTime: String
    )