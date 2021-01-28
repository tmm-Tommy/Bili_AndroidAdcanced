# Android进阶-三方框架1

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


## 2.Android中的二维码和条码ZXing

+ 导入

```xml
  implementation 'com.google.zxing:core:3.3.3'
```

+ 创建一个工具包

```java
package com.example.test_android_advanced.utils;

public class EncodingUtils {
    public static HashMap hashMap; //用hasmap放置二维码的参数
    public static Bitmap bitmap;//声明一个bitmap对象用于放置图片;
    //生成二维码
    public static Bitmap createQRCode(String contents) {
        hashMap = new HashMap();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //定义二维码的纠错级别，为L
        hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置字符编码为utf-8
        hashMap.put(EncodeHintType.MARGIN, 2);
        //设置margin属性为2,也可以不设置
        BitMatrix bitMatrix = null;   //这个类是用来描述二维码的,可以看做是个布尔类型的数组
        try {
            bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 250, 250, hashMap);
            //调用encode()方法,第一次参数是二维码的内容，第二个参数是生二维码的类型，第三个参数是width，第四个参数是height，最后一个参数是hints属性
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int width = bitMatrix.getWidth();//获取width
        int height = bitMatrix.getHeight();//获取height
        int[] pixels = new int[width * height]; //创建一个新的数组,大小是width*height
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //通过两层循环,为二维码设置颜色
                if (bitMatrix.get(i, j)) {
                    pixels[i * width + j] = Color.BLACK;  //设置为黑色

                } else {
                    pixels[i * width + j] = Color.WHITE; //设置为白色
                }
            }
        }

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //调用Bitmap的createBitmap()，第一个参数是width,第二个参数是height,最后一个是config配置，可以设置成RGB_565
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        //调用setPixels(),第一个参数就是上面的那个数组，偏移为0，x,y也都可为0，根据实际需求来,最后是width ,和height
        return bitmap;
        //调用setImageBitmap()方法，将二维码设置到imageview控件里

    }

    public static String  getQRCode(Bitmap bitmap) {
        hashMap = new HashMap();
        hashMap.put(DecodeHintType.CHARACTER_SET, "utf-8");//设置解码的字符，为utf-8
        int width = bitmap.getWidth();//现在是从那个bitmap中得到width和height
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];//新建数组，大小为width*height
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height); //和什么的setPixels()方法对应
        Result result = null;//Result类主要是用于保存展示二维码的内容的
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(width, height, pixels)));
        //BinaryBitmap这个类是用于反转二维码的，HybridBinarizer这个类是zxing在对图像进行二值化算法的一个类
        try {
            result = new MultiFormatReader().decode(binaryBitmap);//调用MultiFormatReader()方法的decode()，传入参数就是上面用的反转二维码的
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 2、生成条形码
     *
     * @param contents      需要生成的内容
     * @param desiredWidth  生成条形码的宽带
     * @param desiredHeight 生成条形码的高度
     * @return
     */
    public static Bitmap creatBarcode(String contents, int desiredWidth, int desiredHeight) {
        try {
            Bitmap ruseltBitmap = null;
            /**
             * 图片两端所保留的空白的宽度
             */
            int marginW = 20;
            /**
             * 条形码的编码类型
             */
            BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
            ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,
                    desiredWidth, desiredHeight);
            return ruseltBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /*以下的为条形码生成辅助工具方法*/

    /**
     * 生成条形码的Bitmap
     *
     * @param contents      需要生成的内容
     * @param format        编码格式
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    protected static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth,
                    desiredHeight, null);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
```

+ 布局

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".ZXing">

    <ImageView
        android:layout_weight="1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/zxing_iv1"/>
    <ImageView
        android:id="@+id/zxing_iv2"
        android:layout_weight="1"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    <TextView
        android:id="@+id/zxing_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#000"
        android:text="提示:"/>
</LinearLayout>
```

+ java控制代码

```java
package com.example.test_android_advanced;

public class ZXing extends AppCompatActivity {

    private ImageView mZxingIv2;
    private TextView mZxingTv;
    private ImageView mZxingIv1;
    Bitmap bitmapQR;
    Bitmap bitmapBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_xing);
        initView();
        setView();
    }

    private void setView() {
        bitmapQR = EncodingUtils.createQRCode("数据");
        bitmapBar = EncodingUtils.creatBarcode("123", mZxingIv2.getWidth(), 100);
        mZxingIv1.setImageBitmap(bitmapQR);
        mZxingIv2.setImageBitmap(bitmapBar);
        mZxingIv1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mZxingTv.setText(EncodingUtils.getQRCode(bitmapQR));
                return false;
            }
        });
        mZxingIv2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mZxingTv.setText(EncodingUtils.getQRCode(bitmapBar));
                return false;
            }
        });
    }

    private void initView() {
        mZxingIv2 = findViewById(R.id.zxing_iv2);
        mZxingTv = findViewById(R.id.zxing_tv);
        mZxingIv1 = findViewById(R.id.zxing_iv1);
    }
}
```

## 3.Android中的图表MPAndroidChart

+ 引入

```xml
//MPAndroidChart（开源图表框架）
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```

> 加上其他网络下载通道
>
> ```xml
> allprojects {
>  repositories {
>      google()
>      jcenter()
>      maven { url 'https://jitpack.io' }
>  }
> }
> ```

+ 布局

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MPChart">

    <com.github.mikephil.charting.charts.LineChart
        android:id="@+id/mpchart_lchart"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"/>
    <com.github.mikephil.charting.charts.BarChart
        android:id="@+id/mpchart_bchart"
        android:layout_weight="1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    <com.github.mikephil.charting.charts.PieChart
        android:id="@+id/mpchart_pchart"
        android:layout_weight="1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

+ java控制代码

```java
package com.example.test_android_advanced;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MPChart extends AppCompatActivity {

    private LineChart mMpchartLchart;
    private BarChart mMpchartBchart;
    private PieChart mMpchartPchart;
    private List<String> chatX;
    List<Integer> colors;
    //--线形图
    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<Entry> lineY;
    //---柱形图
    private BarData barData;
    private BarDataSet barDataSet;
    private List<BarEntry> barY;
    //--饼图
    private PieData pieData;
    private PieDataSet pieDataSet;
    private List<PieEntry> pieY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_chart);
        initView();
        setView();
    }

    private void setView() {
        //获取数据
        for (int i = 0; i < colors.size(); i++) {
            int value  = new Random().nextInt(100);
            chatX.add("数据"+(i+1));
            lineY.add(new Entry(i,value));
            //如果在不知道当前的x轴,可以使用getEntryCount获取当前的x的下一个进行给值,一样的效果.
            barY.add(new BarEntry(barDataSet.getEntryCount(),value));
            pieY.add(new PieEntry(value));
        }
        //刷新数据
        updateBar();
        updateLine();
        updatePie();
    }

    private void initView() {
        mMpchartLchart = findViewById(R.id.mpchart_lchart);
        mMpchartBchart = findViewById(R.id.mpchart_bchart);
        mMpchartPchart = findViewById(R.id.mpchart_pchart);
        //实例化公共数据
        chatX = new ArrayList<>();
        colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.BLACK);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        //初始化线图
        lineY = new ArrayList<>();
        lineDataSet = new LineDataSet(lineY,"线形图");
        //设置x
        lineDataSet.setColors(colors);
        lineData = new LineData(lineDataSet);
        mMpchartLchart.setData(lineData);
        mMpchartLchart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value>=0&&value<chatX.size())
                    return chatX.get((int) value);
                return "...";
            }
        });
        mMpchartLchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mMpchartLchart.getAxisRight().setEnabled(false);
        //初始化柱形图
        barY = new ArrayList<>();
        barDataSet = new BarDataSet(barY,"柱形图");
        barDataSet.setColors(colors);
        barData = new BarData(barDataSet);
        mMpchartBchart.setData(barData);
        mMpchartBchart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value>=0&&value<chatX.size())
                    return chatX.get((int) value);
                return "...";
            }
        });
        mMpchartBchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mMpchartBchart.getAxisRight().setEnabled(false);
        //初始化饼图
        pieY = new ArrayList<>();
        pieDataSet = new PieDataSet(pieY,"饼图");
        pieDataSet.setColors(colors);
        pieData = new PieData(pieDataSet);
        mMpchartPchart.setData(pieData);
    }
    //刷新线形图
    private void updateLine(){
        lineDataSet.notifyDataSetChanged();
        lineData.notifyDataChanged();
        mMpchartLchart.notifyDataSetChanged();
        mMpchartLchart.invalidate();
    }
    //刷新柱形图
    private void updateBar(){
        barDataSet.notifyDataSetChanged();
        barData.notifyDataChanged();
        mMpchartBchart.notifyDataSetChanged();
        mMpchartBchart.invalidate();
    }
    //刷新饼图
    private void updatePie(){
        pieDataSet.notifyDataSetChanged();
        pieData.notifyDataChanged();
        mMpchartPchart.notifyDataSetChanged();
        mMpchartPchart.invalidate();
    }
}
```



