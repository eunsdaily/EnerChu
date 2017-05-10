package com.enerchu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.enerchu.BillCustomAdapter;
import com.enerchu.SQLite.DAO.BillDAO;
import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by admin on 2017-04-30.
 */

public class BillFragment extends Fragment {
    private View root = null;
    private ViewFlipper viewFlipper;
    private int preTouchPosX = 0;

    private BillDAO billDAO;
    private ClientDAO clientDAO;

    private TextView thisMonthBillTextView;
    private TextView lastMonthBillTextView;
    private TextView goalBillTextView;
    private TextView chageTextView;
    private TextView warringTextView;
    private boolean changeTextViewState = true;

    private ListView listView;
    private BillCustomAdapter adapter;

    public BillFragment() {
        billDAO = new BillDAO();
        clientDAO = new ClientDAO();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bill, container, false);
        viewFlipper = (ViewFlipper) root.findViewById(R.id.viewFlipper);
        viewFlipper.setOnTouchListener(flipperTouchListener);

        setChart();

        // set text
        lastMonthBillTextView = (TextView) root.findViewById(R.id.lastMonthBill);
        thisMonthBillTextView = (TextView) root.findViewById(R.id.thisMonthBill);
        goalBillTextView = (TextView) root.findViewById(R.id.goalBill);
        chageTextView = (TextView) root.findViewById(R.id.chageTextView);
        warringTextView = (TextView) root.findViewById(R.id.warring);

        lastMonthBillTextView.setText(billDAO.getLastMonthBillKWh()+" kWh");
        thisMonthBillTextView.setText(billDAO.getThisMonthBillKWh()+" kWh");
        warringTextView.setVisibility(View.INVISIBLE);
        if(clientDAO.getGoalBillKWh() != 0){
            goalBillTextView.setText(clientDAO.getGoalBillKWh()+ " kWh");
        }
        chageTextView.setOnClickListener(changeTextClickListener);

        listView = (ListView) root.findViewById(R.id.listView);
        adapter = new BillCustomAdapter();
        listView.setAdapter(adapter);

        return root;
    }

    private void moveNextView() {
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(root.getContext(), R.anim.appear_from_right));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(root.getContext(), R.anim.disappear_to_left));
        viewFlipper.showNext();
    }

    private void movePreviousView() {
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(root.getContext(), R.anim.appear_from_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(root.getContext(), R.anim.disappear_to_right));
        viewFlipper.showNext();
    }

    View.OnTouchListener flipperTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                preTouchPosX = (int) event.getX();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int touchPosX = (int) event.getX();
                if (touchPosX < preTouchPosX) moveNextView();
                else if (touchPosX > preTouchPosX) movePreviousView();
                preTouchPosX = touchPosX;
            }
            return true;
        }
    };

    View.OnClickListener changeTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(changeTextViewState){ // kWh -> 원
                lastMonthBillTextView.setText(billDAO.getLastMonthBillWon() + " 원");
                thisMonthBillTextView.setText(billDAO.getThisMonthBillWon()+" 원");
                if(clientDAO.getGoalBillWon() != 0){
                    goalBillTextView.setText(clientDAO.getGoalBillWon()+ " 원");
                }
                chageTextView.setText("전력량으로 단위변경");
                warringTextView.setVisibility(View.VISIBLE);
                changeTextViewState = false;
            }
            else{
                lastMonthBillTextView.setText(billDAO.getLastMonthBillKWh()+" kWh");
                thisMonthBillTextView.setText(billDAO.getThisMonthBillKWh()+" kWh");
                if(clientDAO.getGoalBillKWh() != 0){
                    goalBillTextView.setText(clientDAO.getGoalBillKWh()+ " kWh");
                }
                chageTextView.setText("금액으로 단위변경");
                warringTextView.setVisibility(View.INVISIBLE);
                changeTextViewState = true;
            }
        }
    };

    private void setChart(){
        LineChart lineChart = (LineChart) root.findViewById(R.id.chart);
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

    @Override
    public void onResume() {
        super.onResume();

        MultiTapDAO multiTapDAO = new MultiTapDAO();
        PlugDAO plugDAO = new PlugDAO();
        int totalOfMultiTap = multiTapDAO.getTotalOfMultiTap();
        updateMultiTap(totalOfMultiTap);

    }

    private void updateMultiTap(int totalOfMultiTap){
        for(int i = 0 ; i < totalOfMultiTap; i++){
            adapter.add("multiTapKey");
        }
    }
}