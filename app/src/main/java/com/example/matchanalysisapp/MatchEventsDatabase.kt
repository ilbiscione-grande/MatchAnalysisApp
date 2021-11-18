package com.example.matchanalysisapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.matchanalysisapp.dao.MatchEventsDao
import com.example.matchanalysisapp.data.MatchEvents

@Database(
    entities = [MatchEvents::class],
    version = 1,
    exportSchema = false
)
abstract class MatchEventsDatabase: RoomDatabase() {

    abstract fun matchEventsDao(): MatchEventsDao

}