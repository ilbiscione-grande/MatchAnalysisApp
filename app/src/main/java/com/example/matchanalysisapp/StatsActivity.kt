package com.example.matchanalysisapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
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



        GlobalScope.launch {


            // DELETE ALL EVENTS FROM DATABASE
            //db.matchEventsDao().deleteAllMatchEvents()



                // GET ALL MATCHEVENTS FROM DATABASE AND SHOW THEM IN RECYCLERVIEW
                val allMatchEvents = db.matchEventsDao().getAllMatchEvents()

                val allButtons = db.matchEventsDao().getAllButtons()


            var testNr = 0
            for (i in allMatchEvents) {

                //tv_stats4_box.text = "${tv_stats4_box.text}\n ${i.eventText}"

                if (i.eventText == allButtons[0].buttonText) {
                    stat_1 = stat_1 + 1
                }else if (i.eventText == allButtons[1].buttonText) {
                    stat_2 = stat_2 +1
                }else if (i.eventText == allButtons[2].buttonText) {
                    stat_3 = stat_3 +1
                }else if (i.eventText == allButtons[3].buttonText) {
                    stat_4 = stat_4 +1
                }

                testNr += 1

            }


            //stat_1 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_1.text}").size
            //stat_2 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_2.text}").size
            //stat_3 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_3.text}").size
            //stat_4 = db.matchEventsDao().searchAllMatchEvents("${tv_cutom_button_4.text}").size


            if (allMatchEvents.isNotEmpty()) {
                tv_statsChosenMatch.text =
                    "Match ${allMatchEvents[0].matchDate} vs ${allMatchEvents[0].eventTeam}"
                textView14.visibility = GONE



                val adapter = ItemAdapter(allMatchEvents)
                rv_matchStats.adapter = adapter

                rv_matchStats.layoutManager = LinearLayoutManager(applicationContext)
                rv_matchStats.setHasFixedSize(true)


                tv_stats1_text.text = "${allButtons[0].buttonText}"
                tv_stats1_amount.text = "$stat_1"
                tv_stats2_text.text = "${allButtons[1].buttonText}"
                tv_stats2_amount.text = "$stat_2"
                tv_stats3_text.text = "${allButtons[2].buttonText}"
                tv_stats3_amount.text = "$stat_3"
                tv_stats4_text.text = "${allButtons[3].buttonText}"
                tv_stats4_amount.text = "$stat_4"
            }else {
                tv_stats1_amount.text = "$stat_1"
                tv_stats2_amount.text = "$stat_2"
                tv_stats3_amount.text = "$stat_3"
                tv_stats4_amount.text = "$stat_4"


            }




        }


        iv_btnMatch.setOnClickListener() {


            iv_btnMatch.setImageResource(R.drawable.ic_sharp_sports_soccer_24_pressed)

            Handler().postDelayed({
                iv_btnMatch.setImageResource(R.drawable.ic_sharp_sports_soccer_24)
            }, 50)



            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        iv_statsHome.setOnClickListener() {

            iv_statsHome.setImageResource(R.drawable.ic_baseline_home_24_pressed)

            Handler().postDelayed({
                iv_statsHome.setImageResource(R.drawable.ic_baseline_home_24)
            }, 50)



            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }






            }



}



