package com.sgr.dyg.test.adapter;

import android.view.View;

import androidx.annotation.NonNull;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.bean.HomeBean;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {
    public HomeAdapter(List<HomeBean> result) {
        super(R.layout.item_test,result);


    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeBean s) {

        baseViewHolder.setText(R.id.title,s.getTitle());
        baseViewHolder.setText(R.id.content,s.getContent());

    }


}
