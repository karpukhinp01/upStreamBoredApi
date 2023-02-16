package com.example.upstreamboredapi.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.model.ActionActivity

class AAListAdapter(private val aAList: ArrayList<ActionActivity>, private var context: Context) :
    RecyclerView.Adapter<AAListAdapter.AAViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AAViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item, parent, false)
        return AAViewHolder(view)
    }

    override fun onBindViewHolder(holder: AAViewHolder, position: Int) {

        val currentItem = aAList[position]
        holder.vActionActivity.text = currentItem.activity
        holder.vType.text = currentItem.type
        val intentView = Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.link))

        holder.vLink.setOnClickListener {
            context.startActivity(intentView)
        }

        if (currentItem.link.isNullOrEmpty()) {
            holder.vLink.visibility = View.GONE
            holder.vSeparator.visibility = View.GONE
        }

        holder.vShare.setOnClickListener {
            context.startActivity(sendEmail(currentItem))
        }

        holder.vPrice.text = when {
            currentItem.price == 0.0 -> "Free"
            (0.0 < currentItem.price!! && currentItem.price <= 0.2) -> "$"
            (0.2 < currentItem.price && currentItem.price <= 0.4) -> "$$"
            (0.4 < currentItem.price && currentItem.price <= 0.6) -> "$$$"
            (0.6 < currentItem.price && currentItem.price <= 0.8) -> "$$$$"
            (0.8 < currentItem.price && currentItem.price <= 1.0) -> "$$$$$"
            else -> "N/a"
        }
        
        setImage(holder.vImage, currentItem)

        holder.vAccessibility.apply {
            when {
                currentItem.accessibility == 0.0 -> {
                    setText(R.string.basic)
                    setTextAppearance(R.style.color_easy)
                }
                (0.0 < currentItem.accessibility!! && currentItem.accessibility <= 0.2) -> {
                    setText(R.string.very_easy)
                    setTextAppearance(R.style.color_easy)
                }
                (0.2 < currentItem.accessibility && currentItem.accessibility <= 0.4) -> {
                    setText(R.string.easy)
                    setTextAppearance(R.style.color_easy)

                }
                (0.4 < currentItem.accessibility && currentItem.accessibility <= 0.6) -> {
                    setText(R.string.medium)
                    setTextAppearance(R.style.color_medium)

                }
                (0.6 < currentItem.accessibility && currentItem.accessibility <= 0.8) -> {
                    setText(R.string.medium)
                    setTextAppearance(R.style.color_hard)

                }
                (0.8 < currentItem.accessibility && currentItem.accessibility <= 1.0) -> {
                    setText(R.string.very_hard)
                    setTextAppearance(R.style.color_hard)
                }
                else -> {
                    text = "N/a"
                }
            }
        }

        holder.vParticipants.text = getParticipantsString(currentItem.participants ?: 0)
        
        
    }

    override fun getItemCount() = aAList.size

    class AAViewHolder(finalView: View) : RecyclerView.ViewHolder(finalView) {
        val vActionActivity = finalView.findViewById<TextView>(R.id.action_activity)
        val vPrice = finalView.findViewById<TextView>(R.id.price)
        val vType = finalView.findViewById<TextView>(R.id.type)
        val vAccessibility = finalView.findViewById<TextView>(R.id.accesibility_value)
        val vParticipants = finalView.findViewById<TextView>(R.id.participants_value)
        val vImage = finalView.findViewById<ImageView>(R.id.activity_image)
        val vLink = finalView.findViewById<TextView>(R.id.link)
        val vSeparator = finalView.findViewById<TextView>(R.id.separator)
        val vShare = finalView.findViewById<TextView>(R.id.share)
    }

    fun updateAAList(newAAList: List<ActionActivity>) {
        aAList.clear()
        aAList.addAll(newAAList)
        datasetChanged()
    }

    fun datasetChanged() {
        notifyDataSetChanged()
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

    private fun setImage(vImage: ImageView, currentItem: ActionActivity) {
        when (currentItem.type) {
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