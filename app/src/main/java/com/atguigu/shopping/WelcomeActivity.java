package com.atguigu.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class WelcomeActivity extends AppCompatActivity {

    //定义Handler
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        //两秒跳转
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();

            }
        }, 2000);
    }
    //跳转页面
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //结束当前页面
        finish();
    }

    //点击直接进入
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startMainActivity();
        }
        return super.onTouchEvent(event);

    }

    //移除所有消息和任务
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
