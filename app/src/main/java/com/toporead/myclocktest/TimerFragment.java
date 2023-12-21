package com.toporead.myclocktest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class TimerFragment extends Fragment {

    private TextView textViewTimer;
    private Button buttonStartPause, buttonReset;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis = 60000;


    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        textViewTimer = view.findViewById(R.id.fragmenttextview);
        buttonStartPause = view.findViewById(R.id.start_pause);
        buttonReset = view.findViewById(R.id.reset);


        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTime();
            }
        });


        updateCountDownText();
        return view;
    }

    private void startTimer() {
        updateCountDownText();
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                buttonStartPause.setText("START");
                buttonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        timerRunning = true;
        buttonStartPause.setText("PAUSE");
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        updateCountDownText();
        buttonStartPause.setText("CONTINUE");
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTime() {
        countDownTimer.cancel();
        buttonStartPause.setText("START");
        timeLeftInMillis = 60000;
        updateCountDownText();
        buttonReset.setVisibility(View.VISIBLE);
        buttonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeFormatted);

    }
    public void onDestroyView(){
        super.onDestroyView();
        if (countDownTimer !=null){
            countDownTimer.cancel();
        }
    }
}