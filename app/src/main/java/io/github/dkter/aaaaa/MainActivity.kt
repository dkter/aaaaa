/*
 * Copyright (c) David Teresi 2021.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.github.dkter.aaaaa

import androidx.appcompat.app.AppCompatActivity
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
        if (hapticFeedbackPreference != null)
            hapticFeedbackPreference.onPreferenceChangeListener = this
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

        return true
    }
}

class MainActivity: AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
