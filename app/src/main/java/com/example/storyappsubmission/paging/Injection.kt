package com.example.storyappsubmission.paging

import android.content.Context
import com.example.storyappsubmission.api.ApiConfig
import com.example.storyappsubmission.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): StoryRepo{
        val apiService = ApiConfig.getApiService()
        val storyDatabase = StoryDatabase.getDatabase(context)
        return StoryRepo(storyDatabase, apiService)
    }
}