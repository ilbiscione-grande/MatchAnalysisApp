package com.ilbiscione.MatchAnalysisApp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchanalysisapp.MatchEventsDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchanalysisapp.adapters.ItemAdapter
import com.example.matchanalysisapp.data.MatchEvents

import androidx.room.Room
import kotlinx.android.synthetic.main.activity_stats.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatsActivity : AppCompatActivity() {

    lateinit var db : MatchEventsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        db = Room.databaseBuilder(
            applicationContext,
            MatchEventsDatabase::class.java,
            "MatchEvents-DB")
            .build()


        var matchEventList = mutableListOf(
            MatchEvents(1, null, "00:00", "Matchen startar", "Hvetlanda Gif"),
            MatchEvents(1, null, "01:05", "Djupledsboll", "Hvetlanda Gif"),
            MatchEvents(1, null, "01:15", "Avslut", "Hvetlanda Gif"),
            MatchEvents(1, null, "03:27", "Kontring", "Hvetlanda Gif"),
            MatchEvents(1, null, "04:57", "Avslut", "Hvetlanda Gif"),
            MatchEvents(1, null, "04:59", "Mål", "Hvetlanda Gif")
        )

        tv_statsEventTeam.text = matchEventList[3].toString()



        GlobalScope.launch {


            //db.matchEventsDao().deleteAllMatchEvents()

            // ADD  ABOVE MUTABLE LIST TO DATABASE
/*
            var position = 0
            for (matchEvent : MatchEvents in matchEventList) {

                db.matchEventsDao().insertMatchEvent(matchEventList[position])

                tv_statsEventText.text = "${tv_statsEventText.text} - Round $position, "

                position += 1
            }
*/




                // GET ALL MATCHEVENTS FROM DATABASE AND SHOW THEM IN RECYCLERVIEW
                val allMatchEvents = db.matchEventsDao().getAllMatchEvents()

                val adapter = ItemAdapter(allMatchEvents)
                rv_matchStats.adapter = adapter

                rv_matchStats.layoutManager = LinearLayoutManager(applicationContext)
                rv_matchStats.setHasFixedSize(true)


        }


        //val adapter = ItemAdapter(matchEventList)
        //rv_matchStats.adapter = adapter

        //rv_matchStats.layoutManager = LinearLayoutManager(this)
        //rv_matchStats.setHasFixedSize(true)






            }



}



