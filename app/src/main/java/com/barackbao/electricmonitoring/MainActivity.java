package com.barackbao.electricmonitoring;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.barackbao.electricmonitoring.utils.FileUtils;
import com.fengmap.android.FMDevice;
import com.fengmap.android.map.FMMap;
import com.fengmap.android.map.FMMapUpgradeInfo;
import com.fengmap.android.map.FMMapView;
import com.fengmap.android.map.FMViewMode;
import com.fengmap.android.map.event.OnFMMapInitListener;
import com.fengmap.android.widget.FM3DControllerButton;
import com.fengmap.android.widget.FMZoomComponent;

public class MainActivity extends Activity implements OnFMMapInitListener {

    private static final String TAG = "MainActivity";

    private FMMapView mMapView;
    private FMMap mMap;
    private FMZoomComponent mZoomComponent;
    FM3DControllerButton mTextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        openMapByPath();
    }

    private void openMapByPath() {

        mMapView = findViewById(R.id.map_view);
        mMap = mMapView.getFMMap();

        mMap.setOnFMMapInitListener(this);
        String mapId = "nucmain";
        mMap.openMapById(mapId, true);
      /*  String path = FileUtils.getDefaultMapPath(this);
        mMap.openMapByPath(path);*/
    }


    @Override
    public void onMapInitSuccess(String s) {
//        mMap.loadThemeByPath(FileUtils.getDefaultThemePath(this));
        mMap.loadThemeById("nucmain");
        if (mZoomComponent == null) {
            initZoomComponent();
        }
        init3DControllerComponent();

    }

    /**
     * 加载2d/3d切换控件
     */
    private void init3DControllerComponent() {
        mTextBtn = new FM3DControllerButton(this);
        //设置初始状态为3D(true),设置为false为2D模式
        mTextBtn.initState(true);
        mTextBtn.measure(0, 0);
        int width = mTextBtn.getMeasuredWidth();
        //设置3D控件位置
        mMapView.addComponent(mTextBtn, FMDevice.getDeviceWidth() - 10 - width, 50);
        //2、3D点击监听
        mTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FM3DControllerButton button = (FM3DControllerButton) v;
                if (button.isSelected()) {
                    button.setSelected(false);
                    mMap.setFMViewMode(FMViewMode.FMVIEW_MODE_2D);
                } else {
                    button.setSelected(true);
                    mMap.setFMViewMode(FMViewMode.FMVIEW_MODE_3D);
                }
            }
        });
    }

    private void initZoomComponent() {
        mZoomComponent = new FMZoomComponent(this);
        mZoomComponent.measure(0, 0);
        int width = mZoomComponent.getMeasuredWidth();
        int height = mZoomComponent.getMeasuredHeight();
        //缩放控件位置
        int offsetX = FMDevice.getDeviceWidth() - width - 10;
        int offsetY = FMDevice.getDeviceHeight() - 400 - height;
        mMapView.addComponent(mZoomComponent, offsetX, offsetY);

        mZoomComponent.setOnFMZoomComponentListener(new FMZoomComponent.OnFMZoomComponentListener() {
            @Override
            public void onZoomIn(View view) {
                //地图放大
                mMap.zoomIn();
            }

            @Override
            public void onZoomOut(View view) {
                //地图缩小
                mMap.zoomOut();
            }
        });
    }

    @Override
    public void onMapInitFailure(String s, int i) {
        Log.i(TAG, "onMapInitFailure: id : " + i);
    }

    @Override
    public boolean onUpgrade(FMMapUpgradeInfo fmMapUpgradeInfo) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mMap != null) {
            mMap.onDestroy();
        }
        super.onBackPressed();
    }
}