package com.example.submission2.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.example.submission2.Prefrence.PrefHelper
import com.example.submission2.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private val pref by lazy { PrefHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        switch_theme_activity.isChecked = pref.getBoolean("pref_is_dark_mode")
        switch_theme_activity.setOnCheckedChangeListener { compoundButton, isChecked ->
            when(isChecked){
                true->{
                    pref.put("pref_is_dark_mode",true)
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false->{
                    pref.put("pref_is_dark_mode",false)
                    setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}