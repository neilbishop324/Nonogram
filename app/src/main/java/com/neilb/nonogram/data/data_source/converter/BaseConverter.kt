package com.neilb.nonogram.data.data_source.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import java.lang.reflect.Type

abstract class BaseConverter<T>(type: Type) {
    private val gson: Gson
    private val type: Type

    init {
        this.type = type
        gson = GsonBuilder()
            .serializeNulls()
            .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
            .create()
    }

    @TypeConverter
    fun mapStringToList(value: String?): List<List<T>> {
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun mapListToString(value: List<List<T>>): String {
        return gson.toJson(value, type)
    }
}

