package com.app.bookscollection.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.bookscollection.databinding.FragmentBookListBinding


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected lateinit var mViewBinding: VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = getBinding(inflater, container)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(savedInstanceState)
        attachListener()
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun setupUI(savedInstanceState: Bundle?)

    open fun attachListener(){}
}