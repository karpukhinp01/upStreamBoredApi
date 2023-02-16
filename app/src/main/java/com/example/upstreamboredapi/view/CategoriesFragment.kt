package com.example.upstreamboredapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upstreamboredapi.databinding.FragmentCategoriesBinding
import com.example.upstreamboredapi.viewmodel.CategoriesViewModel

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val categoriesListAdapter = CategoriesAdapter()
    private lateinit var mViewModel: CategoriesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        mViewModel.fetchCategoriesFromDb()
        observeViewModel()



        binding.categoriesRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesListAdapter
        }
    }

    private fun observeViewModel() {
        mViewModel.mCategories.observe(viewLifecycleOwner) {
            categoriesListAdapter.updateAAList(it)
        }
    }

}