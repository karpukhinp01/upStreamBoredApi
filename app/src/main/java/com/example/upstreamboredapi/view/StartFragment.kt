package com.example.upstreamboredapi.view

import android.app.Application
import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.upstreamboredapi.R
import com.example.upstreamboredapi.databinding.FragmentStartBinding
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



        binding.goButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_detailFragment)
            mViewModel.deleteAll()
        }

        setUpDialog()

    }

    private fun setUpDialog() {

        val dialog = BottomSheetDialog(requireContext())

        binding.filterButton.setOnClickListener {
            val mView = layoutInflater.inflate(R.layout.filter_dialog_view, null)
            mViewModel.setFilterValues()
            dialog.apply {
                setCancelable(true)
                setContentView(mView)
                show()
            }

            val priceRange = mView.findViewById<RangeSlider>(R.id.price_range)
            priceRange.stepSize = 0.1F
            mViewModel.prices.observe(viewLifecycleOwner){
                priceRange.setValues(it[0].toFloat(), it[1].toFloat())
            }

            val typeGroup = mView.findViewById<RadioGroup>(R.id.type_group)
            mViewModel.buttonId.observe(viewLifecycleOwner){
                if (!it.equals(2929)) {
                    typeGroup.check(it)
                } else typeGroup.clearCheck()
            }

            val applyButton = mView.findViewById<Button>(R.id.apply_button)
            applyButton.setOnClickListener {
                val values = priceRange.values
                val selectedOption = typeGroup.checkedRadioButtonId
                val selectedButton = mView.findViewById<RadioButton>(selectedOption)
                val type = if (selectedButton?.text.isNullOrEmpty()) "" else selectedButton.text.toString().lowercase()
                mViewModel.setFilters(values[0], values[1], type, selectedOption)
                dialog.dismiss()
            }

            val resetButton = mView.findViewById<TextView>(R.id.reset_button)
            resetButton.setOnClickListener {
                mViewModel.resetFilters()
                dialog.dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}