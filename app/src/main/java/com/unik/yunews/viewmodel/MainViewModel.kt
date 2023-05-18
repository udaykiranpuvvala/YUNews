package com.unik.yunews.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unik.yunews.models.Article
import com.unik.yunews.models.NewsModel
import com.unik.yunews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Logger

class MainViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val API_KEY = "b77a54e52da24f7a997172dd6d586811"
//    private val API_KEY = "b4a851d17a694b7cb0764a92388b4194"

    /*init{
        viewModelScope.launch (Dispatchers.IO){
            newsRepository.getNews("apple", "2022-09-01", "2022-09-01", "popularity", API_KEY)
        }
    }*/
    val position = MutableLiveData<Int>()
    val webUrl = MutableLiveData<String>()

    // function to send message
    fun setPosition(positionVal: Int) {
        position.value = positionVal
    }

    fun setWebString(webUrlVal: String) {
        webUrl.value = webUrlVal
    }

    fun callIndonesiaLatest(){
        viewModelScope.launch (Dispatchers.IO){
            newsRepository.getIndonesiaNews("id", API_KEY)
        }
    }

    fun callSetToken(token: String){
        viewModelScope.launch (Dispatchers.IO){
//            newsRepository.setDeviceToken(API_KEY,)
            newsRepository.setDeviceToken(token,API_KEY)
        }
    }

    fun callIndonesiaSearchLatest(key: String){
        Log.e("Key","key ::::::::::::::: "+key)
        viewModelScope.launch (Dispatchers.IO){
            newsRepository.getNews(key, "2022-09-01", "2022-11-01", "popularity", API_KEY)
        }
    }

    val news : MutableLiveData<NewsModel?>
        get() = newsRepository.articles
}