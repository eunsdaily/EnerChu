package com.enerchu;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.enerchu.fragment.BillFragment;
import com.enerchu.fragment.FaceFragment;
import com.enerchu.fragment.MissionFragment;
import com.enerchu.fragment.PlugFragment;
import com.enerchu.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_face:
                        fragment = new FaceFragment();
                        break;
                    case R.id.navigation_pulg:
                        fragment = new PlugFragment();
                        break;
                    case R.id.navigation_bill:
                        fragment = new BillFragment();
                        break;
                    case R.id.navigation_mission:
                        fragment = new MissionFragment();
                        break;
                    case R.id.navigation_setting:
                        fragment = new SettingFragment();
                        break;
                    default:
                        break;
                }
                if (fragment != null) {
                    replaceFragment(fragment);
                }
                return true;
            }
        });

        //replace SongsFragment
        replaceFragment(new FaceFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}
