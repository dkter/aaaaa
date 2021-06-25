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
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import kotlin.concurrent.thread

class AaaaaInputMethodService : InputMethodService(), AaaaaKeyboardView.AaaaaKeyboardListener {

    private fun repeatThread(delay: Long = 100L, action: () -> Unit) = thread {
        while (!Thread.currentThread().isInterrupted) {
            try {
                action()
                Thread.sleep(delay)
            } catch (e: InterruptedException) {
                break
            }
        }
    }

    private var aRepeatThread: Thread? = null
    private var backspaceRepeatThread: Thread? = null

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

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        aRepeatThread?.interrupt()
        backspaceRepeatThread?.interrupt()
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
        aRepeatThread?.interrupt()
        aRepeatThread = repeatThread { onA() }
    }

    override fun onReleaseA() {
        aRepeatThread?.interrupt()
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

    override fun onLongBackspace() {
        backspaceRepeatThread?.interrupt()
        backspaceRepeatThread = repeatThread { onBackspace() }
    }

    override fun onReleaseBackspace() {
        backspaceRepeatThread?.interrupt()
    }
}
