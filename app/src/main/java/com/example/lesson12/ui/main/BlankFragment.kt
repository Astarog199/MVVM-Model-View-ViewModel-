package com.example.lesson12.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lesson12.databinding.FragmentBlankBinding
import kotlinx.coroutines.launch

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var binding: FragmentBlankBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.button.setOnClickListener {
            val login = binding.login.text.toString()
            val password = binding.login.text.toString()
            viewModel.onSignInClick(login, password)
        }
//        viewLifecycleOwner отвечает за жизненный цикл окна
//        lifecycleScope для запуска CoroutineScope
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        MyState.Loading -> {
                            binding.progress.isVisible = true
                        }

                        MyState.Success -> {
                            binding.progress.isVisible = false
                        }
                    }
                }
        }
        }
    }
}

