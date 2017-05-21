package com.enerchu.Adapter;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.enerchu.R;
import com.enerchu.SQLite.DAO.MultiTapDAO;
import com.enerchu.SQLite.DAO.PlugDAO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017-05-22.
 */

public class renameCustomAdapter extends BaseAdapter {

    private class RenameHolder{
        private EditText multitapNameEdit;
        private TextView multitapNameRename;
        private EditText firstEdit;
        private TextView firstRename;
        private EditText secondEdit;
        private TextView secondRename;
        private EditText thirdEdit;
        private TextView thirdRename;
        private EditText fourthEdit;
        private TextView fourthRename;
    }

    private class Tag{
        private int position;
        private String newNickname;

        private Tag(int position, String newNickname){
            this.position = position;
            this.newNickname = newNickname;
        }
    }

    private Context context = null;
    RenameHolder holder =new RenameHolder();
    private String multiTapKey = null;
    private int totalOfMultiTap = 0;
    private MultiTapDAO multiTapDAO;
    private PlugDAO plugDAO;
    private List<String> multiTapKeyList;

    public renameCustomAdapter(){
        totalOfMultiTap = 0;
        multiTapDAO = new MultiTapDAO();
        plugDAO = new PlugDAO();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new RenameHolder();
        context = parent.getContext();

        if( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.one_multitap_rename, parent, false);

            holder.multitapNameEdit      = (EditText) convertView.findViewById(R.id.multitapNameEdit);
            holder.multitapNameRename    = (TextView) convertView.findViewById(R.id.multitapNameRename);
            holder.firstEdit             = (EditText) convertView.findViewById(R.id.firstEdit);
            holder.firstRename           = (TextView) convertView.findViewById(R.id.firstRename);
            holder.secondEdit            = (EditText) convertView.findViewById(R.id.secondEdit);
            holder.secondRename          = (TextView) convertView.findViewById(R.id.secondRename);
            holder.thirdEdit             = (EditText) convertView.findViewById(R.id.thirdEdit);
            holder.thirdRename           = (TextView) convertView.findViewById(R.id.thirdRename);
            holder.fourthEdit            = (EditText) convertView.findViewById(R.id.fourthEdit);
            holder.fourthRename          = (TextView) convertView.findViewById(R.id.fourthRename);

            holder.multitapNameRename.setTag(new Tag(position, holder.multitapNameEdit.getText().toString()));
            holder.firstRename.setTag(new Tag(position, holder.firstEdit.getText().toString()));
            holder.secondRename.setTag(new Tag(position, holder.secondEdit.getText().toString()));
            holder.thirdRename.setTag(new Tag(position, holder.thirdEdit.getText().toString()));
            holder.fourthRename.setTag(new Tag(position, holder.fourthEdit.getText().toString()));

            // set edit text
           convertView.setFocusable(true);
            holder.multitapNameEdit.setText(multiTapDAO.getNickName(multiTapKeyList.get(position)));
            holder.firstEdit.setText(plugDAO.getNickName(multiTapKeyList.get(position), 1));
            holder.secondEdit.setText(plugDAO.getNickName(multiTapKeyList.get(position), 2));
            holder.thirdEdit.setText(plugDAO.getNickName(multiTapKeyList.get(position), 3));
            holder.fourthEdit.setText(plugDAO.getNickName(multiTapKeyList.get(position), 4));

            holder.multitapNameEdit.clearFocus();
            holder.firstEdit.clearFocus();
            holder.secondEdit.clearFocus();
            holder.thirdEdit.clearFocus();
            holder.fourthEdit.clearFocus();

            // set onclick
            holder.multitapNameRename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag tmpTag = (Tag) v.getTag();
                    multiTapDAO.updateNickName(multiTapKeyList.get(tmpTag.position), tmpTag.newNickname);
                }
            });

            holder.firstRename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag tmpTag = (Tag) v.getTag();
                    plugDAO.updateNickName(multiTapKeyList.get(tmpTag.position), 1, tmpTag.newNickname);
                }
            });

            holder.secondRename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag tmpTag = (Tag) v.getTag();
                    plugDAO.updateNickName(multiTapKeyList.get(tmpTag.position), 2, tmpTag.newNickname);
                }
            });

            holder.thirdRename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag tmpTag = (Tag) v.getTag();
                    plugDAO.updateNickName(multiTapKeyList.get(tmpTag.position), 3, tmpTag.newNickname);
                }
            });

            holder.fourthRename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag tmpTag = (Tag) v.getTag();
                    plugDAO.updateNickName(multiTapKeyList.get(tmpTag.position), 4, tmpTag.newNickname);
                }
            });

            convertView.setTag(holder);

        } else {
            holder = (RenameHolder) convertView.getTag();
        }

        return convertView;
    }

    public void add(String multiTapKey){
        this.multiTapKey = multiTapKey;
        Log.i(multiTapKey, "adding");
        multiTapKeyList.add(multiTapKey);
        totalOfMultiTap++;
    }
}
