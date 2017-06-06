package com.enerchu.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enerchu.ConnectWeb.ConnectWeb;
import com.enerchu.R;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-05-01.
 */


public class PlugCustomAdapter extends BaseAdapter {

    private class PlugCustomHolder {
        private TextView    multitapName;
        private TextView    plugName1;
        private TextView    plugName2;
        private TextView    plugName3;
        private TextView    plugName4;
        private ImageView   onoff1;
        private ImageView   onoff2;
        private ImageView   onoff3;
        private ImageView   onoff4;

    }

    private class ImageViewTag{
        private int postion;
        private ImageView view;

        private ImageViewTag(int postion, ImageView view){
            this.postion = postion;
            this.view = view;
        }
    }

    private Context context = null;
    PlugCustomHolder holder = new PlugCustomHolder();

    private int totalOfMultiTap = 0;
    private String multiTapKey = null;
    private List<String> multiTapKeyList;
    private boolean[] state;

    private MultiTapDAO multiTapDAO;
    private PlugDAO plugDAO;

    public PlugCustomAdapter(){
        totalOfMultiTap = 0;
        state = new boolean[condition.getMaxNumberOfMultitap()*4];
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

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new PlugCustomHolder();
        context = parent.getContext();

        multiTapDAO = new MultiTapDAO(context);
        plugDAO = new PlugDAO(context);

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.one_multitap, parent, false);

            holder.multitapName = (TextView) convertView.findViewById(R.id.multitapName);
            holder.plugName1 = (TextView) convertView.findViewById(R.id.name1);
            holder.plugName2 = (TextView) convertView.findViewById(R.id.name2);
            holder.plugName3 = (TextView) convertView.findViewById(R.id.name3);
            holder.plugName4 = (TextView) convertView.findViewById(R.id.name4);
            holder.onoff1 = (ImageView) convertView.findViewById(R.id.onoff1);
            holder.onoff2 = (ImageView) convertView.findViewById(R.id.onoff2);
            holder.onoff3 = (ImageView) convertView.findViewById(R.id.onoff3);
            holder.onoff4 = (ImageView) convertView.findViewById(R.id.onoff4);

            holder.onoff1.setTag(new ImageViewTag(position, holder.onoff1));
            holder.onoff2.setTag(new ImageViewTag(position, holder.onoff2));
            holder.onoff3.setTag(new ImageViewTag(position, holder.onoff3));
            holder.onoff4.setTag(new ImageViewTag(position, holder.onoff4));

            // set text view
            if( multiTapDAO.getNickName(multiTapKey) != null ){
                holder.multitapName.setText(multiTapDAO.getNickName(multiTapKeyList.get(position)));
            }
            if( plugDAO.getNickName((multiTapKeyList.get(position)), 1) != null ) {
                holder.plugName1.setText(plugDAO.getNickName((multiTapKeyList.get(position)), 1));
            }
            if( plugDAO.getNickName((multiTapKeyList.get(position)), 2) != null ) {
                holder.plugName2.setText(plugDAO.getNickName((multiTapKeyList.get(position)), 2));
            }
            if( plugDAO.getNickName((multiTapKeyList.get(position)), 3) != null ) {
                holder.plugName3.setText(plugDAO.getNickName((multiTapKeyList.get(position)), 3));
            }
            if( plugDAO.getNickName((multiTapKeyList.get(position)), 4) != null ) {
                holder.plugName4.setText(plugDAO.getNickName((multiTapKeyList.get(position)), 4));
            }

            // set state
            if( plugDAO.getState((multiTapKeyList.get(position)), 1) ){
                holder.onoff1.setImageResource(R.drawable.on);
                state[position*4] = true;
            }
            else{
                holder.onoff1.setImageResource(R.drawable.off);
                state[position*4] = false;
            }

            if( plugDAO.getState((multiTapKeyList.get(position)), 2) ){
                holder.onoff2.setImageResource(R.drawable.on);
                state[1+position*4] = true;
            }
            else{
                holder.onoff2.setImageResource(R.drawable.off);
                state[1+position*4] = false;
            }

            if( plugDAO.getState((multiTapKeyList.get(position)), 3) ){
                holder.onoff3.setImageResource(R.drawable.on);
                state[2+position*4] = true;
            }
            else{
                holder.onoff3.setImageResource(R.drawable.off);
                state[2+position*4] = false;
            }

            if( plugDAO.getState((multiTapKeyList.get(position)), 4) ){
                holder.onoff4.setImageResource(R.drawable.on);
                state[3+position*4] = true;
            }
            else{
                holder.onoff4.setImageResource(R.drawable.off);
                state[3+position*4] = false;
            }

            holder.onoff1.setOnClickListener(new ononffOnClickListener(0).getListner());
            holder.onoff2.setOnClickListener(new ononffOnClickListener(1).getListner());
            holder.onoff3.setOnClickListener(new ononffOnClickListener(2).getListner());
            holder.onoff4.setOnClickListener(new ononffOnClickListener(3).getListner());


            convertView.setTag(holder);
        } else {
            holder = (PlugCustomHolder) convertView.getTag();
        }

        return convertView;
    }

    public void add(String multiTapKey){
        this.multiTapKey = multiTapKey;
        Log.i("PlugCustomAdapter", multiTapKey+" adding");
        multiTapKeyList.add(multiTapKey);
        totalOfMultiTap++;
    }

    class ononffOnClickListener{
        private int n;

        ononffOnClickListener(int n){ this.n = n; }

        View.OnClickListener getListner(){
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageViewTag tmpTag = (ImageViewTag) v.getTag();
                    if(state[n+tmpTag.postion*4]){
                        tmpTag.view.setImageResource(R.drawable.off);
                        state[n+tmpTag.postion*4] = false;
                        plugDAO.updateState(multiTapKeyList.get(tmpTag.postion), n+1, false);
                        //  ♫꒰･‿･๑꒱ 통신
                    }
                    else {

                        tmpTag.view.setImageResource(R.drawable.on);
                        state[n + tmpTag.postion * 4] = true;
                        plugDAO.updateState(multiTapKeyList.get(tmpTag.postion), n + 1, true);
                        //  ♫꒰･‿･๑꒱ 통신
                    }
                    tmpTag.view.invalidate();

                    setTabInfoToWeb(tmpTag);
                    //Connect to web : update mysql to current state
                    
                    Log.i("on click", String.valueOf(n+tmpTag.postion*4));
                }

                private void setTabInfoToWeb(ImageViewTag tmpTag){
                    ConnectWeb task = new ConnectWeb();
                    String tabinfos = ""+booleanToInt(state[0+tmpTag.postion*4]) + " " + booleanToInt(state[1+tmpTag.postion*4])+" "
                            +booleanToInt(state[2+tmpTag.postion*4])+" "+booleanToInt(state[3+tmpTag.postion*4]);
                    task.connectToWeb("tab", "userid", multiTapKeyList.get(tmpTag.postion), tabinfos);
                }
                private int booleanToInt(boolean bool){
                    return (bool? 1 : 0);
                }
            };
        }
    }
}