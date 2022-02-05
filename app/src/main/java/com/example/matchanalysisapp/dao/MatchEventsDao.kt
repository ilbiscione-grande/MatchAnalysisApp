package com.example.matchanalysisapp.dao

import androidx.room.*
import com.example.matchanalysisapp.data.*

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




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertButton(vararg buttonTexts: ButtonTexts)


    @Query("SELECT * FROM ButtonTexts ORDER BY buttonId")
    fun getAllButtons() : List<ButtonTexts>

    @Query("SELECT * FROM ButtonTexts WHERE buttonName = :searchString")
    fun searchAllButtons(searchString: String) : List<ButtonTexts>

    @Query("DELETE FROM ButtonTexts")
    fun deleteAllButtons()




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(vararg teams: Teams)

    @Query("SELECT * FROM Teams ORDER BY teamId")
    fun getAllTeams() : List<Teams>

    @Query("SELECT * FROM Teams WHERE teamName = :searchString")
    fun searchAllTeams(searchString: String) : List<Teams>

    @Query("DELETE FROM Teams")
    fun deleteAllTeams()






    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(vararg settingsData: SettingsData)

    @Query("SELECT * FROM SettingsData ORDER BY settingsId ASC")
    fun getAllSettings() : List<SettingsData>

    @Query("SELECT * FROM SettingsData WHERE fullName = :searchString")
    fun searchAllSettings(searchString: String) : List<SettingsData>

    @Query("DELETE FROM SettingsData")
    fun deleteAllSettings()





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(vararg firstTimeUser: FirstTimeUser)

    @Query("SELECT * FROM FirstTimeUser ORDER BY userId DESC")
    fun getAllUsers() : List<FirstTimeUser>



}