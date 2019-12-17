/*
 * Copyright (c) David Teresi 2019.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */

package io.github.dkter.aaaaa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = getSharedPreferences(getString(R.string.preferenceFileKey), Context.MODE_PRIVATE)

        val testBox = findViewById<EditText>(R.id.testBox)
        testBox.addTextChangedListener(this)

        val hapticFeedbackSwitch = findViewById<Switch>(R.id.enableHapticFeedback)
        hapticFeedbackSwitch.isChecked = preferences.getBoolean(getString(R.string.hapticFeedbackKey), true)
        hapticFeedbackSwitch.setOnCheckedChangeListener { view: CompoundButton, enabled: Boolean ->
            with (preferences.edit()) {
                putBoolean(getString(R.string.hapticFeedbackKey), enabled)
                commit()
            }

            if (!enabled) {
                val toast = Toast.makeText(this, "you monster", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    fun keyboardSettings(v: View) {
        val intent = Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
    }

    fun closeKeyboardSettingsReminder(v: View) {
        val toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(editable: Editable) {

    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (text != null) {
            val withoutA = text.replace("a".toRegex(), "")
            val errorField = findViewById<TextView>(R.id.testBoxErrorField)
            if (!withoutA.isBlank()) {
                errorField.text = "Error: invalid characters detected"
            }
            else {
                errorField.text = ""
            }
        }
    }
}
