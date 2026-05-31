package com.methodiusdev.lispedu.data

data class QuizQuestion (
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

data class Lesson(
    val id: Int,
    val title: String,
    val description: String,
    val content: String, // TODO: The content of lesson should not be just a string.
    val quizQuestions: List<QuizQuestion>
)