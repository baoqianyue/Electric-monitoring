package com.barackbao.electricmonitoring.map;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.barackbao.electricmonitoring.R;
import com.barackbao.electricmonitoring.utils.FileUtils;
import com.fengmap.android.map.FMGroupInfo;
import com.fengmap.android.map.FMMap;
import com.fengmap.android.map.FMMapInfo;
import com.fengmap.android.map.FMMapUpgradeInfo;
import com.fengmap.android.map.FMMapView;
import com.fengmap.android.map.event.OnFMMapInitListener;

import java.util.ArrayList;

/**
 * Created by Baoqianyue on 2018/5/26.
 */

public class MapFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, OnFMMapInitListener {

    private FMMap mMap;
    private CheckBox mGroupControl;
    //楼层选择器
    private RadioButton[] mComeRbs;
    private Handler mHandler = new Handler();


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        loadMapLByPath(view);
        return view;
    }

    /**
     * 加载地图数据
     */
    private void loadMapLByPath(View view) {
        FMMapView mapView = view.findViewById(R.id.map_view);
        mMap = mapView.getFMMap();
        mMap.setOnFMMapInitListener(this);
        //加载离线地图数据
        String path = FileUtils.getDefaultMapPath(getContext());
        mMap.openMapByPath(path);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    @Override
    public void onMapInitSuccess(String s) {
        mMap.loadThemeByPath(FileUtils.getDefaultThemePath(getContext()));

        FMMapInfo mapInfo = mMap.getFMMapInfo();
        ArrayList<FMGroupInfo> groups = mapInfo.getGroups();

    }


    /**
     * 地图加载失败回调
     *
     * @param s
     * @param i
     */
    @Override
    public void onMapInitFailure(String s, int i) {
        Toast.makeText(getContext(), "加载失败 ： " + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onUpgrade(FMMapUpgradeInfo fmMapUpgradeInfo) {
        return false;
    }


    @Override
    public void onPause() {
        if (mMap != null) {
            mMap.onDestroy();
        }
        super.onPause();
    }
}
