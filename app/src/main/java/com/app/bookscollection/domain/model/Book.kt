package com.app.bookscollection.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.app.bookscollection.data.model.BookEntity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    var id: Int,
    var authors: List<Book.Author>? = listOf(),
    var bookshelves: List<String>? = listOf(),
    var copyright: Boolean? = false,
    @SerializedName("download_count")
    var downloadCount: Int? = 0,
    var formats: Book.Formats? = Book.Formats(),
    @SerializedName("media_type")
    var mediaType: String? = "",
    var subjects: List<String>? = listOf(),
    var title: String? = "",
    var isFavorite: Boolean = false
): Parcelable {
    fun getAuthorsString(): String{
        return authors?.joinToString(separator = ", ") { it.name ?: "" } ?: ""
    }

    fun getSubjectsString(): String{
        val subjectsStr = subjects?.joinToString(separator = ", ") { it } ?: ""
        subjectsStr.ifEmpty {
            "No subjects available"
        }
        return subjectsStr
    }

    fun toBookEntity(): BookEntity{
        return BookEntity(
            id,
            authors,
            Gson().toJson(bookshelves),
            copyright, downloadCount,
            Gson().toJson(formats),
            mediaType,
            subjects,
            title,
            isFavorite

        )
    }

    @Entity(tableName = "authors")
    @Parcelize data class Author(
        @SerializedName("birth_year")
        var birthYear: Int? = 0,
        @SerializedName("death_year")
        var deathYear: Int? = 0,
        var name: String? = ""
    ): Parcelable

    @Parcelize
    data class Formats(
        @SerializedName("application/epub+zip")
        var applicationepubzip: String? = "",
        @SerializedName("application/octet-stream")
        var applicationoctetStream: String? = "",
        @SerializedName("application/rdf+xml")
        var applicationrdfxml: String? = "",
        @SerializedName("application/x-mobipocket-ebook")
        var applicationxMobipocketEbook: String? = "",
        @SerializedName("image/jpeg")
        var imagejpeg: String? = "",
        @SerializedName("text/html")
        var texthtml: String? = "",
        @SerializedName("text/plain; charset=us-ascii")
        var textplainCharsetusAscii: String? = ""
    ): Parcelable
}