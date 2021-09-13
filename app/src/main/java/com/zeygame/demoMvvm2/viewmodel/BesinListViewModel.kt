package com.zeygame.demoMvvm2.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.zeygame.demoMvvm2.db.BesinDatabase
import com.zeygame.demoMvvm2.model.Besin
import com.zeygame.demoMvvm2.service.BesinAPIService
import com.zeygame.demoMvvm2.util.CustomSharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val hataMesaji= MutableLiveData<Boolean>()
    val yukleniyor = MutableLiveData<Boolean>()

    private val apiService = BesinAPIService()
    //Kullan At - Kullan Sil işlem bittiğinde sil
    private val disposable = CompositeDisposable()


    private val customSharedPreferences = CustomSharedPreferences(getApplication())

    fun refreshData(){
        val savedTime = customSharedPreferences.getTime()
        if (savedTime !=null
            && savedTime!=0L
            && System.currentTimeMillis()-savedTime < 10*60*1000){
            // 10 dakika geçmediyse   localden al
            getDataFromSQlite()

        }else{
            // eğer geçmişse internetten al
            getDataFromNet()
        }
    }

    private fun getDataFromSQlite(){
        yukleniyor.value = true

        launch {
            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            showList(besinListesi)
            Toast.makeText(getApplication(),"Room verileri alındı",Toast.LENGTH_LONG).show()

        }
    }

     fun getDataFromNet(){
        yukleniyor.value = true

        disposable.add(
            apiService.getData()
                .subscribeOn(Schedulers.newThread())
                    // map ile search özelliği ekleyebilirsin
                    //.map(BesinModel -> {
                ////                    movieList.postValue(besinmodel.getSearch());
                        //                    return searchingModel;
                         //                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        Toast.makeText(getApplication(),"İnternet verileri alındı",Toast.LENGTH_LONG).show()
                        saveToSQlite(t)
                    }
                    override fun onError(e: Throwable) {
                        hataMesaji.value = true
                        yukleniyor.value = false
                        e.printStackTrace()
                    }
                })
        )

    }
    private fun showList(besinList: List<Besin>){
        besinler.value = besinList
        hataMesaji.value = false
        yukleniyor.value = false
    }

    private fun saveToSQlite(besinList: List<Besin>){
        //baseviewmodel ile
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            //listenin başına yıldız ekleyip totypedarray metodu ile listeyi tekil hale getirir
            val idList = dao.insertAll(*besinList.toTypedArray())

            //oluşturulmuş olan modellere id ler set ediliyor
            var i =0;
            while (i<idList.size){
                besinList[i].uuid = idList[i].toInt()
                i++
            }
            showList(besinList)
        }
        customSharedPreferences.saveTime(System.currentTimeMillis())
    }
}
