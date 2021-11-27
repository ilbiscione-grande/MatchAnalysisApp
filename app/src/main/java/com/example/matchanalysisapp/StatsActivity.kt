package com.example.matchanalysisapp

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchanalysisapp.adapters.ItemAdapter

import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_stats.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StatsActivity : AppCompatActivity() {

    lateinit var db : MatchEventsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)


        var stat_1 = 0
        var stat_2 = 0
        var stat_3 = 0
        var stat_4 = 0

        db = Room.databaseBuilder(
            applicationContext,
            MatchEventsDatabase::class.java,
            "MatchEvents-DB")
            .build()


/*
        var matchEventList = mutableListOf(
            MatchEvents(1, null, "00:00", "Matchen startar", "Hvetlanda Gif"),
            MatchEvents(1, null, "01:05", "Djupledsboll", "Hvetlanda Gif"),
            MatchEvents(1, null, "01:15", "Avslut", "Hvetlanda Gif"),
            MatchEvents(1, null, "03:27", "Kontring", "Hvetlanda Gif"),
            MatchEvents(1, null, "04:57", "Avslut", "Hvetlanda Gif"),
            MatchEvents(1, null, "04:59", "Mål", "Hvetlanda Gif")
        )
*/

        //tv_statsEventTeam.text = matchEventList[3].toString()


        //var testText = "${tv_cutom_button_1.text}"


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

            for (i in allMatchEvents) {

                tv_stats4.text = "${tv_stats4.text}\n ${i.eventText}"

                if (i.eventText == "AVSLUT") {
                    stat_1 = stat_1 + 1
                }else if (i.eventText == "ÅTERERÖVRING") {
                    stat_2 = stat_2 +1
                }else if (i.eventText == "KONTRING") {
                    stat_3 = stat_3 +1
                }else if (i.eventText == "DJUPLEDSBOLLAR") {
                    stat_4 = stat_4 +1
                }



            }


            //stat_1 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_1.text}").size
            //stat_2 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_2.text}").size
            //stat_3 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_3.text}").size
            //stat_4 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_4.text}").size


            if (allMatchEvents.isNotEmpty()) {
                tv_statsChosenMatch.text =
                    "Match ${allMatchEvents[0].matchDate} vs ${allMatchEvents[0].eventTeam}"
                tv_statsEventText.visibility = GONE

                tv_stats1.text = "Avslut: $stat_1"
                tv_stats2.text = "Återerövring: $stat_2"
                tv_stats3.text = "Kontring: $stat_3"
                tv_stats4.text = "Djupledsbollar: $stat_4"
            }


                val adapter = ItemAdapter(allMatchEvents)
                rv_matchStats.adapter = adapter

                rv_matchStats.layoutManager = LinearLayoutManager(applicationContext)
                rv_matchStats.setHasFixedSize(true)


        }


        iv_btnMatch.setOnClickListener() {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }






            }



}



