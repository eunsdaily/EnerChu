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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.enerchu.Adapter.DelMultitapCustomAdapter;
import com.enerchu.Adapter.renameCustomAdapter;
import com.enerchu.R;
import com.enerchu.SQLite.DAO.ClientDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SettingFragment extends Fragment {

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
        nickNmaeManage.setOnClickListener(nickNameManageOnClickListener);
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
    View.OnClickListener nickNameManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            settingRenamePopup();
        }
    };


    // window popup
    View.OnClickListener goalBillManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            settingGoalPopup();
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

        MultiTapDAO multiTapDAO = new MultiTapDAO(root.getContext());
        ArrayList<String> mulitapCodes = multiTapDAO.getMultitapCodes();
        int totalOfMultiTap = mulitapCodes.size();
        for(int i = 0 ; i < totalOfMultiTap; i++){
            String key = mulitapCodes.get(i);
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
    private void settingRenamePopup() {
        View renamePopupView = root.inflate(root.getContext(), R.layout.popup_rename, null);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) root.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = root.getContext().getResources().getDisplayMetrics().heightPixels;
        int width = root.getContext().getResources().getDisplayMetrics().widthPixels;
        final PopupWindow renamePopupWindow = new PopupWindow(renamePopupView, width, height);

        renamePopupWindow.setTouchable(true);
        renamePopupWindow.setFocusable(true);
        renamePopupWindow.setOutsideTouchable(false);

        ListView listView = (ListView) renamePopupView.findViewById(R.id.listView);
        listView.setItemsCanFocus(true);
        final renameCustomAdapter adapter = new renameCustomAdapter();
        listView.setAdapter(adapter);

        renamePopupWindow.showAtLocation(renamePopupView, Gravity.CENTER, 0, 0);

        MultiTapDAO multiTapDAO = new MultiTapDAO(root.getContext());
        ArrayList<String> mulitapCodes = multiTapDAO.getMultitapCodes();
        int totalOfMultiTap = mulitapCodes.size();
        for(int i = 0 ; i < totalOfMultiTap; i++){
            String key = mulitapCodes.get(i);
            adapter.add(key);
        }

        Button comfirmBtn = (Button) renamePopupView.findViewById(R.id.comfirmBtn);
        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renamePopupWindow.dismiss();
            }
        });
    }
    private void settingGoalPopup() {
        View goalPopupView = root.inflate(root.getContext(), R.layout.popup_goal, null);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) root.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = root.getContext().getResources().getDisplayMetrics().heightPixels;
        int width = root.getContext().getResources().getDisplayMetrics().widthPixels;
        final PopupWindow goalPopupWindow = new PopupWindow(goalPopupView, width, height);

        goalPopupWindow.setTouchable(true);
        goalPopupWindow.setFocusable(true);
        goalPopupWindow.setOutsideTouchable(false);

        final ClientDAO clientDAO = new ClientDAO(root.getContext());
        final TextView goalBefore = (TextView) goalPopupView.findViewById(R.id.goalBefore);
        String goalText = clientDAO.getGoalBillKWh()+" kWh";
        goalBefore.setText(goalText);

        Button comfirmBtn = (Button) goalPopupView.findViewById(R.id.comfirmBtn);
        final EditText goalNow = (EditText) goalPopupView.findViewById(R.id.goalNow);
        comfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = goalNow.getText().toString();
                if (input.equals("")){
                    Toast.makeText(root.getContext(), "아무것도 변경되지 않습니다 :-0", Toast.LENGTH_SHORT).show();
                    goalPopupWindow.dismiss();
                }else {
                    float newGoal = Float.valueOf(input);
                    clientDAO.setGoal(newGoal);
                    Toast.makeText(root.getContext(), "목표 전력량이 변경되었습니다 :-)", Toast.LENGTH_SHORT).show();
                    goalPopupWindow.dismiss();
                }
            }
        });

        goalPopupWindow.showAtLocation(goalPopupView, Gravity.CENTER, 0, 0);
    }

    // 멀티탭 등록 결과를 반환
    private boolean tmpfun() {
        Log.i("tmpfun", "start");
        int tmp = 0;
        for (int i = 0; i < 10000; i++) { // delay
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


}
