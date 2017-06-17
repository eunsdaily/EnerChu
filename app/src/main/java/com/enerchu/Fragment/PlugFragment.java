package com.enerchu.Fragment;

import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.Adapter.PlugCustomAdapter;
import com.enerchu.R;
import com.enerchu.SQLite.DAO.Singleton;

import java.util.ArrayList;

public class PlugFragment extends Fragment {
    private View root = null;
    private ListView listView;
    private PlugCustomAdapter adapter;
    private MultiTapDAO multiTapDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_plug, container, false);
        listView = (ListView)root.findViewById(R.id.listView);

        multiTapDAO = Singleton.getMultiTapDAO();

        adapter = new PlugCustomAdapter();
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        MultiTapDAO multiTapDAO = new MultiTapDAO(root.getContext());
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
        multiTapDAO.close();
    }
}
