package com.example.localepersistence

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {

    @BindView(R.id.titleTextView)
    lateinit var mTitleTextView: TextView

    @BindView(R.id.descTextView)
    lateinit var mDescTextView: TextView

    @BindView(R.id.aboutTextView)
    lateinit var mAboutTextView: TextView

    @BindView(R.id.toTRButton)
    lateinit var mToTRButton: Button

    @BindView(R.id.toENButton)
    lateinit var mToENButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        title = getString(R.string.main_activity_toolbar_title)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base!!))
    }

    @OnClick(R.id.toTRButton)
    fun onChangeToTRClicked(view: View) {
        updateViews("tr")
    }

    @OnClick(R.id.toENButton)
    fun onChangeToENClicked(view: View) {
        updateViews("en")
    }

    private fun updateViews(languageCode: String) {
        val context = LocaleHelper.setLocale(this, languageCode)
        val resources: Resources = context.resources
        mTitleTextView.text = resources.getString(R.string.main_activity_title)
        mDescTextView.text = resources.getString(R.string.main_activity_desc)
        mAboutTextView.text = resources.getString(R.string.main_activity_about)
        mToTRButton.text = resources.getString(R.string.main_activity_to_tr_button)
        mToENButton.text = resources.getString(R.string.main_activity_to_en_button)
        title = resources.getString(R.string.main_activity_toolbar_title)
    }
}
