package com.sgr.dyg.test.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.adapter.HomeAdapter;
import com.sgr.dyg.test.adapter.StudentAdapter;
import com.sgr.dyg.test.bean.Student;
import com.sgr.dyg.test.greendao.helper.StudentOpt;

import java.util.List;

public class GreenActivity extends AppCompatActivity {
    private EditText et_Name, et_age;
    private Button btn,btn_query;
    private List<Student> listsUser;
    private RecyclerView mRecyclerView;
    private StudentAdapter mStudentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green);
        initView();
        initData();
    }

    private void initData() {
        listsUser = StudentOpt.queryAll(GreenActivity.this);
        mStudentAdapter = new StudentAdapter(listsUser);
        mStudentAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft);
        mStudentAdapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(mStudentAdapter);
    }

    private void initView() {
        et_Name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        btn = (Button) findViewById(R.id.btn_insert);
        btn_query= (Button) findViewById(R.id.btn_query);
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String    name = et_Name.getText().toString().trim();
                String age = et_age.getText().toString();
                Student insertData = new Student( name, Integer.parseInt(age));
                Log.e("插入","插入"+insertData);
                StudentOpt.insertData(GreenActivity.this,insertData);
                listsUser = StudentOpt.queryAll(GreenActivity.this);
                mStudentAdapter.setNewInstance(listsUser);
                mStudentAdapter.notifyDataSetChanged();
//                adapter.setData(listsUser);

            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentOpt.deleteAllData(GreenActivity.this);
                listsUser.clear();
                System.out.println("集合数"+listsUser.size());

                mStudentAdapter.setNewInstance(listsUser);
                mStudentAdapter.notifyDataSetChanged();

//                mStudentAdapter.setNewData(listsUser);
//                mStudentAdapter.setDiffNewData(listsUser);
            }
        });
    }
}