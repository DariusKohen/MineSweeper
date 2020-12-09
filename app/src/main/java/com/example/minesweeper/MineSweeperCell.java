package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MineSweeperCell extends View {
    public MineSweeperCell(Context ctx) {
        super(ctx);
        init();
    }

    public MineSweeperCell(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init();
    }

    public MineSweeperCell(Context ctx, AttributeSet attrs, int defStyleAttr) {
        super(ctx, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
