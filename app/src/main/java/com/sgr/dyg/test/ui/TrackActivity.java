package com.sgr.dyg.test.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.adapter.在线人数Adapter;
import com.sgr.dyg.test.bean.EntityResult;
import com.sgr.dyg.test.http.Api;
import com.sgr.dyg.test.utils.Constants;

import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.sgr.dyg.test.bean.entities;

public class TrackActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button btn;
    private EditText et;
    private List<entities> entities;//在线人数
    private 在线人数Adapter m在线人数Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        initView();
        getAllOnlineNum("");//加载全部数据
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn=findViewById(R.id.btn);
        et=findViewById(R.id.et);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllOnlineNum(et.getText().toString());
            }
        });
    }

    private void initData() {
    }

    //获得全部在线人员
    private void getAllOnlineNum(String keyword){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yingyan.baidu.com/api/v3/")
                // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        String activiteTime=null;//当前时间的前十分钟
        long now_10 = new Date(new Date().getTime() - 600000).getTime()/1000;//十分钟前
        activiteTime="active_time:"+now_10;
        retrofit.create(Api.class)
                .getOnLineNum(Constants.AK,String.valueOf(Constants.serviceId),keyword,999)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EntityResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EntityResult bean) {
                        if(bean.getStatus()==0){
                            ShowData(bean.getEntities());
                        }



                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void ShowData(List<entities> entitieList) {
        m在线人数Adapter = new 在线人数Adapter(entitieList);
        m在线人数Adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        m在线人数Adapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(m在线人数Adapter);
        m在线人数Adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Intent intent = new Intent(TrackActivity.this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("entities", entitieList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }
}