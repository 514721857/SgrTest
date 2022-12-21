package com.sgr.dyg.test.umeng;


import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.sgr.dyg.test.ReviceMessageActivity;
import com.sgr.dyg.test.utils.ActivityManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.api.UPushRegisterCallback;
import com.umeng.message.entity.UMessage;

import org.android.agoo.honor.HonorRegister;
import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.json.JSONException;


/**
 * PushSDK集成帮助类
 */
public class PushHelper {

    public static final String TAG = "PushHelper";

    /**
     * 预初始化
     */
    public static void preInit(Context context) {
        UMConfigure.preInit(context, PushConstants.APP_KEY, PushConstants.CHANNEL);
    }

    /**
     * 初始化
     */
    public static void init(final Context context) {
        // 基础组件包提供的初始化函数，应用配置信息：http://message.umeng.com/list/apps
        // 参数一：上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：发布渠道名称；
        // 参数四：设备类型，UMConfigure.DEVICE_TYPE_PHONE：手机；UMConfigure.DEVICE_TYPE_BOX：盒子；默认为手机
        // 参数五：Push推送业务的secret，填写Umeng Message Secret对应信息
        UMConfigure.init(context, PushConstants.APP_KEY, PushConstants.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE, PushConstants.MESSAGE_SECRET);

        //获取推送实例
        PushAgent pushAgent = PushAgent.getInstance(context);
        //服务端控制声音
        pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

//客户端允许呼吸灯点亮
        pushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//客户端禁止振动
        pushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        //修改为您app/src/main/AndroidManifest.xml中package值
        pushAgent.setResourcePackageName("com.sgr.dyg.test");

        //推送设置
        pushSetting(context);

        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new UPushRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "deviceToken: " + deviceToken);
//                //获取deviceToken可通过接口：
//                PushAgent.getInstance(context).getRegistrationId();
//                //可设置别名，推送时使用别名推送
//                String alias = "123456";
//                String type = "uid";
//                PushAgent.getInstance(context).setAlias(alias, type, new UPushAliasCallback() {
//                    @Override
//                    public void onMessage(boolean success, String message) {
//                        Log.i(TAG, "setAlias " + success + " msg:" + message);
//                    }
//                });
            }

            @Override
            public void onFailure(String errCode, String errDesc) {
                Log.e(TAG, "register failed! " + "code:" + errCode + ",desc:" + errDesc);
            }
        });

        if (UMUtils.isMainProgress(context)) {
            registerDevicePush(context);
        }
    }

    /**
     * 注册设备推送通道（小米、华为等设备的推送）
     */
    private static void registerDevicePush(Context context) {
        //小米推送：填写您在小米后台APP对应的xiaomi id和key
        MiPushRegistar.register(context, PushConstants.MI_ID, PushConstants.MI_KEY);
        //华为推送：注意华为推送的初始化参数在AndroidManifest.xml中配置
        HuaWeiRegister.register(context.getApplicationContext());
        //魅族推送：填写您在魅族后台APP对应的app id和key
        MeizuRegister.register(context, PushConstants.MEI_ZU_ID, PushConstants.MEI_ZU_KEY);
        //OPPO推送：填写您在OPPO后台APP对应的app key和secret
        OppoRegister.register(context, PushConstants.OPPO_KEY, PushConstants.OPPO_SECRET);
        //vivo推送：注意vivo推送的初始化参数在AndroidManifest.xml中配置
        VivoRegister.register(context);

        //荣耀推送：注意荣耀推送的初始化参数在AndroidManifest.xml中配置
        HonorRegister.register(context);

        //谷歌fcm推送
//        FCMRegister.register(context);
    }

    /**
     * 推送设置
     */
    private static void pushSetting(Context context) {
        PushAgent pushAgent = PushAgent.getInstance(context);

        //设置通知栏显示通知的最大个数（0～10），0：不限制个数
        pushAgent.setDisplayNotificationNumber(0);

        //推送消息处理
        UmengMessageHandler msgHandler = new UmengMessageHandler() {
            //处理通知栏消息
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                super.dealWithNotificationMessage(context, msg);
                showVibrator(context);
                Log.i(TAG, "notification receiver:" + msg.getRaw().toString());
            }

            //自定义通知样式，此方法可以修改通知样式等
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                return super.getNotification(context, msg);
            }

            //处理透传消息
            @Override
            public void dealWithCustomMessage(Context context, UMessage msg) {
                super.dealWithCustomMessage(context, msg);
                Log.i(TAG, "custom receiver:" + msg.getRaw().toString());

            }
        };
        pushAgent.setMessageHandler(msgHandler);

        //推送消息点击处理
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void openActivity(Context context, UMessage msg) {
//                super.openActivity(context, msg);
                //点击通知栏，指定跳转到app
                Log.i(TAG, "click openActivity: " + msg.getRaw().toString());
                try {
                    //跳转到指定activity
                    if( msg.getRaw().getJSONObject("body").opt("after_open").toString().equals("go_activity")){
                        Bundle bundle = new Bundle();
                        bundle.putString("title" , msg.getRaw().getJSONObject("body").opt("title").toString());
                        bundle.putString("text" , msg.getRaw().getJSONObject("body").opt("text").toString());
                        bundle.putString("msg_id" , msg.getExtra().get("msg_id"));
                        Intent intent1=new Intent();
                        intent1.setClassName(context,msg.getRaw().getJSONObject("body").opt("activity").toString());
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        intent1.putExtras(bundle);
                        context.startActivity(intent1);
//                        ActivityManager.goActivity(context, ReviceMessageActivity.class,bundle);
                    }

//                    System.out.println("消息内容"+ msg.getRaw().getJSONObject("body").opt("title"));
//                    System.out.println("额外内容"+ msg.getExtra());
//                    System.out.println("额外内容2"+ msg.getExtra().get("msg_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                Log.i(TAG, "click launchApp: " + msg.getRaw().toString());
            }

            @Override
            public void dismissNotification(Context context, UMessage msg) {
                super.dismissNotification(context, msg);
                Log.i(TAG, "click dismissNotification: " + msg.getRaw().toString());
            }
        };
        pushAgent.setNotificationClickHandler(notificationClickHandler);

        //通过Service自定义接收并处理消息
//        pushAgent.setPushIntentServiceClass(MyCustomMessageService.class);

    }

   public static void showVibrator(Context context){
        Vibrator mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        long[] patern = {500,500,500};
        AudioAttributes audioAttributes = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audioAttributes = new AudioAttributes.Builder()

                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)

                    .setUsage(AudioAttributes.USAGE_ALARM) //key

                    .build();

            mVibrator.vibrate(patern, -1, audioAttributes);

        }else {

            mVibrator.vibrate(patern, -1);

        }
    }

}
