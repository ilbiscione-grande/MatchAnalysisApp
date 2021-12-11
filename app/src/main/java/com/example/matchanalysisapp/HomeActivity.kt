package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.matchanalysisapp.data.ButtonTexts
import com.example.matchanalysisapp.data.SettingsData
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

            }


        }




        btn_saveSettings.setOnClickListener {


                val newSetting = SettingsData(
                    0,
                    "${et_fullName.text.toString()}",
                    "${et_myClub.text.toString()}",
                    "${et_myTeam.text.toString()}",
                    "${et_division.text.toString()}",
                    "${et_oppostionTeam.text.toString()}",
                    "2x45",

                    )

            GlobalScope.launch {

                db.matchEventsDao().insertSetting(newSetting)

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