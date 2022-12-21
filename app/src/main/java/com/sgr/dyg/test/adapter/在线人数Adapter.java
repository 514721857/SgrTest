package com.sgr.dyg.test.adapter;


import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.bean.entities;
import com.sgr.dyg.test.utils.MyUtils;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class 在线人数Adapter extends BaseQuickAdapter<entities, BaseViewHolder> {


    public 在线人数Adapter(List<entities> result) {
        super(R.layout.item_online,result);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, entities 在线Bean) {
        baseViewHolder.setText(R.id.姓名,在线Bean.getEntity_name().split("_")[1]);
        try {

            baseViewHolder.setText(R.id.时间, MyUtils.stampToTime(在线Bean.getLatest_location().getLoc_time()+""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseViewHolder.setText(R.id.位置,MyUtils.getAddress(new LatLng(在线Bean.getLatest_location().getLatitude(),在线Bean.getLatest_location().getLongitude())));

    }
}