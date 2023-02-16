package com.example.upstreamboredapi.view

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.model.ActionActivity

class CategoriesAdapter():
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private val categoriesList = mutableListOf<String>()

        class CategoriesViewHolder(var view: View): RecyclerView.ViewHolder(view) {
            val catImage = view.findViewById<ImageView>(R.id.type_image)
            val catType = view.findViewById<TextView>(R.id.type_name)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.type_card_layout, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val currentItem = categoriesList[position]
        holder.catType.text = currentItem

        when (currentItem) {
            "busywork" -> holder.catImage.setImageResource(R.drawable.busywork)
            "diy" -> holder.catImage.setImageResource(R.drawable.diy)
            "education" -> holder.catImage.setImageResource(R.drawable.education)
            "social" -> holder.catImage.setImageResource(R.drawable.social)
            "recreational" -> holder.catImage.setImageResource(R.drawable.recreational)
            "charity" -> holder.catImage.setImageResource(R.drawable.charity)
            "cooking" -> holder.catImage.setImageResource(R.drawable.cooking)
            "relaxation" -> holder.catImage.setImageResource(R.drawable.relaxation)
            "music" -> holder.catImage.setImageResource(R.drawable.music)
        }

        val action = CategoriesFragmentDirections.actionCategoriesFragmentToFavoriteListFragment(currentItem)

        holder.view.setOnClickListener {
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = categoriesList.size

    fun updateAAList(newCatList: List<String>) {
        categoriesList.clear()
        categoriesList.addAll(newCatList)
        notifyDataSetChanged()
    }
}