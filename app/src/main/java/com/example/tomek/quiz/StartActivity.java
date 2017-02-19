package com.example.tomek.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {
    @BindView(R.id.player_name)
    protected EditText mName;
    @BindView(R.id.difficulty)
    protected Spinner mDifficulty;
    private UserPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        Log.d("StartActivity", "onCreate");


        mPrefs = new UserPreferences(this);
        mName.setText(mPrefs.getUsername());
        mDifficulty.setSelection(mPrefs.getLevel());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("StartActivity", "onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("StartActivity", "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("StartActivity", "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("StartActivity", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("StartActivity", "onDestroy");

    }

    @OnClick(R.id.next)
    protected void onNextClick(){
// Sprawdzanie czy w ogóle wpisane coś w pole imię
        String name = mName.getText().toString();
        if ( name.trim().isEmpty() ){
            mName.setError("Brak nazwy gracza");
            return;
        }

        //Sprawdzanie czy wybrano poziom trudności
        int selectedLevel = mDifficulty.getSelectedItemPosition();
        if ( selectedLevel == 0 ){
            Toast.makeText(this, "Wybierz poziom trudności", Toast.LENGTH_LONG).show();
            return;
        }
        // Zapamiętanie nazwy gracza i poziom trudności
        mPrefs.setUsername(name);
        mPrefs.setLevel(selectedLevel);
        // Losowani puli pytań

        QuestionDatabase db = new MemoryQuestionDatabase();
        List<Question> question = db.getQuestion(selectedLevel);
        Random random = new Random();
        while (question.size() > 5){
            // Usuwamy losowe pytania aż zostanie nam ich tylko 5
            question.remove(random.nextInt(question.size()));
        }  // Przetasowujemy kolekcje pozostałych pytań aby kolejność była losowa
        Collections.shuffle(question);

        // TODO : Przekazanie lub zapisanie wylosowanych pytań na potrzeby następnego ekranu

        // Otwarcie nowego ekranu
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        questionIntent.putExtra("questions", new ArrayList<>(question));
        startActivity(questionIntent);
    }
}
