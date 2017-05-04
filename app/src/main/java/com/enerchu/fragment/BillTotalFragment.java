package com.enerchu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.enerchu.R;

/**
 * Created by admin on 2017-05-01.
 */

public class BillTotalFragment extends Fragment{

    LinearLayout layout;

    public BillTotalFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_bill_total, container, false);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
