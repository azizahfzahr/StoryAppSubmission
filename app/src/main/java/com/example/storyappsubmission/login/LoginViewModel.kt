package com.example.storyappsubmission.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.pref.UserModel
import com.example.storyappsubmission.pref.UserPreference
import com.example.storyappsubmission.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference):ViewModel() {
    val data = MutableLiveData<LoginResponse>()
    fun login(email: String, password: String){
        val retro = com.example.storyappsubmission.api.ApiConfig.getApiService().login(email,password)
        retro.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    data.postValue(response.body())
                    saveUser(UserModel(response.body()?.loginResult?.token!!,true))
                }else{
                    Log.d("Error :", response.message().toString())
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                Log.d("Failure", t.message!!)
            }
        })
    }
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
    fun getLogin(): LiveData<LoginResponse> {
        return data

    }
}