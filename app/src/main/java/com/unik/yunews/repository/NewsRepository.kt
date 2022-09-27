package com.unik.yunews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unik.yunews.api.NewsService
import com.unik.yunews.models.Article
import com.unik.yunews.models.NewsModel

class NewsRepository (val newService: NewsService) {

    private val articleList = MutableLiveData<NewsModel>()

    val articles : LiveData<NewsModel>
        get() = articleList

    suspend fun getNews(q:String, from : String, to : String, sortBy : String, apiKey : String){
        val response = newService.getNews(q,from,to,sortBy,apiKey)

        if (response.body()!=null){
            articleList.postValue(response.body())
        }
    }

    suspend fun getIndonesiaNews( country : String, apiKey : String){
        val response = newService.getIndonesiaNews(country,apiKey)

        if (response.body()!=null){
            articleList.postValue(response.body())
        }
    }

}