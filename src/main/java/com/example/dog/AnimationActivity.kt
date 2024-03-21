package com.example.dog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_animatie)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        val loadingText = findViewById<TextView>(R.id.loadingText2)

        // Preiau rasa de câine din intentul anterior, care a fost folosit pentru a deschide activitatea AnimationActivity
        val selectedBreed = intent.getStringExtra("breed") ?: return

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            loadingText.visibility = View.GONE

            // Transmit rasa către ImageDogActivity
            val intentToImageDogActivity = Intent(this, ImageDogActivity::class.java).apply {
                putExtra("breed", selectedBreed)
            }
            startActivity(intentToImageDogActivity)
            finish()
        }, 2000) // 2000 ms = 2 secunde
    }
}
