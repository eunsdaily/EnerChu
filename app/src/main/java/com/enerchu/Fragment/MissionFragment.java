package com.enerchu.Fragment;

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

import com.enerchu.MissoinManager.MissionMaker;
import com.enerchu.R;
import com.enerchu.SQLite.DAO.MissionDAO;
import com.enerchu.SQLite.Singleton.Singleton;
import com.enerchu.SQLite.VO.MissionVO;

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

    private int numberOfMission = 0;
    private int numberOfPage = 0;

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
        missionDAO = Singleton.getMissionDAO();

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

//        numberOfMission = missionDAO.getTotalMission();
//        numberOfPage = 0;
//
//        if (numberOfMission % 12 == 0){
//            numberOfPage = numberOfMission/12;
//        }else{
//            numberOfPage = numberOfMission/12 + 1;
//        }

        ArrayList<MissionVO> missionVOArray = missionVOgetMission(arrage, success, fail);
        numberOfMission = missionVOArray.size();
        if (numberOfMission % 12 == 0){
            numberOfPage = numberOfMission/12;
        }else{
            numberOfPage = numberOfMission/12 + 1;
        }

        Log.i("numberOfMission", numberOfMission + "");
        Log.i("numberOfPage", numberOfPage + "");

        if(numberOfPage != 0) {
            for (int pageNumber = 0; pageNumber < numberOfPage; pageNumber++) {
                View tmpView = View.inflate(root.getContext(), R.layout.mission_list, null);

                setMissionListContent(pageNumber, tmpView, missionVOArray);
                setPageBtn(pageNumber);
            }

            tableLayout.removeAllViews();
            tableLayout.addView(tableLayouts.get(0));
        }
    }
    private void setMissionListContent(int pageNumber, View tmpView, ArrayList<MissionVO> missionVOArray) {
        TableLayout tmpTable = (TableLayout) tmpView.findViewById(R.id.tableLayout);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tmpTable.setLayoutParams(params);

        setMissionListTextView(pageNumber, tmpView, missionVOArray);
        tableLayouts.add(tmpTable);
    }

    // need to change! here!
    private void setMissionListTextView(int pageNumber, View tmpView, ArrayList<MissionVO> missionVOArray) {
        ArrayList<TextView> dateTextViewArray = addDataTextView(tmpView);
        ArrayList<TextView> missionStrTextViewArray = addMissionStrTextView(tmpView);
        ArrayList<TextView> successTextViewArray = addSuccessTextView(tmpView);

        int remandeMission = numberOfMission - pageNumber*12;
        int thisPageMissionStart = missionVOArray.size()-(pageNumber*12);

        Log.i("remandeMission", remandeMission+"");
        Log.i("thisPageMission", thisPageMissionStart+"");
        for(int i = 0; i < remandeMission; i++){
            int missionNum = pageNumber*12+i;
            dateTextViewArray.get(missionNum).setText(String.valueOf(missionVOArray.get(missionNum).getDate()));
            missionStrTextViewArray.get(missionNum).setText(MissionMaker.makeMissionString(missionVOArray.get(pageNumber).getMissionType(), String.valueOf(missionVOArray.get(missionNum).getParam())));
            successTextViewArray.get(missionNum).setText(String.valueOf(missionVOArray.get(pageNumber).isSuccess()));
        }
    }

    private ArrayList<MissionVO> missionVOgetMission(boolean arrage, boolean success, boolean fail) {
        ArrayList<MissionVO> missionVOArray;
        if(arrage){
            if(success){
                if(fail){
                    Log.i("missionVOgetMission", "TTT");
                    missionVOArray = missionDAO.getPastMissionTTT();
                }else{
                    Log.i("missionVOgetMission", "TTF");
                    missionVOArray = missionDAO.getPastMissionTTF();
                }
            }else{
                if(fail){
                    Log.i("missionVOgetMission", "TFT");
                    missionVOArray = missionDAO.getPastMissionTFT();
                }else{
                    Log.i("missionVOgetMission", "TFF");
                    missionVOArray = missionDAO.getPastMissionATFF();
                }
            }
        }else{
            if(success){
                if(fail){
                    Log.i("missionVOgetMission", "FTT");
                    missionVOArray = missionDAO.getPastMissionFTT();
                }else{
                    Log.i("missionVOgetMission", "FTF");
                    missionVOArray = missionDAO.getPastMissionFTF();
                }
            }else{
                if(fail){
                    Log.i("missionVOgetMission", "FFT");
                    missionVOArray = missionDAO.getPastMissionFFT();
                }else{
                    Log.i("missionVOgetMission", "FFF");
                    missionVOArray = missionDAO.getPastMissionFFF();
                }
            }
        }

        return missionVOArray;
    }

    private ArrayList<TextView> addSuccessTextView(View tmpView) {
        ArrayList<TextView> successTextViewArray = new ArrayList<>();

        TextView text = (TextView) tmpView.findViewById(R.id.row_1_success);
        successTextViewArray.add(0, text);
        text = (TextView) tmpView.findViewById(R.id.row_2_success);
        successTextViewArray.add(1, text);
        text = (TextView) tmpView.findViewById(R.id.row_3_success);
        successTextViewArray.add(2, text);
        text = (TextView) tmpView.findViewById(R.id.row_4_success);
        successTextViewArray.add(3, text);
        text = (TextView) tmpView.findViewById(R.id.row_5_success);
        successTextViewArray.add(4, text);
        text = (TextView) tmpView.findViewById(R.id.row_6_success);
        successTextViewArray.add(5, text);
        text = (TextView) tmpView.findViewById(R.id.row_7_success);
        successTextViewArray.add(6, text);
        text = (TextView) tmpView.findViewById(R.id.row_8_success);
        successTextViewArray.add(7, text);
        text = (TextView) tmpView.findViewById(R.id.row_9_success);
        successTextViewArray.add(8, text);
        text = (TextView) tmpView.findViewById(R.id.row_10_success);
        successTextViewArray.add(9, text);
        text = (TextView) tmpView.findViewById(R.id.row_11_success);
        successTextViewArray.add(10, text);
        text = (TextView) tmpView.findViewById(R.id.row_12_success);
        successTextViewArray.add(11, text);

        return successTextViewArray;
    }

    private ArrayList<TextView> addMissionStrTextView(View tmpView) {
        ArrayList<TextView> missionStrTextViewArray = new ArrayList<>();

        TextView text = (TextView) tmpView.findViewById(R.id.row_1_mission);
        missionStrTextViewArray.add(0, text);
        text = (TextView) tmpView.findViewById(R.id.row_2_mission);
        missionStrTextViewArray.add(1, text);
        text = (TextView) tmpView.findViewById(R.id.row_3_mission);
        missionStrTextViewArray.add(2, text);
        text = (TextView) tmpView.findViewById(R.id.row_4_mission);
        missionStrTextViewArray.add(3, text);
        text = (TextView) tmpView.findViewById(R.id.row_5_mission);
        missionStrTextViewArray.add(4, text);
        text = (TextView) tmpView.findViewById(R.id.row_6_mission);
        missionStrTextViewArray.add(5, text);
        text = (TextView) tmpView.findViewById(R.id.row_7_mission);
        missionStrTextViewArray.add(6, text);
        text = (TextView) tmpView.findViewById(R.id.row_8_mission);
        missionStrTextViewArray.add(7, text);
        text = (TextView) tmpView.findViewById(R.id.row_9_mission);
        missionStrTextViewArray.add(8, text);
        text = (TextView) tmpView.findViewById(R.id.row_10_mission);
        missionStrTextViewArray.add(9, text);
        text = (TextView) tmpView.findViewById(R.id.row_11_mission);
        missionStrTextViewArray.add(10, text);
        text = (TextView) tmpView.findViewById(R.id.row_12_mission);
        missionStrTextViewArray.add(11, text);

        return missionStrTextViewArray;
    }

    private ArrayList<TextView> addDataTextView(View tmpView) {
        ArrayList<TextView> dateTextViewArray = new ArrayList<>();

        TextView text = (TextView) tmpView.findViewById(R.id.row_1_date);
        dateTextViewArray.add(0, text);
        text = (TextView) tmpView.findViewById(R.id.row_2_date);
        dateTextViewArray.add(1, text);
        text = (TextView) tmpView.findViewById(R.id.row_3_date);
        dateTextViewArray.add(2, text);
        text = (TextView) tmpView.findViewById(R.id.row_4_date);
        dateTextViewArray.add(3, text);
        text = (TextView) tmpView.findViewById(R.id.row_5_date);
        dateTextViewArray.add(4, text);
        text = (TextView) tmpView.findViewById(R.id.row_6_date);
        dateTextViewArray.add(5, text);
        text = (TextView) tmpView.findViewById(R.id.row_7_date);
        dateTextViewArray.add(6, text);
        text = (TextView) tmpView.findViewById(R.id.row_8_date);
        dateTextViewArray.add(7, text);
        text = (TextView) tmpView.findViewById(R.id.row_9_date);
        dateTextViewArray.add(8, text);
        text = (TextView) tmpView.findViewById(R.id.row_10_date);
        dateTextViewArray.add(9, text);
        text = (TextView) tmpView.findViewById(R.id.row_11_date);
        dateTextViewArray.add(10, text);
        text = (TextView) tmpView.findViewById(R.id.row_12_date);
        dateTextViewArray.add(11, text);

        return dateTextViewArray;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
