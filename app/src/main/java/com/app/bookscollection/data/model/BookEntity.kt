package com.app.bookscollection.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.bookscollection.domain.model.Book
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    var id: Int,
    var authors: List<Book.Author>? = listOf(),
    var bookshelves: String? = "",
    var copyright: Boolean? = false,
    @SerializedName("download_count")
    var downloadCount: Int? = 0,
    var formats: String? = "",
    @SerializedName("media_type")
    var mediaType: String? = "",
    var subjects: List<String>? = listOf(),
    var title: String? = "",
    var isFavorite: Boolean = false

) {
    fun toBook(): Book{
        val bookshelves: List<String> = try {
            val bookshelvesType = object : TypeToken<List<String>>() {}.type
             Gson().fromJson(this.bookshelves ?: "", bookshelvesType)
        }catch (e: Exception){
            listOf()
        }

        val formats: Book.Formats = try {
            Gson().fromJson(this.formats ?: "", Book.Formats::class.java)
        }catch (e: Exception){
            Book.Formats()
        }


        return Book(
            id,
            authors,
            bookshelves,
            copyright,
            downloadCount,
            formats,
            mediaType,
            subjects,
            title,
            isFavorite,
        )
    }

}