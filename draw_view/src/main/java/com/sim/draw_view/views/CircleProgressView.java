package com.sim.draw_view.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.sim.draw_view.R;

import java.io.InputStream;

/**
 * 自定义环形进度条
 * by Sim create 2022.11.11
 */
public class CircleProgressView extends View {

    private Paint mBgPaint;//背景弧线paint
    private Paint mProgressPaint;//进度Paint

    private int locationStart;//起始位置
    private float startAngle;//开始角度
    private int sweepAngle = 180;//旋转角度

    private float mProgressWidth;//进度条宽度
    private int mProgressColorDefault;//进度条默认颜色
    private int mProgressColorStart;//进度条起始颜色
    private int mProgressColorEnd;//进度条结束颜色

    private int maxProgress = 100;//最大进度
    private int currentProgress;//当前进度

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        locationStart = typedArray.getInt(R.styleable.CircleProgressView_location_start, 1);
        sweepAngle = typedArray.getInt(R.styleable.CircleProgressView_sweep_angle, 180);
        mProgressWidth = typedArray.getDimension(R.styleable.CircleProgressView_progress_width, dp2px(context, 4));
        mProgressColorDefault = typedArray.getColor(R.styleable.CircleProgressView_progress_color_default, Color.parseColor("#eaecf0"));
        mProgressColorStart = typedArray.getColor(R.styleable.CircleProgressView_progress_color_start, Color.RED);
        mProgressColorEnd = typedArray.getColor(R.styleable.CircleProgressView_progress_color_end, Color.RED);
        typedArray.recycle();

        //背景圆弧画笔
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStrokeWidth(mProgressWidth);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(mProgressColorDefault);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);

        //进度圆弧画笔
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        //角度
        if (locationStart == 1) {//左
            startAngle = -180;
        } else if (locationStart == 2) {//上
            startAngle = -90;
        } else if (locationStart == 3) {//右
            startAngle = 0;
        } else if (locationStart == 4) {//下
            startAngle = 90;
        } else if (locationStart == 5) {//左上
            startAngle = -135;
        } else if (locationStart == 6) {//右上
            startAngle = -45;
        } else if (locationStart == 7) {//右下
            startAngle = 45;
        } else if (locationStart == 8) {//左下
            startAngle = 135;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width < height ? width : height;
        setMeasuredDimension(size, size);
    }

    /**
     * oval // 绘制范围
     * startAngle // 开始角度
     * sweepAngle // 扫过角度
     * useCenter // 是否使用中心
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景圆弧
        RectF rectF = new RectF(mProgressWidth / 2, mProgressWidth / 2, getWidth() - mProgressWidth / 2, getHeight() - mProgressWidth / 2);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mBgPaint);

        //绘制当前进度
        float sweepAngle = -this.sweepAngle * currentProgress / maxProgress;
        mProgressPaint.setShader(new LinearGradient(0, 0, rectF.right, 0, mProgressColorStart, mProgressColorEnd, Shader.TileMode.CLAMP));
        canvas.drawArc(rectF, startAngle, -sweepAngle, false, mProgressPaint);
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /*private ValueAnimator mAnimator;
    private int tCurrent = -1;

    *//**
     * 动画效果
     *
     * @param current  精度条进度：0-100
     * @param duration 动画时间
     *//*
    public void startAnimProgress(int current, int duration) {
        mAnimator = ValueAnimator.ofInt(0, current);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                if (tCurrent != current) {
                    tCurrent = current;
                    setCurrentProgress(current);
                    if (mOnAnimProgressListener != null)
                        mOnAnimProgressListener.valueUpdate(current);
                }
            }
        });
        mAnimator.start();
    }

    public interface OnAnimProgressListener {
        void valueUpdate(int progress);
    }

    private OnAnimProgressListener mOnAnimProgressListener;

    *//**
     * 监听进度条进度
     *
     * @param onAnimProgressListener
     *//*
    public void setOnAnimProgressListener(OnAnimProgressListener onAnimProgressListener) {
        mOnAnimProgressListener = onAnimProgressListener;
    }

    public void destroy() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }*/

}
