package com.enerchu.fragment;

import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.enerchu.R;

public class SettingFragment extends Fragment {

    private LinearLayout linearLayout;
    private TableRow connectionManage;
    private TableRow nickNmaeManage;
    private TableRow goalBillManage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        linearLayout = (LinearLayout) root.findViewById(R.id.linearLayout);
        connectionManage = (TableRow) root.findViewById(R.id.connectionManage);
        nickNmaeManage = (TableRow) root.findViewById(R.id.nickNmaeManage);
        goalBillManage = (TableRow) root.findViewById(R.id.goalBillManage);

        return root;
    }

    View.OnClickListener connectionManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // window popup
    View.OnClickListener nickNmaeManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    // window popup
    View.OnClickListener goalBillManageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
