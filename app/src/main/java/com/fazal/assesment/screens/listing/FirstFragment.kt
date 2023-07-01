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
        _binding?.vm = vm
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.navigateToAdd.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
                    vm.reset()
                }
            }
        }

        binding.rvListProducts.adapter = AdapterProducts()
            .apply {
            vm.list.observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        vm.showLoading(true)
                    }
                    Status.ERROR -> {
                        vm.showLoading(false)
                        vm.setError(it.message!!)
                    }
                    Status.SUCCESS -> {
                        vm.showLoading(false)
                        it.data?.body().let { list ->
                            this.submitList(list)
                        }
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