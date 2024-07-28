package com.app.bookscollection.presentation.offline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.bookscollection.R
import com.app.bookscollection.data.model.Resource
import com.app.bookscollection.databinding.FragmentBookListBinding
import com.app.bookscollection.presentation.base.BaseFragment
import com.app.bookscollection.presentation.books.BookAdapter
import com.app.bookscollection.presentation.books.BookPagedAdapter
import com.app.bookscollection.utils.extensions.hideKeyboard
import com.app.bookscollection.utils.extensions.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OfflineFragment : BaseFragment<FragmentBookListBinding>() {

    private val viewModel: OfflineViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mViewBinding.llSearch.toVisible()
        mViewBinding.swiperefresh.isEnabled = false
        setupRecyclerView()
        setupSearchBar()
        observeViewModel()
        searchBooks(viewModel.lastQuery)
    }

    private fun setupSearchBar() {
        mViewBinding
            .etSearch
            .setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBooks(mViewBinding.etSearch.text.toString().trim())
                    hideKeyboard()
                    true
                } else {
                    false
                }
            }
    }

    private fun setupRecyclerView() {
        adapter = BookAdapter(listOf()) { book, action ->
            if (action == BookPagedAdapter.ITEM_FAVOURITE){
                book.isFavorite = !book.isFavorite
                viewModel.updateFavoriteStatus(book, book.isFavorite)
            }else{
                val bundle = Bundle()
                bundle.putParcelable("book", book)
                findNavController().navigate(R.id.bookFragment, bundle)
            }

        }
        mViewBinding.booksRV.adapter = adapter
    }

    private fun searchBooks(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchLocalBooks(query)
        }
    }

    private fun observeViewModel() {
        viewModel.books.observe(viewLifecycleOwner){
            if (it is Resource.Success){
                adapter.updateList(it.value.results)
            }
        }
    }

}