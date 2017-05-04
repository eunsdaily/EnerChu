package com.enerchu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enerchu.BillPagerAdapter;
import com.enerchu.R;

/**
 * Created by admin on 2017-04-30.
 */

public class BillFragment extends Fragment{
    private View root = null;
    private ViewPager viewPager = null;

    public BillFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bill, container, false);
        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        BillPagerAdapter adapter = new BillPagerAdapter(inflater);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        return root;
    }
}
