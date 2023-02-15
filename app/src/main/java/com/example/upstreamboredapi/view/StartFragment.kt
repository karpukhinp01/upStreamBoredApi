package com.example.upstreamboredapi.view

import android.app.AlertDialog
import android.app.Dialog
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
    ): View {

        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(this)[StartViewModel::class.java]

        binding.goButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_detailFragment)
        }
        binding.filterButton.setOnClickListener {
            setUpDialog()
        }
    }

    private fun setUpDialog() {

        val dialog = BottomSheetDialog(requireContext())

            val mView = layoutInflater.inflate(R.layout.filter_dialog_view, null)
            mViewModel.setFilterValuesToDialog()
            dialog.apply {
                setCancelable(true)
                setContentView(mView)
                show()
            }

            val priceRange = mView.findViewById<RangeSlider>(R.id.price_range)
            priceRange.stepSize = 0.1F
            mViewModel.prices.observe(viewLifecycleOwner) {
                priceRange.setValues(it[0].toFloat(), it[1].toFloat())
            }
            priceRange.addOnChangeListener { _, _, _ ->
                val values = priceRange.values
                mViewModel.setPrices(values[0], values[1])
            }

            val accessRange = mView.findViewById<RangeSlider>(R.id.accesibility_range)
            mViewModel.access.observe(viewLifecycleOwner) {
                accessRange.setValues(it[0], it[1])
            }
            accessRange.addOnChangeListener { _, _, _ ->
                val values = accessRange.values
                mViewModel.setAccess(values[0], values[1])
            }

        val typeValue = mView.findViewById<TextView>(R.id.type_value)
        typeValue.setOnClickListener {
            setUpTypeDialog()
        }
        mViewModel.type.observe(viewLifecycleOwner) {
            if (!it.equals("")) {
                typeValue.text = it
            } else typeValue.text = "All types"
        }


            val applyButton = mView.findViewById<Button>(R.id.apply_button)
            applyButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_detailFragment)
                dialog.dismiss()
            }

            val resetButton = mView.findViewById<TextView>(R.id.reset_button)
            resetButton.setOnClickListener {
                mViewModel.resetFilters()
                dialog.dismiss()
            }

    }

    private fun setUpTypeDialog() {
        val builder = AlertDialog.Builder(requireContext())

        val mView = layoutInflater.inflate(R.layout.type_dialog_view, null)
        builder.apply {
            setTitle("Choose activity type")
            setView(mView)
            setPositiveButton("Done!") { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                mViewModel.setTypes("All Activities", 2929)
                dialog.dismiss()
            }
            show()
        }

        val typeGroup = mView.findViewById<RadioGroup>(R.id.type_group)
        mViewModel.buttonId.observe(viewLifecycleOwner) {
            if (!it.equals(2929)) {
                typeGroup.check(it)
            } else typeGroup.clearCheck()
        }

        typeGroup.setOnCheckedChangeListener { radioGroup, _ ->
            val selectedButtonId = radioGroup.checkedRadioButtonId
            val selectedButton = mView.findViewById<RadioButton>(selectedButtonId)
            val type =
                if (selectedButton?.text.isNullOrEmpty() || selectedButton?.text == "All types") "" else selectedButton.text.toString()
                    .lowercase()
            mViewModel.setTypes(type, selectedButtonId)
            mViewModel.setFilterValuesToDialog()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}