package com.example.upstreamboredapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.upstreamboredapi.databinding.FragmentFavoriteListBinding
import com.example.upstreamboredapi.viewmodel.FavoriteListViewModel


class FavoriteListFragment : Fragment() {

    private lateinit var mViewModel: FavoriteListViewModel
    private val aAListAdapter = AAListAdapter(arrayListOf())
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

        mViewModel = ViewModelProvider(this)[FavoriteListViewModel::class.java]
        mViewModel.fetchFromDB(args.type)
        observeViewModel()

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