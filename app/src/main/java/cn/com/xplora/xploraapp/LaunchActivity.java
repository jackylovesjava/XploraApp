package cn.com.xplora.xploraapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import cn.com.xplora.xploraapp.asyncTasks.ActiveCitiesAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.LocateCityAsyncTask;
import cn.com.xplora.xploraapp.baidumap.MyLocationListener;
import cn.com.xplora.xploraapp.doAfter.DoAfterLocationInteface;
import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.LocateCityResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LaunchActivity extends Activity implements DoAfterLocationInteface,DoAfterResultInterface{

    private View mContentView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private LocateCityAsyncTask mLocateCityTask;
    public LocationClient mLocationClient = null;//百度地图定位类
    public BDLocationListener myListener = new MyLocationListener(LaunchActivity.this);//百度地图定位监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);
        mContentView = findViewById(R.id.fullscreen_content);
        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.setDuration(3000);
        container.startShimmerAnimation();

        mLocationClient = new LocationClient(LaunchActivity.this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
//        mLocationClient.stop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Launch Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cn.com.xplora.xploraapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Launch Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cn.com.xplora.xploraapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void doAfterLocation(BDLocation location) {
        String locateCityName = location.getCity();
        mLocateCityTask = new LocateCityAsyncTask(LaunchActivity.this,locateCityName);
        mLocateCityTask.execute();
        mLocationClient.stop();
    }

    @Override
    public void doAfterResult(BaseResult result, int taskSource) {

        if(taskSource== IConstant.TASK_SOURCE_LOCATECITY){

            LocateCityResult apiResult = (LocateCityResult)result;
            CityModel cityModel = new CityModel();
            if(apiResult!=null&&apiResult.isResult()) {

                UserModel currentUser = CommonUtil.getCurrentUser(LaunchActivity.this);
                if(currentUser!=null&&currentUser.getUuidInBack()>0){//已登录且选择了城市
                    if(currentUser.getCityId()>0) {
                        cityModel.setCityNameEn(currentUser.getCityNameEn());
                        cityModel.setCityName(currentUser.getCityName());
                        cityModel.setUuidInBack(currentUser.getCityId());
                    }else{
                        cityModel = apiResult.getCityModel();
                    }

                }else{
                    cityModel = apiResult.getCityModel();
                }

            }

            SharedPreferences.Editor editor = CommonUtil.getSharedPreferenceEditor(LaunchActivity.this);
            editor.putInt(IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_UUID,cityModel.getUuidInBack());
            editor.putString(IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME, cityModel.getCityName());
            editor.putString(IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME_EN,cityModel.getCityNameEn());
            editor.commit();
            Intent goToMain = new Intent(LaunchActivity.this,MainActivity.class);
            startActivity(goToMain);
            finish();
        }

    }
}
