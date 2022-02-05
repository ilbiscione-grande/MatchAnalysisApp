package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.example.matchanalysisapp.data.ButtonTexts
import com.example.matchanalysisapp.data.FirstTimeUser
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LandingActivity : AppCompatActivity() {

    lateinit var db: MatchEventsDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        db = Room.databaseBuilder(
            applicationContext,
            MatchEventsDatabase::class.java,
            "MatchEvents-DB"
        ).build()


        GlobalScope.launch {

            if (db.matchEventsDao().getAllUsers().isEmpty()) {
                val newUser = FirstTimeUser(
                    1,
                    "true"
                )

                db.matchEventsDao().insertUser(newUser)
            }


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
        }

       // var allusers = db.matchEventsDao().getAllButtons()

/*
        if (db.matchEventsDao().getAllUsers().isEmpty()) {

            val newUser = FirstTimeUser(
                1,
                "true"
            )

            GlobalScope.launch {
                db.matchEventsDao().insertUser(newUser)
            }

        }
*/


        iv_btnStart.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }
}