package com.zeygame.demoMvvm2.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import demoMvvm2.R


/// deneme extension
//fun String.benimEklentim(param:String){
//    println(param)
//}

fun ImageView.load(url:String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions.placeholderOf(placeholder)
        .error(R.mipmap.ic_launcher)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun createPlaceholder(context: Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}