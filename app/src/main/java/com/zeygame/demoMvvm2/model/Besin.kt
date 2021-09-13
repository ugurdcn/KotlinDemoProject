package com.zeygame.demoMvvm2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "BesinTable")
data class Besin(
    @ColumnInfo(name = "isim")
    @SerializedName("isim")
    var besimIsim : String?,
    @ColumnInfo(name = "kalori")
    @SerializedName("kalori")
    var besinKalori : String?,
    @ColumnInfo(name = "karbonhidrat")
    @SerializedName("karbonhidrat")
    var besinKarbonhidrat: String?,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    var besinProtein: String?,
    @ColumnInfo(name = "yag")
    @SerializedName("yag")
    var besinYag: String?,
    @ColumnInfo(name = "gorsel")
    @SerializedName("gorsel")
    var besinGorsel : String?) {

    @PrimaryKey(autoGenerate = true)
    var uuid : Int =0
}