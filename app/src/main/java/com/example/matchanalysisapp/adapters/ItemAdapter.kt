package com.example.matchanalysisapp.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.matchanalysisapp.data.MatchEvents
import com.example.matchanalysisapp.R
import kotlinx.android.synthetic.main.custom_item_row.view.*

class ItemAdapter(
        private var matchEvents: List<MatchEvents>
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
            tv_actionsId.text = matchEvents[position].eventID.toString()

            tv_extra_info_event.text = matchEvents[position].eventText
            tv_extra_info_time.text = matchEvents[position].eventTime

            cv_customItem.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Detta är matchhändelse nr: ${position+1} med ID-nummer: ${matchEvents[position].eventID}",Toast.LENGTH_SHORT).show()


                if (cl_extra_info.visibility == GONE) {
                    cl_extra_info.visibility = VISIBLE
                } else if (cl_extra_info.visibility == VISIBLE) {
                    cl_extra_info.visibility = GONE
                }

                imageView2.setOnClickListener{
                    Toast.makeText(holder.itemView.context, "ImageView2 clicked", Toast.LENGTH_SHORT).show()
                }

            }

            cv_customItem.setOnLongClickListener {
                Toast.makeText(holder.itemView.context,"Detta är en lååååååååååååååååååååång matchhändelse nr: ${position + 1}", Toast.LENGTH_SHORT).show()
                true
            }

        }
    }

    override fun getItemCount(): Int {
        return matchEvents.size

    }

    fun Int.toDp(context: Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    ).toInt()

}