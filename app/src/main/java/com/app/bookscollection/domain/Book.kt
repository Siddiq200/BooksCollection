package com.app.bookscollection.domain

import com.google.gson.annotations.SerializedName

data class Book(
    var authors: List<Author>? = listOf(),
    var bookshelves: List<String>? = listOf(),
    var copyright: Boolean? = false,
    @SerializedName("download_count")
    var downloadCount: Int? = 0,
    var formats: Formats? = Formats(),
    var id: Int? = 0,
    var languages: List<String>? = listOf(),
    @SerializedName("media_type")
    var mediaType: String? = "",
    var subjects: List<String>? = listOf(),
    var title: String? = "",
//    var translators: List<Any>? = listOf()
) {

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
    data class Author(
        @SerializedName("birth_year")
        var birthYear: Int? = 0,
        @SerializedName("death_year")
        var deathYear: Int? = 0,
        var name: String? = ""
    )

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
    )
}