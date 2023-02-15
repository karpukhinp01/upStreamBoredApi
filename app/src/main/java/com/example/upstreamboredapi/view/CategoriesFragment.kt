package com.example.upstreamboredapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.databinding.FragmentCategoriesBinding
import com.example.upstreamboredapi.databinding.FragmentFavoriteListBinding

class CategoriesFragment : Fragment() {
    private val categories = listOf("education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork")

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val categoriesListAdapter = CategoriesAdapter(categories)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoriesRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesListAdapter
        }
    }

}