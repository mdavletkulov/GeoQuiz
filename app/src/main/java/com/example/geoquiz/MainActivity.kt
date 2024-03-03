package com.example.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
//            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
            checkAnswer(true, it)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false, it)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        val currentQuestionAnswer = questionBank[currentIndex].answer
        val messageResId = if (currentQuestionAnswer == userAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Snackbar.make(view, messageResId, Snackbar.LENGTH_SHORT).show()
    }
}