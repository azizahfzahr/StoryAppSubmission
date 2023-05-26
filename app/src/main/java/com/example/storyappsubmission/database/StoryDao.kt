package com.example.storyappsubmission.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storyappsubmission.response.story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<story>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, story>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}