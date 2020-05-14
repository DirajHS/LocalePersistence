package com.example.localepersistence

import android.app.Application
import android.content.Context

class MainApplication: Application() {

    override fun attachBaseContext(base: Context?) {
        base?.let { super.attachBaseContext(LocaleHelper.onAttach(base)) }
    }
}