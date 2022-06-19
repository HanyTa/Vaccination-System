package com.hany.vaccinationsystem.utils.viewModelFactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hany.vaccinationsystem.ui.login.LoginViewModel

class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}