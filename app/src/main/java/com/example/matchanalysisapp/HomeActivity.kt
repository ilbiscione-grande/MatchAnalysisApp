package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.room.Room
import com.example.matchanalysisapp.data.ButtonTexts
import com.example.matchanalysisapp.data.FirstTimeUser
import com.example.matchanalysisapp.data.SettingsData
import com.example.matchanalysisapp.data.Teams
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.imageView3
import kotlinx.android.synthetic.main.activity_home.iv_btnStats
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity() {

    lateinit var db: MatchEventsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        var homeMatch = false

        var firstTime_name = ""
        var firstTime_club = ""
        var firstTime_team = ""
        var firstTime_division = ""
        var firstTime_button1 = ""
        var firstTime_button2 = ""
        var firstTime_button3 = ""
        var firstTime_button4 = ""

        var btn_advanced1 = ""
        var btn_advanced2 = ""
        var btn_advanced3 = ""
        var btn_advanced4 = ""

        val spinner_matchlength_arrayAdapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(this, R.layout.spinner_list_match_length, resources.getStringArray(R.array.MatchLength))
        spinner_matchlength_arrayAdapter.setDropDownViewResource(R.layout.spinner_list_match_length)
        spinner_matchLength.adapter = spinner_matchlength_arrayAdapter

        spinner_matchLength.setSelection(0)
        var matchlength_selectedText = resources.getStringArray(R.array.MatchLength)[0]

        spinner_matchLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                matchlength_selectedText = resources.getStringArray(R.array.MatchLength)[position]
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



        btn_save_advancedSettings.setOnClickListener {

            if (et_advancedSettings_button1.text.isNotEmpty()) {
           //     Toast.makeText(this, "Its not empty button 1", Toast.LENGTH_SHORT).show()
                btn_advanced1 = et_advancedSettings_button1.text.toString()
            }
            if (et_advancedSettings_button.text.isNotEmpty()) {
          //      Toast.makeText(this, "Its not empty button 2", Toast.LENGTH_SHORT).show()
                btn_advanced2 = et_advancedSettings_button.text.toString()
            }
            if (et_advancedSettings_button2.text.isNotEmpty()) {
                btn_advanced3 = et_advancedSettings_button2.text.toString()
             //   Toast.makeText(this, "Its not empty button $btn_advanced3", Toast.LENGTH_SHORT).show()
            }
            if (et_advancedSettings_button3.text.isNotEmpty()) {
               // Toast.makeText(this, "Its not empty button 1", Toast.LENGTH_SHORT).show()
                btn_advanced4 = et_advancedSettings_button3.text.toString()
            }

            GlobalScope.launch {

            delay(200)


                val updateButton = ButtonTexts(
                    1,
                    "button1",
                    "$btn_advanced1"
                )
                val updateButton2 = ButtonTexts(
                    2,
                    "button2",
                    "$btn_advanced2"
                )
                val updateButton3 = ButtonTexts(
                    3,
                    "button3",
                    "$btn_advanced3"
                )
                val updateButton4 = ButtonTexts(
                    4,
                    "button4",
                    "$btn_advanced4"
                )

                db.matchEventsDao().insertButton(updateButton)
                db.matchEventsDao().insertButton(updateButton2)
                db.matchEventsDao().insertButton(updateButton3)
                db.matchEventsDao().insertButton(updateButton4)



            }

            Toast.makeText(this, "Inställningarna har sparats", Toast.LENGTH_SHORT).show()

        }






        iv_close_advancedSettings.setOnClickListener {
            layout_cv_advancedSettings.visibility = INVISIBLE
        }







        GlobalScope.launch {

             var currentUser = db.matchEventsDao().getAllUsers()

            if (currentUser[0].FirstTime == "false") {

                var currentUserId = currentUser[0].userId

                withContext(Dispatchers.Main) {

                    //Toast.makeText(applicationContext, "$currentUserId", Toast.LENGTH_SHORT).show()
                    layout_cv_firstStart.visibility = GONE
                    layout_cv_firstStart_2.visibility = GONE
                    layout_cv_firstStart_3.visibility = GONE
                    layout_cv_main.visibility = VISIBLE

                }
            }


            if (db.matchEventsDao().getAllButtons().isEmpty()) {


/*
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



*/

            }else {

                var buttons = db.matchEventsDao().getAllButtons()


                btn_advanced1 = buttons[0].buttonText
                btn_advanced2 = buttons[1].buttonText
                btn_advanced3 = buttons[2].buttonText
                btn_advanced4 = buttons[3].buttonText


                withContext(Dispatchers.Main) {

                et_advancedSettings_button1.setText("$btn_advanced1")
                et_advancedSettings_button.setText("$btn_advanced2")
                et_advancedSettings_button2.setText("$btn_advanced3")
                et_advancedSettings_button3.setText("$btn_advanced4")

                }



            }


            val savedSettings = db.matchEventsDao().getAllSettings()

            if (db.matchEventsDao().getAllSettings().isNotEmpty()) {

                withContext(Dispatchers.Main) {
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

                when (savedSettings[0].matchLength) {
                    "2x45 minuter" -> {
                        spinner_matchLength.setSelection(0)
                    }
                    "2x40 minuter" -> {
                        spinner_matchLength.setSelection(1)
                    }
                    "2x35 minuter" -> {
                        spinner_matchLength.setSelection(2)
                    }
                    "2x30 minuter" -> {
                        spinner_matchLength.setSelection(3)
                    }
                    "3x25 minuter" -> {
                        spinner_matchLength.setSelection(4)
                    }
                    "3x20 minuter" -> {
                        spinner_matchLength.setSelection(5)
                    }
                    "2x20 minuter" -> {
                        spinner_matchLength.setSelection(6)
                    }
                    "2x15 minuter" -> {
                        spinner_matchLength.setSelection(7)
                    }
                }

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


        iv_advanced_settings.setOnClickListener {
            layout_cv_advancedSettings.visibility = VISIBLE
        }



        btn_saveSettings.setOnClickListener {



            if (homeAway_selectedText == "Hemmamatch") {

                homeMatch = true
            }


                val newSetting = SettingsData(
                    1,
                    "${et_fullName.text.toString()}",
                    "${et_myClub.text.toString()}",
                    "${et_myTeam.text.toString()}",
                    "${et_division.text.toString()}",
                    "${et_oppostionTeam.text.toString()}",
                    "$matchlength_selectedText",
                    homeMatch
                    )
                val newTeam = Teams(
                    1,
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


        tv_firstTime_next.setOnClickListener {
            layout_cv_firstStart.visibility = GONE

            firstTime_name = et_firstTime_name.text.toString()
            firstTime_club = et_firstTime_club.text.toString()
            firstTime_team = et_firstTime_team.text.toString()
            firstTime_division = et_firstTime_division.text.toString()

           // Toast.makeText(this, "Namn: $firstTime_name, klubb: $firstTime_club, lag: $firstTime_team, division: $firstTime_division", Toast.LENGTH_SHORT).show()

        }

        tv_firstTime_next2.setOnClickListener {
            layout_cv_firstStart_2.visibility = GONE

            firstTime_button1 = et_firstTime_button1.text.toString()
            firstTime_button2 = et_firstTime_button2.text.toString()
            firstTime_button3 = et_firstTime_button3.text.toString()
            firstTime_button4 = et_firstTime_button4.text.toString()

        }

        tv_firstTime_finished_close.setOnClickListener {
            layout_cv_firstStart_3.visibility = GONE
            layout_cv_main.visibility = VISIBLE

/**/            // UPDATE FIRSTTIMEUSER TO FALSE SINCE SETTINGS NOW HAVE BEEN SAVED
/**/            GlobalScope.launch {
/**/
/**/            var updateUser = FirstTimeUser(
/**/                1,
/**/                "false"
/**/            )
/**/
/**/            db.matchEventsDao().insertUser(updateUser)

                var newSettings = SettingsData(
                    1,
                    firstTime_name,
                    firstTime_club,
                    firstTime_team,
                    firstTime_division,
                    "",
                    "2x45",
                    true
                )

            db.matchEventsDao().insertSetting(newSettings)

            var firstTimeButton1 = ButtonTexts(
                1,
                "button1",
                firstTime_button1
            )
            var firstTimeButton2 = ButtonTexts(
                2,
                "button2",
                firstTime_button2
            )
            var firstTimeButton3 = ButtonTexts(
                3,
                "button3",
                firstTime_button3
            )
            var firstTimeButton4 = ButtonTexts(
                4,
                "button4",
                firstTime_button4
            )

            db.matchEventsDao().insertButton(firstTimeButton1, firstTimeButton2, firstTimeButton3, firstTimeButton4)


            withContext(Dispatchers.Main) {
            et_fullName.setText("$firstTime_name")
            et_myClub.setText("$firstTime_club")
            et_myTeam.setText("$firstTime_team")
            et_division.setText("$firstTime_division")
            }
/**/
/**/            }
/**/        }
/**/        // END OF UPDATING FIRSTTIMEUSER



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