package com.yy.yy_progressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yy.yy_progressbar.view.HorizontalProgressbarWithProgress;
import com.yy.yy_progressbar.view.RoundProgressBarWithProgress;

public class MainActivity extends AppCompatActivity {
    private HorizontalProgressbarWithProgress mHprogress01;
    private HorizontalProgressbarWithProgress mHprogress02;
    private HorizontalProgressbarWithProgress mHprogress03;
    private RoundProgressBarWithProgress mRprogress01;
    private RoundProgressBarWithProgress mRprogress02;

    private static final int MSG_UPDATE = 0X110;  //声明消息
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int progressH1 = mHprogress01.getProgress();
            int progressH2 = mHprogress02.getProgress();
            int progressH3 = mHprogress03.getProgress();
            int progressR1 = mRprogress01.getProgress();
            int progressR2 = mRprogress02.getProgress();
            mHprogress01.setProgress(+2+progressH1);
            mHprogress02.setProgress(++progressH2);
            mHprogress03.setProgress(++progressH3);

            mRprogress01.setProgress(+2+progressR1);
            mRprogress02.setProgress(++progressR2);
            if(progressH1 >= 100){
                mHandler.removeMessages(MSG_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 100);//发送消息，每次延迟100毫秒
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //触发消息
        mHandler.sendEmptyMessage(MSG_UPDATE);
    }

    private void initView() {
        mHprogress01 = (HorizontalProgressbarWithProgress) findViewById(R.id.id_horizontal_progress01);
        mHprogress02 = (HorizontalProgressbarWithProgress) findViewById(R.id.id_horizontal_progress02);
        mHprogress03 = (HorizontalProgressbarWithProgress) findViewById(R.id.id_horizontal_progress03);
        mRprogress01 = (RoundProgressBarWithProgress) findViewById(R.id.id_round_progress01);
        mRprogress02 = (RoundProgressBarWithProgress) findViewById(R.id.id_round_progress02);
    }
}
