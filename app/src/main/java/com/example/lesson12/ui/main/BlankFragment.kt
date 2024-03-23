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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BlankFragment : Fragment() {


    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels{MainViewModelFactory()}

    companion object{
        fun newInstance() = BlankFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val login = binding.login.text.toString()
            val password =  binding.password.text.toString()
            viewModel.onSignInClick(login, password)
        }
//        viewLifecycleOwner отвечает за жизненный цикл окна
//        lifecycleScope для запуска CoroutineScope
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        MyState.Loading -> {
                            binding.progress.isVisible = true
                            binding.loginLayout.error = null
                            binding.passwordLayout.error = null
                            binding.button.isEnabled = false
                        }

                        MyState.Success -> {
                            binding.progress.isVisible = false
                            binding.loginLayout.error = null
                            binding.passwordLayout.error = null
                            binding.button.isEnabled = true
                        }

                        is MyState.Error -> {
                            binding.progress.isVisible = false
                            binding.loginLayout.error = state.loginError
                            binding.passwordLayout.error = state.passwordError

                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { message ->
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


