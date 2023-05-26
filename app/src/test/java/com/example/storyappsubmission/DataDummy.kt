package com.example.storyappsubmission

import com.example.storyappsubmission.response.story

object DataDummy {

    fun generateDummyStoryResponse(): List<story> {
        val items: MutableList<story> = arrayListOf()
        for (i in 0..100) {
            val story = story(
                i.toString(),
                "name + $i",
                "description + $i",
                "photoUrl + $i",
                "createdAt + $i",
                "lat + $i",
                "lon $i",
            )
            items.add(story)
        }
        return items
    }
}