package com.app.bookscollection.data.db

import androidx.room.TypeConverter
import com.app.bookscollection.domain.model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromAuthorsList(value: List<Book.Author>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<Book.Author>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAuthorsList(value: String): List<Book.Author> {
        val gson = Gson()
        val type = object : TypeToken<List<Book.Author>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSubjects(value: List<String>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubjects(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}