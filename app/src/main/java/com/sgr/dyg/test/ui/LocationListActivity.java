package com.sgr.dyg.test.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.adapter.LocationAdapter;
import com.sgr.dyg.test.adapter.StudentAdapter;
import com.sgr.dyg.test.bean.MyLocation;
import com.sgr.dyg.test.bean.Student;
import com.sgr.dyg.test.greendao.helper.LocationOpt;
import com.sgr.dyg.test.greendao.helper.StudentOpt;

import java.util.List;

public class LocationListActivity extends AppCompatActivity {

    private List<MyLocation> lists;
    private RecyclerView mRecyclerView;
    private LocationAdapter mLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initData() {
        lists = LocationOpt.queryAll(LocationListActivity.this);
        if(lists!=null){
            for (int i = 0; i <lists.size() ; i++) {
                System.out.println("地点"+lists.get(i).getTitle()+"lat"+lists.get(i).getLatitude()+"lon"+lists.get(i).getLongitude());

            }
        }

        mLocationAdapter = new LocationAdapter(lists);
        mLocationAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mLocationAdapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(mLocationAdapter);
    }
}