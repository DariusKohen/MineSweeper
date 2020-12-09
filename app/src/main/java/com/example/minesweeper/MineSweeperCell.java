package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MineSweeperCell extends View {
    boolean touchOn;
    int x = 0;
    int y = 0;

    public MineSweeperCell(Context ctx) {
        super(ctx);
        init();
    }

    public MineSweeperCell(Context ctx, int x, int y) {
        super(ctx);
        this.x = x;
        this.y = y;
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
        touchOn = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (touchOn) {
            canvas.drawColor(Color.LTGRAY);
        } else {
            canvas.drawColor(Color.BLACK);
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        touchOn = !touchOn;
        invalidate();
        return true;
    }

    public int getPosx() {
        return x;
    }

    public int getPosY() {
        return y;
    }

}
