# Android进阶-三方框架

## 1.Android的java和kotline对比

>  目标:初学者可直接观选择哪门语言

### 1.1、我们拿LIstVIew列表做例子

+ Android-java

  + 优点：
    + 中规中矩
    + 跨平台性
    + 大优点是开源
    + ....
  + xml布局

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout
                xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                tools:context=".MainActivity">
  
      <ListView
                android:id="@+id/main1_lv"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
  
  </LinearLayout>
  ```

  + java控制代码

  ```java
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
          mMain2Lv.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list));
      }
  
      private void initView() {
          mMain2Lv = findViewById(R.id.main2_lv);
      }
  }
  ```

+ Android-Kotline

  + 优点：
    + 简化代码
    + 集成了许多语言的优点（缺点）
    + 无空指针
    + ...
  + xml布局

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:tools="http://schemas.android.com/tools"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      tools:context=".MainActivity">
  
      <ListView
          android:id="@+id/main1_lv"
          android:layout_width="match_parent"
          android:layout_height="match_parent"/>
  
    </LinearLayout>
  ```

  + Kotline控制代码

  ```kotlin
  package com.example.test_android_advanced
  
  import androidx.appcompat.app.AppCompatActivity
  import android.os.Bundle
  import android.widget.ArrayAdapter
  import android.widget.ListAdapter
  import kotlinx.android.synthetic.main.activity_main.*
  
  class MainActivity : AppCompatActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          setView()
      }
  
      private fun setView() {
          //不可变List
          //        var list:List<String> = listOf();
          //可变List
          var list : MutableList<String> = mutableListOf();
          for (i in 0..9){
              list.add("数据${i}")
          }
          main1_lv.adapter = ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list)
      }
  }
  ```

### 1.2、最后福利：Android与Flutter对比

+ Flutter-dart

  + 优点：
    + ios，Android等等跨平台
    + 布局与控制代码的依赖性降低(缺点)
    + ....
  + 布局与代码

  ```dart
  import 'package:flutter/material.dart';
  
  void main() {
      runApp(MyApp());
  }
  
  class MyApp extends StatelessWidget {
      // This widget is the root of your application.
      @override
      Widget build(BuildContext context) {
          List<String> list = [] ;
          for(int i = 0;i<10;i++)
              list.add("数据&${i}");
          return MaterialApp(
              home: Container(
                  child: Scaffold(
                      body: ListView(
                          children:list.map<Widget>((e){
                              return Text(e);
                          }).toList()
                      )
                  )
              ),
          );
      }
  }
  ```

  

