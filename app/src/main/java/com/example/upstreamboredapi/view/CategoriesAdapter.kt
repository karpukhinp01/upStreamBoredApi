package com.example.upstreamboredapi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.upstreamboredapi.R

class CategoriesAdapter(private val CategoriesList: List<String>):
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {


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
        val currentItem = CategoriesList[position]
        holder.catType.text = currentItem

        val action = CategoriesFragmentDirections.actionCategoriesFragmentToFavoriteListFragment(currentItem)

        holder.view.setOnClickListener {
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = CategoriesList.size
}