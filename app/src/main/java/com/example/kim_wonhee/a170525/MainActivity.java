package com.example.kim_wonhee.a170525;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageButton imageButton;
    Button button;
    EditText editText;
    MyTask myTask;

    int food = 0;
    int temp = 1;
    int change_time = 0;
    int cancel = 0;
    int time = 0;

    static int[] foodlist = {R.drawable.burger, R.drawable.pizza, R.drawable.chicken, R.drawable.dok,
                             R.drawable.kimbab, R.drawable.ramen, R.drawable.aaa};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("오늘 뭐 먹지? ");

        init();
    }

    void init() {
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.text);
        imageButton = (ImageButton) findViewById(R.id.image);
    }

    public void onMyClick(View v) {
        //--- 이미지 버튼
        if (v.getId() == R.id.image) {
            if (cancel % 2 == 0) {
                if (editText.getText().toString().equals("")) {
                    Toast toastView = Toast.makeText(getApplicationContext(), "입력해주세요. ", Toast.LENGTH_SHORT);
                    toastView.setGravity(Gravity.TOP, 0, 400);
                    toastView.show();
                }
                else {
                    temp = 1;
                    change_time = Integer.parseInt(editText.getText().toString());
                    Toast toastView = Toast.makeText(getApplicationContext(), "메뉴 이미지 변경 간격은 " + change_time + "초 입니다. ", Toast.LENGTH_SHORT);
                    toastView.setGravity(Gravity.TOP, 0, 400);
                    toastView.show();
                    ++cancel;
                    imageButton.setImageResource(foodlist[food]);
                    textView.setText("시작부터 0초 ");
                    myTask = new MyTask();
                    myTask.execute(0);
                }
            } else {
                ++cancel;
                myTask.cancel(true);
                myTask = null;
            }
        } else if (v.getId() == R.id.button) {
            food = 0;
            temp = 1;
            change_time = 0;
            cancel = 0;
            time = 0;
            textView.setText("");
            imageButton.setImageResource(R.drawable.main);
        }
    }

    private class MyTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            for (int i = 1; i < 10000; i++) {
                if (isCancelled() == true)
                     return null;
                try {
                    Thread.sleep(1000);
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
            time = values[0];

            if (temp == change_time) {
                if (food != 0 && food % 6 == 0)
                    food = 0;
                else
                    ++food;
                temp = 1;
                imageButton.setImageResource(foodlist[food]);
            }
            ++temp;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast toastView = Toast.makeText(getApplicationContext(), "취소되었습니다. ", Toast.LENGTH_SHORT);
            toastView.setGravity(Gravity.TOP, 0, 400);
            toastView.show();
            if (food == 0)
                textView.setText("버거가 선택되었습니다. " + "(" + time + "초)");
            else if (food == 1)
                textView.setText("피자가 선택되었습니다. " + "(" + time + "초)");
            else if (food == 2)
                textView.setText("치킨이 선택되었습니다. " + "(" + time + "초)");
            else if (food == 3)
                textView.setText("떡볶이가 선택되었습니다. " + "(" + time + "초)");
            else if (food == 4)
                textView.setText("김밥이 선택되었습니다. " + "(" + time + "초)");
            else if (food == 5)
                textView.setText("라면이 선택되었습니다. " + "(" + time + "초)");
            else if (food == 6)
                textView.setText("파전이 선택되었습니다. " + "(" + time + "초)");
        }
    }
}
