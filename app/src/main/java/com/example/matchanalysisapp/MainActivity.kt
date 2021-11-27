package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View.VISIBLE
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.matchanalysisapp.MatchEventsDatabase
import com.example.matchanalysisapp.data.MatchEvents
import com.example.matchanalysisapp.StatsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger.global

class MainActivity : AppCompatActivity() {

    lateinit var db: MatchEventsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv_myTeam = "Hvetlanda Gif"

        val btnStats = findViewById<ImageView>(R.id.iv_btnStats)

        val btnCustomButton1 = findViewById<TextView>(R.id.tv_cutom_button_1)
        val btnCustomButton2 = findViewById<TextView>(R.id.tv_cutom_button_2)
        val btnCustomButton3 = findViewById<TextView>(R.id.tv_cutom_button_3)
        val btnCustomButton4 = findViewById<TextView>(R.id.tv_cutom_button_4)

        val tv_liveTickerTime = findViewById<TextView>(R.id.tv_liveTickerTime)
        val tv_liveTickerText = findViewById<TextView>(R.id.tv_liveTickerText)
        val tv_liveTickerTeam = findViewById<TextView>(R.id.tv_liveTickerTeam)

        val btnPlayPauseButton = findViewById<ImageView>(R.id.btn_playPause)
        val btnStopButton = findViewById<ImageView>(R.id.btn_stop)
        var tvHalfText = findViewById<TextView>(R.id.tv_half_text)
        var tvHalf1 = findViewById<ImageView>(R.id.tv_half_1)

        val c_meter = findViewById<Chronometer>(R.id.c_meter)
        var chronoStatus = "notActive"
        var chronoTime: Int = 0


        var matchStatus = "notStarted"
        var currentHalf = 0
        var nrOfPeriods = 2

        var currentMinute = "00"
        var currentSecond = "00"
        val todaysDate = SimpleDateFormat("yy/MM/dd")


        var eventOpposition = "Motståndare"



        db = Room.databaseBuilder(
            applicationContext,
            MatchEventsDatabase::class.java,
            "MatchEvents-DB"
        ).build()


/*
        GlobalScope.launch(Dispatchers.Default) {
            val currentMatch = db.matchEventsDao().getAllMatchEvents()

            withContext(Dispatchers.Main) {
                // if we use `Dispatchers.Main` as a coroutine context next two lines will be executed on UI thread.
                if (currentMatch.isNotEmpty()) {

                    photoView.visibility = VISIBLE

                    }
                }
            }
*/

            // BUTTON PLAY AND PAUSE THE CLOCK
            btnPlayPauseButton.setOnClickListener {


                if (matchStatus == "notStarted") {

                    matchStatus = "started"
                    currentHalf = currentHalf + 1

                    tvHalf1.setImageResource(R.drawable.halves_first_half)
                    tv_liveTickerTime.text = "00:00"
                    tv_liveTickerText.text = "Matchen startar\n"
                    tv_liveTickerTeam.text = tv_myTeam


                    if (chronoStatus == "paused") {
                        c_meter.base = SystemClock.elapsedRealtime() - chronoTime
                    } else if (chronoStatus == "started") {

                    } else {
                        c_meter.base = SystemClock.elapsedRealtime()
                    }

                    c_meter.start()
                    chronoStatus = "started"


                } else {

                    if (matchStatus == "paused") {

                        matchStatus = "started"

                        var currentChrono = c_meter.text.toString()
                        var chronoMinutes = c_meter.text.toString().substringBefore(":").toInt()
                        var chronoSeconds = c_meter.text.toString().substringAfter(":").toInt()
                        chronoTime = (60 * chronoMinutes) + (1000 * chronoSeconds)



                        tv_liveTickerText.text =
                            "Klockan har startats igen\n${tv_liveTickerText.text}"
                        tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                        tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                        c_meter.base = SystemClock.elapsedRealtime() - chronoTime


                        c_meter.start()
                        chronoStatus = "started"


                    } else if (matchStatus == "stopped") {


                        matchStatus = "started"


                        tvHalf1.setImageResource(R.drawable.halves_second_half)

                        var currentChrono = c_meter.text.toString()
                        var chronoMinutesDb = c_meter.text.toString().substringBefore(":").toInt()
                        var chronoSecondsDb = c_meter.text.toString().substringAfter(":").toInt()


                        tv_liveTickerText.text = "2a halvlek startar\n${tv_liveTickerText.text}"
                        tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                        tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                        if (chronoStatus == "paused") {
                            c_meter.base = SystemClock.elapsedRealtime() - chronoTime
                        } else if (chronoStatus == "started") {

                        } else {
                            c_meter.base = SystemClock.elapsedRealtime()
                        }

                        c_meter.start()
                        chronoStatus = "started"


                    } else if (matchStatus == "started") {

                        matchStatus = "paused"


                        var currentChrono = c_meter.text.toString()
                        var chronoMinutesDb = c_meter.text.toString().substringBefore(":").toInt()
                        var chronoSecondsDb = c_meter.text.toString().substringAfter(":").toInt()

                        tv_liveTickerText.text = "Klockan har pausats\n${tv_liveTickerText.text}"
                        tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                        tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                        chronoStatus = "paused"
                        c_meter.stop()


                    }

                }
            }


            // BUTTON STOP THE CLOCK
            btnStopButton.setOnClickListener {

                var currentChrono = c_meter.text.toString()
                var chronoMinutesDb = c_meter.text.toString().substringBefore(":").toInt()
                var chronoSecondsDb = c_meter.text.toString().substringAfter(":").toInt()

                c_meter.stop()
                c_meter.base = SystemClock.elapsedRealtime()


                chronoStatus = "reset"


                if (currentHalf == 1) {
                    tvHalf1.setImageResource(R.drawable.halves_half_time)

                    tv_liveTickerText.text = "1a halvlek är slut\n${tv_liveTickerText.text}"
                    tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                } else if (currentHalf == 2) {
                    tvHalf1.setImageResource(R.drawable.halves_end)

                    tv_liveTickerText.text = "Matchen är slut\n${tv_liveTickerText.text}"
                    tv_liveTickerTime.text =
                        "$currentMinute:$currentSecond\n${tv_liveTickerTime.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"

                }

                if (matchStatus == "notStarted") {
                    Toast.makeText(
                        this,
                        "Matchen måste startas innan du kan stoppa den.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // OM MATCHEN HAR STARTAT SÅ HÄNDER NEDANSTÅENDE, INNAN DESS FUNGERAR INTE STOPP-KNAPPEN
                if (matchStatus != "notStarted") {
                    matchStatus = "stopped"
                    currentHalf = currentHalf + 1

                    //OM HALVLEK/PERIOD GÅR ÖVER FÖRBESTÄMT ANTAL TAR MATCHEN SLUT, PROMPTA OM ATT STARTA NY MATCH
                    if (currentHalf > nrOfPeriods) {

                        //   tvHalfText.text = "end of match"
                        matchStatus = "finished"

                        //PROMPTA ANVÄNDAREN OM ATT STARTA NY MATCH
                    }


                }
            }


            // BUTTON 1
            btnCustomButton1.setOnClickListener {
                btnCustomButton1.setBackgroundResource(R.drawable.button_left_side_pushed)

                Handler().postDelayed({
                    btnCustomButton1.setBackgroundResource(R.drawable.button_left_side)
                }, 50)

                if (matchStatus != "notStarted" && matchStatus != "paused" && matchStatus != "stopped" && matchStatus != "finished") {

                    var currentChrono = c_meter.text.toString()
                    var chronoMinutesDb: String = c_meter.text.toString().substringBefore(":")
                    var chronoSecondsDb: String = c_meter.text.toString().substringAfter(":")

                    tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                    tv_liveTickerText.text = "${btnCustomButton1.text}\n${tv_liveTickerText.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                    val newEvent = MatchEvents(
                        todaysDate.format(Date()),
                        1,
                        null,
                        "$currentChrono",
                        btnCustomButton1.text.toString(),
                        "Hvetlanda Gif",
                        "$eventOpposition"
                    )

                    Toast.makeText(this, "${todaysDate.format(Date())}", Toast.LENGTH_SHORT).show()

                    GlobalScope.launch {
                        delay(50)
                        db.matchEventsDao().insertMatchEvent(newEvent)
                    }


                } else {
                    Toast.makeText(
                        this,
                        "Matchen måste startas innan du kan lägga till händelser.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }


            // BUTTON 2
            btnCustomButton2.setOnClickListener {
                btnCustomButton2.setBackgroundResource(R.drawable.button_left_side_pushed)
                Handler().postDelayed({
                    btnCustomButton2.setBackgroundResource(R.drawable.button_left_side)
                }, 50)

                if (matchStatus != "notStarted" && matchStatus != "paused" && matchStatus != "stopped" && matchStatus != "finished") {

                    var currentChrono = c_meter.text.toString()
                    var chronoMinutesDb = c_meter.text.toString().substringBefore(":").toInt()
                    var chronoSecondsDb = c_meter.text.toString().substringAfter(":").toInt()

                    tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                    tv_liveTickerText.text = "${btnCustomButton2.text}\n${tv_liveTickerText.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                    val newEvent = MatchEvents(
                        todaysDate.format(Date()),
                        1,
                        null,
                        "$currentChrono",
                        btnCustomButton2.text.toString(),
                        "Hvetlanda Gif",
                        "$eventOpposition"
                    )

                    Toast.makeText(this, "$currentChrono", Toast.LENGTH_SHORT).show()

                    GlobalScope.launch {
                        delay(50)
                        db.matchEventsDao().insertMatchEvent(newEvent)
                    }


                } else {
                    Toast.makeText(
                        this,
                        "Matchen måste startas innan du kan lägga till händelser.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }


            // BUTTON 3
            btnCustomButton3.setOnClickListener {
                btnCustomButton3.setBackgroundResource(R.drawable.button_left_side_pushed)

                Handler().postDelayed({
                    btnCustomButton3.setBackgroundResource(R.drawable.button_left_side)
                }, 50)
                //Timer("SettingUp", false).schedule(50) {
                //btnCustomButton2.setBackgroundResource(R.drawable.button_left_side)

                if (matchStatus != "notStarted" && matchStatus != "paused" && matchStatus != "stopped" && matchStatus != "finished") {

                    var currentChrono = c_meter.text.toString()
                    var chronoMinutesDb = c_meter.text.toString().substringBefore(":").toInt()
                    var chronoSecondsDb = c_meter.text.toString().substringAfter(":").toInt()

                    tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                    tv_liveTickerText.text = "${btnCustomButton3.text}\n${tv_liveTickerText.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                    val newEvent = MatchEvents(
                        todaysDate.format(Date()),
                        1,
                        null,
                        "$currentChrono",
                        btnCustomButton3.text.toString(),
                        "Hvetlanda Gif",
                        "$eventOpposition"
                    )

                    Toast.makeText(this, "$currentChrono", Toast.LENGTH_SHORT).show()

                    GlobalScope.launch {
                        delay(50)
                        db.matchEventsDao().insertMatchEvent(newEvent)
                    }


                } else {
                    Toast.makeText(
                        this,
                        "Matchen måste startas innan du kan lägga till händelser.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }


            // BUTTON 4
            btnCustomButton4.setOnClickListener {
                btnCustomButton4.setBackgroundResource(R.drawable.button_left_side_pushed)

                Handler().postDelayed({
                    btnCustomButton4.setBackgroundResource(R.drawable.button_left_side)
                }, 50)


                if (matchStatus != "notStarted" && matchStatus != "paused" && matchStatus != "stopped" && matchStatus != "finished") {


                    var currentChrono = c_meter.text.toString()
                    var chronoMinutesDb = c_meter.text.toString().substringBefore(":").toInt()
                    var chronoSecondsDb = c_meter.text.toString().substringAfter(":").toInt()

                    tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                    tv_liveTickerText.text = "${btnCustomButton4.text}\n${tv_liveTickerText.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                    val newEvent = MatchEvents(
                        todaysDate.format(Date()),
                        1,
                        null,
                        "$currentChrono",
                        btnCustomButton4.text.toString(),
                        "Hvetlanda Gif",
                        "$eventOpposition"
                    )

                    Toast.makeText(this, "$chronoMinutesDb:$chronoSecondsDb", Toast.LENGTH_SHORT)
                        .show()

                    GlobalScope.launch {
                        delay(50)
                        db.matchEventsDao().insertMatchEvent(newEvent)
                    }


                } else {
                    Toast.makeText(
                        this,
                        "Matchen måste startas innan du kan lägga till händelser.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }



            btnStats.setOnClickListener() {
                intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
            }


        }




}