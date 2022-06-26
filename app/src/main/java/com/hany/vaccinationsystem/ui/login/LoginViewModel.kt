package com.hany.vaccinationsystem.ui.login

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hany.vaccinationsystem.R

interface Inputs {

    /**Invoke when page load*/
    fun pageLoad()

    /**Invoke when change email text*/
    fun onEmailChanged(email: String)

    /**Invoke when change password text*/
    fun onPasswordChanged(password: String)

    /**Invoke when click on login button*/
    fun onLoginClicked()

}

interface Outputs {
    /**get when empty email*/
    fun emptyEmailMessage(): LiveData<String>

    /**get when invalid email*/
    fun errorEmailMessage(): LiveData<String>

    /**get when empty password*/
    fun emptyPasswordMessage(): LiveData<String>

    /**get when invalid password*/
    fun errorPasswordMessage(): LiveData<String>

    /**when login succeeded*/
    fun successMessage(): LiveData<String>

    /**when login failed*/
    fun failedMessage(): LiveData<String>
}

class LoginViewModel(application: Application) : AndroidViewModel(application), Inputs, Outputs {

    private var auth: FirebaseAuth = Firebase.auth
    private val successMessage: MutableLiveData<String> = MutableLiveData()
    private val failedMessage: MutableLiveData<String> = MutableLiveData()
    private val emptyEmailMessage: MutableLiveData<String> = MutableLiveData()
    private val errorEmailMessage: MutableLiveData<String> = MutableLiveData()
    private val emptyPasswordMessage: MutableLiveData<String> = MutableLiveData()
    private val errorPasswordMessage: MutableLiveData<String> = MutableLiveData()
    private lateinit var email: String
    private lateinit var password: String

    override fun pageLoad() {
        email = ""
        password = ""
    }


    override fun onEmailChanged(email: String) {
        this.email = email
    }

    override fun onPasswordChanged(password: String) {
        this.password = password
    }

    override fun onLoginClicked() {
        if (validateFields()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getApplication<Application>().mainExecutor) {
                    if (it.isSuccessful) {
                        successMessage.value =
                            getApplication<Application>().resources.getString(R.string.login_success)
                    } else if (it.exception != null) {
                        failedMessage.value = it.exception!!.message
                    }
                }
        }
    }

    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(email)) {
            emptyEmailMessage.value =
                getApplication<Application>().resources.getString(R.string.empty_email)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorEmailMessage.value =
                getApplication<Application>().resources.getString(R.string.invalid_email)
            return false
        } else if (TextUtils.isEmpty(password)) {
            emptyPasswordMessage.value =
                getApplication<Application>().resources.getString(R.string.empty_password)
            return false
        } else if (password.length < 8) {
            errorPasswordMessage.value =
                getApplication<Application>().resources.getString(R.string.minimum_eight)
            return false
        } else if (!password.matches(".*[A-Z].*".toRegex())) {
            errorPasswordMessage.value =
                getApplication<Application>().resources.getString(R.string.contain_1_uppercase)
            return false
        }
        if (!password.matches(".*[a-z].*".toRegex())) {
            errorPasswordMessage.value =
                getApplication<Application>().resources.getString(R.string.contain_1_lowercase)
            return false
        }
        if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {
            errorPasswordMessage.value =
                getApplication<Application>().resources.getString(R.string.contain_1_special)
            return false
        }
        return true
    }

    override fun emptyEmailMessage(): LiveData<String> {
        return emptyEmailMessage
    }

    override fun errorEmailMessage(): LiveData<String> {
        return errorEmailMessage
    }

    override fun emptyPasswordMessage(): LiveData<String> {
        return emptyPasswordMessage
    }

    override fun errorPasswordMessage(): LiveData<String> {
        return errorPasswordMessage
    }

    override fun successMessage(): LiveData<String> {
        return successMessage
    }

    override fun failedMessage(): LiveData<String> {
        return failedMessage
    }


}