package com.example.matchanalysisapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.matchanalysisapp.dao.MatchEventsDao
import com.example.matchanalysisapp.data.ButtonTexts
import com.example.matchanalysisapp.data.MatchEvents
import com.example.matchanalysisapp.data.SettingsData
import com.example.matchanalysisapp.data.Teams

@Database(
    entities = [MatchEvents::class, ButtonTexts::class, Teams::class, SettingsData::class],
    version = 1,
    exportSchema = false
)
abstract class MatchEventsDatabase: RoomDatabase() {

    abstract fun matchEventsDao(): MatchEventsDao

}