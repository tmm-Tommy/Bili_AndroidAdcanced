package com.example.android_advanced.javaANDKotline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.android_advanced.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        var list:MutableList<String> = mutableListOf();
        for (i in 0..9)
            list.add("数据${i}");
        main_lv.adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list)
    }
}