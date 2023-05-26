package com.example.storyappsubmission.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.storyappsubmission.paging.Injection
import com.example.storyappsubmission.paging.StoryRepo
import com.example.storyappsubmission.pref.UserModel
import com.example.storyappsubmission.pref.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference, private val repo: StoryRepo) : ViewModel() {

    fun getStoryPage(token: String) = repo.getStory(token).cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }



    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    class ViewModelFactory(private val context: Context, private val pref: UserPreference) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(pref, Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}