package com.unik.yunews.api

import com.google.gson.JsonElement
import com.unik.yunews.models.Article
import com.unik.yunews.models.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    )
            : Response<NewsModel>


    /*@GET("/v2/top-headlines")
    suspend fun getIndonesiaNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    )
            : Response<NewsModel>*/


    @GET("/api/Getnews")
    suspend fun getIndonesiaNews(
        @Query("userid") userId: Int,
        @Query("Apikey") apiKey: String,
        @Query("numberofnews") newsCount: Int,
        @Query("area") area: String
    )
            : Response<NewsModel>

    @POST("/api/PostDevice")
    suspend fun setDeviceToken(
        @Query("device_token") deviceToken: String,
        @Query("device_type") deviceType: String,
        @Query("Apikey") apiKey: String
    )
            : Response<JsonElement>
}