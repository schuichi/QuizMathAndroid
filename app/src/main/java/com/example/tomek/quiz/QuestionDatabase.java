package com.example.tomek.quiz;

import java.util.List;

/**
 * Created by Tomek on 2016-11-23.
 */

public interface QuestionDatabase {
    List<Question> getQuestion(int difficulty);
}
