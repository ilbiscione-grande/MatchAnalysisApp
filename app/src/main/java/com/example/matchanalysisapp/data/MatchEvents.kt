package com.example.matchanalysisapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MatchEvents(
    val matchDate : String,
    val matchID: Int,
    @PrimaryKey(autoGenerate = true)
    val eventID: Int?,
    val eventTime: String,
    val eventText: String,
    val eventTeam: String,
    val eventOpposition: String
    )