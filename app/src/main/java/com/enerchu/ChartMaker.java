package com.enerchu;

import android.graphics.Color;
import android.view.View;

import com.enerchu.SQLite.DAO.BillDAO;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-12.
 */

public class ChartMaker {
    public ChartMaker() {}

    public void setMultitapChart(View root){
        LineChart lineChart = (LineChart) root.findViewById(R.id.chart);
        BillDAO billDAO = new BillDAO();

        lineChart.setDescription("");

        ArrayList<Entry> entries = new ArrayList<>();
        float day1 = billDAO.getBeforeBill(null, null, 2);
        float day2 = billDAO.getBeforeBill(null, null, 1);
        float day3 = billDAO.getTodayBill(null, null);
        float day4 = billDAO.getTomorrowBill(null, null);
        float maxYWidth = Math.max(Math.max(day1, day2), Math.max(day3, day4)) + 10;
        entries.add(new Entry(day1, 0));
        entries.add(new Entry(day2, 1));
        entries.add(new Entry(day3, 2));
        entries.add(new Entry(day4, 3));
        entries.add(new Entry(day4, 4));

        LineDataSet dataset = new LineDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("어제");
        labels.add("오늘");
        labels.add("내일");
        labels.add("");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(5000);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMaxValue(maxYWidth);
        leftAxis.setAxisMinValue(0);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        lineChart.invalidate();
    }

    public void setPlugChart(View root){
        HorizontalBarChart barChart = (HorizontalBarChart) root.findViewById(R.id.barChart);
        BillDAO billDAO = new BillDAO();

        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(new float[]{12, 3, 5, 7}, 0, "어제"));
        entries.add(new BarEntry(new float[]{12, 3, 5, 24}, 1, "오늘"));

        BarDataSet dataset = new BarDataSet(entries, "");
        dataset.setStackLabels(new String[]{"1구", "2구", "3구", "4구"});
        dataset.setColors(new int[]{Color.rgb(255, 127, 102),
                                    Color.rgb(255, 206, 117),
                                    Color.rgb(125, 206, 253),
                                    Color.rgb(33, 132, 199)});
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setBarSpacePercent(40);
        dataset.setValueTextSize(13);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(13);
        xAxis.setEnabled(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.addAll(dataSets);

        BarData data = new BarData();
        data.addXValue("어제");
        data.addXValue("오늘");
        data.addDataSet(dataset);
        data.setValueTextColor(Color.WHITE);

        barChart.setData(data);

        barChart.setFitsSystemWindows(true);
        barChart.invalidate();
    }
}
