package com.fazal.assesment.screens.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fazal.assesment.api.Status
import com.fazal.assesment.databinding.FragmentFirstBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModel<FirstViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.vm = vm
        _binding?.lifecycleOwner = viewLifecycleOwner

        vm.navigateToAdd.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
                    vm.reset()
                }
            }
        }

        val adapter = AdapterProducts().apply {
                vm.list.observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            vm.showLoading(true)
                        }
                        Status.ERROR -> {
                            vm.showLoading(false)
                            vm.setError(resource.message!!)
                        }
                        Status.SUCCESS -> {
                            vm.showLoading(false)
                            resource.data?.body().let { list ->
                                this.submitList(list)
                            }
                        }
                    }
                }
            }
        binding.rvListProducts.adapter = adapter

        vm.searchQuery.observe(viewLifecycleOwner) { query ->
            vm.list.value?.data?.body()?.let { list ->
                if (query.isNullOrEmpty()) {
                    adapter.submitList(list)
                } else {
                    val filterList = list.filter {
                        it.productName?.contains(query,true)!! || it.productType?.contains(query,true)!!

                    }
                    adapter.submitList(filterList)
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}