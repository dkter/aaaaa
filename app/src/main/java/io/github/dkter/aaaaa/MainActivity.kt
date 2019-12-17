package io.github.dkter.aaaaa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun keyboardSettings(v: View) {
        val intent = Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
    }

    fun closeKeyboardSettingsReminder(v: View) {
        val toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT)
        toast.show()
    }
}
