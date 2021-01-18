package com.example.android_advanced;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMpChart extends AppCompatActivity {

    private List<String> chartX;
    private List<Integer> colors;
    //线形图
    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<Entry> lineY;
    //柱形图
    private BarData barData;
    private BarDataSet barDataSet;
    private List<BarEntry> barY;
    //饼图
    private PieData pieData;
    private PieDataSet pieDataSet;
    private List<PieEntry> pieY;
    private LineChart mMpchartLine;
    private BarChart mMpchartBar;
    private PieChart mMpchartPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mp_chart);
        initView();
        setView();
    }

    private void setView() {
        for (int i = 0; i < colors.size(); i++) {
            int value = new Random().nextInt(100);
            lineY.add(new Entry(i,value));
            barY.add(new BarEntry(i,value));
            pieY.add(new PieEntry(value));
            chartX.add("星期:"+(i+1));
        }
        updateChart();
    }

    private void initView() {
        mMpchartLine = findViewById(R.id.mpchart_line);
        mMpchartBar = findViewById(R.id.mpchart_bar);
        mMpchartPie = findViewById(R.id.mpchart_pie);
        chartX = new ArrayList<>();
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        initChart();

    }

    private void initChart() {
        //--线
        mMpchartLine.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mMpchartLine.getAxisRight().setEnabled(false);
        mMpchartLine.getXAxis().setGranularityEnabled(true);
        mMpchartLine.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value>=0&&value<chartX.size())
                    return chartX.get((int) value);
                else return  "...";
            }
        });
        lineY = new ArrayList<>();
        lineDataSet = new LineDataSet(lineY, "线形图");
        lineDataSet.setColors(colors);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineData = new LineData(lineDataSet);
        mMpchartLine.setData(lineData);
        //--柱
        mMpchartBar.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value>=0&&value<chartX.size())
                    return chartX.get((int) value);
                else return  "...";
            }
        });
        mMpchartBar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mMpchartBar.getAxisRight().setEnabled(false);
        barY = new ArrayList<>();
        barDataSet = new BarDataSet(barY, "柱状图");
        barDataSet.setColors(colors);
        barData = new BarData(barDataSet);
        mMpchartBar.setData(barData);
        //--饼
        pieY = new ArrayList<>();
        pieDataSet = new PieDataSet(pieY,"饼图");
        pieDataSet.setColors(colors);
        pieData = new PieData(pieDataSet);
        mMpchartPie.setData(pieData);
    }
    private void updateChart(){
        //--线
        lineDataSet.notifyDataSetChanged();
        lineData.notifyDataChanged();
        mMpchartLine.notifyDataSetChanged();
        mMpchartLine.invalidate();
        //--柱
        barDataSet.notifyDataSetChanged();
        barData.notifyDataChanged();
        mMpchartBar.notifyDataSetChanged();
        mMpchartBar.invalidate();
        //--饼
        pieDataSet.notifyDataSetChanged();
        pieData.notifyDataChanged();
        mMpchartPie.notifyDataSetChanged();
        mMpchartPie.invalidate();
    }
}