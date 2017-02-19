package com.example.tomek.quiz;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity {
    @BindView(R.id.question_text)
    protected TextView mTitle;
    @BindView(R.id.answer)
    protected RadioGroup mAnswer;
    @BindViews({R.id.answer_a, R.id.answer_b, R.id.answer_c})
    protected List<RadioButton> mAnswerButtons;

    @BindView(R.id.btn_next)
    protected Button mNextButton;

    private int mCurrentQuestion = 0;
    private List<Question> mQuestions;

    private int[] mAnswersArray;

    private boolean mFirstBackClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        Log.d("QuestionActivity", "onCreate");

        mQuestions = (List<Question>) getIntent().getSerializableExtra("questions");
        mAnswersArray = new int[mQuestions.size()];
        refreshQuestionView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("QuestionActivity", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QuestionActivity", "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QuestionActivity", "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("QuestionActivity", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("QuestionActivity", "onDestroy");

    }

    // Zapisanie stanu aplikacji przed zniszczeniem Activity przy obrocie
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("QuestionActivity", "onSaveInstanceState");
        mAnswersArray[mCurrentQuestion] = mAnswer.getCheckedRadioButtonId();

        // Zapisanie bieżącej pozycji pytania
        outState.putInt("position", mCurrentQuestion);
        // Zapisanie tabloicy z udzielonymi odpowiedziami przez użytkownika
        outState.putIntArray("answers", mAnswersArray);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("QuestionActivity", "onRestoreInstanceState");

        mCurrentQuestion = savedInstanceState.getInt("position");
        mAnswersArray = savedInstanceState.getIntArray("answers");

        refreshQuestionView();
    }

    @Override
    public void onBackPressed() {
        onBackTapped();
    }

    private void onBackTapped(){
            // Pierwsze kliknięcie
        if (!mFirstBackClicked){
            // Ustawić flagę na true
            mFirstBackClicked = true;
            // pokazać Toast
            Toast.makeText(this, "Kliknij ponownie aby wyjść", Toast.LENGTH_LONG).show();
            // uruchomić odliczanie 1-2 sek  po tym czasie ustawić flagę ponownie
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFirstBackClicked = false;
                }
            }, 2000);
        } else { // Drugie kliekniecie 1-2 sek
            // zamknąć okno Activiyu
            finish();
        }
    }


    private void refreshQuestionView() {
        mAnswer.clearCheck();
        Question q1 = mQuestions.get(mCurrentQuestion);
        mTitle.setText(q1.getContent());
        for (int i = 0; i < 3; i++){
            mAnswerButtons.get(i).setText(q1.getAnswers().get(i));
        }
            // Sprawdza czy dla danego pytania została udzielona odpowiedź
        if (mAnswersArray[mCurrentQuestion] > 0){
            // Jeżeli tak to zaznacz wybrana
            mAnswer.check(mAnswersArray[mCurrentQuestion]);
        }
        mNextButton.setText(mCurrentQuestion < mQuestions.size() -1 ? "Dalej" : "Zakończ");
    }


    @OnClick(R.id.btn_back)
    protected void onBackClick(){
        if(mCurrentQuestion == 0){
            onBackTapped();
            return;
        }
        mAnswersArray[mCurrentQuestion] = mAnswer.getCheckedRadioButtonId();
        mCurrentQuestion--;
        refreshQuestionView();
    }

    @OnClick(R.id.btn_next)
    protected void onNextClick(){
        mAnswersArray[mCurrentQuestion] = mAnswer.getCheckedRadioButtonId();
        // Sprawdzamy czy użytkownik wybrał cokolwiek (getCheckRadioButtonId zwróci coś innego
        if (mAnswersArray[mCurrentQuestion] == -1){
            // Jeżeli nie wyświetlamy komunikat i zatrzymujemy przejście dalej (return)
            Toast.makeText(this, "Wybierz odpowiedź !", Toast.LENGTH_LONG).show();
            return;
        }
        if(mCurrentQuestion == mQuestions.size() -1){
            int correctAnswers = countCorrectAnswer();
            int totalAnswers = mAnswersArray.length;
            displeyResoult(correctAnswers, totalAnswers);
            return;
        }
        mCurrentQuestion++;
        refreshQuestionView();
    }

    private void displeyResoult(int correctAnswers, int totalAnswers) {
        QuizResuktsDialog.newInstance(correctAnswers, totalAnswers)
                .show(getSupportFragmentManager(), null);
    }


    private int countCorrectAnswer(){
        int sum = 0;

        for (int i = 0; i < mQuestions.size(); i++){
            Question question = mQuestions.get(i);
            int userAnswerId = mAnswersArray[i];
            int correctAnswerId = mAnswerButtons.get(question.getCorrectAnswer()).getId();
            if (userAnswerId == correctAnswerId) {
                sum++;
            }
        }

        return sum;
    }


}
