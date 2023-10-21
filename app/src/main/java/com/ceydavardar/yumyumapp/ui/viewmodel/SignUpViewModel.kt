package com.ceydavardar.yumyumapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ceydavardar.yumyumapp.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    var repo: UserRepository
): ViewModel() {

    fun save(username: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            repo.save(username, password)
        }
    }

    fun usernameExists(username: String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            if (repo.getUsers().any { it.username == username}) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }

}