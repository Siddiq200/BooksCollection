package com.app.bookscollection.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.bookscollection.databinding.FragmentBookListBinding
import com.app.bookscollection.domain.Book
import com.app.bookscollection.presentation.base.BaseFragment
import com.app.bookscollection.presentation.books.BookAdapter
import com.app.bookscollection.utils.extensions.toVisible


class SearchFragment : BaseFragment<FragmentBookListBinding>() {

    private var adapter: BookAdapter = BookAdapter(arrayListOf()){Book, Int ->

    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mViewBinding.searchEditText.toVisible()
        mViewBinding.booksRV.adapter = adapter

        val books = arrayListOf(Book(), Book(), Book())
        adapter.updateAdapter(books)
    }

}