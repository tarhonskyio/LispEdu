package com.methodiusdev.lispedu.data

object LispRepository {
    val sampleLesson = Lesson(
        id = 1,
        title = "Lisp Syntax & REPL",
        description = "Learn basic of Common Lisp syntax: lists, atoms, expressions.",
        content = "In Common Lisp, everything is built from s-expressions (symbolic expressions). S-expressions can be atoms or lists. A list is written as open parenthesis, elements separated by spaces, and a closed parenthesis. For example: (plus 2 3). The REPL (Read-Eval-Print-Loop) reads this expression, evaluates it, prints the result, and loops back.",
        quizQuestions = listOf(
            QuizQuestion(
                id = 101,
                questionText = "What does the REPL stand for in Common Lisp?",
                options = listOf("Read-Eval-Print-Loop", "Run-Evaluation-Program-Language", "Real-Engine-Processing-Link"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 102,
                questionText = "How are elements in a Lisp list separated?",
                options = listOf("By commas", "By spaces", "By semicolons"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 103,
                questionText = "What does s-expression states for?",
                options = listOf("super expression", "status expression", "symbolic expression"),
                correctAnswerIndex = 2
            )
        )
    )
}