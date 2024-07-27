package com.app.bookscollection.presentation.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.bookscollection.databinding.ItemBookBinding
import com.app.bookscollection.domain.Book

class BookAdapter(
    private var data: ArrayList<Book>,
    private var clickUnit: (Book, Int) -> Unit,
) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    fun updateAdapter(arrayList: ArrayList<Book>) {
        this.data = arrayList
        notifyDataSetChanged()
    }

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
        val model = data[position]
        model.let { book ->
            holder.bindData(book)
        }
        holder.itemView.setOnClickListener{
            clickUnit.invoke(model, ITEM_CLICK)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            book: Book
        ) {
            binding.book = book
            binding.executePendingBindings()
        }
    }

    companion object{
        const val ITEM_CLICK: Int = 0
        const val ITEM_FAVOURITE: Int = 1
    }

}