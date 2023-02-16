package com.example.upstreamboredapi.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    private var currentDisplayedAA: ActionActivity? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val instruction = ActionActivity(
            "swipe right if You like, left, if you dont",
            "instruction",
            2,
            1.0,
            "",
            "",
            1.1
        )
        actionActivities.add(
            instruction
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        observeViewModel()

        cardsAdapter = CardsAdapter(requireContext(), R.layout.item, actionActivities)
        binding.frame.adapter = cardsAdapter

        mViewModel.setAccessibilityParams(binding.accessMin, mViewModel.accessMin.toDouble())
        mViewModel.setAccessibilityParams(binding.accessMax, mViewModel.accessMax.toDouble())


        binding.apply {
            filterPriceMinValue.text =
                mViewModel.convertedToDollarRange(mViewModel.priceMin.toDouble())
            filterPriceMaxValue.text =
                mViewModel.convertedToDollarRange(mViewModel.priceMax.toDouble())
            filterActivityTypeValue.text = if (mViewModel.type == "") "ALL" else mViewModel.type
        }

        binding.frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                currentDisplayedAA = actionActivities[0]
                actionActivities.removeAt(0)
                if (actionActivities.contains(currentDisplayedAA)) {
                    actionActivities.remove(currentDisplayedAA)
                }
                (cardsAdapter as CardsAdapter).notifyDataSetChanged()
            }

            override fun onLeftCardExit(p0: Any?) {
                mViewModel.refresh()
                currentDisplayedAA?.let {
                    mViewModel.storeAALocally(it)
                }
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

        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_favorites -> {
                    findNavController().navigate(R.id.action_detailFragment_to_categoriesFragment)
                    true
                }
                else -> false
            }
        }

        binding.buttonLike.setOnClickListener {
            binding.frame.topCardListener.selectLeft()
        }
        binding.buttonDislike.setOnClickListener {
            binding.frame.topCardListener.selectRight()
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
                        binding.frame.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                    } else {
                        binding.errorLayout.visibility = View.GONE

                    }
                }
            }
        }
        mViewModel.aALoadErrorMessage.observe(viewLifecycleOwner) {
            binding.errorMessage.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}