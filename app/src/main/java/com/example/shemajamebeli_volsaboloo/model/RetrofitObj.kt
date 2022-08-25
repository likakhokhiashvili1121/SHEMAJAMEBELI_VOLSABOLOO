package com.example.shemajamebeli_volsaboloo.model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitObj {
    private const val BASE_URL = "https://reqres.in/api/"

    private val instance by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAuthApi(): GettinPostApiState = instance.create(GettinPostApiState::class.java)


    interface GettinPostApiState {

        @POST("login")
        suspend fun getLoginForm(@Body userInfo: AboutUser): Response<LoginUser>

        @POST("register")
        suspend fun getRegisterForm(@Body userInfo: AboutUser): Response<RegisterUser>

    }

}