package com.example.tomek.quiz;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tomek on 2016-11-23.
 */

public class Question implements Serializable {
    private String content;
    private int difficulty;
    private List<String > answers;
    private int correctAnswer;

    public Question(String content, int difficulty, List<String> answers, int correctAnswer) {
        this.content = content;
        this.difficulty = difficulty;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
