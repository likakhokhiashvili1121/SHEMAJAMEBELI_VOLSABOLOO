package com.example.shemajamebeli_volsaboloo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shemajamebeli_volsaboloo.BaseFragment
import com.example.shemajamebeli_volsaboloo.FragmentRes
import com.example.shemajamebeli_volsaboloo.R
import com.example.shemajamebeli_volsaboloo.ResponseState
import com.example.shemajamebeli_volsaboloo.databinding.FragmentRegisterBinding
import com.example.shemajamebeli_volsaboloo.model.AboutUser
import com.example.shemajamebeli_volsaboloo.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun viewCreated() {

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.btnRegister.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getRegisterFlow(getUserInfo()).collect {
                    when (it) {
                        is ResponseState.Success<*> -> {
                            buildFragmentResult()
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                        }
                        is ResponseState.Error -> {
                            Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                        }
                        is ResponseState.Loader -> {
                            binding.progressBar.isVisible = it.isLoading
                        }
                    }
                }
            }
        }
    }

    private fun buildFragmentResult() {
        setFragmentResult(
            requestKey = FragmentRes.AUTH_KEY,
            result = bundleOf(
                FragmentRes.EMAIL to binding.etEmail.text.toString(),
                FragmentRes.PASSWORD to binding.etPassword.text.toString()
            )
        )
    }

    private fun getUserInfo() = AboutUser(
        binding.etEmail.text.toString(),
        binding.etPassword.text.toString()
    )

}