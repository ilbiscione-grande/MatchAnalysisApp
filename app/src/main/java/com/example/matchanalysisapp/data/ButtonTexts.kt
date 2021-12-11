package com.example.matchanalysisapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ButtonTexts(
    @PrimaryKey(autoGenerate = true)
    val buttonId: Int?,
    val buttonName: String,
    val buttonText: String
    )