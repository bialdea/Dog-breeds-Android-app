package com.example.dog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val loadingText = findViewById<TextView>(R.id.loadingText)

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            loadingText.visibility = View.GONE

            val intent = Intent(this, DogActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000) // 2000 ms = 2 secunde
    }

}