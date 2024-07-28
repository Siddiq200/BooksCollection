package com.app.bookscollection.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.bookscollection.databinding.FragmentBookListBinding
import com.app.bookscollection.presentation.base.BaseFragment
import com.app.bookscollection.utils.extensions.toGone


class FavouriteFragment : BaseFragment<FragmentBookListBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mViewBinding.llSearch.toGone()
    }

}