package com.enerchu.fragment;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.enerchu.R;
import com.enerchu.SQLite.DAO.MissionDAO;

import java.util.ArrayList;

public class MissionFragment extends Fragment {
    private View root = null;

    private LinearLayout buttonLayout;
    private TableLayout tableLayout;
    private Switch arrageSwitch;
    private Switch successSwitch;
    private Switch failSwitch;

    ArrayList<TableLayout> tableLayouts = new ArrayList<>();
    ArrayList<ImageButton> buttonArrayList = new ArrayList<>();
    int nowPage = 0;

    private MissionDAO missionDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mission, container, false);

        tableLayout = (TableLayout) root.findViewById(R.id.tableLayout);
        buttonLayout = (LinearLayout) root.findViewById(R.id.buttonLayout);
        arrageSwitch = (Switch) root.findViewById(R.id.arrageSwitch);
        successSwitch = (Switch) root.findViewById(R.id.successSwitch);
        failSwitch = (Switch) root.findViewById(R.id.failSwitch);
        missionDAO = new MissionDAO(root.getContext());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSwitch();
        setMissionList(true, true, true);
    }

    private void setSwitch() {
        arrageSwitch.setChecked(true);
        successSwitch.setChecked(true);
        failSwitch.setChecked(true);

        arrageSwitch.setOnCheckedChangeListener(switchCheckedChangeListener);
        successSwitch.setOnCheckedChangeListener(switchCheckedChangeListener);
        failSwitch.setOnCheckedChangeListener(switchCheckedChangeListener);
    }

    CompoundButton.OnCheckedChangeListener switchCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            tableLayout.removeAllViews();
            buttonLayout.removeAllViews();
            setMissionList(arrageSwitch.isChecked(), successSwitch.isChecked(), failSwitch.isChecked());
        }
    };

    private void setMissionList(boolean arrage, boolean success, boolean fail) {
        tableLayouts = new ArrayList<>();
        buttonArrayList = new ArrayList<>();
        nowPage = 0;

        int numberOfMission = missionDAO.getTotalMission();
        int numberOfPage = numberOfMission / 12;
        if(numberOfPage != 0) {
            if (numberOfMission % 12 != 0) {
                numberOfPage++;
            }
            Log.i("numberOfMission", numberOfMission + "");
            Log.i("numberOfPage", numberOfPage + "");

            for (int i = 0; i < numberOfPage; i++) {
                View tmpView = View.inflate(root.getContext(), R.layout.mission_list, null);

                setMissionListContent(i, tmpView);
                setPageBtn(i);
            }

            tableLayout.removeAllViews();
            tableLayout.addView(tableLayouts.get(0));
        }
    }

    private void setPageBtn(int i) {
        final ImageButton tmpButton = new ImageButton(root.getContext());
        tmpButton.setBackgroundColor(Color.WHITE);

        if( i == 0 ){
            tmpButton.setImageDrawable(getResources().getDrawable(R.drawable.mission_list_btn_sky));
        }
        else{
            tmpButton.setImageDrawable(getResources().getDrawable(R.drawable.mission_list_btn_gray));
        }


        final int index = i;
        tmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button clicked", String.valueOf(index));
                tableLayout.removeAllViews();
                tableLayout.addView(tableLayouts.get(index));
                buttonArrayList.get(nowPage).setImageDrawable(getResources().getDrawable(R.drawable.mission_list_btn_gray));
                tmpButton.setImageDrawable(getResources().getDrawable(R.drawable.mission_list_btn_sky));
                nowPage = index;
            }
        });


        buttonArrayList.add(tmpButton);
        buttonLayout.addView(buttonArrayList.get(index));
    }

    private void setMissionListContent(int i, View tmpView) {
        TableLayout tmpTable = (TableLayout) tmpView.findViewById(R.id.tableLayout);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tmpTable.setLayoutParams(params);

        setMissionListTextView(i, tmpView);
        tableLayouts.add(tmpTable);
    }

    // need to change! here!
    private void setMissionListTextView(int i, View tmpView) {
        TextView text = (TextView) tmpView.findViewById(R.id.row_1_date);
        int missionNum = i*12+0;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_2_date);
        missionNum = i*12+1;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_3_date);
        missionNum = i*12+2;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_4_date);
        missionNum = i*12+3;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_5_date);
        missionNum = i*12+4;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_6_date);
        missionNum = i*12+5;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_7_date);
        missionNum = i*12+6;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_8_date);
        missionNum = i*12+7;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_9_date);
        missionNum = i*12+8;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_10_date);
        missionNum = i*12+9;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_11_date);
        missionNum = i*12+10;
        text.setText(String.valueOf(missionNum));
        text = (TextView) tmpView.findViewById(R.id.row_12_date);
        missionNum = i*12+11;
        text.setText(String.valueOf(missionNum));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        missionDAO.close();
    }
}
