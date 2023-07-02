package com.fazal.assesment.screens.add

import android.app.Activity
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fazal.assesment.R
import com.fazal.assesment.api.Status
import com.fazal.assesment.databinding.FragmentSecondBinding
import com.fazal.assesment.utils.showTwoButtonDialog
import com.github.dhaval2404.imagepicker.ImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
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
                    showTwoButtonDialog(requireContext(),
                        onDismissListener = { dialog, which ->
                            if(which == DialogInterface.BUTTON_POSITIVE){
                                findNavController().navigate(SecondFragmentDirections.actionSecondFragmentToFirstFragment())
                            }
                        })
                    vm.reset()
                }
            }
        }
        vm.clickedUpload.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    openImagePicker()
                    vm.reset()
                }
            }
        }
        vm.clickedRemove.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    vm.setUri(Uri.parse(""))
                    vm.reset()
                }
            }
        }
        vm.saveResponse.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        vm.setLoadingText("Saving...")
                    }
                    Status.ERROR -> {
                        vm.setLoadingText("")
                        vm.setError(it.message!!)
                    }
                    Status.SUCCESS -> {
                        vm.setLoadingText("")
                        //showDialog
                        vm.goToList()
                    }
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

        setUpDropDown()
    }


    private fun openImagePicker() {
        ImagePicker.with(this)
            .compress(1024)//Final image size will be less than 1 MB(Optional)
            .galleryMimeTypes(arrayOf(
                "image/png",
                "image/jpg",
                "image/jpeg"))
            .cropSquare()
            .maxResultSize(1024, 1024)  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                //binding.pbLoading.visibility = View.VISIBLE
                startForProfileImageResult.launch(intent)
            }
    }


    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                data?.data?.let {vm.setUri(it)}
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
                //binding.pbLoading.visibility = View.INVISIBLE
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                //binding.pbLoading.visibility = View.INVISIBLE
            }
        }
    }

    private fun setUpDropDown() {
        val arrType = requireContext().resources.getStringArray(R.array.types)
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_type,
            arrType
        )
        binding.etType.setAdapter(adapter)
        binding.etType.setOnItemClickListener { parent, view, position, id ->
            vm.type.postValue(arrType[position])
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}