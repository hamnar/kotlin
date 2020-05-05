package com.hem.kotlinrecyclerview


import com.google.gson.annotations.SerializedName

data class ContactsItem(
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String
)