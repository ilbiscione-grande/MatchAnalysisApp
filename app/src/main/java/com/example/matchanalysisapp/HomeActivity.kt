package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.room.Room
import com.example.matchanalysisapp.data.ButtonTexts
import com.example.matchanalysisapp.data.SettingsData
import com.example.matchanalysisapp.data.Teams
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.imageView3
import kotlinx.android.synthetic.main.activity_home.iv_btnStats
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    lateinit var db: MatchEventsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val spinner_matchlength_arrayAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, R.layout.spinner_list_match_length, resources.getStringArray(R.array.MatchLength))
        spinner_matchlength_arrayAdapter.setDropDownViewResource(R.layout.spinner_list_match_length)
        spinner_matchLength.adapter = spinner_matchlength_arrayAdapter

        val spinner_homeaway_arrayAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, R.layout.spinner_list_match_length, resources.getStringArray(R.array.HomeAway))
        spinner_homeaway_arrayAdapter.setDropDownViewResource(R.layout.spinner_list_match_length)
        spinner_matchHomeAway.adapter = spinner_homeaway_arrayAdapter


        db = Room.databaseBuilder(
            applicationContext,
            MatchEventsDatabase::class.java,
            "MatchEvents-DB"
        ).build()



        GlobalScope.launch {

            if (db.matchEventsDao().getAllButtons().isEmpty()) {


                val newButton = ButtonTexts(
                    1,
                    "Button1",
                    "AVSLUT"
                )
                val newButton2 = ButtonTexts(
                    2,
                    "Button2",
                    "BOLLVINST"
                )
                val newButton3 = ButtonTexts(
                    3,
                    "Button3",
                    "KONTRING"
                )
                val newButton4 = ButtonTexts(
                    4,
                    "Button4",
                    "DJUPLEDSBOLL"
                )

                db.matchEventsDao().insertButton(newButton)
                db.matchEventsDao().insertButton(newButton2)
                db.matchEventsDao().insertButton(newButton3)
                db.matchEventsDao().insertButton(newButton4)





            }


            val savedSettings = db.matchEventsDao().getAllSettings()

            if (db.matchEventsDao().getAllSettings().isNotEmpty()) {

                et_fullName.setText(savedSettings[0].fullName)
                et_myClub.setText(savedSettings[0].myClub)
                et_myTeam.setText(savedSettings[0].myTeam)
                et_division.setText(savedSettings[0].division)
                et_oppostionTeam.setText(savedSettings[0].opposition)

                if (savedSettings[0].homeMatch) {
                    radio_Yes.isChecked = true
                }else {
                    radio_No.isChecked = true
                }

                if (savedSettings[0].fullName.isNotEmpty()) {
                    tv_profile_teamName4.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
                if (savedSettings[0].myTeam.isNotEmpty()) {
                    tv_profile_teamName9.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
                if (savedSettings[0].myClub.isNotEmpty()) {
                    tv_profile_teamName6.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
                if (savedSettings[0].division.isNotEmpty()) {
                    tv_profile_teamName19.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
                if (savedSettings[0].opposition.isNotEmpty()) {
                    tv_profile_teamName26.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
/*
                if (savedSettings[0].matchLength.isNotEmpty()) {
                    tv_profile_teamName4.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
                if (savedSettings[0].homeMatch.isNotEmpty()) {
                    tv_profile_teamName4.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
*/

            }


        }




        btn_saveSettings.setOnClickListener {


            var homeMatch = false

            if (radio_Yes.isChecked) {

                homeMatch = true
            }


                val newSetting = SettingsData(
                    0,
                    "${et_fullName.text.toString()}",
                    "${et_myClub.text.toString()}",
                    "${et_myTeam.text.toString()}",
                    "${et_division.text.toString()}",
                    "${et_oppostionTeam.text.toString()}",
                    "2x45",
                    homeMatch
                    )
                val newTeam = Teams(
                    0,
                    "${et_myClub.text.toString()}",
                    "${et_myTeam.text.toString()}"
                    )


            GlobalScope.launch {

                db.matchEventsDao().insertSetting(newSetting)
                db.matchEventsDao().insertTeam(newTeam)

                withContext(Dispatchers.Main) {

                    if (et_fullName.text.toString().isNotEmpty()) {
                        tv_profile_teamName4.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
                    if (et_myClub.text.toString().isNotEmpty()) {
                        tv_profile_teamName9.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
                    if (et_myTeam.text.toString().isNotEmpty()) {
                        tv_profile_teamName6.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
                    if (et_division.text.toString().isNotEmpty()) {
                        tv_profile_teamName19.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
                    if (et_oppostionTeam.text.toString().isNotEmpty()) {
                        tv_profile_teamName26.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
/*
                    if (spinner_matchLength.text.toString().isNotEmpty()) {
                        tv_profile_teamName9.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
                    if (homeMatch.toString().isNotEmpty()) {
                        tv_profile_teamName9.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
*/
                }

            }

            Toast.makeText(this, "Inställningarna har sparats!", Toast.LENGTH_LONG).show()

        }

        iv_btnStats.setOnClickListener {
            intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        imageView3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}