package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GridLayout gl;
    TextView minesTxt;
    TextView marksTxt;
    Button resetBtn;
    Button markBtn;
    MineSweeperCell[] cells;
    boolean gameState = true;
    boolean markClick = false;
    int nbMines = 10;
    int nbCols;
    int nbRows;
    int nbMarks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minesTxt = (TextView) findViewById(R.id.minesTxt);
        marksTxt = (TextView) findViewById(R.id.marksTxt);
        gl = (GridLayout) findViewById(R.id.minesGrid);
        resetBtn = (Button) findViewById(R.id.reset);
        markBtn = (Button) findViewById(R.id.markToggle);

        minesTxt.setText(getString(R.string.mines_txt, nbMines));
        marksTxt.setText(getString(R.string.marks_txt, nbMarks));
        nbCols = gl.getColumnCount();
        nbRows = gl.getRowCount();
        cells = new MineSweeperCell[nbCols * nbRows];
        initGrid();
        dispatchMines();
        dispatchNumbers();
        sizeGridCell();
        resetBtn.setOnClickListener(v -> resetGame());
        markBtn.setOnClickListener(v -> {
            if (gameState) {
                markClick = !markClick;
                markBtn.setText((
                    markClick ? getString(R.string.uncover_btn) :
                        getString(R.string.mark_btn)
                ));
            }
        });
    }

    private void initGrid() {
        for(int i = 0; i < (nbCols * nbRows); i++) {
            cells[i] = new MineSweeperCell(this, i);
            cells[i].setOnSelectedListener(msc -> {
                if (!gameState) {
                    return true;
                }
                if (markClick) {
                    if (msc.isCovered()) {
                        nbMarks += (msc.isMarked() ? -1 : 1);
                        marksTxt.setText(getString(R.string.marks_txt, nbMarks));
                        msc.toggleMark();
                        if (nbMarks == nbMines) {
                            checkWin();
                        }
                        return false;
                    }
                } else if (!msc.isMarked()) {
                    if (msc.isMine()) {
                        gameOver();
                        return false;
                    }
                    if (!msc.isMine() && msc.getNumber() == 0) {
                        clickAround(msc.getIndex());
                    }
                }
                return false;
            });
            gl.addView(cells[i]);
        }
    }

    private void sizeGridCell() {
        gl.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            GridLayout.LayoutParams params;
            int gridWidth = gl.getWidth();
            int gridHeight = gl.getHeight();
            int cellMargin = 5;

            for (MineSweeperCell cell : cells) {
                params = (GridLayout.LayoutParams) cell.getLayoutParams();
                params.width = (gridWidth / nbCols) - 2 * cellMargin;
                params.height = (gridHeight / nbRows) - 2 * cellMargin;
                params.setMargins(cellMargin, cellMargin, cellMargin, cellMargin);
                cell.setLayoutParams(params);
            }
        });
    }

    private void dispatchMines() {
        for(int i = 0; i < nbMines; i++) {
            int rand = new Random().nextInt(nbCols * nbRows);
            if (!cells[rand].isMine()) {
                cells[rand].setMine();
            } else {
                --i;
            }
        }
    }

    private void dispatchNumbers() {
        int count = 0;

        for(int i = 0; i < cells.length; i++) {
            if (!cells[i].isMine()) {
                for (int y = -1; y < 2; y++) {
                    for (int x = -1; x < 2; x++) {
                        if ((i / nbRows + y) >= 0 && (i / nbRows + y) < nbRows &&
                                (i % nbCols + x) >= 0 && (i % nbCols + x) < nbCols
                        ) {
                            count += (cells[(i / nbRows + y) * nbCols + (i % nbCols + x)].isMine() ? 1 : 0);
                        }
                    }
                }
            }
            cells[i].setNumber(count);
            count = 0;
        }
    }

    private void clearGrid() {
        for (MineSweeperCell cell : cells) {
            cell.resetCell();
        }
    }

    private void resetGame() {
        minesTxt.setText(getString(R.string.mines_txt, nbMines));
        marksTxt.setText(getString(R.string.marks_txt, 0));
        markClick = false;
        nbMarks = 0;
        markBtn.setText(getString(R.string.mark_btn));
        clearGrid();
        dispatchMines();
        dispatchNumbers();
        gameState = true;
    }

    private void clickAround(int i) {
        int index;

        for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
                if ((i / nbRows + y) >= 0 && (i / nbRows + y) < nbRows &&
                        (i % nbCols + x) >= 0 && (i % nbCols + x) < nbCols
                ) {
                    index = (i / nbRows + y) * nbCols + (i % nbCols + x);
                    if (cells[index].isCovered()) {
                        cells[index].reveal();
                        if (cells[index].getNumber() == 0) {
                            clickAround(index);
                        }
                    }
                }
            }
        }
    }

    private void gameOver() {
        gameState = false;
        // Reveal mines
        for (MineSweeperCell cell : cells) {
            if (cell.isMine()) {
                cell.reveal();
            }
        }
        minesTxt.setText(getString(R.string.lose_txt1));
        marksTxt.setText(getString(R.string.lose_txt2));
    }

    private void checkWin() {
        for (MineSweeperCell cell : cells) {
            if ((cell.isMine() && !cell.isMarked()) ||
                    (cell.isMarked() && !cell.isMine())
            ) {
                return;
            }
        }
        gameState = false;
        minesTxt.setText(getString(R.string.win_txt1));
        marksTxt.setText(getString(R.string.win_txt2));
    }
}