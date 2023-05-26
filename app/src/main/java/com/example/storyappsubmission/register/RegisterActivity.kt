package com.example.storyappsubmission.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.storyappsubmission.databinding.ActivityRegisterBinding
import com.example.storyappsubmission.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel : RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]

        setupMasuk()
        viewModel.getRegister().observe(this) {
            if (it==null) {
                showLoading(true)
            }else{
                Toast.makeText(this,"${it.message}",Toast.LENGTH_LONG).show()
                showLoading(false)
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
    private fun setupMasuk(){
        val nama = binding.nama.text
        val email = binding.emailCustom.text
        val pass = binding.passwordCustom.text
        binding.button.setOnClickListener {
            when{
                nama.isEmpty() -> {
                    Toast.makeText(this, "Harap isi nama anda", Toast.LENGTH_LONG).show()
                }
                email?.isEmpty()!! -> {
                    Toast.makeText(this, "Harap isi email anda", Toast.LENGTH_LONG).show()
                }
                pass?.isEmpty()!! -> {
                    Toast.makeText(this, "Harap isi password anda", Toast.LENGTH_LONG).show()
                }else->{
                setupRegister()
                showLoading(true)
            }
            }
        }
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
    private fun setupRegister(){
        binding.apply {
            val nama = binding.nama.text.toString()
            val email = emailCustom.text.toString()
            val pass = passwordCustom.text.toString()
            viewModel.register(nama,email,pass)
        }
    }
    private fun showLoading(loading: Boolean){
        if(loading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}