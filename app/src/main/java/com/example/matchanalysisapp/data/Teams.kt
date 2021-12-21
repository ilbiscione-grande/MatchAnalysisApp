package com.example.matchanalysisapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Teams (
    @PrimaryKey(autoGenerate = true)
    val teamId: Int?,
    val clubName: String,
    val teamName: String,
        )
