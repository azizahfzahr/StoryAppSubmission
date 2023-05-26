package com.example.storyappsubmission.paging

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.storyappsubmission.api.ApiService
import com.example.storyappsubmission.data.StoryRemoteMediator
import com.example.storyappsubmission.database.StoryDatabase
import com.example.storyappsubmission.response.story

class StoryRepo (private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStory(token: String): LiveData<PagingData<story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase,apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}