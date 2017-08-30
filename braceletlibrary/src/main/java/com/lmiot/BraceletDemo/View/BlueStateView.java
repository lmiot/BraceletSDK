package com.lmiot.BraceletDemo.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.lmiot.BraceletDemo.R;

/**
 * Created by ming on 2016/3/28.
 */
@SuppressLint("AppCompatCustomView")
public class BlueStateView extends ImageView{

    int[] imgs={R.drawable.blue_search00,R.drawable.blue_search01,R.drawable.blue_search02,R.drawable.blue_search03};
    private Handler mHandler;
    private Runnable mRunnable;
    int mNum=0;


    public BlueStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mNum++;
                setBackgroundResource(imgs[mNum%4]);
                startSearcheBlue();

            }
        };
    }

    public BlueStateView(Context context) {
        super(context);
    }

    public BlueStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void startSearcheBlue(){
        mHandler.postDelayed(mRunnable,500);


    }
    public void stopSearcheBlue(){
        if(mHandler!=null&mRunnable!=null){
            mHandler.removeCallbacks(mRunnable);
        }

    }




}
