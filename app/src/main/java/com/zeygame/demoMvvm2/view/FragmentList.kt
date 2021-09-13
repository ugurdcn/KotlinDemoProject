package com.zeygame.demoMvvm2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeygame.demoMvvm2.adapter.BesinAdapter
import com.zeygame.demoMvvm2.viewmodel.BesinListViewModel
import demoMvvm2.R
import kotlinx.android.synthetic.main.fragment_list.*

class FragmentList : Fragment() {
    val recylerAdapter = BesinAdapter(arrayListOf())
    private lateinit var viewModel: BesinListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BesinListViewModel::class.java)
        viewModel.refreshData()

        recyclerBesinler.layoutManager = LinearLayoutManager(context)
        recyclerBesinler.adapter = recylerAdapter

        swipeRefresh.setOnRefreshListener {
            hide()
            progressbar.visibility = View.VISIBLE
            viewModel.getDataFromNet()
            //bizim progressimiz olduğundan swipe progressini kapatıyoruz
            swipeRefresh.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, {
            it?.let {
                hide()
                recyclerBesinler.visibility = View.VISIBLE
                recylerAdapter.updateList(it)
            }
        })

        viewModel.hataMesaji.observe(viewLifecycleOwner, {
            it?.let {
                if (it){
                    hide()
                    textHata.visibility= View.VISIBLE
                }else textHata.visibility= View.GONE

            }

        })
        viewModel.yukleniyor.observe(viewLifecycleOwner, {
            it?.let {
                if (it){
                    hide()
                    progressbar.visibility = View.VISIBLE
                }else progressbar.visibility = View.GONE
            }
        })
    }
    private fun hide(){
        progressbar.visibility = View.GONE
        textHata.visibility = View.GONE
        recyclerBesinler.visibility = View.GONE
    }

}