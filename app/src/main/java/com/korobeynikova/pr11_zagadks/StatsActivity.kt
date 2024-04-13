package com.korobeynikova.pr11_zagadks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val totalRiddles = intent.getIntExtra("totalRiddles", 0)
        val correctAnswers = intent.getIntExtra("correctAnswers", 0)
        val incorrectAnswers = intent.getIntExtra("incorrectAnswers", 0)

        val textTotalRiddles = findViewById<TextView>(R.id.text_total_riddles)
        val textCorrectAnswers = findViewById<TextView>(R.id.text_correct_answers)
        val textIncorrectAnswers = findViewById<TextView>(R.id.text_incorrect_answers)

        textTotalRiddles.text = "Полученные загадки: $totalRiddles"
        textCorrectAnswers.text = "Правильные ответы: $correctAnswers"
        textIncorrectAnswers.text = "Неправильные ответы: $incorrectAnswers"


        val btnNewSession = findViewById<Button>(R.id.btn_new_session)
        btnNewSession.setOnClickListener {
            startNewSession()
        }
    }

    private fun startNewSession() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}