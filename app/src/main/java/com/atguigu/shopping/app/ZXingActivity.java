package com.atguigu.shopping.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.atguigu.shopping.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ZXingActivity extends AppCompatActivity {


    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.activity_zxing)
    RelativeLayout activityZxing;
    private String stringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.inject(this);
        getdata();
    }

    private void getdata() {
        stringExtra = getIntent().getStringExtra("image");
        setdata();
    }

    private void setdata() {
        if (TextUtils.isEmpty(stringExtra)) {
            Toast.makeText(ZXingActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap image1 = CodeUtils.createImage(stringExtra, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        image.setImageBitmap(image1);
    }

}
