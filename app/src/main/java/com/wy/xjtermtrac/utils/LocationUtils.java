package com.wy.xjtermtrac.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocationUtils {

    private Context context;
    private ProgressDialog progressDialog;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationClientOption;
    private LocationInterface locationInterface;
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged (AMapLocation aMapLocation) {
            removeDialog();
            if (aMapLocation != null) {
                locationInterface.getLocInfo(aMapLocation);
                stopLocation();
            } else {
                Toast.makeText(context, "定位失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    public LocationUtils (Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        initLocation();
    }

    public void getLocationInfo (LocationInterface locationInterface) {
        this.locationInterface = locationInterface;
    }

    private void initLocation () {
        locationClient = new AMapLocationClient(context);
        locationClient.setLocationOption(getLocationClientOption());
        locationClient.setLocationListener(locationListener);
    }

    private AMapLocationClientOption getLocationClientOption () {
        locationClientOption = new AMapLocationClientOption();
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        locationClientOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        locationClientOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        locationClientOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        locationClientOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        locationClientOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        locationClientOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        locationClientOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        locationClientOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationClientOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        locationClientOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return locationClientOption;
    }

    public void startLocation () {
        if (null != locationClient) {
            showDialog("正在获取当前位置,请稍后...");
            locationClient.startLocation();
        }
    }

    public void stopLocation () {
        if (null != locationClient) {
            locationClient.stopLocation();
        }
    }

    public void showDialog (String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    public void removeDialog () {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
