package com.zeygame.demoMvvm2.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeygame.demoMvvm2.model.Besin

@Dao
interface BesinDAO {
    //Data access object - Veri erişim objesi


    //vararg çoklu değişken veya obje için,, id listesini döndürecek
    @Insert
    suspend fun insertAll(vararg besin: Besin) : List<Long>

    @Query("select * from BesinTable")
    suspend fun getAllBesin() : List<Besin>

    @Query("delete from BesinTable")
    suspend fun deleteAllBesin()

    @Query("select * from BesinTable where uuid = :besinId")
    suspend fun getSingleBesin(besinId: Int) : Besin


}