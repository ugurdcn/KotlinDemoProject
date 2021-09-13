package com.zeygame.demoMvvm2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

//Bütün uygulamaya uyarlayabilmek için Android View Model dan türeyecek
open class BaseViewModel(application: Application) : AndroidViewModel(application) ,CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main // arkaplanda yapılan işlem yapılıp main threade dönecek

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}