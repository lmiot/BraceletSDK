package com.lmiot.BraceletDemo.View;


import com.lmiot.BraceletDemo.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Administrator on 2016/5/22 0022.
 */
public class ColorCircleProgressView extends View {

    private Paint mPaint;
    private int mStrokeWith;
    private boolean mIsRound;
    private int mColor01;
    private int mColor02;
    private int mColor03;
    private int mColor04;
    private int mColor05;
    private int mColor06;
    private int mColor07;
    private int mViewAangle;
    private int mStartAangle;
    private int mViewPadding;
    private Paint mPaint1;
    private int mPointColor;
    private int mPointRaido;
    private float mView_x0;
    private float mView_y0;
    private boolean mCanTouch=true;
    private Paint mPaint2;


    public void setPointAngle(int pointAngle) {
        mPointAngle = (int)(2.7*pointAngle)+45;
        mIsStop=false;
        invalidate();
    }

    private int mPointAngle=45;
    private  OnProgressListener mOnProgressListener;
    private boolean mIsStop=false;



    public void setOnProgressListener(OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }
    public void SetEnAble(Boolean enable) {
        mCanTouch = enable;


    }

    public ColorCircleProgressView(Context context) {
        super(context);
    }

    public ColorCircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int color_01 = getResources().getColor(R.color.color01);
        int color_02 = getResources().getColor(R.color.color02);
        int color_03 = getResources().getColor(R.color.color03);
        int color_04 = getResources().getColor(R.color.color04);
        int color_05 = getResources().getColor(R.color.color05);
        int color_06 = getResources().getColor(R.color.color06);
        int color_07 = getResources().getColor(R.color.color07);




        /*获取属性集合*/
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ColorCircleProgressView, 0, 0);

       /*渐变颜色值*/
        mColor01 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color01, color_01);
        mColor02 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color02, color_02);
        mColor03 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color03, color_03);
        mColor04 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color04, color_04);
        mColor05 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color05, color_05);
        mColor06 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color06, color_06);
        mColor07 = typedArray.getColor(R.styleable.ColorCircleProgressView_Color07, color_07);

        /*圆环角度，开始的角度，到边框的距离*/
        mViewAangle = typedArray.getInteger(R.styleable.ColorCircleProgressView_ViewAngle, 270);
        mStartAangle = typedArray.getInteger(R.styleable.ColorCircleProgressView_StartAngle, 135);
        mViewPadding = typedArray.getInteger(R.styleable.ColorCircleProgressView_ViewPadding, 50);

        /*圆环的大小及是否圆角*/
        mStrokeWith = typedArray.getInteger(R.styleable.ColorCircleProgressView_StrokeWith, 20);
        mIsRound = typedArray.getBoolean(R.styleable.ColorCircleProgressView_IsRound, true);

        /*Point的颜色和大小*/
        mPointColor = typedArray.getColor(R.styleable.ColorCircleProgressView_PointColor, Color.WHITE);
        mPointRaido = typedArray.getInteger(R.styleable.ColorCircleProgressView_PointRadio, 30);




        /*设置圆环画笔*/
        SetPaint();

        /*设置移动点的画笔*/
        SetPaint01();


         /*设置移动点的边框*/
        SetPaint02();

    }

    private void SetPaint01() {
        mPaint1 = new Paint();
        mPaint1.setColor(mPointColor);
        mPaint1.setAntiAlias(true);               /*抗锯齿*/

    }
    private void SetPaint02() {
        mPaint2 = new Paint();
        mPaint2.setColor(Color.GRAY);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(2);
        mPaint2.setAntiAlias(true);               /*抗锯齿*/

    }

    private void SetPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);  /*画笔为线条线条*/
        mPaint.setStrokeWidth(mStrokeWith);     /*线条的宽*/
        mPaint.setAntiAlias(true);               /*抗锯齿*/
        if(mIsRound) {mPaint.setStrokeCap(Paint.Cap.ROUND);}  /*是否圆角*/


    }

    @Override
    protected void onDraw(Canvas canvas) {

        /*得到view的宽高*/
        int width = getWidth();
        int height = getHeight();

        /*把宽高赋值给全局变量,得到圆心的坐标*/
        mView_x0=width/2;
        mView_y0=height/2;



        /*设置线性渐变*/
        SweepGradient sweepGradient = new SweepGradient(width/ 2, height/ 2, new int[]{mColor01, mColor02, mColor03, mColor04, mColor05, mColor06, mColor07}, null);
        mPaint.setShader(sweepGradient);






        /*定义圆环的所占的矩形区域:注意view一定为正方形*/

        RectF   rectF = new RectF(0 + mViewPadding, 0 + mViewPadding, width - mViewPadding, width - mViewPadding);

        /*根据矩形区域画扇形:因为sweep的起点在右边中心处，所以先旋转90度画布*/
        canvas.rotate(90,width/2,height/2);
        canvas.drawArc(rectF, mStartAangle - 90, mViewAangle, false, mPaint);


        /*动态获取圆上起始点的坐标*/
        //圆点坐标：width/2,height/2
        //半径：（width-mViewPadding-mViewPadding）/2
        //角度：a0


        if(mPointAngle<=45){mPointAngle=45;}
        else if(mPointAngle>315&mPointAngle<=360){mPointAngle=315;}

        /*将45-315范围的角度转为0-100*/
        if(mOnProgressListener!=null) {
            int progress = (int)((mPointAngle - 45) / 2.7);
            mOnProgressListener.onScrollingListener(progress,mIsStop);
        }

        float x0=width/2;
        float y0=height/2;
        float R = (float) ((width - mViewPadding - mViewPadding) / 2);
        float Point_x= (float) (x0+R*Math.cos(mPointAngle*3.14/180));
        float Point_y= (float) (y0+R*Math.sin(mPointAngle * 3.14 / 180));

        canvas.drawCircle(Point_x,Point_y,mPointRaido,mPaint1);
        canvas.drawCircle(Point_x,Point_y,mPointRaido,mPaint2);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mCanTouch) {
        /*获取点击位置的坐标*/
        float Action_x = event.getX();
        float Action_y = event.getY();

        /*根据坐标转换成对应的角度*/
        float get_x0 = Action_x - mView_x0;
        float get_y0 = Action_y - mView_y0;
        /*01：左下角区域*/
        if(get_x0<=0&get_y0>=0){
            float tan_x = get_x0 * (-1);
            float tan_y = get_y0;
            double atan = Math.atan(tan_x / tan_y);
            mPointAngle= (int) Math.toDegrees(atan);
        }

        /*02：左上角区域*/
        if(get_x0<=0&get_y0<=0){
            float tan_x = get_x0 * (-1);
            float tan_y = get_y0*(-1);
            double atan = Math.atan(tan_y / tan_x);
            mPointAngle= (int) Math.toDegrees(atan)+90;
        }

        /*03：右上角区域*/
        if(get_x0>=0&get_y0<=0){
            float tan_x = get_x0 ;
            float tan_y = get_y0*(-1);
            double atan = Math.atan(tan_x/ tan_y);
            mPointAngle= (int) Math.toDegrees(atan)+180;
        }

        /*04：右下角区域*/
        if(get_x0>=0&get_y0>=0){
            float tan_x = get_x0 ;
            float tan_y = get_y0;
            double atan = Math.atan(tan_y / tan_x);
            mPointAngle= (int) Math.toDegrees(atan)+270;
        }

        if(event.getAction()==MotionEvent.ACTION_UP){
          mIsStop=true;
        }

        if(event.getAction()==MotionEvent.ACTION_DOWN){
          mIsStop=false;
        }






        /*得到点的角度后进行重绘*/


            invalidate();
        }
        return true;
    }


    public interface OnProgressListener{

        public void onScrollingListener(Integer progress, boolean isStop);

    }

}
