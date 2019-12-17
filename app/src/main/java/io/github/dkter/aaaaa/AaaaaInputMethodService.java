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

package io.github.dkter.aaaaa;

import android.inputmethodservice.InputMethodService;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class AaaaaInputMethodService extends InputMethodService implements AaaaaKeyboardView.AaaaaKeyboardListener {

    @Override
    public View onCreateInputView() {
        AaaaaKeyboardView keyboardView = new AaaaaKeyboardView(this);
        keyboardView.setKeyboardListener(this);
        return keyboardView;
    }

    private void inputChar(char ch) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.commitText(String.valueOf(ch), 1);
    }

    @Override
    public void onA() {
        inputChar('a');
    }

    @Override
    public void onBackspace() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        CharSequence selectedText = ic.getSelectedText(0);
        if (TextUtils.isEmpty(selectedText)) {
            // no selection, so delete previous character
            ic.deleteSurroundingText(1, 0);
        } else {
            // delete the selection
            ic.commitText("", 1);
        }
    }

    @Override
    public void onSpace() {
        inputChar(' ');
    }

    @Override
    public void onReturn() {
        inputChar('\n');
    }
}
