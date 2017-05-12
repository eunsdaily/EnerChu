package com.enerchu.fragment;

import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.Adapter.PlugCustomAdapter;
import com.enerchu.R;

public class PlugFragment extends Fragment {
    private View root = null;
    private ListView listView;
    private PlugCustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_plug, container, false);
        listView = (ListView)root.findViewById(R.id.listView);

        adapter = new PlugCustomAdapter();
        listView.setAdapter(adapter);

        return root;
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
