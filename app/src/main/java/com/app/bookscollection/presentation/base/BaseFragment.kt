package com.app.bookscollection.presentation.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.bookscollection.databinding.FragmentBookListBinding


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var mViewBinding: VB

    private lateinit var connectivityManager: ConnectivityManager
    private var isNetworkAvailable = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = getBinding(inflater, container)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        setupUI(savedInstanceState)
        attachListener()
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun setupUI(savedInstanceState: Bundle?)

    open fun onConnectionAvailable(network: Network){}
    open fun onConnectionLost(network: Network){}

    open fun attachListener(){
        if (this::connectivityManager.isInitialized){
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isNetworkAvailable = true
                    onConnectionAvailable(network)
                }

                override fun onLost(network: Network) {
                    isNetworkAvailable = false
                    onConnectionLost(network)
                }
            })
        }
    }

    fun isNetworkAvailable() = isNetworkAvailable
}