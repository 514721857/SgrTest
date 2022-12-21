package com.sgr.dyg.test.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.bean.HomeBean;
import com.sgr.dyg.test.bean.Student;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StudentAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {
    public StudentAdapter(List<Student> result) {
        super(R.layout.item_test1,result);


    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Student s) {

        baseViewHolder.setText(R.id.title,s.getName());
        baseViewHolder.setText(R.id.content,String.valueOf(s.getAge()));
        baseViewHolder.setText(R.id.tv_id,String.valueOf(s.getId()));


    }


}
