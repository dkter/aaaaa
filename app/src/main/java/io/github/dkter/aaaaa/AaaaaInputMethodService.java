package io.github.dkter.aaaaa;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class AaaaaInputMethodService extends InputMethodService implements AaaaaKeyboardView.AaaaaKeyboardListener {

    @Override
    public View onCreateInputView() {
        // get the KeyboardView and add our Keyboard layout to it
        //View keyboardView = (View) getLayoutInflater().inflate(R.layout.aaaaa_keyboard_view, null);
        //Keyboard keyboard = new Keyboard(this, R.xml.keyboard);
        //keyboardView.setKeyboard(keyboard);
        //keyboardView.setOnKeyboardActionListener(this);
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
