package com.hany.vaccinationsystem.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.hany.vaccinationsystem.R
import com.hany.vaccinationsystem.databinding.ActivityLoginBinding
import com.hany.vaccinationsystem.utils.viewModelFactories.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModelFactory = LoginViewModelFactory(application)
        viewModel =ViewModelProvider(this,loginViewModelFactory)[LoginViewModel::class.java]

        viewModel.pageLoad()
//        val navController = findNavController(R.id.nav_host_fragment_content_login)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.email.addTextChangedListener {
            viewModel.onEmailChanged(it.toString())
        }

        binding.password.addTextChangedListener {
            viewModel.onPasswordChanged(it.toString())
        }

        binding.loginButton.setOnClickListener {
            viewModel.onLoginClicked()
        }

        viewModel.failedMessage().observe(this, {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })

        viewModel.successMessage().observe(this, {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })

        viewModel.emptyEmailMessage().observe(this, {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })

        viewModel.emptyPasswordMessage().observe(this, {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })
        viewModel.errorEmailMessage().observe(this, {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })

        viewModel.errorPasswordMessage().observe(this, {
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_login)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}