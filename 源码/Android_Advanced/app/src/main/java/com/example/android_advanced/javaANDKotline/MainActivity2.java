package com.example.android_advanced.javaANDKotline;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_advanced.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ListView mMain2Lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        setView();
    }

    private void setView() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("数据"+i);
        }
        mMain2Lv.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,list));
    }

    private void initView() {
        mMain2Lv = findViewById(R.id.main2_lv);
    }
}