package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GridLayout gl;
    Button resetBtn;
    MineSweeperCell[] cells;
    boolean gameState = true;
    int nbMines = 20;
    int nbCols;
    int nbRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gl = (GridLayout) findViewById(R.id.minesgrid);
        resetBtn = (Button) findViewById(R.id.reset);

        nbCols = gl.getColumnCount();
        nbRows = gl.getRowCount();
        cells = new MineSweeperCell[nbCols * nbRows];
        initGrid();
        dispatchMines();
        sizeGridCell();
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void initGrid() {
        for(int i = 0; i < (nbCols * nbRows); i++) {
            cells[i] = new MineSweeperCell(this, i % 10, i / 10);
            cells[i].setOnSelectedListener(new MineSweeperCell.OnSelectedListener() {
                @Override
                public boolean OnSelected(MineSweeperCell msc, boolean mine) {
                    if (mine && gameState) {
                        gameState = false;
                        return false;
                    } else if (!gameState) {
                        return true;
                    }
                    return false;
                }
            });
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            gl.addView(cells[i]);
        }
    }

    private void sizeGridCell() {
        gl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                GridLayout.LayoutParams params = null;
                int gridWidth = gl.getWidth();
                int gridHeight = gl.getHeight();
                int cellMargin = 5;

                for(int i = 0; i < (nbCols * nbRows); i++) {
                    params = (GridLayout.LayoutParams)cells[i].getLayoutParams();
                    params.width = (gridWidth/nbCols) - 2*cellMargin;
                    params.height = (gridHeight/nbRows) - 2*cellMargin;
                    params.setMargins(cellMargin, cellMargin, cellMargin, cellMargin);
                    cells[i].setLayoutParams(params);
                }
            }
        });
    }

    private void dispatchMines() {
        for(int i = 0; i < nbMines; i++) {
            int rand = new Random().nextInt(nbCols * nbRows);
            if (!cells[rand].getMine()) {
                cells[rand].setMine();
            } else {
                --i;
            }
        }
    }

    private void cleargrid() {
        for(int i = 0; i < (nbCols * nbRows); i++) {
            cells[i].resetCell();
        }
    }

    private void resetGame() {
        cleargrid();
        dispatchMines();
        gameState = true;
    }
}