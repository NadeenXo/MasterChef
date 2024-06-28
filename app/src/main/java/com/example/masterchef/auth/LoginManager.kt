package com.example.masterchef.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class LoginManager(val context: Context) {
    companion object {
        const val PREFERENCE_FILE_KEY = "com.example.masterchef.onboarding.PREFERENCE_LOGIN"
        const val PREFERENCE_IS_LOGGED = "com.example.masterchef.onboarding.PREFERENCE_IS_LOGGED_IN"
        const val PREFERENCE_EMAIL = "com.klivvr.klivvrcommunity.auth.PREFERENCE_EMAIL"
    }

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

//    fun changeLoggedInStatus(email: String, isLoggedIn: Boolean) {
//        sharedPreferences.edit {
//            putString(PREFERENCE_EMAIL, email)
//            putBoolean(PREFERENCE_IS_LOGGED, isLoggedIn)
//        }
//    }

    fun isUserLoggedIn(): Boolean = sharedPreferences.getBoolean(PREFERENCE_IS_LOGGED, false)
    fun logout() {
        sharedPreferences.edit {
            remove(PREFERENCE_IS_LOGGED)
        }

    }
}