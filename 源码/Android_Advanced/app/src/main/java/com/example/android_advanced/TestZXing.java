package com.example.android_advanced;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_advanced.utils.EncodingUtils;

public class TestZXing extends AppCompatActivity {

    private ImageView mZxingIv1;
    private ImageView mZxingIv2;
    private TextView mZxingTv;
    Bitmap bitmap1;
    Bitmap bitmap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_z_xing);
        initView();
        setView();
    }

    private void setView() {
         bitmap1 = EncodingUtils.creatBarcode("111000", 200, 200);
         bitmap2 = EncodingUtils.createQRCode("http:");
         mZxingIv1.setImageBitmap(bitmap1);
         mZxingIv2.setImageBitmap(bitmap2);
         mZxingIv1.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View view) {
                 mZxingTv.setText(EncodingUtils.getCode(bitmap1));
                 return false;
             }
         });
        mZxingIv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mZxingTv.setText(EncodingUtils.getCode(bitmap2));
                return false;
            }
        });
    }

    private void initView() {
        mZxingIv1 = findViewById(R.id.zxing_iv1);
        mZxingIv2 = findViewById(R.id.zxing_iv2);
        mZxingTv = findViewById(R.id.zxing_tv);
    }
}