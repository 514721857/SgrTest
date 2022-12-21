package com.sgr.dyg.test.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.bean.entities;
import com.sgr.dyg.test.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnGetGeoCoderResultListener {

    entities mEntities;
    TextView name,time,address;
    private GeoCoder mSearch = null;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        Bundle extras = getIntent().getExtras();
        mEntities = (entities) extras.getSerializable("entities");
        name=findViewById(R.id.name);
        time=findViewById(R.id.time);
        address=findViewById(R.id.address);
        name.setText(mEntities.getEntity_name().split("_")[1]);
        try {
            time.setText(MyUtils.stampToTime(mEntities.getLatest_location().getLoc_time()+""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(new LatLng(mEntities.getLatest_location().getLatitude(),mEntities.getLatest_location().getLongitude()))
                // 设置是否返回新数据 默认值0不返回，1返回
                .newVersion(1));
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为100
        //设置地图放大的倍数
        MapStatus.Builder  builder=new MapStatus.Builder();
        builder.zoom(18f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        LatLng ll = new LatLng(mEntities.getLatest_location().getLatitude(),mEntities.getLatest_location().getLongitude());
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(update);
        //添加标记
        //定义Maker坐标点
        LatLng point =new LatLng(mEntities.getLatest_location().getLatitude(),mEntities.getLatest_location().getLongitude());
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .draggable(true)
                .flat(true)
                .alpha(0.5f);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        AddLayerAndText();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AddLayerAndText(){
        画办公楼();
        画一号楼();
        画化成1();
        画腐蚀1();
        画立冬仓库();
        画化成2();
        画环保2();
        画环保1();
        画环保11();
        画动力车间();
        画煤场();
        画印刷();
        画腐蚀4();
        画立东公司();

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画办公楼(){
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.758514, 113.338859));
        points.add(new LatLng(24.75844, 113.338754));
        points.add(new LatLng(24.758261, 113.338726));
        points.add(new LatLng(24.757759, 113.339543));

        points.add(new LatLng(24.757599, 113.339915));
        points.add(new LatLng(24.757783, 113.340034));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);

        //判断点pt是否在位置点列表mPoints构成的多边形内。
//        SpatialRelationUtil.isPolygonContainsPoint(mPoints,pt);
        //文字覆盖物位置坐标
//        24.758043079571955long113.33936565561852
        LatLng llText = new LatLng(24.758043079571955, 113.33936565561852);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("办公楼") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-30) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画腐蚀1(){
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.757804, 113.340617));
        points.add(new LatLng(24.757567, 113.340248));

        points.add(new LatLng(24.757808, 113.340046));
        points.add(new LatLng(24.758648, 113.338712));

        points.add(new LatLng(24.758966, 113.338712));
//        points.add(new LatLng(24.757783, 113.340034));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        //文字覆盖物位置坐标
//        24.758312585182424long113.33954986246074
        LatLng llText = new LatLng( 24.758312585182424, 113.33954986246074);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("腐蚀一") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-30) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画化成1(){
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
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        //文字覆盖物位置坐标
//        24.757609877314877long113.33924971136231
        LatLng llText = new LatLng( 24.757609877314877, 113.33924971136231);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("化成一") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-30) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画一号楼(){


        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.758487, 113.338693));
        points.add(new LatLng(24.758237719748426, 113.33869843683865));
//        24.758237719748426long113.33869843683865
//        points.add(new LatLng(24.758083, 113.338708));
        points.add(new LatLng(24.758027, 113.338553));

        points.add(new LatLng(24.758411, 113.338528));
        points.add(new LatLng(24.758499, 113.338544));

//        points.add(new LatLng(24.757349, 113.339176));
//        points.add(new LatLng(24.758013, 113.338604));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        //文字覆盖物位置坐标
//      24.758328570091663long113.33861989002897
        LatLng llText = new LatLng(  24.758328570091663, 113.33861989002897);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("一号楼") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-90) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画立东公司() {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(    24.754774314026903,113.34389430508517));
        points.add(new LatLng( 24.75428168290405,113.34324177278293));
        points.add(new LatLng(    24.753867182350017,113.34361282461998));

        points.add(new LatLng( 24.754330810472457,113.34427147944919));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.754356864744032,113.3437274142225);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("立东办公室") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画腐蚀4() {

        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.755083,113.342488));
        points.add(new LatLng(    24.75457,113.341698));
        points.add(new LatLng(    24.75332,113.342734));
        points.add(new LatLng( 24.753845,113.34356));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.75421502383177,113.34257316633665);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("腐蚀四") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画印刷() {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.754859,113.343973));
        points.add(new LatLng( 24.754427,113.344363));
        points.add(new LatLng(    24.755169,113.345525));

        points.add(new LatLng( 24.755623,113.345068));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.75504285503068,113.3446993076709);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("印刷厂") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);

        LatLng llText1 = new LatLng(  24.755245,113.345564);
        //构建TextOptions对象
        OverlayOptions mTextOptions1 = new TextOptions()
                .text("保安亭") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText1);
        mBaiduMap.addOverlay(mTextOptions1);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画煤场() {

        List<LatLng> points = new ArrayList<>();
        points.add(new LatLng(24.75612569365784,113.34350170905235));
        points.add(new LatLng(    24.756464593101526,113.34275805436394));
        points.add(new LatLng(    24.756266318435603,113.3425104012007));
        points.add(new LatLng( 24.75566039545605,113.34306260901886));

        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.756186054598885,113.34294805502518);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("煤场") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画动力车间() {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.756044032116893,113.34379712532613));
        points.add(new LatLng(    24.755396051187784,113.34287755785928));
        points.add(new LatLng(    24.755441333736044,113.34281483577911));
        points.add(new LatLng( 24.755605909162014,113.34300098755158));



        points.add(new LatLng( 24.756103613673854,113.34357843770965));

        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color2)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.755908473354296,113.34349401961107);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("动力") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画环保2() {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.755180529536666,113.34267413275883));
        points.add(new LatLng( 24.754407,113.343353));
        points.add(new LatLng(    24.755681,113.345025));

        points.add(new LatLng( 24.756034022195987,113.34388019602935));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.75526736484594,113.34376692391763);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("环保二") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画环保1() {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.7573020724578,113.34105828211581));
        points.add(new LatLng( 24.756344,113.340235));
        points.add(new LatLng(    24.755593,113.340834));
        points.add(new LatLng( 24.756621,113.342285));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.75651090917728,113.34112113639962);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("环保一") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画环保11() {
        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.755964066425268,113.3416333844177));
        points.add(new LatLng( 24.7557983397022,113.34137273483982));
        points.add(new LatLng(    24.754848613688953,113.34204720297208));

        points.add(new LatLng( 24.75508585090175,113.3424166776272));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.7554689744627,113.34183271625434);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("环保一") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画化成2() {

        //多边形顶点位置
        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.754615, 113.341662));
        points.add(new LatLng( 24.754821, 113.342019));
        points.add(new LatLng(    24.755771959660713,113.34133666348987));

        points.add(new LatLng( 24.755485455414956,113.34092334927115));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.755226380036596,113.34144027131651);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("化成二") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void 画立冬仓库() {

        List<LatLng> points = new ArrayList<>();

        points.add(new LatLng(24.756414636972924,113.34231804016987));
        points.add(new LatLng( 24.756005000726653,113.34168906383658));
        points.add(new LatLng(    24.75511496342452,113.34245003210604));
        points.add(new LatLng(    24.755396051187784,113.34287755785928));
        points.add(new LatLng(    24.755441333736044,113.34281483577911));
        points.add(new LatLng( 24.755605909162014,113.34300098755158));
        PolygonOptions mPolygonOptions = new PolygonOptions()
                .points(points)
                .fillColor(getColor(R.color.default_Color)) ;//填充颜色
        mBaiduMap.addOverlay(mPolygonOptions);
        LatLng llText = new LatLng(  24.75569764650458,113.34240662389973);
        //构建TextOptions对象
        OverlayOptions mTextOptions = new TextOptions()
                .text("立东生产") //文字内容

                .fontSize(24) //字号
                .fontColor(getColor(R.color.default_textColor)) //文字颜色
                .rotate(-60) //旋转角度
                .position(llText);

        //在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
            return;
        } else {
            //详细地址
            String addre = reverseGeoCodeResult.getAddress();
            address.setText(addre);
        }

    }
}