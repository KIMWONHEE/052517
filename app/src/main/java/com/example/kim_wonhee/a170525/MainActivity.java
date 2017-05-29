package com.example.kim_wonhee.a170525;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    ImageView imageView;
    EditText editText;
    MyTask myTask;

    int food = 0;
    int second = 0;
    int change_time = 0;

    String write_time;

    static int[] foodlist = {R.drawable.burger, R.drawable.pizza, R.drawable.chicken, R.drawable.dok,
                             R.drawable.kimbab, R.drawable.ramen, R.drawable.aaa};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //--- 이미지 변경 간격
//        if (editText.getText().toString().equals(""))
//            Toast.makeText(getApplicationContext(), "입력해주세요. ", Toast.LENGTH_SHORT).show();
//        else {
//            change_time = Integer.parseInt(editText.getText().toString());
//            Toast.makeText(getApplicationContext(), "메뉴 이미지 변경 간격은 " + change_time + "초 입니다. ", Toast.LENGTH_SHORT).show();
//        }

        write_time = editText.getText().toString();

    }

    void init() {
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.text);
    }

    public void onMyClick(View v) {
        if (v.getId() == R.id.start) {
            myTask = new MyTask();
            myTask.execute(0);

            if (editText.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "입력해주세요. ", Toast.LENGTH_SHORT).show();
            else {
                change_time = Integer.parseInt(editText.getText().toString());
                Toast.makeText(getApplicationContext(), "메뉴 이미지 변경 간격은 " + change_time + "초 입니다. ", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.button) {
            myTask.cancel(true);
            myTask = null;
        }
    }

    private class MyTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setVisibility(View.VISIBLE);
            textView.setText("시작부터 0초 ");
            food = 0;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            for (int i = 1; i < 10000; i++) {
                if (isCancelled() == true)
                     return null;
                if (write_time.equals(""))
                    return null;
                try {
                    Thread.sleep(change_time * 1000);
                    ++food;
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textView.setText("시작부터 " + values[0] + "초" );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText("선택되었습니다. ");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "취소되었습니다. ", Toast.LENGTH_SHORT).show();
            textView.setVisibility(View.GONE);
        }
    }
}
