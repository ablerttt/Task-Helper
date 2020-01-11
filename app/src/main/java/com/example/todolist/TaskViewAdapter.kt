package com.example.todolist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TaskViewAdapter( val context: Context, val list: MutableList<Task>) : RecyclerView.Adapter<TaskViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from( context ).inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ttitle.text = list[position].title
        holder.tdescription.text = list[position].description

        holder.tcard.setOnClickListener {
            val intent = Intent(context, IsolateActivity::class.java)
            intent.putExtra(INTENT_TITLE, list[position].title.toString())
            intent.putExtra(INTENT_DESCRIPTION, list[position].description.toString())
            intent.putExtra(INTENT_ID, list[position].id)
            context.startActivity(intent)

        }
    }

    class ViewHolder ( v: View) : RecyclerView.ViewHolder(v){
        val ttitle : TextView = v.findViewById(R.id.textViewTitle)
        val tdescription : TextView = v.findViewById(R.id.textViewDescription)
        val tcard : CardView = v.findViewById(R.id.cardtask)
    }

}
