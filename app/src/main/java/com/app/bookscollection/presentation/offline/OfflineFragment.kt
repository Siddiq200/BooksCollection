package com.app.bookscollection.presentation.offline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.bookscollection.databinding.FragmentBookListBinding
import com.app.bookscollection.presentation.base.BaseFragment
import com.app.bookscollection.utils.extensions.toVisible


class OfflineFragment : BaseFragment<FragmentBookListBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mViewBinding.searchEditText.toVisible()
    }

}