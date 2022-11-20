package com.inha.hbc.util.sharedpreference

import android.content.Context
import com.google.gson.Gson
import com.inha.hbc.data.local.Jwt
import com.inha.hbc.data.remote.resp.GetMyInfoBirth
import com.inha.hbc.data.remote.resp.GetMyInfoData

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

    fun setInfo(info: GetMyInfoData) {
        prefs.edit().apply{
            putString("id", info.id.toString()).commit()
            putString("username", info.username).commit()
            putString("name", info.name).commit()
            putString("phone", info.phone).commit()
            putString("image", info.image_url).commit()
            putString("birth", "${info.birth_date.year}+!${info.birth_date.month}+!${info.birth_date.date}+!${info.birth_date.type}").commit()
            var authorities: String = ""
            for(i in 0 until info.authorities.size) {
                if (i == info.authorities.size - 1) {
                    authorities += info.authorities[i]
                }
                else {
                    authorities += info.authorities[i]
                }
            }
            putString("authorities", authorities).commit()
        }
    }

    fun getInfo(): GetMyInfoData? {
        val tmp = prefs.getString("authorities", "")
        if (tmp == "") {
            return null
        }
        var arr = tmp!!.split("!")
        var birthArr = prefs.getString("birth", "")!!.split("!")
        return GetMyInfoData(authorities = arr,
            birth_date = GetMyInfoBirth(year = birthArr[0].toInt(), month = birthArr[1].toInt(), date = birthArr[2].toInt(), type = birthArr[3]),
            image_url = prefs.getString("image", "")!!,
        phone = prefs.getString("phone", "")!!,
        name = prefs.getString("name", "")!!,
        username = prefs.getString("username", "")!!,
        id = prefs.getString("id", "")!!.toInt())
    }
//    var jwt: String?
//        get() = prefs.getString(accessJwtKey, "")
//        set(value) = prefs.edit().putString(accessJwtKey, value).apply()

}