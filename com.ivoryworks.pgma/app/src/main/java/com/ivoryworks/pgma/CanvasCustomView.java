package com.ivoryworks.pgma;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
        final int centerX = canvas.getWidth() / 2;
        final int centerY = canvas.getHeight() / 2;

        // Background
        canvas.drawColor(Color.parseColor("#330000cc"));

        // Text
        mPaint.setTextSize(48);
        canvas.drawText("Canvas draw", 50, 50, mPaint);

        // Rect
        mPaint.setColor(Color.DKGRAY);
        canvas.drawRect(centerX - 100, centerY - 100, centerX + 100, centerY + 100, mPaint);

        // Circle
        mPaint.setColor(Color.argb(192, 64, 255, 64));
        mPaint.setAntiAlias(true);
        canvas.drawCircle(centerX, centerY + 500, 300, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);    // 塗りつぶさない
        mPaint.setColor(Color.argb(255, 64, 255, 255));
        canvas.drawCircle(centerX, centerY + 500, 310, mPaint);

        // Oval
        mPaint.setStyle(Paint.Style.FILL);    // 塗りつぶす
        mPaint.setColor(Color.argb(192, 255, 64, 255));
        RectF ovalF = new RectF(centerX - 200, centerY - 100, centerX + 200, centerY + 100);
        canvas.drawOval(ovalF, mPaint);

        // KADOMARU
        mPaint.setColor(Color.argb(192, 128, 128, 128));
        RectF rectf = new RectF(centerX - 200, centerY + 150, centerX + 200, centerY + 200);
        canvas.drawRoundRect(rectf, 20, 20, mPaint);

        // Line
        mPaint.setColor(Color.argb(192, 0, 0, 255));
        canvas.drawLine(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);
    }
}
