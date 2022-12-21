package com.sgr.dyg.test.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.sgr.dyg.test.MainActivity;
import com.sgr.dyg.test.R;
import com.sgr.dyg.test.bean.MyLocation;
import com.sgr.dyg.test.bean.Student;
import com.sgr.dyg.test.greendao.helper.LocationOpt;
import com.sgr.dyg.test.greendao.helper.StudentOpt;
import com.sgr.dyg.test.utils.ActivityManager;
import com.sgr.dyg.test.utils.MyUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener, BaiduMap.OnMapClickListener {
    private TextView tvResult;
    private EditText et_name;
    private Button btLocation,bt_change,bt_save,bt_query;
    private View lin_text;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    LocationClientOption mOption;
    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private String address;
    private Double Latitude;
    private Double Longitude;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        try {
            initLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getPersimmions();
        AddLayerAndText();

    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
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

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }
    void initLocation() throws Exception {
        LocationClient.setAgreePrivacy(true);
//        SDKInitializer.setAgreePrivacy(getApplicationContext(), status);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = getDefaultLocationClientOption();
        // 签到场景 只进行一次定位返回最接近真实位置的定位结果（定位速度可能会延迟1-3s）
        option.setLocationPurpose(LocationClientOption.BDLocationPurpose.SignIn);
        mLocationClient.setLocOption(option);

    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /***
     *
     * @return DefaultLocationClientOption  默认O设置
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setCoorType( "bd09ll" ); // 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(3000); // 可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true); // 可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false); // 可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false); // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true); // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop
            mOption.setIsNeedLocationDescribe(true); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
            mOption.setIsNeedLocationPoiList(true); // 可选，默认false，设置是否需要POI结果，可以在BDLocation
            mOption.SetIgnoreCacheException(false); // 可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备，模糊
            mOption.setIsNeedAltitude(false); // 可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
            // 可选，设置首次定位时选择定位速度优先还是定位准确性优先，默认为速度优先
            mOption.setFirstLocType(LocationClientOption.FirstLocType.ACCURACY_IN_FIRST_LOC);
        }
        return mOption;
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        System.out.println("地图坐标"+latLng.latitude+"long"+latLng.longitude+ MyUtils.工厂(latLng));

    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取位置描述信息相关的结果
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            System.out.println("定位成功");
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                int tag = 1;
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());

                sb.append(SystemClock.elapsedRealtime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                Latitude =location.getLatitude();
                sb.append(location.getLatitude());
                sb.append("\nlongtitude : ");// 经度
                Longitude=location.getLongitude();
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());

                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                address=location.getAddrStr();

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                tvResult.setText(sb.toString());


                //mapView 销毁后不在处理新接收的位置
                if (location == null || mMapView == null){
                    return;
                }
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(location.getDirection()).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();

                mBaiduMap.setMyLocationData(locData);

//                logMsg(sb.toString(), tag);
            }
        }
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
//        mBaiduMap.setMapType(BaiduMap.);
//        mBaiduMap.setMyLocationEnabled(true);

        et_name = (EditText) findViewById(R.id.et_name);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btLocation = (Button) findViewById(R.id.bt_location);
        bt_change = (Button)findViewById(R.id.bt_change);
        bt_save = (Button)findViewById(R.id.bt_save);
        bt_query = (Button)findViewById(R.id.bt_query);
        lin_text =(View)findViewById(R.id.lin_text);
        bt_change.setOnClickListener(this);
        btLocation.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        init();
        configure();
        mBaiduMap.setOnMapClickListener(this);
    }
    /**
     *    设置地图放大的倍数
     */
    public  void init(){
        //设置地图放大的倍数
        MapStatus.Builder  builder=new MapStatus.Builder();
        builder.zoom(18f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 自定义内容:
     * 参数说明
     * (1)定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
     * （2）是否开启方向
     * （3）自定义定位图标 支持自定义定位图标样式，
     * （4）自定义精度圈填充颜色
     * （5）自定义精度圈边框颜色
     */
    public void configure(){
//        MyLocationConfiguration configuration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,
                null));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_location) {
            if (btLocation.getText().equals("开始定位")) {

                btLocation.setText("停止定位");
                tvResult.setText("正在定位...");
                startLocation();
            } else {


                stopLocation();
                btLocation.setText("开始定位");
                tvResult.setText("开始定位");
            }
        }else if(v.getId() == R.id.bt_change){
            if(lin_text.getVisibility()==View.VISIBLE){

                lin_text.setVisibility(View.GONE);
            }else{

                lin_text.setVisibility(View.VISIBLE);
            }

        }else if(v.getId() == R.id.bt_save){
            String    name = et_name.getText().toString().trim();
            MyLocation myLocation=new MyLocation();
            myLocation.setTitle(name);
            myLocation.setAddress(address);
            myLocation.setCreateTime(new Date());
            myLocation.setLatitude(Latitude);
            myLocation.setLongitude(Longitude);

            Log.e("插入","插入"+myLocation);
            LocationOpt.insertData(LocationActivity.this,myLocation);
        }else if(v.getId() == R.id.bt_query){
            ActivityManager.goActivity(LocationActivity.this, LocationListActivity.class);
        }
    }

    private void startLocation() {
        mLocationClient.start();
    }

    private void stopLocation() {
        mLocationClient.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}