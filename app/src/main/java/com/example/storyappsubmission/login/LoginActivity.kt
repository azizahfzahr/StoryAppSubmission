package com.example.storyappsubmission.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyappsubmission.ui.MainActivity
import com.example.storyappsubmission.databinding.ActivityLoginBinding
import com.example.storyappsubmission.pref.UserPreference
import com.example.storyappsubmission.register.RegisterActivity
import com.example.storyappsubmission.ui.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel : LoginViewModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[LoginViewModel::class.java]
        playAnimation()
        setupMasuk()
        viewModel.getLogin().observe(this) {
            if (it.error) {
                showLoading(false)
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }else{
                showLoading(true)
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setupMasuk(){
        val email = binding.emailCustom.text
        val pass = binding.passwordCustom.text
        binding.button.setOnClickListener {
            when{
                email?.isEmpty()!! -> {
                    Toast.makeText(this, "Harap isi email anda", Toast.LENGTH_LONG).show()
                }
                pass?.isEmpty()!! -> {
                    Toast.makeText(this, "Harap isi password anda", Toast.LENGTH_LONG).show()
                }else->{
                setupLogin()
                showLoading(true)
            }
            }
        }
        binding.registerBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
    private fun setupLogin(){
        binding.apply {
            val email = emailCustom.text.toString()
            val pass = passwordCustom.text.toString()
            viewModel.login(email, pass)
        }
    }



    private fun playAnimation() {
        val emailEditText = ObjectAnimator.ofFloat(binding.emailCustom, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordCustom, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.button, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerBtn, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(emailEditText, passwordEditTextLayout, login,register)
            startDelay = 500
        }.start()
    }
    private fun showLoading(loading: Boolean){
        if(loading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}