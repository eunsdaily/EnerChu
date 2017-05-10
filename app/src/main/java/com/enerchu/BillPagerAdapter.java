package com.enerchu;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enerchu.SQLite.DAO.BillDAO;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Created by admin on 2017-05-01.
 */

public class BillPagerAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private BillDAO billDAO = new BillDAO();

    public BillPagerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        View view = null;

        switch (position){
            case 0:
                //view = inflater.inflate(R.layout.fragment_bill_total, null);
                setChart(view, 1);
                ViewGroup chartView = (ViewGroup) view.findViewById(R.id.chart);
                ((ViewPager)container).addView(view, 0);
                break;
            case 1:
                //view = inflater.inflate(R.layout.fragment_bill_plug, null);
                ((ViewPager)container).addView(view, 0);
                break;
        }
        return view;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private void setChart(View view){
        LineChart chart = (LineChart) view.findViewById(R.id.chart);
        ArrayList<Entry> valsComp = new ArrayList<Entry>();

        //chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        //set values
        valsComp = getValsComp();

        // create lineDataSet
        LineDataSet setComp = new LineDataSet(valsComp, "소비전력");
        setComp.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.addAll(dataSets);

        // Set Name of X values
        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add(" ");
        xValues.add("어제");
        xValues.add(" ");
        xValues.add("오늘");
        xValues.add(" ");
        xValues.add("내일예상");
        xValues.add(" ");

        //LineData data = new LineData(xValues, dataSets);
        //LineData data = new LineData(dataSets);

        // Set data and refresh
//        chart.setData(data);
        chart.invalidate();
    }

    private ArrayList<Entry> getValsComp(){
        ArrayList<Entry> valsComp = new ArrayList<Entry>();
//        valsComp.add(new Entry(getBeteenValue(billDAO.getBeforeBill(null, null, 2),billDAO.getBeforeBill(null,null,1)), 0, 0));
//        valsComp.add(new Entry(billDAO.getBeforeBill(null, null, 1), 1, 0));
//        valsComp.add(new Entry(getBeteenValue(billDAO.getBeforeBill(null, null, 1), billDAO.getTodayBill(null, null)), 2,0));
//        valsComp.add(new Entry(billDAO.getTodayBill(null, null), 3, 0));
//        valsComp.add(new Entry(getBeteenValue(billDAO.getTodayBill(null, null), billDAO.getTomorrowBill(null, null)), 4,0));
//        valsComp.add(new Entry(billDAO.getTomorrowBill(null, null), 5, 0));
//        valsComp.add(new Entry(billDAO.getTomorrowBill(null, null)/2, 6, 0));

        return valsComp;
    }

    private int getBeteenValue(int a, int b){
        int differ = Math.abs(a-b);
        int result = Integer.parseInt(String.valueOf(Math.round(Math.max(a,b)-differ*1.2)));
        return result;
    }




    private void setChart(View view, int i) {
        LineChart mChart = (LineChart) view.findViewById(R.id.chart);
        mChart.setViewPortOffsets(0,0,0,0);
        mChart.setBackgroundColor(Color.rgb(104, 241,175));

        // no description text
        //mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        //mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
        //y.setTypeface(mTfLight);
        y.setTypeface(Typeface.DEFAULT_BOLD);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        mChart.getAxisRight().setEnabled(false);

        mChart = setData(100, 50, mChart);
        mChart.getLegend().setEnabled(false);
        mChart.animateXY(2000, 2000);
        // dont forget to refresh the drawing
        mChart.invalidate();
    }

    private LineChart setData(int count, int range, LineChart chart) {

        LineChart mChart = chart;

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            int mult = (range + 1);
            int val =  Integer.parseInt(String.valueOf(Math.round((Math.random() * mult) + 20)));
             yVals.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);

            for(int i = 0; i < count; i++){
                set1.addEntry(yVals.get(i));
            } //set1.addEntry(yVals);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");
            //set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //set1.isDrawCubicEnabled();
            set1.setCubicIntensity(1f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            //set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return -10;
//                }
//            });


            ArrayList<String> xValues = new ArrayList<String>();
            for(int i = 0; i < count; i++){
                xValues.add(i+"");
            }

            // create a data object with the datasets
            //LineData data = new LineData(set1);
            //data.setValueTypeface(mTfLight);
//            data.setValueTextSize(9f);
//            data.setDrawValues(false);

            // set data
//            mChart.setData(data);
        }
        return mChart;
    }
}
