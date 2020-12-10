package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MineSweeperCell extends View {
    public interface OnSelectedListener {
        boolean OnSelected(MineSweeperCell msc);
    }

    private boolean downTouch;
    private Paint txtStyle;
    private int nbr;
    private boolean uncovered;
    private boolean mine;
    private boolean marked;
    private OnSelectedListener selectedListener;
    private int index;

    public MineSweeperCell(Context ctx) {
        super(ctx);
        init();
    }

    public MineSweeperCell(Context ctx, int index) {
        super(ctx);
        this.index = index;
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
        downTouch = false;
        uncovered = false;
        mine = false;
        marked = false;
        txtStyle = new Paint(Paint.ANTI_ALIAS_FLAG);
        txtStyle.setStyle(Paint.Style.FILL);
        txtStyle.setTextSize(80);
        txtStyle.setFakeBoldText(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (uncovered) {
            if (mine && txtStyle != null) {
                canvas.drawColor(Color.RED);
                txtStyle.setColor(Color.BLACK);
                canvas.drawText("M",
                    (float) this.getLayoutParams().width / 2 - 35,
                    (float) this.getLayoutParams().height / 2 + 30,
                    txtStyle
                );
            } else if (nbr != 0 && txtStyle != null) {
                canvas.drawColor(Color.LTGRAY);
                txtStyle.setColor(Color.GRAY);
                canvas.drawText(String.valueOf(nbr),
                    (float) this.getLayoutParams().width / 2 - 25,
                    (float) this.getLayoutParams().height / 2 + 30,
                    txtStyle
                );
            } else {
                canvas.drawColor(Color.LTGRAY);
            }
        } else if (marked) {
            canvas.drawColor(Color.YELLOW);
        } else {
            canvas.drawColor(Color.BLACK);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTouch = true;
                return true;
            case MotionEvent.ACTION_UP:
                if (downTouch) {
                    downTouch = false;
                    performClick();
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean performClick() {
        boolean tmpMarked = marked;

        if (selectedListener != null) {
            if (selectedListener.OnSelected(this)) {
                return true;
            }
        }
        super.performClick();
        if (!marked && !tmpMarked) {
            uncovered = true;
        }
        invalidate();
        return true;
    }

    public void reveal() {
        uncovered = true;
        invalidate();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        selectedListener = listener;
    }

    public void resetCell() {
        uncovered = false;
        marked = false;
        mine = false;
        nbr = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setMine() {
        mine = true;
    }

    public boolean isMine() {
        return mine;
    }

    public void setNumber(int nbr) {
        this.nbr = nbr;
    }

    public int getNumber() {
        return nbr;
    }

    public boolean isCovered() {
        return !uncovered;
    }

    public void toggleMark() {
        marked = !marked;
    }

    public boolean isMarked() {
        return marked;
    }

}
