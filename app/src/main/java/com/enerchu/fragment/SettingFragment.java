package com.enerchu.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableRow;
import android.widget.TextView;

import com.enerchu.Adapter.DelMultitapCustomAdapter;
import com.enerchu.R;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SettingFragment extends Fragment {

    private ArrayList<String> groupList = null;
    private ArrayList<ArrayList<String>> childList = null;
    private ArrayList<String> childListContent = null;

    private LinearLayout linearLayout;
    private ExpandableListView listView;
    private TableRow connectionManage;
    private TableRow connectionManage_addNewMultitap;
    private TableRow connectionManage_delExistMultitap;
    private TableRow nickNmaeManage;
    private TableRow goalBillManage;

    private View root;
    private View addMultitapPopupView;
    private PopupWindow addMultitapPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_setting, container, false);
        linearLayout = (LinearLayout) root.findViewById(R.id.linearLayout);
        connectionManage = (TableRow) root.findViewById(R.id.connectionManage);
        connectionManage_addNewMultitap = (TableRow) root.findViewById(R.id.connectionManage_addNewMultitap);
        connectionManage_delExistMultitap = (TableRow) root.findViewById(R.id.connectionManage_delExistMultitap);
        nickNmaeManage = (TableRow) root.findViewById(R.id.nickNmaeManage);
        goalBillManage = (TableRow) root.findViewById(R.id.goalBillManage);

        connectionManage_addNewMultitap.setOnClickListener(addNewMultitapOnClickListener);
        connectionManage_delExistMultitap.setOnClickListener(delExistMultitapOnClickListener);
        nickNmaeManage.setOnClickListener(nickNmaeManageOnClickListener);
        goalBillManage.setOnClickListener(goalBillManageOnClickListener);

        return root;
    }

    View.OnClickListener addNewMultitapOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // call popview
            settingAddNewMultitapPopup();
        }
    };

    View.OnClickListener delExistMultitapOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            settingDelExistMultiapPopup();
        }
    };

    // window popup
    View.OnClickListener nickNmaeManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("nickNmaeManage", "called");
        }
    };

    // window popup
    View.OnClickListener goalBillManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("goalBillManage", "called");
        }
    };

    private void settingAddNewMultitapPopup() {
        addMultitapPopupView = root.inflate(root.getContext(), R.layout.popup_add_new_multitap, null);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) root.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = root.getContext().getResources().getDisplayMetrics().heightPixels;
        int width = root.getContext().getResources().getDisplayMetrics().widthPixels;
        addMultitapPopupWindow = new PopupWindow(addMultitapPopupView, width, height);

        addMultitapPopupWindow.setTouchable(true);
        addMultitapPopupWindow.setFocusable(false);
        addMultitapPopupWindow.setOutsideTouchable(false);

        Button comfirmBtn = (Button) addMultitapPopupView.findViewById(R.id.comfirmBtn);
        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMultitapPopupWindow.dismiss();
            }
        });

        ImageView enerChuImage = (ImageView) addMultitapPopupView.findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(addMultitapPopupView.getContext(), R.anim.rotate);
        enerChuImage.setAnimation(animation);

        TextView textView = (TextView) addMultitapPopupView.findViewById(R.id.textVeiw);
        addMultitapPopupWindow.showAtLocation(addMultitapPopupView, Gravity.CENTER, 0, 0);

        // test refeshAddMultitapPopupView
        Button testBtn = (Button) addMultitapPopupView.findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refeshAddMultitapPopupView();
            }
        });
    }
    private void settingDelExistMultiapPopup() {
        View delMultitapPopupView = root.inflate(root.getContext(), R.layout.popup_delete_exist_multitap, null);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) root.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = root.getContext().getResources().getDisplayMetrics().heightPixels;
        int width = root.getContext().getResources().getDisplayMetrics().widthPixels;
        final PopupWindow delMultitapPopupWindow = new PopupWindow(delMultitapPopupView, width, height);

        delMultitapPopupWindow.setTouchable(true);
        delMultitapPopupWindow.setFocusable(false);
        delMultitapPopupWindow.setOutsideTouchable(false);

        ListView listView = (ListView) delMultitapPopupView.findViewById(R.id.listView);
        final DelMultitapCustomAdapter adapter = new DelMultitapCustomAdapter();
        listView.setAdapter(adapter);

        delMultitapPopupWindow.showAtLocation(delMultitapPopupView, Gravity.CENTER, 0, 0);

        MultiTapDAO multiTapDAO = new MultiTapDAO();
        int totalOfMultiTap = multiTapDAO.getTotalOfMultiTap();
        for(int i = 0 ; i < totalOfMultiTap; i++){
            String key = "multiTapKey" + String.valueOf(i);
            adapter.add(key);
        }

        Button comfirmBtn = (Button) delMultitapPopupView.findViewById(R.id.comfirmBtn);
        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delMultitapPopupWindow.dismiss();
                delMultitap(adapter.getDeletMap());
            }
        });
    }


    // 멀티탭 등록 결과를 반환
    private boolean tmpfun() {
        Log.i("tmpfun", "start");
        int tmp = 0;
        for (int i = 0; i < 1000000000; i++) { // delay
            tmp++;
        }
        Log.i("tmpfun", "end");

        Random random = new Random();
        return random.nextBoolean();
    }

    private void refeshAddMultitapPopupView() {
        boolean result = tmpfun();

        addMultitapPopupView.findViewById(R.id.imageView).setAnimation(AnimationUtils.loadAnimation(addMultitapPopupView.getContext(), R.anim.rotate));

        if (addMultitapPopupWindow.isShowing()) {
            if (result) {
                addMultitapPopupView.findViewById(R.id.imageView).setAnimation(null);
                TextView textView = (TextView) addMultitapPopupView.findViewById(R.id.textVeiw);
                textView.setText("새로운 멀티탭 등록을 완료하였습니다.\n확인 버튼을 눌러 창을 종료해 주세요:)");
            } else {
                addMultitapPopupView.findViewById(R.id.imageView).setAnimation(null);
                TextView textView = (TextView) addMultitapPopupView.findViewById(R.id.textVeiw);
                textView.setText("새로운 멀티탭 등록을 실패하였습니다.\n확인 버튼을 눌러 창을 종료해 주세요:)");
            }
            Button confirmBtn = (Button) addMultitapPopupView.findViewById(R.id.comfirmBtn);
            confirmBtn.setText("확  인");
        }

        addMultitapPopupWindow.update();
    }

    // 멀티탭 삭제
    private void delMultitap(HashMap<String, Boolean> deletMap) {
        Log.i("del Multitap", "called");
    }

    //



}
