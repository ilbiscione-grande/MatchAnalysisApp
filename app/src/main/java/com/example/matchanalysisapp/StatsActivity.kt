package com.ilbiscione.MatchAnalysisApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilbiscione.MatchAnalysisApp.adapters.ItemAdapter
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)


        var matchEventList = mutableListOf(
                MatchEvents("1", "1", "00:00", "Matchen startar", "Hvetlanda Gif"),
                MatchEvents("1", "2", "01:05", "Djupledsboll", "Hvetlanda Gif"),
                MatchEvents("1", "3", "01:15", "Avslut", "Hvetlanda Gif"),
                MatchEvents("1", "4", "03:27", "Kontring", "Hvetlanda Gif"),
                MatchEvents("1", "5", "04:57", "Avslut", "Hvetlanda Gif"),
                MatchEvents("1", "6", "04:59", "Mål", "Hvetlanda Gif")
        )

        val adapter = ItemAdapter(matchEventList)
        rv_matchStats.adapter = adapter

        rv_matchStats.layoutManager = LinearLayoutManager(this)




            }



}



