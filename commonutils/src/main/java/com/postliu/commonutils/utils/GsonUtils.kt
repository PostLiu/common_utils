package com.postliu.commonutils.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.ToNumberPolicy
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {
    private val gson by lazy {
        Gson().newBuilder().setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER).create()
    }

    inline fun <reified T> fromJson(json: String): T =
        Gson().newBuilder().setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER).create()
            .fromJson(json, object : TypeToken<T>() {}.type)

    fun <T> fromJson(json: JsonElement, classOfT: Class<T>): T = gson.fromJson(json, classOfT)

    fun <T> fromJson(json: String, classOfT: Class<T>): T = gson.fromJson(json, classOfT)

    fun <T> fromJson(json: Reader, classOfT: Class<T>): T = gson.fromJson(json, classOfT)

    fun <T> fromJson(json: JsonReader, typeOfT: Type): T = gson.fromJson(json, typeOfT)

    fun <T> fromJson(json: JsonElement, typeOfT: Type): T = gson.fromJson(json, typeOfT)

    fun <T> fromJson(json: String, typeOfT: Type): T = gson.fromJson(json, typeOfT)

    fun <T> fromJson(json: Reader, typeOfT: Type): T = gson.fromJson(json, typeOfT)

    fun toJson(src: Any?): String = gson.toJson(src ?: "")
}
