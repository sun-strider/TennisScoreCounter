package com.example.android.tennisscorecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private final String ONE_POINT = "15";
    private final String TWO_POINTS = "30";
    private final String THREE_POINTS = "40";
    private final String ADVANTAGE = "advantage";

    private int gamePointsPlayerA = 0;
    private int gamePointsPlayerB = 0;
    private int setNr = 1;
    private int setScorePlayerA = 0;
    private int setScorePlayerB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Here start score calculations.
     *
     * Increases the current Game score for respective player by 1 point.
     * Check for Game won. If won, the Set score is increased and displayed. Game score is reset.
     * Check for Set won. If won, the Set is increased.
     * Check for Deuce situation. If first player scores, but second is at advantage, both scores are reduced to 3-3 -> Deuce (40 - 40)
     */

    /**
     * Calculations for A
     */
    public void addOneForA(View v) throws NoSuchFieldException, IllegalAccessException {
        gamePointsPlayerA = gamePointsPlayerA + 1;
        if ((gamePointsPlayerA > gamePointsPlayerB + 1) && (gamePointsPlayerA > 3)) {
            setScorePlayerA = addSetScore(setScorePlayerA);
            displaySetPointsA(setScorePlayerA, setNr);
            resetGame();
            if ((setScorePlayerA > setScorePlayerB + 1) && (setScorePlayerA > 5) && (setNr < 5)) {
                setNr = setNr + 1;
                setScorePlayerA = 0;
                setScorePlayerB = 0;
            }
        }

        if ((gamePointsPlayerA == 4) && (gamePointsPlayerB == 4)) {
            gamePointsPlayerA = gamePointsPlayerA - 1;
            gamePointsPlayerB = gamePointsPlayerB - 1;
        }

        displayGameScoreA(gamePointsPlayerA);
        displayGameScoreB(gamePointsPlayerB);

    }

    /**
     * Calculations for B
     */
    public void addOneForB(View v) throws NoSuchFieldException, IllegalAccessException {
        gamePointsPlayerB = gamePointsPlayerB + 1;
        if ((gamePointsPlayerB > gamePointsPlayerA + 1) && (gamePointsPlayerB > 3)) {
            setScorePlayerB = addSetScore(setScorePlayerB);
            displaySetPointsB(setScorePlayerB, setNr);
            resetGame();
            if ((setScorePlayerB > setScorePlayerA + 1) && (setScorePlayerB > 5) && (setNr < 5)) {
                setNr = setNr + 1;
                setScorePlayerA = 0;
                setScorePlayerB = 0;
            }
        }

        if ((gamePointsPlayerA == 4) && (gamePointsPlayerB == 4)) {
            gamePointsPlayerA = gamePointsPlayerA - 1;
            gamePointsPlayerB = gamePointsPlayerB - 1;
        }

        displayGameScoreA(gamePointsPlayerA);
        displayGameScoreB(gamePointsPlayerB);

    }

    /**
     * Function to increases the Set score for respective player by 1 points.
     */
    public int addSetScore(int score) throws NoSuchFieldException, IllegalAccessException {
        score++;
        return score;
    }


    /**
     * Function to reset Game score
     */
    public void resetGame() {
        gamePointsPlayerA = 0;
        gamePointsPlayerB = 0;
        displayGameScoreA(gamePointsPlayerA);
        displayGameScoreB(gamePointsPlayerB);
    }

    /**
     * Resets all Scores by pressing the Reset Button. The Set scores are reset by loops, going through every Set displayed.
     */
    public void resetScore(View v) throws NoSuchFieldException, IllegalAccessException {
        gamePointsPlayerA = 0;
        gamePointsPlayerB = 0;
        setNr = 1;
        setScorePlayerA = 0;
        setScorePlayerB = 0;

        displayGameScoreA(gamePointsPlayerA);
        displayGameScoreB(gamePointsPlayerB);

        for (int n = 1; n < 6; n++) {
            displaySetPointsA(setScorePlayerA, n);
        }

        for (int n = 1; n < 6; n++) {
            displaySetPointsB(setScorePlayerB, n);
        }
    }



    /**
     * Displays current Game score for A. Displayed in Tennis Game score values
     */
    private void displayGameScoreA(int points) {
        TextView scoreView = (TextView) findViewById(R.id.game_score_a);
        String score = gamePointsToScore(points);
        scoreView.setText(score);
    }


    /**
     * Displays current Game score for B. Displayed in Tennis Game score values
     */
    private void displayGameScoreB(int points) {
        TextView scoreView = (TextView) findViewById(R.id.game_score_b);
        String score = gamePointsToScore(points);
        scoreView.setText(score);
    }

    /**
     * Function to switch Game score from normal points to Tennis Game score values
     */
    private String gamePointsToScore(int points) {
        String score = "0";

        switch (points) {
            case 0:
                score = "0";
                break;
            case 1:
                score = ONE_POINT;
                break;
            case 2:
                score = TWO_POINTS;
                break;
            case 3:
                score = THREE_POINTS;
                break;
            case 4:
                score = ADVANTAGE;
        }
        return score;
    }

    /**
     * Displays the given set score for A. As the Set advances, the TextView field name is dynamically constructed.
     */
    public void displaySetPointsA(int points, int set) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = R.id.class;
        Field f = clazz.getField("set_score_a" + set);
        int id = f.getInt(null);  // pass in null, since field is a static field.
        TextView scoreView = (TextView) findViewById(id);
        scoreView.setText(String.valueOf(points));
    }

    /**
     * Displays the given set score for B. As the Set advances, the TextView field name is dynamically constructed.
     */
    public void displaySetPointsB(int points, int set) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = R.id.class;
        Field f = clazz.getField("set_score_b" + set);
        int id = f.getInt(null);  // pass in null, since field is a static field.
        TextView scoreView = (TextView) findViewById(id);
        scoreView.setText(String.valueOf(points));
    }
}

