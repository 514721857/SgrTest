package com.sgr.dyg.test.greendao.helper;

import android.content.Context;

import com.sgr.dyg.test.bean.MyLocation;
import com.sgr.dyg.test.bean.Student;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23/023.
 */

public class LocationOpt {
    /**
     * 添加数据至数据库
     *
     * @param context
     * @param stu
     */
    public static void insertData(Context context, MyLocation stu) {

        DbManager.getDaoSession(context).getMyLocationDao().save(stu);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<MyLocation> queryAll(Context context) {
        QueryBuilder<MyLocation> builder = DbManager.getDaoSession(context).getMyLocationDao().queryBuilder();

        return builder.build().list();
    }

}
