package com.enerchu.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.enerchu.GradeManager;
import com.enerchu.MissionController;
import com.enerchu.R;

public class FaceFragment extends Fragment {
    private View root = null;
    private MissionController missionController = new MissionController();
    private GradeManager gradeManager = new GradeManager();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_face, container, false);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        // set mission text
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) root.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        double textSize = (root.getContext().getResources().getDisplayMetrics().heightPixels*0.02);

        TextView missionText = (TextView) root.findViewById(R.id.missionText);
        missionText.setText(missionController.getTodayMisson());
        missionText.setTextSize((float)textSize);


        // set background color
        final Bitmap gradation = BitmapFactory.decodeResource(getResources(), R.drawable.gradation);
        int nowGrade = gradeManager.getGrade();
        int rgb = gradation.getPixel(Integer.parseInt(String.valueOf(Math.round(gradation.getWidth()*0.01*nowGrade))), 5);
        root.setBackgroundColor(rgb);


        // set face
        ImageView face = (ImageView) root.findViewById(R.id.face);
        if(nowGrade > 75){face.setImageResource(R.drawable.grade_1_dark);}
        else if(nowGrade > 50){face.setImageResource(R.drawable.grade_2_dark);}
        else if(nowGrade > 25){face.setImageResource(R.drawable.grade_3_dark);}
        else{face.setImageResource(R.drawable.grade_4_dark);}
    }
}
