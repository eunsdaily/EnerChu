package com.enerchu.Adapter;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.enerchu.ChartMaker;
import com.enerchu.R;
import com.enerchu.SQLite.DAO.BillDAO;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;

/**
 * Created by admin on 2017-05-05.
 */

public class BillCustomAdapter extends BaseAdapter {

    private class BillCustomHolder {
        private TextView    multitapName;
        private TextView    oneMultiTapLastMonthBill;
        private TextView    oneMultiTapThisMonthBill;
        private TextView    showDetailBill;
    }

    private class TextViewTag{
        private int postion;
        private TextView view;

        private TextViewTag(int postion, TextView view){
            this.postion = postion;
            this.view = view;
        }
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
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.one_multitap_bill, parent, false);

            holder.multitapName = (TextView) convertView.findViewById(R.id.multitapName);
            holder.oneMultiTapLastMonthBill = (TextView) convertView.findViewById(R.id.oneMultiTapLastMonthBill);
            holder.oneMultiTapThisMonthBill = (TextView) convertView.findViewById(R.id.oneMultiTapThisMonthBill);
            holder.showDetailBill = (TextView) convertView.findViewById(R.id.showDetailBill);

            holder.showDetailBill.setTag(new TextViewTag(position, holder.showDetailBill));

            // set text view
            if(multiTapDAO.getNickName(multiTapKey) != null){
                holder.multitapName.setText(multiTapDAO.getNickName(multiTapKey));
            }

            holder.oneMultiTapLastMonthBill.setText("20 kWh");
            holder.oneMultiTapThisMonthBill.setText("12 kWh");

            final View rootView = convertView;
            // set onclick
            holder.showDetailBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // set popup view
                    TextViewTag tmpTag = (TextViewTag)v.getTag();
                    Log.i("showDetailBill", "called");

                    View popupView = inflater.inflate(R.layout.popup_bill_detail, null);
                    ChartMaker chartMaker = new ChartMaker();
                    chartMaker.setPlugChart(popupView);

                    DisplayMetrics metrics = new DisplayMetrics();
                    WindowManager windowManager = (WindowManager) rootView.getContext().getSystemService(Context.WINDOW_SERVICE);
                    windowManager.getDefaultDisplay().getMetrics(metrics);
                    int height = rootView.getContext().getResources().getDisplayMetrics().heightPixels;
                    int width = rootView.getContext().getResources().getDisplayMetrics().widthPixels;

                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height);
                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                    popupWindow.setTouchable(true);
                    popupWindow.setFocusable(false);
                    popupWindow.setOutsideTouchable(false);

                    Button comfirmBtn = (Button) popupView.findViewById(R.id.comfirmBtn);
                    comfirmBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });

                    setTextView(popupView, multiTapKey);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (BillCustomHolder) convertView.getTag();
        }

        return convertView;
    }

    public void setTextView(View popupView, String multiTapKey){

        PlugDAO plugDAO = new PlugDAO();

        TextView plugNameTextView = (TextView) popupView.findViewById(R.id.plugNameTextView);
        plugNameTextView.setText(multiTapDAO.getNickName(multiTapKey));
        if(plugNameTextView.getText() == ""){plugNameTextView.setText("멀티탭");}

        TextView firstNickName = (TextView) popupView.findViewById(R.id.firstNickName);
        firstNickName.setText(plugDAO.getNickName(multiTapKey, 1));
        if(firstNickName.getText() == ""){firstNickName.setText("1구");}
        TextView firstLastMonthBill = (TextView) popupView.findViewById(R.id.firstLastMonthBill);
        firstLastMonthBill.setText(plugDAO.getLastMonthBill(multiTapKey, 1)+" wh");
        TextView firstThisMonthBill = (TextView) popupView.findViewById(R.id.firstThisMonthBill);
        firstThisMonthBill.setText(plugDAO.getThisMonthBill(multiTapKey, 1)+" wh");

        TextView secondNickName = (TextView) popupView.findViewById(R.id.secondNickName);
        secondNickName.setText(plugDAO.getNickName(multiTapKey,1));
        if(secondNickName.getText() == ""){secondNickName.setText("2구");}
        TextView secondLastMonthBill = (TextView) popupView.findViewById(R.id.secondLastMonthBill);
        secondLastMonthBill.setText(plugDAO.getLastMonthBill(multiTapKey,2)+" wh");
        TextView secondThisMonthBill = (TextView) popupView.findViewById(R.id.secondThisMonthBill);
        secondThisMonthBill.setText(plugDAO.getThisMonthBill(multiTapKey,2)+" wh");

        TextView thirdNickName = (TextView) popupView.findViewById(R.id.thirdNickName);
        thirdNickName.setText(plugDAO.getNickName(multiTapKey,3));
        if(thirdNickName.getText() == ""){thirdNickName.setText("3구");}
        TextView thirdLastMonthBill = (TextView) popupView.findViewById(R.id.thirdLastMonthBill);
        thirdLastMonthBill.setText(plugDAO.getLastMonthBill(multiTapKey,3)+" wh");
        TextView thirdThisMonthBill = (TextView) popupView.findViewById(R.id.thirdThisMonthBill);
        thirdThisMonthBill.setText(plugDAO.getThisMonthBill(multiTapKey,3)+" wh");

        TextView fourthNickName = (TextView) popupView.findViewById(R.id.fourthNickName);
        fourthNickName.setText(plugDAO.getNickName(multiTapKey,4));
        if(fourthNickName.getText() == ""){fourthNickName.setText("4구");}
        TextView fourthLastMonthBill = (TextView) popupView.findViewById(R.id.fourthLastMonthBill);
        fourthLastMonthBill.setText(plugDAO.getLastMonthBill(multiTapKey,4)+" wh");
        TextView fourthThisMonthBill = (TextView) popupView.findViewById(R.id.fourthThisMonthBill);
        fourthThisMonthBill.setText(plugDAO.getThisMonthBill(multiTapKey,4)+" wh");
    }

    public void add(String multiTapKey){
        this.multiTapKey = multiTapKey;
        totalOfMultiTap++;
    }
}
