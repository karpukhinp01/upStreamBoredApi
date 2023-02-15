package com.example.upstreamboredapi.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.model.ActionActivity

open class CardsAdapter(context: Context, resourceId: Int, actionActivities: List<ActionActivity>) :
    ArrayAdapter<ActionActivity>(context, resourceId, actionActivities) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentAA = getItem(position)
        val finalView =
            convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        val vActionActivity = finalView.findViewById<TextView>(R.id.action_activity)
        val vPrice = finalView.findViewById<TextView>(R.id.price)
        val vType = finalView.findViewById<TextView>(R.id.type)
        val vAccessibility = finalView.findViewById<TextView>(R.id.accesibility_value)
        val vParticipants = finalView.findViewById<TextView>(R.id.participants_value)

        vActionActivity.text = currentAA!!.activity
        vPrice.text = when {
            currentAA.price == 0.0 -> "Free"
            (0.0 < currentAA.price!! && currentAA.price <= 0.2) -> "$"
            (0.2 < currentAA.price && currentAA.price <= 0.4) -> "$$"
            (0.4 < currentAA.price && currentAA.price <= 0.6) -> "$$$"
            (0.6 < currentAA.price && currentAA.price <= 0.8) -> "$$$$"
            (0.8 < currentAA.price && currentAA.price <= 1.0) -> "$$$$$"
            else -> "N/a"
        }
        vType.text = currentAA.type


        vAccessibility.apply {
            when {
                currentAA.accessibility == 0.0 -> {
                    setText(R.string.basic)
                    setTextAppearance(R.style.color_easy)
                }
                (0.0 < currentAA.accessibility!! && currentAA.accessibility <= 0.2) -> {
                    setText(R.string.very_easy)
                    setTextAppearance(R.style.color_easy)
                }
                (0.2 < currentAA.accessibility && currentAA.accessibility <= 0.4) -> {
                    setText(R.string.easy)
                    setTextAppearance(R.style.color_easy)

                }
                (0.4 < currentAA.accessibility && currentAA.accessibility <= 0.6) -> {
                    setText(R.string.medium)
                    setTextAppearance(R.style.color_medium)

                }
                (0.6 < currentAA.accessibility && currentAA.accessibility <= 0.8) -> {
                    setText(R.string.medium)
                    setTextAppearance(R.style.color_hard)

                }
                (0.8 < currentAA.accessibility && currentAA.accessibility <= 1.0) -> {
                    setText(R.string.very_hard)
                    setTextAppearance(R.style.color_hard)
                }
                else -> {
                    text = "N/a"
                }
            }
        }

        vParticipants.text = getParticipantsString(currentAA.participants ?: 0)
        return finalView
    }

    private fun getParticipantsString(participants: Int): String {
        var participantsValue = ""
        repeat(participants) {
            participantsValue += "\uD83D\uDC71\uD83C\uDFFD"
        }
        return participantsValue
    }
}