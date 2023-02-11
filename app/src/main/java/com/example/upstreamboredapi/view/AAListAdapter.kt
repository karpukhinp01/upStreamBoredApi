package com.example.upstreamboredapi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.model.ActionActivity

class AAListAdapter(private val aAList: ArrayList<ActionActivity>): RecyclerView.Adapter<AAListAdapter.AAViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AAViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_card_recycler_view, parent, false)
        return AAViewHolder(view)
    }

    override fun onBindViewHolder(holder: AAViewHolder, position: Int) {

        val currentItem = aAList[position]
        holder.actionActivityStr.text = currentItem.activity.toString()
    }

    override fun getItemCount() = aAList.size

class AAViewHolder(var view: View): RecyclerView.ViewHolder(view) {
    val actionActivityStr: TextView = view.findViewById(R.id.action_activity_str)
}

    fun updateAAList(newAAList: List<ActionActivity>) {
        aAList.clear()
        aAList.addAll(newAAList)
        notifyDataSetChanged()
    }

}