package com.barackbao.electricmonitoring.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barackbao.electricmonitoring.R;
import com.barackbao.electricmonitoring.adapter.DetailRecyclerAdapter;
import com.barackbao.electricmonitoring.bean.DetailItem;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Wangtianrui on 2018/6/2.
 */

public class DetailFragment extends Fragment {
    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private boolean isLoad = false;

    private DetailRecyclerAdapter detailRecyclerAdapter;
    private ArrayList<DetailItem> datas = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detial_information_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (isLoad) {
            loadData();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("设备未连接")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(true)
                    .show();
        }
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test", "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Test", "onpause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void loadData() {
        datas.add(makeData("瞬时电压", "6489"));
        datas.add(makeData("瞬时电流", "2"));
        datas.add(makeData("瞬时温度", "32"));
        datas.add(makeData("瞬时功率", "12978"));
        datas.add(makeData("瞬时状态", "1"));
    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        detailRecyclerAdapter = new DetailRecyclerAdapter(getContext(), datas);
        recyclerview.setAdapter(detailRecyclerAdapter);
        recyclerview.setNestedScrollingEnabled(false);

    }

    private DetailItem makeData(String title, String number) {
        DetailItem test = new DetailItem();
        ArrayList<Entry> pointValues = new ArrayList<>();
        for (int i = 1; i < 19; i++) {
            int y = (int) (Math.random() * 20);
            pointValues.add(new Entry(i, y));
        }
        test.setLineList(pointValues);

        //饼图数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(40, "稳定"));
        entries.add(new PieEntry(20, "安全"));
        entries.add(new PieEntry(5, "危险"));
        test.setPieList(entries);

        test.setTitle(title);
        test.setValue(number);
        return test;
    }

    public void changePage() {
        datas.clear();
        detailRecyclerAdapter.notifyDataSetChanged();
        loadData();
        detailRecyclerAdapter.notifyDataSetChanged();
    }

    public void setLoad(boolean b) {
        isLoad = b;
    }

}
