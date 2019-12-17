package io.github.dkter.aaaaa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), TextWatcher {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testBox = findViewById<EditText>(R.id.testBox);
        testBox.addTextChangedListener(this);
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
