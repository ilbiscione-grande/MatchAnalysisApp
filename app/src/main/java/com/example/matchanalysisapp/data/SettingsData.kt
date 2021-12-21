package com.example.matchanalysisapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingsData(
    @PrimaryKey(autoGenerate = true)
    val settingsId: Int?,
    val fullName: String,
    val myClub: String,
    val myTeam: String,
    val division: String,
    val opposition: String,
    val matchLength: String,
    val homeMatch: Boolean
    )