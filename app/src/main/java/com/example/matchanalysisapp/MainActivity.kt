package com.example.matchanalysisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.matchanalysisapp.MatchEventsDatabase
import com.example.matchanalysisapp.data.MatchEvents
import com.example.matchanalysisapp.StatsActivity
import com.example.matchanalysisapp.data.ButtonTexts
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
        var pushedMenuButton = ""

        val c_meter = findViewById<Chronometer>(R.id.c_meter)
        var chronoStatus = "notActive"
        var chronoTime: Int = 0
        var tv_myTeam = ""


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


        GlobalScope.launch {
            val buttons = db.matchEventsDao().getAllButtons()

            var buttonNr = 0
            var currentButton = ""

            val settings = db.matchEventsDao().getAllSettings()

            val matchSaved = db.matchEventsDao().getAllMatchEvents()


            // CHECK IF THERE IS ALREADY A MATCH SAVED
            if (matchSaved.isNotEmpty()) {

                photoView.visibility = VISIBLE
                tv_matchSaved_Ok.visibility = VISIBLE
                tv_matchSaved_Abort.visibility = VISIBLE

            }

            withContext(Dispatchers.Main) {

                tv_cutom_button_1.text = buttons[0].buttonText
                tv_cutom_button_2.text = buttons[1].buttonText
                tv_cutom_button_3.text = buttons[2].buttonText
                tv_cutom_button_4.text = buttons[3].buttonText


                if (settings.isNotEmpty()) {
                    tv_myTeam = settings[0].myClub
                    if (settings[0].homeMatch) {
                        tv_homeTeam.text = settings[0].myClub
                        tv_awayTeam.text = settings[0].opposition
                    } else {
                        tv_awayTeam.text = settings[0].myClub
                        tv_homeTeam.text = settings[0].opposition
                    }
                }else{

                    photoView.text = "Du måste först spara vilka lag som ska mötas innan du kan starta en match. Du lägger till Hemma- och bortalag på inställningssidan. Den når du genom att trycka på Hem-knappen i menyn längst ner på sidan."
                    tv_matchSaved_Abort.visibility = INVISIBLE
                    tv_matchSaved_Ok.visibility = INVISIBLE
                    tv_homeTeam.text = "Inget lag sparat"
                    tv_awayTeam.text = "Inget lag sparat"

                }
            }

        }




            // BUTTON PLAY AND PAUSE THE CLOCK
            btnPlayPauseButton.setOnClickListener {


                // IF MATCH IS NOT STARTED AT ALL THEN RUN THIS
                if (matchStatus == "notStarted") {

                    matchStatus = "started"
                    currentHalf = currentHalf + 1

                    tvHalf1.setImageResource(R.drawable.halves_first_half)
                    tv_liveTickerTime.text = "00:00"
                    tv_liveTickerText.text = "Matchen startar\n"
                    tv_liveTickerTeam.text = tv_myTeam


                    // CHECK IF MATCH IS PAUSED OR NOT
                    if (chronoStatus == "paused") {
                        c_meter.base = SystemClock.elapsedRealtime() - chronoTime
                    } else if (chronoStatus == "started") {

                    } else {
                        c_meter.base = SystemClock.elapsedRealtime()
                    }

                    // START THE CHRONOMETER
                    c_meter.start()
                    chronoStatus = "started"


                    // IF MATCH HAS BEEN STARTED
                } else {

                    // CHECK IF MATCH IS PAUSED THEN RUN
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


                        // IF MATCH IS ALREADY STOPPED THEN RUN THIS TO START 2ND HALF
                    } else if (matchStatus == "stopped") {


                        matchStatus = "started"


                        tvHalf1.setImageResource(R.drawable.halves_second_half)

                        var currentChrono = "45:00"
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

                        c_meter.base = SystemClock.elapsedRealtime() - 2_700_000
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
                //c_meter.base = SystemClock.elapsedRealtime()


                chronoStatus = "reset"


                if (currentHalf == 1) {
                    tvHalf1.setImageResource(R.drawable.halves_half_time)

                    tv_liveTickerText.text = "1a halvlek är slut\n${tv_liveTickerText.text}"
                    tv_liveTickerTime.text = "$currentChrono\n${tv_liveTickerTime.text}"
                    tv_liveTickerTeam.text = "$tv_myTeam\n${tv_liveTickerTeam.text}"


                } else if (currentHalf == 2) {
                    tvHalf1.setImageResource(R.drawable.halves_end)

                    tv_liveTickerText.text = "2a halvlek är slut\n${tv_liveTickerText.text}"
                    tv_liveTickerTime.text =
                        "$currentChrono\n${tv_liveTickerTime.text}"
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


                        Toast.makeText(this, "$currentHalf, $nrOfPeriods", Toast.LENGTH_LONG).show()

                        //   tvHalfText.text = "end of match"
                        matchStatus = "finished"

                        //PROMPTA ANVÄNDAREN OM ATT STARTA NY MATCH
                        textView.text = "Matchen är slut. Alla registrerade händelser " +
                                "från matchen hittar du på statistik-sidan. Om du vill starta en ny" +
                                "match så går du tillbaka till hem-sidan och skriver in nya motståndaren" +
                                "för att sen komma tillbaka hit igen."
                    }


                }
            }


        GlobalScope.launch {
            val buttons = db.matchEventsDao().getAllButtons()


            /*    tv_cutom_button_1.text = buttons[0].buttonText
            tv_cutom_button_2.text = buttons[1].buttonText
            tv_cutom_button_3.text = buttons[2].buttonText
            tv_cutom_button_4.text = buttons[3].buttonText*/

            withContext(Dispatchers.Main) {
                tv_cutom_button_1.text = buttons[0].buttonText
                tv_cutom_button_2.text = buttons[1].buttonText
                tv_cutom_button_3.text = buttons[2].buttonText
                tv_cutom_button_4.text = buttons[3].buttonText
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


        tv_matchSaved_Ok.setOnClickListener {
            photoView.visibility = INVISIBLE
            tv_matchSaved_Ok.visibility = INVISIBLE
            tv_matchSaved_Abort.visibility = INVISIBLE

            GlobalScope.launch {
                db.matchEventsDao().deleteAllMatchEvents()
            }
        }

        tv_matchSaved_Abort.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }




        tv_btnStats.setOnClickListener() {

                pushedMenuButton = "stats"

                if (matchStatus == "notStarted") {
                    intent = Intent(this, StatsActivity::class.java)
                    startActivity(intent)
                }else{
                    tv_leavePage.visibility = VISIBLE
                    tv_leavePage_Ok.visibility = VISIBLE
                    tv_leavePage_Abort.visibility = VISIBLE
                }
            }

        tv_btnHome.setOnClickListener() {

           pushedMenuButton = "home"

            if (matchStatus == "notStarted") {
                intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            }else{
                tv_leavePage.visibility = VISIBLE
                tv_leavePage_Ok.visibility = VISIBLE
                tv_leavePage_Abort.visibility = VISIBLE
            }

        }

        tv_leavePage_Ok.setOnClickListener {
            if (pushedMenuButton == "home") {
                startActivity(Intent(this,HomeActivity::class.java))
            }else if (pushedMenuButton == "stats") {
                startActivity(Intent(this,StatsActivity::class.java))
            }
        }
        tv_leavePage_Abort.setOnClickListener {
            tv_leavePage.visibility = INVISIBLE
            tv_leavePage_Ok.visibility = INVISIBLE
            tv_leavePage_Abort.visibility = INVISIBLE
        }



        }




}