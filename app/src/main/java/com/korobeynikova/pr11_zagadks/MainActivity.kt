package com.korobeynikova.pr11_zagadks

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var textRiddle: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnCheck: Button
    private lateinit var btnStats: Button
    private lateinit var btnRiddle: Button
    private lateinit var textRiddleCount: TextView

    private val answers = listOf(
        Triple(
            "Висит груша, нельзя скушать.",
            listOf("Груша", "Шар", "Лампочка", "Яблоко"),
            2
        ),
        Triple(
            "Что можно сломать, но нельзя увидеть?",
            listOf("Сердце", "Шар", "Воздух", "Доверие"),
            0
        ),
        Triple(
            "Что можно увидеть с закрытыми глазами?",
            listOf("Звезды", "Сны", "Цветы", "Тени"),
            1
        ),
        Triple("Кто его делает, тот не пользуется, кто пользуется, тот его не делает. Что это?", listOf("Ключ", "Гроб", "Ложка", "Ведро"), 1),
        Triple(
            "Что можно увидеть вместе с его создателем?",
            listOf("Душа", "Тень", "Свет", "Отражение"),
            1
        ),
        Triple(
            "Что идет, не шагает, что идет, не едет?",
            listOf("Дождь", "Свет", "Время", "Путь"),
            2
        ),
        Triple(
            "День спит, ночь глядит, утром умирает, другой сменяет. Что это?",
            listOf("Месяц", "Свечи", "Вампир", "Ветер"),
            1
        ),
        Triple("Хоть без глаз, могу бегущих догонять, но только никому меня нельзя обнять. Что это?", listOf("Туча", "Тень", "Деньги", "Звезда"), 1),
        Triple(
            "Без языка, а сказывается. Что это?",
            listOf("Боль", "Жест", "Лай", "Старость"),
            0
        ),
        Triple(
            "Не море, не земля, корабли не плавают, и ходить нельзя. Что за место такое?",
            listOf("Небеса", "Лава", "Болото", "Пустыня"),
            2
        ),
        Triple(
            "Первый говорит — побежим, другой говорит — полежим, третий говорит — покачаемся. Кто первый?",
            listOf("Время", "Грунтовая дорога", "Конь", "Вода"),
            3
        ),
        Triple(
            "Деревянные ноги, хоть всё лето стой. Что это за зверь такой?",
            listOf("Забор", "Ткацкий станок", "Табуретка", "Протезы"),
            1
        ),
        Triple(
            "В лесу выросло, из лесу вынесли, на руках плачет, а по полу скачут. Что это?",
            listOf("Лапти", "Балалайка", "Деревянные доски", "Лук и стрелы"),
            1
        ),
        Triple(
            "Четыре четырки, две растопырки, седьмой вертун, два стёклушка в нём. Что это?",
            listOf("Бык", "Часы", "Автомобиль", "Бинокль"),
            0
        ),
        Triple(
            "Утка в море, хвост на заборе. Что за чудо-юдо?",
            listOf("Ковш", "Морковь", "Буй", "Якорь"),
            0
        )
    )

    private var currentRiddles: List<Triple<String, List<String>, Int>> = emptyList()
    private var currentRiddleIndex = 0
    private var correctAnswersCount = 0
    private var incorrectAnswersCount = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textRiddle = findViewById(R.id.text_riddle)
        radioGroup = findViewById(R.id.radio_group)
        btnCheck = findViewById(R.id.btn_check)
        btnStats = findViewById(R.id.btn_stats)
        textRiddleCount = findViewById(R.id.text_riddle_count)
        btnRiddle = findViewById(R.id.btn_riddle)

        btnCheck.visibility = View.GONE

        btnCheck.setOnClickListener {
            checkAnswer()
        }

        btnStats.setOnClickListener {
            showStats()
        }

        btnRiddle.setOnClickListener {
            showNextRiddle()
        }

        generateRiddles()
        showNextRiddle()
    }

    private fun checkAnswer() {
        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        if (checkedRadioButtonId != -1) {
            val checkedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
            val selectedAnswer = checkedRadioButton.text.toString()
            val (_, answersList, correctAnswerIndex) = currentRiddles[currentRiddleIndex - 1]

            val correctAnswer = answersList[correctAnswerIndex]

            if (selectedAnswer == correctAnswer) {
                Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
                // Увеличиваем счетчик правильных ответов
                correctAnswersCount++
            } else {
                Toast.makeText(this, "Неправильно!", Toast.LENGTH_SHORT).show()
                // Увеличиваем счетчик неправильных ответов
                incorrectAnswersCount++
            }

            showNextRiddle()
        } else {
            Toast.makeText(this, "Выберите ответ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showStats() {
        val totalRiddles = currentRiddles.size
        val correctAnswers = correctAnswersCount
        val incorrectAnswers = incorrectAnswersCount

        val intent = Intent(this, StatsActivity::class.java)
        intent.putExtra("totalRiddles", totalRiddles)
        intent.putExtra("correctAnswers", correctAnswers)
        intent.putExtra("incorrectAnswers", incorrectAnswers)
        startActivity(intent)
    }

    private fun showNextRiddle() {
        if (currentRiddleIndex < currentRiddles.size) {
            val (riddle, answersList, correctAnswerIndex) = currentRiddles[currentRiddleIndex]
            textRiddle.text = riddle

            radioGroup.removeAllViews()

            answersList.shuffled().forEach { answer ->
                val radioButton = RadioButton(this)
                radioButton.text = answer
                radioGroup.addView(radioButton)
            }

            currentRiddleIndex++
            textRiddleCount.text = "Загадка $currentRiddleIndex из ${currentRiddles.size}"

            btnCheck.visibility = View.VISIBLE
        } else {
            btnCheck.visibility = View.GONE
            btnStats.visibility = View.VISIBLE
            btnRiddle.visibility = View.GONE
            Toast.makeText(this, "Все загадки отгаданы", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateRiddles() {
        currentRiddles = getRandomRiddles()
    }

    private fun getRandomRiddles(): List<Triple<String, List<String>, Int>> {
        val shuffledRiddles = answers.shuffled()
        return shuffledRiddles.take(10)
    }
}