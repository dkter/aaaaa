/*
 * Copyright (c) David Teresi 2021.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.github.dkter.aaaaa

import android.inputmethodservice.InputMethodService
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection

class AaaaaInputMethodService:
    InputMethodService(),
    AaaaaKeyboardView.AaaaaKeyboardListener {
    override fun onCreateInputView(): View {
        val keyboardView = AaaaaKeyboardView(
            context=this,
            keyboardListener=this,
        )
        return keyboardView
    }

    override fun onStartInputView(info: EditorInfo, restarting: Boolean) {
        // Since the theme is set when the keyboard is created, we need to
        // recreate the keyboard every time the keyboard is started.
        // Otherwise the theme can't be changed without killing the aaaaa
        // app entirely and restarting it.
        setInputView(onCreateInputView())
    }

    private fun inputChar(ch: Char) {
        val ic: InputConnection? = getCurrentInputConnection()
        if (ic == null) {
            return
        }
        ic.commitText(ch.toString(), 1)
    }

    override fun onA() {
        inputChar('a')
    }

    override fun onLongA() {
        TODO("Not yet implemented")
    }

    override fun onBackspace() {
        val ic: InputConnection? = getCurrentInputConnection()
        if (ic == null) {
            return
        }

        val selectedText = ic.getSelectedText(0)
        if (TextUtils.isEmpty(selectedText)) {
            // no selection, so delete previous character
            ic.deleteSurroundingText(1, 0)
        } else {
            // delete the selection
            ic.commitText("", 1)
        }
    }

    override fun onSpace() {
        inputChar(' ')
    }

    override fun onReturn() {
        sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER)
    }
}
