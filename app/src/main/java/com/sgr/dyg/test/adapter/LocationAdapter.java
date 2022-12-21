package com.sgr.dyg.test.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.bean.MyLocation;
import com.sgr.dyg.test.bean.Student;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LocationAdapter extends BaseQuickAdapter<MyLocation, BaseViewHolder> {
    public LocationAdapter(List<MyLocation> result) {
        super(R.layout.item_test2,result);


    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MyLocation s) {

        baseViewHolder.setText(R.id.title,s.getTitle());
        baseViewHolder.setText(R.id.content,String.valueOf(s.getAddress()));
        baseViewHolder.setText(R.id.lat,String.valueOf(s.getLatitude()));

        baseViewHolder.setText(R.id.lon,String.valueOf(s.getLongitude()));
        baseViewHolder.setText(R.id.time,getDateStr(s.getCreateTime()));
//        baseViewHolder.setText(R.id.lat,String.valueOf(s.getLatitude()));


    }

    public static String getDateStr(Date date) {

           String  format = "MM-dd HH:mm:ss";

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
