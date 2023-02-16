package com.example.upstreamboredapi.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
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
        val vImage = finalView.findViewById<ImageView>(R.id.activity_image)
        val vLink = finalView.findViewById<TextView>(R.id.link)
        val vSeparator = finalView.findViewById<TextView>(R.id.separator)
        val vShare = finalView.findViewById<TextView>(R.id.share)

        vActionActivity.text = currentAA!!.activity
        vType.text = currentAA.type

        val intentView = Intent(Intent.ACTION_VIEW, Uri.parse(currentAA.link))

        vLink.setOnClickListener {
            context.startActivity(intentView)
        }

        if (currentAA.link.isNullOrEmpty()) {
            vLink.visibility = View.GONE
            vSeparator.visibility = View.GONE
        }

        vShare.setOnClickListener {
            context.startActivity(sendEmail(currentAA))
        }

        vPrice.text = when {
            currentAA.price == 0.0 -> "Free"
            (0.0 < currentAA.price!! && currentAA.price <= 0.2) -> "$"
            (0.2 < currentAA.price && currentAA.price <= 0.4) -> "$$"
            (0.4 < currentAA.price && currentAA.price <= 0.6) -> "$$$"
            (0.6 < currentAA.price && currentAA.price <= 0.8) -> "$$$$"
            (0.8 < currentAA.price && currentAA.price <= 1.0) -> "$$$$$"
            else -> "N/a"
        }

        setImage(vImage, currentAA)

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

    private fun sendEmail(item: ActionActivity): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"

        intent.putExtra(Intent.EXTRA_SUBJECT, "Look at the activity I've found!")
        intent.putExtra(Intent.EXTRA_TEXT, "${item.activity}\nType - ${item.type}\nPrice - ${item.price}")
        return intent
    }

    private fun getParticipantsString(participants: Int): String {
        var participantsValue = ""
        repeat(participants) {
            participantsValue += "\uD83D\uDC71\uD83C\uDFFD"
        }
        return participantsValue
    }

    private fun setImage(vImage: ImageView, currentAA: ActionActivity) {
        when (currentAA.type) {
            "busywork" -> vImage.setImageResource(R.drawable.busywork)
            "diy" -> vImage.setImageResource(R.drawable.diy)
            "education" -> vImage.setImageResource(R.drawable.education)
            "social" -> vImage.setImageResource(R.drawable.social)
            "recreational" -> vImage.setImageResource(R.drawable.recreational)
            "charity" -> vImage.setImageResource(R.drawable.charity)
            "cooking" -> vImage.setImageResource(R.drawable.cooking)
            "relaxation" -> vImage.setImageResource(R.drawable.relaxation)
            "music" -> vImage.setImageResource(R.drawable.music)
        }
    }
}