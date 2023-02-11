package com.example.upstreamboredapi.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.model.ActionActivity

open class CardsAdapter(context: Context, resourceId: Int, actionActivities: List<ActionActivity>): ArrayAdapter<ActionActivity>(context, resourceId, actionActivities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentAA = getItem(position)
        val finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        val vActionActivity = finalView.findViewById<TextView>(R.id.action_activity)
        val vPrice = finalView.findViewById<TextView>(R.id.price)
        val vType = finalView.findViewById<TextView>(R.id.type)


        vActionActivity.text = currentAA!!.activity
        vPrice.text = currentAA.price.toString()
        vType.text = currentAA.type
        return finalView
    }
}