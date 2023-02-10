package com.example.upstreamboredapi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.databinding.FragmentDetailBinding
import com.example.upstreamboredapi.model.ActionActivity
import com.example.upstreamboredapi.viewmodel.DetailViewModel
import com.lorentzos.flingswipe.SwipeFlingAdapterView

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private lateinit var mViewModel: DetailViewModel
    private var cardsAdapter: ArrayAdapter<ActionActivity>? = null
    private var actionActivities = ArrayList<ActionActivity>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        actionActivities.add(ActionActivity("swipe right if You like, left, if you dont", "instruction", 2, 1.0, "", "", 1.1))
        observeViewModel()

        cardsAdapter = CardsAdapter(requireContext(), R.layout.item, actionActivities)
        binding.frame.adapter = cardsAdapter

        binding.frame.setFlingListener(object: SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                actionActivities.removeAt(0)
                (cardsAdapter as CardsAdapter).notifyDataSetChanged()
            }

            override fun onLeftCardExit(p0: Any?) {
                mViewModel.refresh()
                mViewModel.storeAALocally(actionActivities[0])
            }

            override fun onRightCardExit(p0: Any?) {
                mViewModel.refresh()
            }

            override fun onAdapterAboutToEmpty(p0: Int) {
                mViewModel.refresh()
            }

            override fun onScroll(p0: Float) {
            }

        })
        binding.errorLayout.setOnRefreshListener {
                mViewModel.refresh()
                binding.errorLayout.isRefreshing = false
                binding.errorLayout.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        mViewModel.actionActivity.observe(viewLifecycleOwner) {
            binding.frame.visibility = View.VISIBLE
            actionActivities.add(it)
            cardsAdapter!!.notifyDataSetChanged()
            Log.d("arraySize", actionActivities.size.toString())
        }
        mViewModel.aALoadError.observe(viewLifecycleOwner) { isError ->
            if (actionActivities.isEmpty()) {
                isError?.let {
                    if (isError) {
                        binding.loadingIndicator.visibility = View.GONE
                        binding.frame.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                    } else {
                        binding.errorLayout.visibility = View.GONE

                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}