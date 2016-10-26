package com.kachidoki.handlertest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button start;
    private Button end;
    private Button thread;
    private Button second;
    private Button third;
    private Button forth;



    Handler handler = new Handler();
    Runnable update_thread = new Runnable() {
        @Override
        public void run() {
//            Log.i("Thread","update_thread:--->"+Thread.currentThread().getId());
            tv.append("\nUpdateThread.....");
            handler.postDelayed(update_thread,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Thread","MainActivity onCreate:--->"+Thread.currentThread().getId());

        tv = (TextView)findViewById(R.id.text_view);
        start = (Button)findViewById(R.id.start);
        end = (Button)findViewById(R.id.end);
        thread = (Button)findViewById(R.id.thread);
        second = (Button)findViewById(R.id.SecondActivity);
        third = (Button) findViewById(R.id.ThirdActivity);
        forth = (Button) findViewById(R.id.ForthActivity);

        final Handler handlertest = new Handler();


        thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Thread","In Thread:--->"+Thread.currentThread().getId());
                        handlertest.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("Thread","In Thread HandlerPost:--->"+Thread.currentThread().getId());
                            }
                        });
                    }
                }).start();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Thread","onClickStart:--->"+Thread.currentThread().getId());
                handler.post(update_thread);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Thread","onClickEnd:--->"+Thread.currentThread().getId());
                handler.removeCallbacks(update_thread);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });

        forth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForthActivity.class);
                startActivity(intent);
            }
        });
    }
}
