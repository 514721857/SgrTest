package com.sgr.dyg.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.king.app.updater.AppUpdater;
import com.sgr.dyg.test.adapter.HomeAdapter;
import com.sgr.dyg.test.bean.HomeBean;
import com.sgr.dyg.test.ui.GreenActivity;
import com.sgr.dyg.test.ui.LocationActivity;
import com.sgr.dyg.test.ui.PermissionActivity;
import com.sgr.dyg.test.ui.TrackActivity;
import com.sgr.dyg.test.utils.ActivityManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("手机型号："+android.os.Build.MODEL);
        System.out.println("手机系统："+ android.os.Build.VERSION.RELEASE);
        System.out.println("厂商"+android.os.Build.BRAND+ Build.DEVICE+"_"+Build.MANUFACTURER+"_"+Build.DISPLAY);

    }

    private void initData() {

        List<HomeBean> result =new ArrayList<>();

            HomeBean temp=new HomeBean();
            temp.setTitle("友盟推送");
            temp.setContent("用来测试友盟推送项目");

            HomeBean temp1=new HomeBean();
            temp1.setTitle("百度地图");
            temp1.setContent("定位显示在地图上，并且获取坐标");

            HomeBean temp2=new HomeBean();
            temp2.setTitle("GreenDao");
            temp2.setContent("GreenDao简单使用");

            HomeBean temp3=new HomeBean();
            temp3.setTitle("鹰眼服务");
            temp3.setContent("查询某人位置信息以及轨迹");

            HomeBean temp4=new HomeBean();
            temp4.setTitle("App升级");
            temp4.setContent("App升级测试");

            HomeBean temp5=new HomeBean();
            temp5.setTitle("权限管理");
            temp5.setContent("最多人用的权限框架");

            result.add(temp);
            result.add(temp1);
            result.add(temp2);
            result.add(temp3);
            result.add(temp4);
            result.add(temp5);

        mHomeAdapter = new HomeAdapter(result);
        mHomeAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mHomeAdapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(mHomeAdapter);

        mHomeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {

                if(position==0){
                    ActivityManager.goActivity(MainActivity.this, ReviceMessageActivity.class);
                }else if(position==1){
                    ActivityManager.goActivity(MainActivity.this, LocationActivity.class);
                }else if(position==2){
                    ActivityManager.goActivity(MainActivity.this, GreenActivity.class);
                }else if(position==3){
                    ActivityManager.goActivity(MainActivity.this, TrackActivity.class);
                }else if(position==4){
//                    new AppUpdater(MainActivity.this,"http://hcb.dyg.com.cn:9999/pic/apk/zshc.apk").start();
                }else if(position==5){
                    ActivityManager.goActivity(MainActivity.this, PermissionActivity.class);
                }

            }
        });
    }
}