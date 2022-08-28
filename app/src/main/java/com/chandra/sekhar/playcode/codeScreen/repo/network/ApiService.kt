package com.chandra.sekhar.playcode.codeScreen.repo.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/*
* Base url for request
* */
private val BASE_URL = "https://api.jdoodle.com/v1/"

/**
 * Moshi converter factory : Json to Object and vise-versa
 * */
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/*
* Retrofit object for HTTP request
* **/

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiService{

    @POST("execute")
    suspend fun runCode(@Body model: ProgramModel ) : OutputModel

}

object CodeRunnerAPi{

    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}