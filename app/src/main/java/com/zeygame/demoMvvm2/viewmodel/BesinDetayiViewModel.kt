package com.zeygame.demoMvvm2.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeygame.demoMvvm2.db.BesinDatabase
import com.zeygame.demoMvvm2.model.Besin
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application): BaseViewModel(application){
    val besinLiveData = MutableLiveData<Besin>()

    fun getRoomData(uuid:Int){
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besinModel = dao.getSingleBesin(uuid)
            besinLiveData.value = besinModel
        }
    }
}