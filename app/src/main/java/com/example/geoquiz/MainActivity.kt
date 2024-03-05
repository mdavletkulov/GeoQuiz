package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

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
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

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
        quizViewModel.nextQuestion()
        updateQuizViews()
    }

    private fun previousQuestion() {
        quizViewModel.previousQuestion()
        updateQuizViews()
    }

    private fun updateQuizViews() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        when {
            quizViewModel.isTheStart -> {
                binding.nextButton.isEnabled = true
                binding.previousButton.isEnabled = false
            }

            quizViewModel.isTheEnd -> {
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
        val currentQuestionAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (currentQuestionAnswer == userAnswer) {
            quizViewModel.setCurrentAnswer(true)
            R.string.correct_toast
        } else {
            quizViewModel.setCurrentAnswer(false)
            R.string.incorrect_toast
        }
        if (quizViewModel.allAnswered()) {
            stopQuiz(view)
        } else {
            Snackbar.make(view, messageResId, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun stopQuiz(view: View) {
        val text = "${getText(R.string.quiz_finished)} - your score ${quizViewModel.getScore()}%"
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
        quizViewModel.refresh()
        updateQuizViews()
    }
}