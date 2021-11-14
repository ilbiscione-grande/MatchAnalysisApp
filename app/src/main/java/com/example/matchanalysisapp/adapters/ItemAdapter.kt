package com.ilbiscione.MatchAnalysisApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilbiscione.MatchAnalysisApp.MatchEvents
import com.ilbiscione.MatchAnalysisApp.R
import kotlinx.android.synthetic.main.custom_item_row.view.*

class ItemAdapter(
        var matchEvents: List<MatchEvents>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            tv_actionsTime.text = matchEvents[position].eventTime
            tv_actionsText.text = matchEvents[position].eventText
            tv_actionsTeam.text = matchEvents[position].eventTeam
        }
    }

    override fun getItemCount(): Int {
        return matchEvents.size
    }

}