package com.example.storyappsubmission.response

import com.google.gson.annotations.SerializedName

data class MapsResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<MapsStory>
)
data class MapsStory(
    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("name")
    val name: String="angga",

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("photoUrl")
    val photoUrl: String?,

    @field:SerializedName("createdAt")
    val createdAt: String?,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double

)