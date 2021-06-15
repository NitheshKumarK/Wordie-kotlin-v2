package com.nithesh.wordie.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://www.dictionaryapi.com/api/v3/references/learners/json/"
private const val BASE_KEY: String = "532c81fa-1ada-455a-b65e-e1d7924ab6c9"

val moshi: Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val wordService: WebService by lazy { retrofit.create(WebService::class.java) }

interface WebService {
    @GET(value = "{word}?key=$BASE_KEY")
    suspend fun getWordListAsync(@Path("word") word: String): List<Word>
}