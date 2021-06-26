/*
 * Copyright (c) David Teresi 2021.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.github.dkter.aaaaa

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.material.color.MaterialColors

@SuppressLint("ClickableViewAccessibility")
class AaaaaKeyboardView(
    context: Context,
    keyboardListener: AaaaaKeyboardListener,
) : ConstraintLayout(context), View.OnClickListener, View.OnLongClickListener,
    View.OnTouchListener {
    interface AaaaaKeyboardListener {
        fun onA()
        fun onLongA()
        fun onReleaseA()
        fun onBackspace()
        fun onSpace()
        fun onReturn()
        fun onUppercase()
        fun onLowercase()
    }

    private val btnA: Button
    private val btnBackspace: ImageButton
    private val btnSpace: Button
    private val btnReturn: ImageButton
    private val btnUppercase: ImageButton

    private val keyboardListener: AaaaaKeyboardListener
    private val preferences: SharedPreferences

    private var isUppercase = false

    init {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(
            context
        )

        // I have to set the theme manually here, because when I call
        // setDefaultNightMode it doesn't apply to the keyboard for some
        // reason. AppThemeLight and AppThemeDark are functionally identical
        // to the light and dark modes of AppTheme.
        // https://stackoverflow.com/a/67340930/5253369
        val themeSetting = getStringPref(R.string.themeSettingKey)
        val themeId = if (themeSetting == "MODE_NIGHT_NO") {
            R.style.AppThemeLight
        } else if (themeSetting == "MODE_NIGHT_YES") {
            R.style.AppThemeDark
        } else {  // MODE_NIGHT_FOLLOW_SYSTEM
            R.style.AppTheme
        }
        val wrapper = ContextThemeWrapper(context, themeId)
        // For some reason Kotlin calls these parameters p0, p1 and p2, so I
        // have to comment out the *actual* parameter names.
        // Have I mentioned how much I absolutely detest this language
        LayoutInflater.from(wrapper).inflate(
            /*resource=*/R.layout.aaaaa_keyboard_view,
            /*root=*/this,
            /*attachToRoot=*/true,
        )

        this.btnA = findViewById<Button>(R.id.btnA)
        this.btnBackspace = findViewById<ImageButton>(R.id.btnBackspace)
        this.btnSpace = findViewById<Button>(R.id.btnSpace)
        this.btnReturn = findViewById<ImageButton>(R.id.btnReturn)
        this.btnUppercase = findViewById<ImageButton>(R.id.btnUppercase)

        this.btnA.setOnLongClickListener(this)
        this.btnA.setOnTouchListener(this)
        this.btnA.setOnClickListener(this)
        this.btnBackspace.setOnClickListener(this)
        this.btnSpace.setOnClickListener(this)
        this.btnReturn.setOnClickListener(this)
        this.btnUppercase.setOnClickListener(this)

        this.keyboardListener = keyboardListener
    }

    private fun getBooleanPref(key: Int): Boolean {
        return this.preferences.getBoolean(context.getString(key), true)
    }

    private fun getStringPref(key: Int): String {
        return this.preferences.getString(context.getString(key), "")!!
    }

    override fun onClick(v: View) {
        val id = v.getId()
        if (this.getBooleanPref(R.string.hapticFeedbackKey))
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        when (id) {
            R.id.btnA -> keyboardListener.onA()
            R.id.btnBackspace -> keyboardListener.onBackspace()
            R.id.btnSpace -> keyboardListener.onSpace()
            R.id.btnReturn -> keyboardListener.onReturn()
            R.id.btnUppercase -> {
                isUppercase = !isUppercase
                if (isUppercase) onUppercase() else onLowercase()
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        val id = v.getId()
        return if (id == R.id.btnA) {
            this.keyboardListener.onLongA()
            true
        } else false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val id = v?.id
        val action = event?.action

        if (id == R.id.btnA && action == MotionEvent.ACTION_UP) keyboardListener.onReleaseA()

        return false
    }

    private fun onUppercase() {
        btnA.text = "A"
        //btnUppercase.drawable.setTint(
        //    MaterialColors.getColor(this, R.attr.colorControlNormal)
        //) FIXME throws IllegalArgumentException
        keyboardListener.onUppercase()
    }

    private fun onLowercase() {
        btnA.text = "a"
        btnUppercase.drawable.setTint(
            ContextCompat.getColor(context, R.color.colorPrimary)
        )
        keyboardListener.onLowercase()
    }
}
