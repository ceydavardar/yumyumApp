package com.ceydavardar.yumyumapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ceydavardar.yumyumapp.databinding.FragmentSignInBinding
import com.ceydavardar.yumyumapp.ui.viewmodel.SignInViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signUpNavigationText.setOnClickListener {
            val gecis = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(gecis)
        }

        binding.button.setOnClickListener {

            if (binding.usernameInput.text.isNullOrBlank()) {
                Snackbar.make(it, "Email boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.passwordInput.text.isNullOrBlank()) {
                Snackbar.make(it, "Şifre boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                if (viewModel.passwordMatch(binding.usernameInput.text.toString())) {
                    viewModel.repo.username = binding.usernameInput.text.toString()
                    val gecis = SignInFragmentDirections.actionSignInFragmentToMainFragment()
                    Navigation.findNavController(it).navigate(gecis)
                } else {
                    Snackbar.make(it, "Email veya şifre yanlış.", Snackbar.LENGTH_SHORT).show()
                }
            }

        }

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SignInViewModel by viewModels()
        viewModel = tempViewModel
    }

}