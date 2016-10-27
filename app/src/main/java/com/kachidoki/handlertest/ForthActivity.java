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
 * Created by mayiwei on 16/10/26.
 */
public class ForthActivity extends AppCompatActivity {


    private ProgressBar mProgressBar;
    private Button button;

    private HandlerThread workThread;
    private Handler handler;

    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Thread","OnCreatForth:--->"+Thread.currentThread().getId()+" name = "+Thread.currentThread().getName());
        setContentView(R.layout.activity_forth);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        button = (Button) findViewById(R.id.startforth);

        workThread = new HandlerThread("wordthread");
        workThread.start();
        handler = new Handler(workThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.e("Thread","HandlerMessage:--->"+Thread.currentThread().getId());
                //这个方法虽然更新UI但是自带线程同步效果误导很久
                mProgressBar.setProgress(msg.arg1);
                handler.post(work);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                i=0;
                handler.post(work);

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workThread.quit();
    }

    Runnable work= new Runnable() {
        @Override
        public void run() {
            Log.e("Thread","WorkThread:--->"+Thread.currentThread().getId());
            Log.e("Thread","WorkThread_i = "+i);
            i += 10;
            Message msg = handler.obtainMessage();

            msg.arg1 = i;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (i > 100) {
                // 将线程对象从队列中移除
                handler.removeCallbacks(workThread);
            } else {
//                update_progress.sendMessage(msg);
                handler.sendMessage(msg);
            }
        }
    };
}
