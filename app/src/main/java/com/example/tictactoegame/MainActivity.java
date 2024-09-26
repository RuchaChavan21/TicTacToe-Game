package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean playerOneTurn = true; // true = X, false = O
    private int[][] boardStatus = new int[3][3];
    private Button[][] buttons = new Button[3][3];
    private TextView statusTextView;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.status);
        resetButton = findViewById(R.id.resetButton);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Button) v).getText().toString().equals("")) {
                            if (playerOneTurn) {
                                ((Button) v).setText("X");
                                boardStatus[finalI][finalJ] = 1;
                            } else {
                                ((Button) v).setText("O");
                                boardStatus[finalI][finalJ] = 2;
                            }
                            playerOneTurn = !playerOneTurn;
                            checkForWinner();
                        }
                    }
                });
            }
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });

        resetBoard();
    }

    private void checkForWinner() {
        // Checking the rows and columns for a winner
        for (int i = 0; i < 3; i++) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2] && boardStatus[i][0] != 0) {
                announceWinner(boardStatus[i][0]);
                return;
            }
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i] && boardStatus[0][i] != 0) {
                announceWinner(boardStatus[0][i]);
                return;
            }
        }
        // Checking diagonals
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2] && boardStatus[0][0] != 0) {
            announceWinner(boardStatus[0][0]);
            return;
        }
        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0] && boardStatus[0][2] != 0) {
            announceWinner(boardStatus[0][2]);
            return;
        }
    }

    private void announceWinner(int winner) {
        if (winner == 1) {
            statusTextView.setText("Player One Wins!");
        } else if (winner == 2) {
            statusTextView.setText("Player Two Wins!");
        }
        disableButtons();
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetBoard() {
        playerOneTurn = true;
        statusTextView.setText("Player One's Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardStatus[i][j] = 0;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
}
