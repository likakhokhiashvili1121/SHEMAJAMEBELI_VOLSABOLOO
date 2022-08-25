package com.example.shemajamebeli_volsaboloo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shemajamebeli_volsaboloo.*
import com.example.shemajamebeli_volsaboloo.databinding.FragmentLoginBinding
import com.example.shemajamebeli_volsaboloo.model.AboutUser
import com.example.shemajamebeli_volsaboloo.model.LoginUser
import com.example.shemajamebeli_volsaboloo.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun viewCreated() {

        checkSession()

        fragmentResultListener()

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.btnLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getLoginFlow(getUserInfo()).collect {
                    when(it) {
                        is ResponseState.Success<*> -> {
                            if (binding.cbRemember.isChecked) {
                                viewModel.save(PrefKeys.TOKEN, (it.result as LoginUser).token ?: "")
                            } else {
                                findNavController().navigate(
                                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                                        token = (it.result as LoginUser).token ?: ""
                                    )
                                )
                            }
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

    private fun checkSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPreferences().collect {
                if (it.contains(stringPreferencesKey(PrefKeys.TOKEN))) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                        token = it[stringPreferencesKey(PrefKeys.TOKEN)] ?: "No Data"
                    ))
                }
            }
        }
    }

    private fun fragmentResultListener() {
        setFragmentResultListener(FragmentRes.AUTH_KEY) { _, bundle ->
            binding.etEmail.setText(bundle.getString(FragmentRes.EMAIL, "No Value"))
            binding.etPassword.setText(bundle.getString(FragmentRes.PASSWORD, "No Value"))
        }
    }

    private fun getUserInfo() = AboutUser(
        binding.etEmail.text.toString(),
        binding.etPassword.text.toString()
    )

}