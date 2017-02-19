package com.example.tomek.quiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Tomek on 2016-11-24.
 */

public class QuizResuktsDialog extends DialogFragment {
    public static QuizResuktsDialog newInstance(int correctAnswers, int totalAnswers){
        QuizResuktsDialog dialog = new QuizResuktsDialog();
        Bundle args = new Bundle();

        args.putInt("correct", correctAnswers);
        args.putInt("total", totalAnswers);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int correctAnswers = getArguments().getInt("correct");
        int totalAnswers = getArguments().getInt("total");

        // setCancelable należy wywołać na obiekcie DialogFragment a nie AlertDialog
        // metoda ta uniemożliwia wyłączenie okna dialogu po nacisnieciu dowolnego pola
        setCancelable(false);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Wynik quizu")
                .setMessage("Odpowiedziałeś poprawnie na " + correctAnswers + " pytań z " + totalAnswers)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        getActivity().finish();
                    }
                })
                .create();
        return dialog;
    }
}
