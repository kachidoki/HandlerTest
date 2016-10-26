package com.kachidoki.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by mayiwei on 16/10/24.
 */
public class SecondActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button start;
    int i=0;

    Handler update_progress = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.e("Thread","handleMessage:--->msg.arg = "+msg.arg1);
            progressBar.setProgress(msg.arg1);
            update_progress.post(workThread);

        }
    };

    Runnable workThread = new Runnable() {
        @Override
        public void run() {
            Log.e("Thread","WorkThread:--->"+Thread.currentThread().getId());
            Log.e("Thread","WorkThread_i = "+i);
            i += 10;
            Message msg = update_progress.obtainMessage();

            msg.arg1 = i;

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            if (i > 100) {
                // 将线程对象从队列中移除
                update_progress.removeCallbacks(workThread);
            } else {
//                update_progress.sendMessage(msg);
                update_progress.sendMessageDelayed(msg,1000);
            }
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e("Thread","SecondActivity onCreate:--->"+Thread.currentThread().getId());

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        start = (Button)findViewById(R.id.startSecond);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                i = 0;
                update_progress.post(workThread);

            }
        });

    }
}
