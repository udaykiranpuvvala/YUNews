package com.unik.yunews.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.unik.yunews.api.NewsService
import com.unik.yunews.models.Article
import com.unik.yunews.models.NewsModel
import java.util.logging.Logger

class NewsRepository (val newService: NewsService) {

    private val articleList = MutableLiveData<NewsModel?>()

    val articles : MutableLiveData<NewsModel?>
        get() = articleList

    suspend fun getNews(q:String, from : String, to : String, sortBy : String, apiKey : String){
        val response = newService.getNews(q,from,to,sortBy,apiKey)
        Log.e("Key","key getNews ::::::::::::::: "+response)

        if (response.body()!=null){
            Log.e("Key","key getNews ::::::::::::::: "+response.body())
            Log.d("Home_Activity", "getNews: response body not null " + response.body())
            articleList.postValue(response.body())
        }else{
            articleList.postValue(null)
        }
    }

    suspend fun getIndonesiaNews( country : String, apiKey : String){
//        val response = newService.getIndonesiaNews(country,apiKey)
        val response = newService.getIndonesiaNews(1,"x9CR6845LF3fyy5",2,"hyd")

        Log.e("Key","key getNews ::::::::::::::: "+response)
        if (response.body()!=null){
            Log.e("Key","key getIndonesiaNews ::::::::::::::: "+response.body())
            Log.d("Home_Activity", "getIndonesiaNews: response body not null " + response.body())
            articleList.postValue(response.body())
        }else{
            articleList.postValue(null)
        }
    }

    suspend fun setDeviceToken(token: String,apiKey:String){
        val response = newService.setDeviceToken(""+token,"Android",apiKey)
        Log.e("setDeviceToken","setDeviceToken ::: "+response)
    }

}