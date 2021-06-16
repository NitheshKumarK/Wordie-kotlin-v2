package com.nithesh.wordie.network

import com.squareup.moshi.Json


data class Word(
    val meta: Meta,
    val hwi: HeadWordInformation?,
    val fl: String?,
    val ins: List<Inflections>?,
    //val def: List<Definition>?,
    val dros: List<DefinedRunOns>?,
    @Json(name = "shortdef") val shortDef: List<String>?

)


data class Meta(
    val id: String,
    val uuid: String,
    val src: String?,
    val section: String?,
    val highlight: String?,
    val stems: List<String>?,
    @Json(name = "app-shortdef") val shortDef: AppShortDef?,
    val offensive: Boolean
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

data class Inflections(
    val il: String?,
    @Json(name = "if") val inflection: String?
)

/*
//data class Definition(
//    val sseq: List<Sseq>?,
//)
//
//data class Sseq(
//    val list: List<SenseList>?
//)
//
//data class SenseList(
//    val sense: String?,
//    val senseObject: Sense?
//)
//
//data class Sense(
//    val dt: List<List<String?>?>?
)*/

data class DefinedRunOns(
    val drp: String?
)