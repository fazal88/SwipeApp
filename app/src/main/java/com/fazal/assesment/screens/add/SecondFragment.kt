package com.fazal.assesment.screens.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fazal.assesment.databinding.FragmentSecondBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModel<SecondViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.vm = vm
        _binding?.lifecycleOwner = viewLifecycleOwner

        vm.navigateToList.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().navigate(SecondFragmentDirections.actionSecondFragmentToFirstFragment())
                    vm.reset()
                }
            }
        }
        vm.name.observe(viewLifecycleOwner) {
            binding.name.isErrorEnabled = it.isEmpty()
        }
        vm.type.observe(viewLifecycleOwner) {
            binding.type.isErrorEnabled = it.isEmpty()
        }
        vm.tax.observe(viewLifecycleOwner) {
            binding.tax.isErrorEnabled = it.isEmpty()||(it.toInt() in 1..99).not()
        }
        vm.price.observe(viewLifecycleOwner) {
            binding.price.isErrorEnabled = it.isEmpty()||it.toDouble() <= 0
        }


        vm.nameError.observe(viewLifecycleOwner) {
            binding.name.isErrorEnabled = it.isNotEmpty()
            if (it.isNotEmpty()) binding.name.error = it
        }

        vm.typeError.observe(viewLifecycleOwner) {
            binding.type.isErrorEnabled = it.isNotEmpty()
            if (it.isNotEmpty()) binding.type.error = it
        }

        vm.taxError.observe(viewLifecycleOwner) {
            binding.tax.isErrorEnabled = it.isNotEmpty()
            if (it.isNotEmpty()) binding.tax.error = it
        }

        vm.priceError.observe(viewLifecycleOwner) {
            binding.price.isErrorEnabled = it.isNotEmpty()
            if (it.isNotEmpty()) binding.price.error = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}