package com.app.bookscollection.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.app.bookscollection.R
import com.app.bookscollection.databinding.FragmentBookListBinding
import com.app.bookscollection.presentation.base.BaseFragment
import com.app.bookscollection.presentation.books.BookAdapter
import com.app.bookscollection.presentation.books.BookPagedAdapter
import com.app.bookscollection.presentation.books.BooksLoadStateAdapter
import com.app.bookscollection.utils.RemotePresentationState
import com.app.bookscollection.utils.asRemotePresentationState
import com.app.bookscollection.utils.extensions.toGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentBookListBinding>() {

    private val viewModel: FavouriteViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookListBinding {
        return FragmentBookListBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        mViewBinding.llSearch.toGone()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.favoriteBooks.asLiveData().observe(viewLifecycleOwner){
            adapter.updateList(it)
        }
    }

    private fun setupRecyclerView() {
        adapter = BookAdapter(listOf()) { book, action ->
            if (action == BookAdapter.ITEM_FAVOURITE){
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
}