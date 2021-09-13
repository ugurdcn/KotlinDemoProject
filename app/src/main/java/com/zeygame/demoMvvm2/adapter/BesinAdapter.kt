package com.zeygame.demoMvvm2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.zeygame.demoMvvm2.model.Besin
import com.zeygame.demoMvvm2.util.createPlaceholder
import com.zeygame.demoMvvm2.util.load
import com.zeygame.demoMvvm2.view.FragmentListDirections
import demoMvvm2.R
import demoMvvm2.databinding.RowLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class BesinAdapter(val list: ArrayList<Besin>) : RecyclerView.Adapter<BesinAdapter.MyViewHolder>(),
    CustomClickListener {
    class MyViewHolder(var view: RowLayoutBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//            .inflate(R.layout.row_layout, parent, false)
        val view =
            DataBindingUtil.inflate<RowLayoutBinding>(inflater, R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //Data Binding yazımı

        holder.view.besin = list.get(position)
        holder.view.clickListener = this


        //Normal yazım

//        holder.itemView.txRowIsim.text = list.get(position).besimIsim
//        holder.itemView.txRowKalori.text = list.get(position).besinKalori

        //extension oluşturup imageview classına load fonksiyonu dahil ettim in Util.kt

//        holder.itemView.imgRowBesin.load(list.get(position).besinGorsel
//            , createPlaceholder(holder.itemView.context))
//        holder.itemView.setOnClickListener({
//            val action = FragmentListDirections.actionFragmentListToFragmentDetails(list.get(position).uuid)
//            Navigation.findNavController(it).navigate(action)
//        })
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:downloadImage")
        fun downloadImage(imgView: ImageView, url: String?) {
            imgView.load(url, createPlaceholder(imgView.context))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listBesin: List<Besin>) {
        list.clear()
        list.addAll(listBesin)
        notifyDataSetChanged()
    }

    override fun onClick(view: View, uuid: Int) {
        val action = FragmentListDirections.actionFragmentListToFragmentDetails(uuid)
        Navigation.findNavController(view).navigate(action)
    }
}