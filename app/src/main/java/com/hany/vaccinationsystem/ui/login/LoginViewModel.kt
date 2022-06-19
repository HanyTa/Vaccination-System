package com.hany.vaccinationsystem.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface Inputs {
    /**Invoke when change email text*/
    fun onEmailChanged(email: String)

    /**Invoke when change password text*/
    fun onPasswordChanged(password: String)

    /**Invoke when click on login button*/
    fun onLoginClicked()

}

interface Outputs {
    /**when login succeeded*/
    fun successMessage() : LiveData<String>

    /**when login failed*/
    fun failedMessage() : LiveData<String>
}

class LoginViewModel(application: Application) : AndroidViewModel(application),Inputs,Outputs {

    private var auth: FirebaseAuth = Firebase.auth
    private var successMessage : MutableLiveData<String> = MutableLiveData()
    private var failedMessage : MutableLiveData<String> = MutableLiveData()
    private lateinit var email : String
    private lateinit var password : String


    override fun onEmailChanged(email: String) {
        this.email = email
    }

    override fun onPasswordChanged(password: String) {
        this.password = password
    }

    override fun onLoginClicked() {

    }

    override fun successMessage(): LiveData<String> {
        return successMessage
    }

    override fun failedMessage(): LiveData<String> {
        return failedMessage
    }


}