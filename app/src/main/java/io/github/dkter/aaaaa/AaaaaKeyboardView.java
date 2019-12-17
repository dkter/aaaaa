package io.github.dkter.aaaaa;

import android.content.Context;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

public class AaaaaKeyboardView extends ConstraintLayout implements View.OnClickListener {
    public interface AaaaaKeyboardListener {
        void onA();
        void onBackspace();
        void onSpace();
        void onReturn();
    }

    private Button btnA;
    private ImageButton btnBackspace;
    private Button btnSpace;
    private ImageButton btnReturn;

    private AaaaaKeyboardListener keyboardListener;

    public AaaaaKeyboardView(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.aaaaa_keyboard_view, this, true);

        btnA = findViewById(R.id.btnA);
        btnBackspace = findViewById(R.id.btnBackspace);
        btnSpace = findViewById(R.id.btnSpace);
        btnReturn = findViewById(R.id.btnReturn);

        btnA.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnSpace.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
    }

    public void setKeyboardListener(AaaaaKeyboardListener listener) {
        this.keyboardListener = listener;
    }

    public void onClick(View v) {
        int id = v.getId();
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (id == R.id.btnA) {
            this.keyboardListener.onA();
        }
        else if (id == R.id.btnBackspace) {
            this.keyboardListener.onBackspace();
        }
        else if (id == R.id.btnSpace) {
            this.keyboardListener.onSpace();
        }
        else if (id == R.id.btnReturn) {
            this.keyboardListener.onReturn();
        }
    }
}
