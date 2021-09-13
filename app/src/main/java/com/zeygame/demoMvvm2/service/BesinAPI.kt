package com.zeygame.demoMvvm2.service

import com.zeygame.demoMvvm2.model.Besin
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BesinAPI {
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>

}