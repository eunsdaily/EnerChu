package com.enerchu.Fragment;

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

import com.enerchu.Adapter.BillCustomAdapter;
import com.enerchu.ChartMaker;
import com.enerchu.SQLite.DAO.BillStateDAO;
import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.R;
import com.enerchu.SQLite.Singleton.Singleton;

import java.util.ArrayList;

/**
 * Created by admin on 2017-04-30.
 */

public class BillFragment extends Fragment {
    private View root = null;
    private ViewFlipper viewFlipper;
    private int preTouchPosX = 0;

    private MultiTapDAO multiTapDAO;
    private ClientDAO clientDAO;
    private BillStateDAO billStateDAO;

    private TextView thisMonthBillTextView;
    private TextView lastMonthBillTextView;
    private TextView goalBillTextView;
    private TextView chageTextView;
    private TextView warringTextView;
    private boolean changeTextViewState = true;

    private ListView listView;
    private BillCustomAdapter adapter;

    public BillFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bill, container, false);
        viewFlipper = (ViewFlipper) root.findViewById(R.id.viewFlipper);
        viewFlipper.setOnTouchListener(flipperTouchListener);

        clientDAO = Singleton.getClientDAO();
        billStateDAO = Singleton.getBillStateDAO();
        multiTapDAO = Singleton.getMultiTapDAO();

        //setChart();
        ChartMaker chartMaker = new ChartMaker();
        chartMaker.setMultitapChart(root);

        // set text
        lastMonthBillTextView = (TextView) root.findViewById(R.id.lastMonthBill);
        thisMonthBillTextView = (TextView) root.findViewById(R.id.thisMonthBill);
        goalBillTextView = (TextView) root.findViewById(R.id.goalBill);
        chageTextView = (TextView) root.findViewById(R.id.chageTextView);
        warringTextView = (TextView) root.findViewById(R.id.warring);

        lastMonthBillTextView.setText(billStateDAO.getAmountUsedSum_lastMonth_Kwh()+" kWh");
        thisMonthBillTextView.setText(billStateDAO.getAmountUsedSum_thisMonth_Kwh()+" kWh");
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
                lastMonthBillTextView.setText(billStateDAO.getAmountUsedSum_lastMonth_won() + " 원");
                thisMonthBillTextView.setText(billStateDAO.getAmountUsedSum_thisMonth_won()+" 원");
                if(clientDAO.getGoalBillWon() != 0){
                    goalBillTextView.setText(clientDAO.getGoalBillWon()+ " 원");
                }
                chageTextView.setText("전력량으로 단위변경");
                warringTextView.setVisibility(View.VISIBLE);
                changeTextViewState = false;
            }
            else{
                lastMonthBillTextView.setText(billStateDAO.getAmountUsedSum_lastMonth_Kwh()+" kWh");
                thisMonthBillTextView.setText(billStateDAO.getAmountUsedSum_thisMonth_Kwh()+" kWh");
                if(clientDAO.getGoalBillKWh() != 0){
                    goalBillTextView.setText(clientDAO.getGoalBillKWh()+ " kWh");
                }
                chageTextView.setText("금액으로 단위변경");
                warringTextView.setVisibility(View.INVISIBLE);
                changeTextViewState = true;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        updateMultiTap();

    }

    private void updateMultiTap(){
        ArrayList<String> mulitapCodes = multiTapDAO.getMultitapCodes();
        int totalOfMultiTap = mulitapCodes.size();
        for(int i = 0 ; i < totalOfMultiTap; i++){
            adapter.add(mulitapCodes.get(i));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        clientDAO.close();
//        billStateDAO.close();
//        multiTapDAO.close();
    }
}