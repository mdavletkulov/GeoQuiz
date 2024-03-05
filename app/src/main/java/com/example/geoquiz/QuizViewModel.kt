package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
private const val USER_ANSWERS = "USER_ANSWERS"

class QuizViewModel(private val savedDataHandler: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var answers
        get() = savedDataHandler[USER_ANSWERS] ?: mutableMapOf<Int, Boolean>()
        set(value) = savedDataHandler.set(USER_ANSWERS, value)

    private var currentIndex
        get() = savedDataHandler[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedDataHandler.set(CURRENT_INDEX_KEY, value)


    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun previousQuestion() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
    }

    fun refresh() {
        currentIndex = 0
        answers.clear()
    }

    fun getScore() = (answers.filter { it.value }.size * 100) / questionBank.size

    fun allAnswered() = answers.size == questionBank.size

    val isTheEnd: Boolean
        get() = currentIndex == questionBank.lastIndex

    val isTheStart: Boolean
        get() = currentIndex == 0

    fun setCurrentAnswer(answer: Boolean) {
        val currentAnswers = answers
        currentAnswers[currentQuestionText] = answer
        answers = currentAnswers
    }
}