package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.util.handleResource
import com.example.myapplication.vm.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val viewModel by activityViewModels<MyViewModel>()
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.countLiveData.observe(viewLifecycleOwner, Observer {
            binding.text1.text = "Count: " + it.toString()
        })

        binding.button1.setOnClickListener {
            viewModel.increment()
        }
        binding.button2.setOnClickListener {
            viewModel.decrement()
        }

        binding.button3.setOnClickListener {
            viewModel.getCurrentTime().observe(viewLifecycleOwner, Observer { resource ->
                Log.i("FirstFragment", "1")
                handleResource(resource,
                    onLoading = {
                        binding.button3.isEnabled = false
                        binding.text2.text = "..loading"
                    },
                    onError = { msg, _ ->
                        binding.text2.text = msg
                        binding.button3.isEnabled = true
                    },
                    onSuccess = {
                        binding.text2.text = it.toString()
                        binding.button3.isEnabled = true
                    })
            })
        }
    }
}
