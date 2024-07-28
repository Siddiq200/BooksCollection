package com.app.bookscollection.presentation.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.app.bookscollection.R
import com.app.bookscollection.databinding.FragmentBookBinding
import com.app.bookscollection.presentation.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookFragment : BaseFragment<FragmentBookBinding>() {

    private val viewModel: BookViewModel by viewModels()
    private val args: BookFragmentArgs by navArgs()

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookBinding {
        return FragmentBookBinding.inflate(inflater, container, false)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        val book = args.book
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = book.title
        mViewBinding.book = book
        mViewBinding.executePendingBindings()
        book.formats?.imagejpeg?.let {
            if (it.isNotEmpty()){
                Glide.with(requireContext())
                    .load(it)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_book)
                    .error(R.drawable.ic_book)
                    .into(mViewBinding.bookImage)
            }else{
                loadDefaultImage(requireContext(), mViewBinding.bookImage)
            }
        } ?: run {
            loadDefaultImage(requireContext(), mViewBinding.bookImage)
        }
    }

    private fun loadDefaultImage(context: Context, imageView: ImageView){
        Glide.with(context)
            .load(R.drawable.ic_book)
            .into(imageView)
    }

    override fun attachListener() {
        super.attachListener()
        mViewBinding.cbFavourite.setOnClickListener {
            val book = args.book
            book.isFavorite = !book.isFavorite
            viewModel.updateFavoriteStatus(book, book.isFavorite)
        }
    }

}