package com.ceydavardar.yumyumapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ceydavardar.yumyumapp.databinding.FragmentSignUpBinding
import com.ceydavardar.yumyumapp.ui.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signInNavigationText.setOnClickListener {

            val navDir = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            Navigation.findNavController(it).navigate(navDir)
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

            viewModel.usernameExists(binding.usernameInput.text.toString()) { exists ->
                if (exists) {
                    Snackbar.make(it, "Bu isim daha önce alınmış.", Snackbar.LENGTH_SHORT).show()
                } else {
                    viewModel.save(binding.usernameInput.text.toString(), binding.passwordInput.text.toString())

                    viewModel.repo.username = binding.usernameInput.text.toString()

                    val gecis = SignUpFragmentDirections.actionSignUpFragmentToMainFragment()
                    Navigation.findNavController(it).navigate(gecis)
                }
            }

        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SignUpViewModel by viewModels()
        viewModel = tempViewModel
    }

}