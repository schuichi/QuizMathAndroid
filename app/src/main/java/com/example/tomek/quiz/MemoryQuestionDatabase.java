package com.example.tomek.quiz;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tomek on 2016-11-23.
 */

public class MemoryQuestionDatabase implements QuestionDatabase {
    @Override
    public List<Question> getQuestion(int difficulty) {
        Random random = new Random();
        List<Question> questions = new LinkedList<>();

        for (int i = 0; i < 30; i++){
            int randomDifficulty = random.nextInt(difficulty) + 1;
            int maxValue = randomDifficulty * 50;
            int left = random.nextInt(maxValue);
            int a = random.nextInt(maxValue);
            int b = random.nextInt(maxValue);
            //String.format pozwala na zdefiniowanie szablonu napisu i wstawianie tam odpowiednich wartości
            String question = String.format("%d + %d = ?", a, b);
            int correctAnswerPosition = random.nextInt(3);

            List<String> answers = new LinkedList<>();
            for (int j = 0; j < 3; j++){
                if (j == correctAnswerPosition) {
                    answers.add(a + b + "");
                    continue;
                }

                int badA = -1;
                while (badA < 0 || badA == a+b){
                    badA = (a + b) - random.nextInt(a + b);
                }
                answers.add(badA + "");

            }
                questions.add(new Question(question, randomDifficulty, answers, correctAnswerPosition));
        }


        return questions;

    }
}
