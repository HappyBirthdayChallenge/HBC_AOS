package com.inha.hbc.util.sharedpreference

import android.content.Context
import com.google.gson.Gson
import com.inha.hbc.data.local.Jwt

class PreferenceUtil(context: Context) {
    val prefsName = "prefs"
    val accessJwtKey = "accessJwt"
    val refreshJwtKey = "refreshJwt"
    val realAccessJwtKey = "realAccessJwt"
    val realRefreshJwtKey = "realRefreshJwt"
    val fcmToken = "fcmToken"
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

    fun setRealAccessJwt(data: String) {
        prefs.edit().putString(realAccessJwtKey, data).apply()
    }

    fun getRealAccessJwt(): String? {
        return prefs.getString(realAccessJwtKey, "")
    }
    fun setRealRefreshJwt(data: String) {
        prefs.edit().putString(realRefreshJwtKey, data).apply()
    }

    fun getRealRefreshJwt(): String? {
        return prefs.getString(realRefreshJwtKey, "")
    }

    fun delJwt() {
        prefs.edit().remove(accessJwtKey)
            .remove(realAccessJwtKey)
            .remove(refreshJwtKey)
            .remove(realRefreshJwtKey)
            .commit()
    }

    fun setFcmtoken(data: String) {
        prefs.edit().putString(fcmToken, data).apply()
    }

    fun getFcmtoken(): String? {
        return prefs.getString(fcmToken, "")
    }

    fun delFcmtoken() {
        prefs.edit().remove(fcmToken).commit()
    }

//    var jwt: String?
//        get() = prefs.getString(accessJwtKey, "")
//        set(value) = prefs.edit().putString(accessJwtKey, value).apply()

}