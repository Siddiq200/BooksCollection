package com.app.bookscollection.presentation.books

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.bookscollection.R
import com.app.bookscollection.databinding.ItemBookBinding
import com.app.bookscollection.domain.model.Book
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BookPagedAdapter(
    private var clickUnit: (Book, Int) -> Unit,
) :
    PagingDataAdapter<Book, BookPagedAdapter.ViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val itemView =
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        getItem(position)?.let { book ->
            holder.bindData(book)
            holder.binding.cbFavourite.setOnClickListener {
                clickUnit.invoke(book, ITEM_FAVOURITE)
            }
            holder.binding.root.setOnClickListener {
                clickUnit.invoke(book, ITEM_CLICK)
            }
        }


    }

    class ViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            book: Book
        ) {
            binding.book = book
            binding.executePendingBindings()
            book.formats?.imagejpeg?.let {
                if (it.isNotEmpty()){
                    Glide.with(binding.root.context)
                        .load(it)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_book)
                        .error(R.drawable.ic_book)
                        .into(binding.bookImage)
                }else{
                    loadDefaultImage(binding.root.context, binding.bookImage)
                }
            } ?: run {
                loadDefaultImage(binding.root.context, binding.bookImage)
            }
        }
        private fun loadDefaultImage(context: Context, imageView: ImageView){
            Glide.with(context)
                .load(R.drawable.ic_book)
                .into(imageView)
        }
    }

    companion object{
        const val ITEM_CLICK: Int = 0
        const val ITEM_FAVOURITE: Int = 1
    }

    private class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }

}