package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
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
import kotlinx.android.synthetic.main.activity_stats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    lateinit var db: MatchEventsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        var homeMatch = false

        val spinner_matchlength_arrayAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, R.layout.spinner_list_match_length, resources.getStringArray(R.array.MatchLength))
        spinner_matchlength_arrayAdapter.setDropDownViewResource(R.layout.spinner_list_match_length)
        spinner_matchLength.adapter = spinner_matchlength_arrayAdapter

        spinner_matchLength.setSelection(0)
        var matchlength_selectedText = resources.getStringArray(R.array.MatchLength)[0]

        spinner_matchLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                matchlength_selectedText = resources.getStringArray(R.array.MatchLength)[position]
                //Toast.makeText(applicationContext, matchlength_selectedText, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        val spinner_homeaway_arrayAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, R.layout.spinner_list_match_length, resources.getStringArray(R.array.HomeAway))
        spinner_homeaway_arrayAdapter.setDropDownViewResource(R.layout.spinner_list_match_length)
        spinner_matchHomeAway.adapter = spinner_homeaway_arrayAdapter

        spinner_matchHomeAway.setSelection(0)
        var homeAway_selectedText = resources.getStringArray(R.array.HomeAway)[0]

        spinner_matchHomeAway.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                homeAway_selectedText = resources.getStringArray(R.array.HomeAway)[position]
                //Toast.makeText(applicationContext, homeAway_selectedText, Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


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
                    "BOLLVINST VID HÖG PRESS"
                )
                val newButton3 = ButtonTexts(
                    3,
                    "Button3",
                    "KONTRING"
                )
                val newButton4 = ButtonTexts(
                    4,
                    "Button4",
                    "DJUPLEDSBOLL SOM LEDER TILL AVSLUT ELLER INLÄGG"
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
                    spinner_matchHomeAway.setSelection(0)
                }else {
                    spinner_matchHomeAway.setSelection(1)
                }

                if (savedSettings[0].matchLength == "2x45 minuter") {
                    spinner_matchLength.setSelection(0)
                }else if (savedSettings[0].matchLength == "2x40 minuter") {
                    spinner_matchLength.setSelection(1)
                }else if (savedSettings[0].matchLength == "2x35 minuter") {
                    spinner_matchLength.setSelection(2)
                }else if (savedSettings[0].matchLength == "2x30 minuter") {
                    spinner_matchLength.setSelection(3)
                }else if (savedSettings[0].matchLength == "3x25 minuter") {
                    spinner_matchLength.setSelection(4)
                }else if (savedSettings[0].matchLength == "3x20 minuter") {
                    spinner_matchLength.setSelection(5)
                }else if (savedSettings[0].matchLength == "2x20 minuter") {
                    spinner_matchLength.setSelection(6)
                }else if (savedSettings[0].matchLength == "2x15 minuter") {
                    spinner_matchLength.setSelection(7)
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

                if (savedSettings[0].matchLength.isNotEmpty()) {
                    tv_profile_teamName36.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                }
                if (savedSettings[0].homeMatch) {
                    tv_profile_teamName46.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)

                }
            }


        }




        btn_saveSettings.setOnClickListener {



            if (homeAway_selectedText == "Hemmamatch") {

                homeMatch = true
            }


                val newSetting = SettingsData(
                    0,
                    "${et_fullName.text.toString()}",
                    "${et_myClub.text.toString()}",
                    "${et_myTeam.text.toString()}",
                    "${et_division.text.toString()}",
                    "${et_oppostionTeam.text.toString()}",
                    "$matchlength_selectedText",
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

                    if (matchlength_selectedText.toString().isNotEmpty()) {
                        tv_profile_teamName9.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }
                    if (homeAway_selectedText.toString().isNotEmpty()) {
                        tv_profile_teamName9.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
                    }

                }

            }

            Toast.makeText(this, "Inställningarna har sparats!", Toast.LENGTH_LONG).show()

        }

        iv_btnStats.setOnClickListener {

            iv_btnStats.setImageResource(R.drawable.ic_baseline_bar_chart_24_pressed)

            Handler().postDelayed({
                iv_btnStats.setImageResource(R.drawable.ic_baseline_bar_chart_24)
            }, 50)



            intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        imageView3.setOnClickListener {

            imageView3.setImageResource(R.drawable.ic_sharp_sports_soccer_24_pressed)

            Handler().postDelayed({
                imageView3.setImageResource(R.drawable.ic_sharp_sports_soccer_24)
            }, 50)



            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}