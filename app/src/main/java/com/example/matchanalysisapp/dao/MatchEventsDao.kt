package com.example.matchanalysisapp.dao

import androidx.room.*
import com.example.matchanalysisapp.data.MatchEvents

@Dao
interface MatchEventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatchEvent(vararg matchEvent: MatchEvents)

    @Query("SELECT * FROM MatchEvents ORDER BY eventID DESC")
    fun getAllMatchEvents() : List<MatchEvents>

    @Query("SELECT * FROM MatchEvents WHERE eventText = :searchString  ORDER BY eventID DESC")
    fun searchAllMatchEvents(searchString: String) : List<MatchEvents>

    @Query("DELETE FROM MatchEvents")
    fun deleteAllMatchEvents()
}