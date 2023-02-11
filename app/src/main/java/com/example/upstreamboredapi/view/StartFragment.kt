package com.example.upstreamboredapi.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.databinding.FragmentStartBinding
import com.example.upstreamboredapi.util.SharedPreferencesHelper
import com.example.upstreamboredapi.viewmodel.DetailViewModel
import com.example.upstreamboredapi.viewmodel.StartViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class StartFragment : Fragment() {
    private lateinit var mViewModel: StartViewModel
    private var _binding: FragmentStartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[StartViewModel::class.java]
        val dialog = BottomSheetDialog(requireContext())


        binding.goButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_detailFragment)
            mViewModel.deleteAll()
        }

        binding.filterButton.setOnClickListener {
            val mView = layoutInflater.inflate(R.layout.filter_dialog_view, null)
            dialog.apply {
                setCancelable(true)
                setContentView(mView)
                show()
            }

            val priceRange = mView.findViewById<RangeSlider>(R.id.price_range)
            priceRange.stepSize = 0.1F
            priceRange.setValues(mViewModel.priceMin.toFloat(), mViewModel.priceMax.toFloat())
            priceRange.addOnChangeListener { slider, value, fromUser ->
                val values = priceRange.values
                mViewModel.setFilters(values[0], values[1])
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}