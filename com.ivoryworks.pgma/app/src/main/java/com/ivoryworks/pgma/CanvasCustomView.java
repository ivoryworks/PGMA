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
        mPaint.setTextSize(48);
        canvas.drawText("Canvas draw", 100, 100, mPaint);
    }
}
