package com.example.localepersistence

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.preference.PreferenceManager
import java.util.*


/**
 * This class is used to change your application locale and persist this change for the next time
 * that your app is going to be used.
 *
 *
 * You can also change the locale of your application on the fly by using the setLocale method.
 *
 */
object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    fun onAttach(context: Context): Context {
        val lang =
            getPersistedData(context, Locale.getDefault().language)
        Log.d("LocaleHelper", "setting language to: $lang")
        return setLocale(context, lang)
    }

    fun setLocale(
        context: Context,
        language: String?
    ): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(
        context: Context,
        defaultLanguage: String
    ): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(
        context: Context,
        language: String?
    ): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration =
            context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(
        context: Context,
        language: String?
    ): Context {
        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}