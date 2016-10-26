package com.kachidoki.handlertest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by mayiwei on 16/10/26.
 */
public class ThirdActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.e("Thread","ThirdActivity onCreate:--->"+Thread.currentThread().getId()+" name = "+Thread.currentThread().getName());

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        button = (Button)findViewById(R.id.startThird);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Thread","ThirdActivity onClick:--->"+Thread.currentThread().getId());

                progressBar.setVisibility(View.VISIBLE);
                new MyAsync().execute();
            }
        });

    }


    private class MyAsync extends AsyncTask<Void,Integer,String>{




        @Override
        protected String doInBackground(Void... params) {

            int i=0;

            for (i=0;i<=100;i=i+10){
                publishProgress(i);
                try {

                    Log.e("Thread","AsyncDoInBackGround---->: "+Thread.currentThread().getId());
                    Log.e("Thread","AsyncDoInBackGround---->: i= "+i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }





            return "finshFromDoInBackGround";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            Log.e("Thread","AsyncUpdate---->: "+Thread.currentThread().getId());
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Thread","AsyncOnPostExecute---->: "+Thread.currentThread().getId());
            Toast.makeText(ThirdActivity.this,s,Toast.LENGTH_SHORT).show();
            progressBar.setProgress(0);

        }
    }
}
