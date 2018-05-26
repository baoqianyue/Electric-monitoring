package com.barackbao.electricmonitoring;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.barackbao.electricmonitoring.mvp.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity {


    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemReselectedListener
            = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_analysis:
                    break;
                case R.id.navigation_map:
                    break;
                case R.id.navigation_mine:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_container;
    }

}