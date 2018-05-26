package com.barackbao.electricmonitoring.mvp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Baoqianyue on 2018/5/26.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
    }

    @LayoutRes
    public abstract int getLayoutId();

    @IdRes
    public abstract int getContainerId();


    public void switchFragment(Fragment fragment) {
        if (mFragment == null ||
                !fragment.getClass().getName().equals(mFragment.getClass().getName())) {
            mFragmentManager.beginTransaction()
                    .replace(getContainerId(), fragment);
            mFragment = fragment;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
