package com.inha.hbc.util

import android.content.Context
import com.google.gson.Gson
import com.inha.hbc.data.local.Jwt

class PreferenceUtil(context: Context) {
    val prefsName = "prefs"
    val accessJwtKey = "accessJwt"
    val refreshJwtKey = "refreshJwt"
    val prefs= context.getSharedPreferences(prefsName, 0)

    private fun jwtToString(jwt: Jwt): String {
        val gson = Gson()
        return gson.toJson(jwt)
    }

    private fun stringToJwt(str: String?): Jwt {
        val gson = Gson()
        return gson.fromJson(str, Jwt::class.java)
    }

    fun getAccessJwt(): Jwt {
        return stringToJwt(prefs.getString(accessJwtKey, ""))
    }

    fun setAccessJwt(data: Jwt) {
        val str = jwtToString(data)
        prefs.edit().putString(accessJwtKey, str).apply()
    }

    fun getRefreshJwt(): Jwt {
        return stringToJwt(prefs.getString(refreshJwtKey, ""))
    }

    fun setRefreshJwt(data: Jwt) {
        val str = jwtToString(data)
        prefs.edit().putString(refreshJwtKey, str).apply()
    }


//    var jwt: String?
//        get() = prefs.getString(accessJwtKey, "")
//        set(value) = prefs.edit().putString(accessJwtKey, value).apply()

}