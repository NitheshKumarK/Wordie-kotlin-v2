package com.nithesh.wordie.network

import com.squareup.moshi.Json


data class Word(
    val meta: Meta,
    val highlight: String?,
    val stems: List<String>?,
    @Json(name = "app-shortdef") val appShortDef: AppShortDef?,
    val offensive: Boolean?,
    val hwi: HeadWordInformation?,
    val fl: String?,
    )


data class Meta(
    val id: String,
    val uuid: String,
    val src: String?,
    val section: String?
)


data class AppShortDef(
    val hw: String?,
    val fl: String?,
    val def: List<String>?
)


data class HeadWordInformation(
    val hw: String?,
    val prs: List<Pronunciation>?
)


data class Pronunciation(
    val ipa: String?,
    val sound: Sound?
)


data class Sound(
    val audio: String?
)
