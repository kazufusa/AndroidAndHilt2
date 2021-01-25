package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.repo.handleResource
import com.example.myapplication.vm.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZoneId

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val viewModel by activityViewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.countLiveData.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.text1).text = "Count: " + it.toString()
        })

        view.findViewById<Button>(R.id.button1).setOnClickListener {
            viewModel.increment()
        }
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            viewModel.decrement()
        }

        view.findViewById<Button>(R.id.button3).setOnClickListener {
            viewModel.getCurrentTime().observe(viewLifecycleOwner, Observer { resource ->
                Log.i("FirstFragment", "1")
                handleResource(resource,
                    onLoading = {
                        view.findViewById<Button>(R.id.button3).isEnabled = false
                        view.findViewById<TextView>(R.id.text2).text = "..loading"
                    },
                    onError = { msg, _ ->
                        view.findViewById<TextView>(R.id.text2).text = msg
                        view.findViewById<Button>(R.id.button3).isEnabled = true
                    },
                    onSuccess = {
                        view.findViewById<TextView>(R.id.text2).text = it.toString()
                        view.findViewById<Button>(R.id.button3).isEnabled = true
                    })
            })
        }
    }
}
