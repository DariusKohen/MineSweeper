package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MineSweeperCell extends View {
    public interface OnSelectedListener {
        boolean OnSelected(MineSweeperCell msc, boolean mine);
    }

    private Paint txt;
    private boolean touchOn;
    private boolean mine;
    private OnSelectedListener selectedListener;
    private int x = 0;
    private int y = 0;

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
        mine = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (touchOn) {
            if (mine && txt != null) {
                canvas.drawColor(Color.RED);
                canvas.drawText("M",
                    this.getLayoutParams().width / 2 - 35,
                    this.getLayoutParams().height / 2 + 30,
                    txt
                );

            } else {
                canvas.drawColor(Color.LTGRAY);
            }
        } else {
            canvas.drawColor(Color.BLACK);
        }
    }

    @Override
    public boolean performClick() {
        if (selectedListener != null) {
            if (selectedListener.OnSelected(this, mine)) {
                return true;
            }
        }
        super.performClick();
        touchOn = !touchOn;
        invalidate();
        return true;
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        selectedListener = listener;
    }

    public void resetCell() {
        touchOn = false;
        mine = false;
    }

    public int getPosx() {
        return x;
    }

    public int getPosY() {
        return y;
    }

    public boolean getMine() {
        return mine;
    }

    public void setMine() {
        mine = true;
        txt = new Paint(Paint.ANTI_ALIAS_FLAG);
        txt.setColor(Color.BLACK);
        txt.setStyle(Paint.Style.FILL);
        txt.setTextSize(80);
        txt.setFakeBoldText(true);
    }

}
