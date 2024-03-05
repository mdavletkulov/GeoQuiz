package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuizViewModelTest {

    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBank() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.nextQuestion()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun checkQuestionAnswers() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 1))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertTrue(quizViewModel.currentQuestionAnswer)
        quizViewModel.nextQuestion()
        assertFalse(quizViewModel.currentQuestionAnswer)
        quizViewModel.previousQuestion()
        assertTrue(quizViewModel.currentQuestionAnswer)
    }
}