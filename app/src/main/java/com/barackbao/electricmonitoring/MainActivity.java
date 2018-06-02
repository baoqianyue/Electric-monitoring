package com.barackbao.electricmonitoring;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.barackbao.electricmonitoring.utils.FileUtils;
import com.barackbao.electricmonitoring.utils.RequestPermission;
import com.barackbao.electricmonitoring.utils.ViewHelper;
import com.barackbao.electricmonitoring.widget.FabTagLayout;
import com.barackbao.electricmonitoring.widget.FloatingActionButtonPlus;
import com.fengmap.android.FMDevice;
import com.fengmap.android.map.FMMap;
import com.fengmap.android.map.FMMapUpgradeInfo;
import com.fengmap.android.map.FMMapView;
import com.fengmap.android.map.FMPickMapCoordResult;
import com.fengmap.android.map.FMViewMode;
import com.fengmap.android.map.event.OnFMMapClickListener;
import com.fengmap.android.map.event.OnFMMapInitListener;
import com.fengmap.android.map.event.OnFMNodeListener;
import com.fengmap.android.map.geometry.FMMapCoord;
import com.fengmap.android.map.layer.FMModelLayer;
import com.fengmap.android.map.marker.FMModel;
import com.fengmap.android.map.marker.FMNode;
import com.fengmap.android.widget.FM3DControllerButton;
import com.fengmap.android.widget.FMZoomComponent;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnFMMapInitListener {

    private static final String TAG = "MainActivity";

    private FMModelLayer mModelLayer;
    private FMModel mClickModel;
    private FMMapView mMapView;
    private FMMap mMap;
    private FMZoomComponent mZoomComponent;
    private FloatingActionButtonPlus mFabGroup;
    FM3DControllerButton mTextBtn;

    private OnFMNodeListener mOnModelClickListener = new OnFMNodeListener() {
        @Override
        public boolean onClick(FMNode fmNode) {
            if (mClickModel != null) {
                mClickModel.setSelected(false);
            }
            FMModel model = (FMModel) fmNode;
            mClickModel = model;
            model.setSelected(true);
            mMap.updateMap();
            Log.i(TAG, "model name is " + model.getName());
            //房间名
            String roomName = model.getName();
            //界面跳转,自己补全
            Intent intent = new Intent(MainActivity.this, DetailInformationActivity.class);
            intent.putExtra("roomName", roomName);
            startActivity(intent);

            FMMapCoord centerMapCoord = model.getCenterMapCoord();
            return true;
        }

        @Override
        public boolean onLongPress(FMNode fmNode) {
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //申请权限
        requestPermiss();
        openMapByPath();
        fabGroupEvents();
    }

    private void fabGroupEvents() {
        mFabGroup.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                Toast.makeText(MainActivity.this, tagView.getTag() + "未实现", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermiss() {
        RequestPermission.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new RequestPermission.OnPermissionsRequestListener() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(List<String> deniedList) {
                        Toast.makeText(MainActivity.this, "拒绝将无法使用本应用", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void openMapByPath() {

        mMapView = findViewById(R.id.map_view);
        mFabGroup = findViewById(R.id.FabPlus);
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
        int groupId = mMap.getFocusGroupId();

        //模型图层
        mModelLayer = mMap.getFMLayerProxy().getFMModelLayer(groupId);
        mModelLayer.setOnFMNodeListener(mOnModelClickListener);
        mMap.addLayer(mModelLayer);

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