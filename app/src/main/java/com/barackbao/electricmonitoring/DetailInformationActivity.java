package com.barackbao.electricmonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.barackbao.electricmonitoring.adapter.DetailPagerAdapter;
import com.barackbao.electricmonitoring.fragment.DetailFragment;
import com.barackbao.electricmonitoring.ui.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailInformationActivity extends AppCompatActivity {

    @BindView(R.id.navigation_layout)
    LinearLayout navigationLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout slidingTabs;
    @BindView(R.id.view_pager)
    NoScrollViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_information);
        ButterKnife.bind(this);
        initViewPager();
    }

    /**
     * 初始化pager
     */
    private void initViewPager() {
        final DetailPagerAdapter mHomeAdapter = new DetailPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mHomeAdapter);
        slidingTabs.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHomeAdapter.changePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
