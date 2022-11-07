package com.example.viewmodelsandapitutorial.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Todo(
//    var userId: Int,
    var id: Int,
    var title: String,
    var completed: Boolean
)

//const val BASE_URL = "https://jsonplaceholder.typicode.com/"
const val BASE_URL = "http://10.0.2.2:8080/api/"
// should be run on: python3 manage.py runserver 8080

interface ApiService{
    @GET("task-list")
    suspend fun getTodos(): List<Todo>

    companion object{
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}