package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

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

    private val answers = questionBank.map { it.textResId }.associateBy({ it }, { false })
        .toMutableMap()

    private var currentIndex = 0

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
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
            nextQuestion()
        }

        binding.previousButton.setOnClickListener {
            previousQuestion()
        }

        binding.questionTextView.setOnClickListener {
            nextQuestion()
        }

        updateQuizViews()
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuizViews()
    }

    private fun previousQuestion() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
        updateQuizViews()
    }

    private fun updateQuizViews() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        when (currentIndex) {
            0 -> {
                binding.nextButton.isEnabled = true
                binding.previousButton.isEnabled = false
            }

            questionBank.lastIndex -> {
                binding.nextButton.isEnabled = false
                binding.previousButton.isEnabled = true
            }

            else -> {
                binding.nextButton.isEnabled = true
                binding.previousButton.isEnabled = true
            }
        }

    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        val question = questionBank[currentIndex]
        val currentQuestionAnswer = question.answer
        val messageResId = if (currentQuestionAnswer == userAnswer) {
            answers[question.textResId] = true
            R.string.correct_toast
        } else {
            answers[question.textResId] = false
            R.string.incorrect_toast
        }
        if (answers.all { it.value }) {
            Snackbar.make(view, R.string.quiz_success, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(view, messageResId, Snackbar.LENGTH_SHORT).show()
        }
    }
}