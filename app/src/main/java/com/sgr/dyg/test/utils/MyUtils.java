package com.sgr.dyg.test.utils;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.baidu.mapapi.model.LatLng;
import com.sgr.dyg.test.MyApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyUtils {
    public static void myToast(Context context,String text){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);//改变弹出位置
        toast.show();
    }

    //将时间戳转换为时间
    public static String stampToTime(String s) throws Exception{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        long lt = new Long(s);
        //将时间戳转换为时间
        Date date = new Date(lt*1000);
        //将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String getAddress(LatLng point){

        if(工厂(point)){
            if(办公楼(point)){
                return "办公楼";
            }else if(一号楼(point)){
                return "一号楼";
            }else if(一号楼(point)){
                return "一号楼";
            }else if(化成1(point)){
                return "化成一";
            }else if(腐蚀1(point)){
                return "腐蚀一";
            }else if(立冬仓库(point)){
                return "立东仓库";
            }else if(化成2(point)){
                return "化成二";
            }else if(环保2(point)){
                return "环保二";
            }else if(环保1(point)){
                return "环保一";
            }else if(环保11(point)){
                return "环保一";
            }else if(动力车间(point)){
                return "动力车间";
            }else if(煤场(point)){
                return "煤场";
            }else if(印刷(point)){
                return "印刷厂";
            }else if(腐蚀4(point)){
                return "腐蚀四";
            }else if(立东公司(point)){
                return "立东公司";
            }

            else{
                return "厂内";
            }


        }else{
            return "厂外";
        }


    }

    private static boolean 工厂(LatLng point){


        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.75979766551718,113.33872190860043));
        points.add(new LatLng(24.758684884837038,113.33795045392569));
        points.add(new LatLng(24.75280363110498,113.34276680658148));
        points.add(new LatLng(24.755654241991486,113.34646823667833));
        return isPolygonContainsPoint(points,point);
    }

    /**
     * 返回一个点是否在一个多边形区域内
     *
     * @param mPoints
     *            多边形坐标点列表
     * @param point
     *            待判断点
     * @return true 多边形包含这个点,false 多边形未包含这个点。
     */
    public static boolean isPolygonContainsPoint(List<LatLng> mPoints, LatLng point) {
        int nCross = 0;
        for (int i = 0; i < mPoints.size(); i++) {
            LatLng p1 = mPoints.get(i);
            LatLng p2 = mPoints.get((i + 1) % mPoints.size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数
            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.latitude == p2.latitude) continue;
            // point 在p1p2 底部 --> 无交点
            if (point.latitude < Math.min(p1.latitude, p2.latitude)) continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.latitude >= Math.max(p1.latitude, p2.latitude)) continue;
            // 求解 point点水平线与当前p1p2边的交点的 lng 坐标
            double lng = (point.latitude - p1.latitude) * (p2.longitude - p1.longitude) / (p2.latitude - p1.latitude) + p1.longitude;
            if (lng > point.longitude) // 当x=point.x时,说明point在p1p2线段上
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }


    private static boolean 办公楼(LatLng point){
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.758514, 113.338859));
        points.add(new LatLng(24.75844, 113.338754));
        points.add(new LatLng(24.758261, 113.338726));
        points.add(new LatLng(24.757759, 113.339543));

        points.add(new LatLng(24.757599, 113.339915));
        points.add(new LatLng(24.757783, 113.340034));
        return isPolygonContainsPoint(points,point);
    }

    private static boolean 腐蚀1(LatLng point){
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.757804, 113.340617));
        points.add(new LatLng(24.757567, 113.340248));

        points.add(new LatLng(24.757808, 113.340046));
        points.add(new LatLng(24.758648, 113.338712));

        points.add(new LatLng(24.758966, 113.338712));
        return isPolygonContainsPoint(points,point);

    }

    private static boolean 化成1(LatLng point){
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.758222, 113.338695));
        points.add(new LatLng(24.757689, 113.339656));

        points.add(new LatLng(24.757495, 113.340112));
        points.add(new LatLng(24.756937, 113.339815));

        points.add(new LatLng(24.75721, 113.339556));
        points.add(new LatLng(24.757119, 113.339456));

        points.add(new LatLng(24.757349, 113.339176));
        points.add(new LatLng(24.758013, 113.338604));
        return isPolygonContainsPoint(points,point);

    }

    private static boolean 一号楼(LatLng point){


        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.758487, 113.338693));
        points.add(new LatLng(24.758237719748426, 113.33869843683865));
//        24.758237719748426long113.33869843683865
//        points.add(new LatLng(24.758083, 113.338708));
        points.add(new LatLng(24.758027, 113.338553));

        points.add(new LatLng(24.758411, 113.338528));
        points.add(new LatLng(24.758499, 113.338544));

        return isPolygonContainsPoint(points,point);

    }

    private static boolean 立东公司(LatLng point) {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(    24.754774314026903,113.34389430508517));
        points.add(new LatLng( 24.75428168290405,113.34324177278293));
        points.add(new LatLng(    24.753867182350017,113.34361282461998));

        points.add(new LatLng( 24.754330810472457,113.34427147944919));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 腐蚀4(LatLng point) {

        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.755083,113.342488));
        points.add(new LatLng(    24.75457,113.341698));
        points.add(new LatLng(    24.75332,113.342734));
        points.add(new LatLng( 24.753845,113.34356));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 印刷(LatLng point) {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.754859,113.343973));
        points.add(new LatLng( 24.754427,113.344363));
        points.add(new LatLng(    24.755169,113.345525));

        points.add(new LatLng( 24.755623,113.345068));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 煤场(LatLng point) {

        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.75612569365784,113.34350170905235));
        points.add(new LatLng(    24.756464593101526,113.34275805436394));
        points.add(new LatLng(    24.756266318435603,113.3425104012007));
        points.add(new LatLng( 24.75566039545605,113.34306260901886));

        return isPolygonContainsPoint(points,point);

    }


    private static boolean 动力车间(LatLng point) {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.756044032116893,113.34379712532613));
        points.add(new LatLng(    24.755396051187784,113.34287755785928));
        points.add(new LatLng(    24.755441333736044,113.34281483577911));
        points.add(new LatLng( 24.755605909162014,113.34300098755158));



        points.add(new LatLng( 24.756103613673854,113.34357843770965));

        return isPolygonContainsPoint(points,point);
    }


    private static boolean 环保2(LatLng point) {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.755180529536666,113.34267413275883));
        points.add(new LatLng( 24.754407,113.343353));
        points.add(new LatLng(    24.755681,113.345025));

        points.add(new LatLng( 24.756034022195987,113.34388019602935));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 环保1(LatLng point) {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.7573020724578,113.34105828211581));
        points.add(new LatLng( 24.756344,113.340235));
        points.add(new LatLng(    24.755593,113.340834));
        points.add(new LatLng( 24.756621,113.342285));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 环保11(LatLng point) {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.755964066425268,113.3416333844177));
        points.add(new LatLng( 24.7557983397022,113.34137273483982));
        points.add(new LatLng(    24.754848613688953,113.34204720297208));

        points.add(new LatLng( 24.75508585090175,113.3424166776272));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 化成2(LatLng point) {

        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.754615, 113.341662));
        points.add(new LatLng( 24.754821, 113.342019));
        points.add(new LatLng(    24.755771959660713,113.34133666348987));

        points.add(new LatLng( 24.755485455414956,113.34092334927115));
        return isPolygonContainsPoint(points,point);
    }


    private static boolean 立冬仓库(LatLng point) {

        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.756414636972924,113.34231804016987));
        points.add(new LatLng( 24.756005000726653,113.34168906383658));
        points.add(new LatLng(    24.75511496342452,113.34245003210604));
        points.add(new LatLng(    24.755396051187784,113.34287755785928));
        points.add(new LatLng(    24.755441333736044,113.34281483577911));
        points.add(new LatLng( 24.755605909162014,113.34300098755158));
        return isPolygonContainsPoint(points,point);

    }


}
