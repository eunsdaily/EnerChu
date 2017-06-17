package com.enerchu.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.enerchu.R;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.SQLite.DAO.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017-05-21.
 */

public class DelMultitapCustomAdapter extends BaseAdapter {

    private class DelMultitapHolder{
        private TextView multitapName;
        private CheckBox checkBox;
    }

    private class Tag{
        private int position;

        private Tag(int position){
            this.position = position;
        }
    }

    private Context context = null;
    DelMultitapHolder holder = new DelMultitapHolder();
    private MultiTapDAO multiTapDAO;
    private int totalOfMultiTap = 0;
    private String multiTapKey = null;
    private List<String> multiTapKeyList;
    HashMap<String, Boolean> map;


    public DelMultitapCustomAdapter(){
        totalOfMultiTap = 0;
        map = new HashMap<String, Boolean>();
        multiTapKeyList = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return totalOfMultiTap;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View  getView(int position, View convertView, ViewGroup parent){
        holder = new DelMultitapHolder();
        context = parent.getContext();
        multiTapDAO = Singleton.getMultiTapDAO();

        if( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.one_multitap_delet, parent, false);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.multitapName = (TextView) convertView.findViewById(R.id.textView);
            holder.checkBox.setTag(new Tag(position));

            //set textview
            if(multiTapDAO.getNickName(multiTapKeyList.get(position)) != null){
                holder.multitapName.setText(multiTapDAO.getNickName(multiTapKeyList.get(position)));
            } else{
                holder.multitapName.setText(multiTapKeyList.get(position));
            }

            // set onclick
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Tag tmpTag = (Tag) buttonView.getTag();
                    map.put(multiTapKeyList.get(tmpTag.position), isChecked);
                }
            });
        } else {
            holder = (DelMultitapHolder) convertView.getTag();
        }

        return convertView;
    }


    public void add(String multiTapKey){
        this.multiTapKey = multiTapKey;
        Log.i(multiTapKey, "adding");
        multiTapKeyList.add(multiTapKey);
        map.put(multiTapKey, false);
        totalOfMultiTap++;
    }
    public HashMap<String, Boolean> getDeletMap(){
//        Log.i("map(multiTapKey2)", String.valueOf(map.get("multiTapKey2")));
        return map;
    }
}
