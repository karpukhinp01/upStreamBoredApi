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
        var currentAA = getItem(position)
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        var vActionActivity = finalView.findViewById<TextView>(R.id.action_activity)

        vActionActivity.text = currentAA!!.activity
        return finalView
    }
}