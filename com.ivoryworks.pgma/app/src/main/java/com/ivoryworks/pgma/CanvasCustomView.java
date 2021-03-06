package com.ivoryworks.pgma;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasCustomView extends View implements View.OnTouchListener {
    private Paint mPaint = new Paint();
    private float mTouchX = 0;
    private float mTouchY = 0;

    public CanvasCustomView(Context context) {
        super(context);
    }

    public CanvasCustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int centerX = canvas.getWidth() / 2;
        final int centerY = canvas.getHeight() / 2;

        // Background
        canvas.drawColor(Color.parseColor("#330000cc"));

        mPaint.setAntiAlias(true);

        // Text
        mPaint.setTextSize(48);
        canvas.drawText("Canvas draw", 50, 50, mPaint);

        // Rect
        mPaint.setColor(Color.DKGRAY);
        canvas.drawRect(centerX - 100, centerY - 100, centerX + 100, centerY + 100, mPaint);

        // Circle
        mPaint.setColor(Color.argb(192, 64, 255, 64));
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

        // Line(点線)
        mPaint.setStrokeWidth(2.0f);
        for (int i = canvas.getHeight(); i > 0; i--) {
            if (i % 5 != 0) {
                continue;
            }
            canvas.drawPoint(canvas.getWidth() - i, i, mPaint);
        }

        // Path
        mPaint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(centerX, centerY);
        path.lineTo(centerX + 50, centerY + 100);
        path.lineTo(centerX - 50, centerY + 100);
        path.lineTo(centerX, centerY);
        canvas.drawPath(path, mPaint);

        // Touch pointer
        mPaint.setColor(Color.argb(192, 64, 64, 255));
        canvas.drawCircle(mTouchX, mTouchY, 50, mPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
        invalidate();
        return false;
    }
}
