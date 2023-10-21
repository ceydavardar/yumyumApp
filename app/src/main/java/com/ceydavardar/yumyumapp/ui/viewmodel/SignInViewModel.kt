package com.ceydavardar.yumyumapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ceydavardar.yumyumapp.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    var repo: UserRepository
): ViewModel() {

    suspend fun passwordMatch(username: String): Boolean {
        val match = repo.getPassword(username)
        return match != null
    }

}