package com.app.bookscollection.presentation.search

import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.app.bookscollection.R
import com.app.bookscollection.databinding.FragmentBookListBinding
import com.app.bookscollection.presentation.base.BaseFragment
import com.app.bookscollection.presentation.books.BookPagedAdapter
import com.app.bookscollection.presentation.books.BooksLoadStateAdapter
import com.app.bookscollection.utils.RemotePresentationState
import com.app.bookscollection.utils.asRemotePresentationState
import com.app.bookscollection.utils.extensions.hideKeyboard
import com.app.bookscollection.utils.extensions.toGone
import com.app.bookscollection.utils.extensions.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentBookListBinding>() {

    private lateinit var adapter: BookPagedAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mViewBinding.llSearch.toVisible()
        setupRecyclerView()
        setupSearchBar()
        searchBooks("")

        if (!isNetworkAvailable()){
            if (adapter.itemCount == 0){
                mViewBinding.layoutNoInternet.root.toVisible()
            }
        }
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
        val header = BooksLoadStateAdapter { adapter.retry() }
        adapter = BookPagedAdapter() { book, action ->
            if (action == BookPagedAdapter.ITEM_FAVOURITE){
                book.isFavorite = !book.isFavorite
                viewModel.updateFavoriteStatus(book, book.isFavorite)
            }else{
                val bundle = Bundle()
                bundle.putParcelable("book", book)
                findNavController().navigate(R.id.bookFragment, bundle)
            }

        }
        mViewBinding.booksRV.adapter = adapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = BooksLoadStateAdapter { adapter.retry() }
        )

        val notLoading = adapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }


        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                // show empty list
//                emptyList.isVisible = isListEmpty
                // Show loading spinner during initial load or refresh.
                mViewBinding.progressBar.isVisible = loadState.refresh is LoadState.Loading

               // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    private fun searchBooks(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchBooks(query).collectLatest { pagingData ->
                adapter.submitData(pagingData)
                if (adapter.itemCount > 0){
                    mViewBinding.layoutNoInternet.root.toGone()
                }
            }
        }
    }

    override fun attachListener() {
        super.attachListener()
        mViewBinding.layoutNoInternet.btnDownloads.setOnClickListener {
            findNavController().navigate(R.id.action_downloads)
        }
    }
    override fun onConnectionAvailable(network: Network) {
        super.onConnectionAvailable(network)
        requireActivity().runOnUiThread {
            mViewBinding.layoutNoInternet.root.toGone()
        }
    }

    override fun onConnectionLost(network: Network) {
        super.onConnectionLost(network)
        if (adapter.itemCount == 0){
            requireActivity().runOnUiThread {
                mViewBinding.layoutNoInternet.root.toVisible()
            }
        }
    }

}