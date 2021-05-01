/*
 * Copyright (c) David Teresi 2021.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.github.dkter.aaaaa

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*

fun setDefaultNightMode(context: Context, theme: String? = null) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    val themeSetting = if (theme == null) {
        preferences.getString(context.getString(R.string.themeSettingKey), "")
    } else theme

    val mode = if (themeSetting == "MODE_NIGHT_NO") {
        AppCompatDelegate.MODE_NIGHT_NO
    } else if (themeSetting == "MODE_NIGHT_YES") {
        AppCompatDelegate.MODE_NIGHT_YES
    } else if (themeSetting == "MODE_NIGHT_FOLLOW_SYSTEM") {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    } else {
        AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
    }

    AppCompatDelegate.setDefaultNightMode(mode)
}

class SettingsFragment:
    PreferenceFragmentCompat(),
    Preference.OnPreferenceChangeListener
{
    override fun onCreatePreferences(
        savedInstanceState: Bundle?, rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val hapticFeedbackPreference: Preference? = findPreference(
            getString(R.string.hapticFeedbackKey)
        )
        hapticFeedbackPreference!!.onPreferenceChangeListener = this
        val themePreference: Preference? = findPreference(
            getString(R.string.themeSettingKey)
        )
        themePreference!!.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(
        preference: Preference, 
        newValue: Any
    ): Boolean {
        if (
            preference.key == getString(R.string.hapticFeedbackKey)
            && newValue == false
        ) {
            val toast = Toast.makeText(
                /*context=*/context,
                /*text=*/getString(R.string.toastDisableHapticFeedback),
                /*duration=*/Toast.LENGTH_SHORT,
            )
            toast.show()
        }
        else if (preference.key == getString(R.string.themeSettingKey)) {
            setDefaultNightMode(requireContext(), newValue as String)
        }

        return true
    }
}

class MainActivity: AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultNightMode(this)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settingsContainer, SettingsFragment())
            .commit()
    }

    fun keyboardSettings(v: View) {
        val intent = Intent(
            android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS
        )
        startActivity(intent)
    }

    fun closeKeyboardSettingsReminder(v: View) {
        val toast = Toast.makeText(
            /*context=*/this,
            /*text=*/getString(R.string.toastFeatureNotImplemented),
            /*duration=*/Toast.LENGTH_SHORT,
        )
        toast.show()
    }

    override fun beforeTextChanged(
        s: CharSequence?, start: Int, count: Int, after: Int
    ) {
        // This is necessary because this class implements TextWatcher
    }

    override fun afterTextChanged(editable: Editable) {
        // This is necessary because this class implements TextWatcher
    }

    override fun onTextChanged(
        text: CharSequence?, start: Int, before: Int, count: Int
    ) {
        if (text != null) {
            val withoutA = text.replace("a".toRegex(), "")
            val errorField = findViewById<TextView>(R.id.testBoxErrorField)
            if (!withoutA.isBlank()) {
                errorField.text = getString(R.string.errorInvalidCharacters)
            }
            else {
                errorField.text = ""
            }
        }
    }
}
