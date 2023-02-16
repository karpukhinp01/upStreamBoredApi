package com.example.upstreamboredapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.databinding.FragmentFavoriteListBinding
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.viewmodel.FavoriteListViewModel
import java.util.*


class FavoriteListFragment : Fragment() {

    private lateinit var mViewModel: FavoriteListViewModel
    private lateinit var  aAListAdapter : AAListAdapter
    private var _binding: FragmentFavoriteListBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<FavoriteListFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.toolbar.title = args.type.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        aAListAdapter =  AAListAdapter(arrayListOf(), requireContext())

        mViewModel = ViewModelProvider(this)[FavoriteListViewModel::class.java]
        mViewModel.fetchFromDB(args.type)
        observeViewModel()

        binding.toolbar.inflateMenu(R.menu.menu_fav)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_clear_all -> {
                    mViewModel.deleteCat(args.type)
                    findNavController().navigate(R.id.action_favoriteListFragment_to_categoriesFragment)
                    true
                }
                else -> false
            }
        }

        binding.activitiesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = aAListAdapter
        }

    }

    private fun observeViewModel() {
        mViewModel.aAList.observe(viewLifecycleOwner) { aAList ->
            aAList?.let {
                aAListAdapter.updateAAList(aAList)
            }
        }
    }
}