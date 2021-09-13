package com.zeygame.demoMvvm2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zeygame.demoMvvm2.util.createPlaceholder
import com.zeygame.demoMvvm2.util.load
import com.zeygame.demoMvvm2.viewmodel.BesinDetayiViewModel
import demoMvvm2.R
import demoMvvm2.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*


class FragmentDetails : Fragment() {
    private var besinId = 0
    lateinit var viewModel: BesinDetayiViewModel
    lateinit var dataBinding : FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)

        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            besinId = FragmentDetailsArgs.fromBundle(it).id
        }

        viewModel = ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.getRoomData(besinId)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, {
            it?.let {

                ///Data Binding
                dataBinding.besin = it

                ///Normal tanımlamalar
//                txDetayAdi.text = it.besimIsim
//                txtDetayKalori.text = it.besinKalori
//                txtDetayKarbonhidrat.text = it.besinKarbonhidrat
//                txtDetayYag.text = it.besinYag
//                context?.let {ctx->
//                    imgDetay.load(it.besinGorsel, createPlaceholder(ctx))
//                }
            }
        })
    }
}