package com.ahmedfahmi.braintrainer;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random randomGenerator;
    private TextView question;
    private int answer;
    private TextView blueBox;
    private TextView greenBox;
    private TextView purpleBox;
    private TextView pinkBox;
    private TextView score;
    private TextView timer;
    private TextView CorrectOrWrong;
    private int correctAnswerCounter;
    private int questionCounter;
    private CountDownTimer countDownTimer;
    private GridLayout MaingridLayout;
    private Button goBtn;
    private Button tryAgainBtn;
    private boolean gameIsNotActive;
    private MediaPlayer mediaPlayer;
    private int wrongAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiate();
        hideLayout();
        setCountDownTimer();
    }

    private void setCountDownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                timer.setText(Integer.toString(seconds) + " S");

            }

            @Override
            public void onFinish() {
                timer.setText("0 S");
                mediaPlayer.start();
                tryAgainBtn.setVisibility(View.VISIBLE);
                CorrectOrWrong.setText("Your Score: " + score.getText().toString());
                CorrectOrWrong.setTextColor(Color.GRAY);
                gameIsNotActive = true;

            }
        };
    }

    private void hideLayout() {
        score.setVisibility(View.INVISIBLE);
        timer.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        tryAgainBtn.setVisibility(View.INVISIBLE);
        CorrectOrWrong.setVisibility(View.INVISIBLE);
        MaingridLayout.setVisibility(View.INVISIBLE);
    }

    private void initiate() {
        correctAnswerCounter = 0;
        questionCounter = 0;
        randomGenerator = new Random();
        wrongAnswer = randomGenerator.nextInt(40);
        question = (TextView) findViewById(R.id.question);
        blueBox = (TextView) findViewById(R.id.blue);
        greenBox = (TextView) findViewById(R.id.green);
        purpleBox = (TextView) findViewById(R.id.purple);
        pinkBox = (TextView) findViewById(R.id.pink);
        score = (TextView) findViewById(R.id.score);
        CorrectOrWrong = (TextView) findViewById(R.id.status);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.car_horn);
        goBtn = (Button) findViewById(R.id.goBtn);
        tryAgainBtn = (Button) findViewById(R.id.tryAginBtn);
        timer = (TextView) findViewById(R.id.timer);
        MaingridLayout = (GridLayout) findViewById(R.id.gridLayout);
    }

    public void tryAgain(View view) {
        gameIsNotActive = false;
        correctAnswerCounter = 0;
        questionCounter = 0;
        tryAgainBtn.setVisibility(View.INVISIBLE);
        generate();
        score.setText("0/0");
        timer.setText("30 S");
        countDownTimer.start();
        CorrectOrWrong.setVisibility(View.INVISIBLE);

    }


    public void go(View view) {
        MaingridLayout.setVisibility(View.VISIBLE);
        goBtn.setVisibility(View.INVISIBLE);
        score.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        gameIsNotActive = false;

        generate();
        countDownTimer.start();
    }


    public void generate() {
        questionCounter++;
        int firstNum = randomGenerator.nextInt(20);
        int secondNum = randomGenerator.nextInt(20);
        int spread = randomGenerator.nextInt(4);
        answer = firstNum + secondNum;
        question.setText(Integer.toString(firstNum) + " + " + Integer.toString(secondNum));
        wrongAnswer = randomGenerator.nextInt(40);


        while (wrongAnswer == answer) {
            wrongAnswer = randomGenerator.nextInt(40);


        }
        if (spread == 0) {


            blueBox.setText(Integer.toString(answer));
            purpleBox.setText(Integer.toString(wrongAnswer + 2));
            pinkBox.setText(Integer.toString(wrongAnswer - 1));
            greenBox.setText(Integer.toString(wrongAnswer + 3));
        } else if (spread == 1) {
            pinkBox.setText(Integer.toString(answer));
            purpleBox.setText(Integer.toString(wrongAnswer + 2));
            blueBox.setText(Integer.toString(wrongAnswer + 3));
            greenBox.setText(Integer.toString(wrongAnswer - 1));
        } else if (spread == 2) {
            greenBox.setText(Integer.toString(answer));
            purpleBox.setText(Integer.toString(wrongAnswer + 1));
            blueBox.setText(Integer.toString(wrongAnswer - 2));
            pinkBox.setText(Integer.toString(wrongAnswer - 3));
        } else if (spread == 3) {
            purpleBox.setText(Integer.toString(answer));
            greenBox.setText(Integer.toString(wrongAnswer + 1));
            blueBox.setText(Integer.toString(wrongAnswer + 2));
            pinkBox.setText(Integer.toString(wrongAnswer + 3));
        }


    }

    public void choose(View view) {
        if (!gameIsNotActive) {
            TextView block = (TextView) view;
            String blockStr = block.getText().toString();
            if (blockStr.equals(Integer.toString(answer))) {
                correctAnswerCounter++;
                CorrectOrWrong.setTextColor(Color.GREEN);
                CorrectOrWrong.setText("Correct!");


            } else {
                CorrectOrWrong.setTextColor(Color.RED);
                CorrectOrWrong.setText("Wrong!");
            }
            CorrectOrWrong.setVisibility(View.VISIBLE);


            score.setText(Integer.toString(correctAnswerCounter) + "/" + Integer.toString(questionCounter));
            generate();
        }


    }
}
