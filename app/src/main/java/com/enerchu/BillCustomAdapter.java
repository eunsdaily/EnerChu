package com.enerchu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enerchu.SQLite.DAO.BillDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;

/**
 * Created by admin on 2017-05-05.
 */

public class BillCustomAdapter extends BaseAdapter {

    private class BillCustomHolder {
        private TextView    multitapName;
        private TextView    oneMultiTapLastMonthBill;
        private TextView    oneMultiTapThisMonthBill;
    }

    private Context context = null;
    BillCustomHolder holder = new BillCustomHolder();
    private String multiTapKey = null;
    private BillDAO billDAO;
    private MultiTapDAO multiTapDAO;
    private int totalOfMultiTap = 0;

    public BillCustomAdapter(){
        totalOfMultiTap = 0;
        multiTapDAO = new MultiTapDAO();
        billDAO = new BillDAO();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new BillCustomHolder();
        context = parent.getContext();

        if ( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.one_multitap_bill, parent, false);

            holder.multitapName = (TextView) convertView.findViewById(R.id.multitapName);
            holder.oneMultiTapLastMonthBill = (TextView) convertView.findViewById(R.id.oneMultiTapLastMonthBill);
            holder.oneMultiTapThisMonthBill = (TextView) convertView.findViewById(R.id.oneMultiTapThisMonthBill);

            // set text view
            if(multiTapDAO.getNickName(multiTapKey) != null){
                holder.multitapName.setText(multiTapDAO.getNickName(multiTapKey));
            }
            holder.oneMultiTapLastMonthBill.setText("20 kWh");
            holder.oneMultiTapThisMonthBill.setText("12 kWh");
        }

        return convertView;
    }

    public void add(String multiTapKey){
        this.multiTapKey = multiTapKey;
        totalOfMultiTap++;
    }
}
