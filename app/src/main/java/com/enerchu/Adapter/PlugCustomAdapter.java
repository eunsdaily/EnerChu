package com.enerchu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enerchu.R;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;
import com.enerchu.condition;

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
    private MultiTapDAO multiTapDAO;
    private PlugDAO plugDAO;
    private int totalOfMultiTap = 0;
    private String multiTapKey = null;
    private boolean[] state;

    public PlugCustomAdapter(){
        totalOfMultiTap = 0;
        multiTapDAO = new MultiTapDAO();
        plugDAO = new PlugDAO();
        state = new boolean[condition.getMaxNumberOfMultitap()*4];
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
                holder.multitapName.setText(multiTapDAO.getNickName(multiTapKey));
            }
            if( plugDAO.getNickName(multiTapKey, 0) != null ) {
                holder.plugName1.setText(plugDAO.getNickName(multiTapKey, 0));
            }
            if( plugDAO.getNickName(multiTapKey, 1) != null ) {
                holder.plugName2.setText(plugDAO.getNickName(multiTapKey, 1));
            }
            if( plugDAO.getNickName(multiTapKey, 2) != null ) {
                holder.plugName3.setText(plugDAO.getNickName(multiTapKey, 2));
            }
            if( plugDAO.getNickName(multiTapKey, 3) != null ) {
                holder.plugName4.setText(plugDAO.getNickName(multiTapKey, 3));
            }

            // set state
            if( plugDAO.getState(multiTapKey, 0) ){
                holder.onoff1.setImageResource(R.drawable.on);
                state[0+position*4] = true;
            }
            else{
                holder.onoff1.setImageResource(R.drawable.off);
                state[0+position*4] = false;
            }

            if( plugDAO.getState(multiTapKey, 1) ){
                holder.onoff2.setImageResource(R.drawable.on);
                state[1+position*4] = true;
            }
            else{
                holder.onoff2.setImageResource(R.drawable.off);
                state[1+position*4] = false;
            }

            if( plugDAO.getState(multiTapKey, 2) ){
                holder.onoff3.setImageResource(R.drawable.on);
                state[2+position*4] = true;
            }
            else{
                holder.onoff3.setImageResource(R.drawable.off);
                state[2+position*4] = false;
            }

            if( plugDAO.getState(multiTapKey, 3) ){
                holder.onoff4.setImageResource(R.drawable.on);
                state[3+position*4] = true;
            }
            else{
                holder.onoff4.setImageResource(R.drawable.off);
                state[3+position*4] = false;
            }

            // set onclick
            holder.onoff1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageViewTag tmpTag = (ImageViewTag) v.getTag();
                    //Log.i("onoff1", "now state "+ state[0+tmpTag.postion*4]+" position "+tmpTag.postion);
                    if(state[tmpTag.postion*4]){
                        tmpTag.view.setImageResource(R.drawable.off);
                        state[tmpTag.postion*4] = false;
                        //Log.i("onoff1", "clicked! true to false");
                    }
                    else {
                        tmpTag.view.setImageResource(R.drawable.on);
                        state[tmpTag.postion*4] = true;
                        //Log.i("onoff1", "clicked! false to true");
                    }
                    tmpTag.view.invalidate();
                }
            });

            holder.onoff2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageViewTag tmpTag = (ImageViewTag) v.getTag();
                    if(state[1+tmpTag.postion*4]){
                        tmpTag.view.setImageResource(R.drawable.off);
                        state[1+tmpTag.postion*4] = false;
                    }
                    else {
                        tmpTag.view.setImageResource(R.drawable.on);
                        state[1+tmpTag.postion*4] = true;
                    }
                    tmpTag.view.invalidate();
                }
            });

            holder.onoff3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageViewTag tmpTag = (ImageViewTag) v.getTag();
                    if(state[2+tmpTag.postion*4]){
                        tmpTag.view.setImageResource(R.drawable.off);
                        state[2+tmpTag.postion*4] = false;
                    }
                    else {
                        tmpTag.view.setImageResource(R.drawable.on);
                        state[2+tmpTag.postion*4] = true;
                    }
                    tmpTag.view.invalidate();
                }
            });

            holder.onoff4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageViewTag tmpTag = (ImageViewTag) v.getTag();
                    if(state[3+tmpTag.postion*4]){
                        tmpTag.view.setImageResource(R.drawable.off);
                        state[3+tmpTag.postion*4] = false;
                    }
                    else {
                        tmpTag.view.setImageResource(R.drawable.on);
                        state[3+tmpTag.postion*4] = true;
                    }
                    tmpTag.view.invalidate();
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (PlugCustomHolder) convertView.getTag();
        }

        return convertView;
    }

    public void add(String multiTapKey){
        this.multiTapKey = multiTapKey;
        totalOfMultiTap++;
    }
}

