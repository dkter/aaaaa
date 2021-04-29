/*
 * Copyright (c) David Teresi 2021.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.github.dkter.aaaaa

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager

class AaaaaKeyboardView(
    context: Context,
    keyboardListener: AaaaaKeyboardListener,
): ConstraintLayout(context), View.OnClickListener {
    interface AaaaaKeyboardListener {
        fun onA()
        fun onBackspace()
        fun onSpace()
        fun onReturn()
    }

    private val btnA: Button
    private val btnBackspace: ImageButton
    private val btnSpace: Button
    private val btnReturn: ImageButton

    private val keyboardListener: AaaaaKeyboardListener
    private val preferences: SharedPreferences

    init {
        // For some reason Kotlin calls these parameters p0, p1 and p2, so I
        // have to comment out the *actual* parameter names.
        // Have I mentioned how much I absolutely detest this language
        LayoutInflater.from(context).inflate(
            /*resource=*/R.layout.aaaaa_keyboard_view,
            /*root=*/this,
            /*attachToRoot=*/true,
        )

        this.btnA = findViewById<Button>(R.id.btnA)
        this.btnBackspace = findViewById<ImageButton>(R.id.btnBackspace)
        this.btnSpace = findViewById<Button>(R.id.btnSpace)
        this.btnReturn = findViewById<ImageButton>(R.id.btnReturn)

        this.btnA.setOnClickListener(this)
        this.btnBackspace.setOnClickListener(this)
        this.btnSpace.setOnClickListener(this)
        this.btnReturn.setOnClickListener(this)

        this.keyboardListener = keyboardListener
        this.preferences = PreferenceManager.getDefaultSharedPreferences(
            context
        )
    }

    private fun getBooleanPref(key: Int): Boolean {
        return preferences.getBoolean(getContext().getString(key), true)
    }

    override fun onClick(v: View) {
        val id = v.getId()
        if (this.getBooleanPref(R.string.hapticFeedbackKey))
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        if (id == R.id.btnA) {
            this.keyboardListener.onA()
        }
        else if (id == R.id.btnBackspace) {
            this.keyboardListener.onBackspace()
        }
        else if (id == R.id.btnSpace) {
            this.keyboardListener.onSpace()
        }
        else if (id == R.id.btnReturn) {
            this.keyboardListener.onReturn()
        }
    }
}
