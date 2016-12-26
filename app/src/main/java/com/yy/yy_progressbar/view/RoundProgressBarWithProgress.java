package com.yy.yy_progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.yy.yy_progressbar.R;

/**
 * Created by 13160677911 on 2016-11-28.
 */

public class RoundProgressBarWithProgress extends HorizontalProgressbarWithProgress {

    private int mRadius = dp2px(30);
    //用作计算宽度
    private int mMaxPaintWidth;

    public RoundProgressBarWithProgress(Context context) {
        this(context, null);
    }

    public RoundProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeight = (int) (mUnReachHeight * 2.5);  //为了放大效果

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWithProgress);

        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBarWithProgress_radius, mRadius);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth = Math.max(mReachHeight, mUnReachHeight);
        //（默认四个padding一致）期望值
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();

        //测量宽度和高度(根据3种不同的模式)
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);

        //取最小值
        int readWidth = Math.min(width, height);

        mRadius = (readWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;

        setMeasuredDimension(readWidth, readWidth);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        //准备绘制
        canvas.save();
        //开始绘制
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop());
        mPaint.setStyle(Paint.Style.STROKE);

        //draw unreach bar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        //画圆(4个参数，x,y,圆心，半径)
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        //draw reach bar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        //获取弧度
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        //绘制弧
        canvas.drawArc(new RectF(0,0,mRadius * 2, mRadius * 2), -90, sweepAngle, false, mPaint);

        //draw text
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        //绘制文本
        canvas.drawText(text,mRadius - textWidth / 2,mRadius - textHeight, mPaint);

        canvas.restore();
    }
}
