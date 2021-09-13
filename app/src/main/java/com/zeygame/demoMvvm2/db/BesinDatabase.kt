package com.zeygame.demoMvvm2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zeygame.demoMvvm2.model.Besin
import com.zeygame.demoMvvm2.service.BesinDAO

@Database(entities = arrayOf(Besin::class),version = 1)
abstract class BesinDatabase : RoomDatabase(){

    abstract fun besinDao() : BesinDAO

    ///Singleton
    companion object {

        //Diğer threadlere görünür hale getirmek için Volatile kullandım
        @Volatile private var instance :BesinDatabase? = null

        //lock değişkeni kilidi takip etmek için oluşturuldu
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDB(context).also {
                instance = it
            }
        }

        private fun createDB(context: Context) = Room.databaseBuilder(context.applicationContext
            ,  BesinDatabase::class.java
            ,"besindatabase").build()
    }

}