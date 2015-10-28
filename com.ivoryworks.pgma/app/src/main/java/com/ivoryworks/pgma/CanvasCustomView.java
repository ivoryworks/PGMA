package com.ivoryworks.pgma;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import static com.ivoryworks.pgma.R.color.fab_red;

public class CanvasCustomView extends View {
    private Paint mPaint = new Paint();

    public CanvasCustomView(Context context) {
        super(context);
    }

    public CanvasCustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        // Background
        canvas.drawColor(Color.parseColor("#330000cc"));

        // Text
        mPaint.setTextSize(48);
        canvas.drawText("Canvas draw", 50, 50, mPaint);

        // Rect
        mPaint.setColor(Color.DKGRAY);
        canvas.drawRect(centerX-100, centerY-100, centerX+100, centerY+100, mPaint);
    }
}
