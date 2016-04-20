package cn.com.xplora.xploraapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.asyncTasks.ActiveCitiesAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.ActiveHobbysAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterResultInterface;
import cn.com.xplora.xploraapp.asyncTasks.TrendsetterPageAsyncTask;
import cn.com.xplora.xploraapp.asyncTasks.UserPageAsyncTask;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.fragments.ExplorePeopleFragment;
import cn.com.xplora.xploraapp.fragments.MyFragment;
import cn.com.xplora.xploraapp.fragments.SelectCityFragment;
import cn.com.xplora.xploraapp.fragments.SelectHobbyFragment;
import cn.com.xplora.xploraapp.fragments.TabFragmentAdapter;
import cn.com.xplora.xploraapp.json.ActiveCitiesResult;
import cn.com.xplora.xploraapp.json.ActiveHobbysResult;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.json.TrendsetterPageResult;
import cn.com.xplora.xploraapp.json.UserPageResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.IConstant;

public class NewUserGuideActivity extends FragmentActivity implements DoAfterResultInterface {

    private ActiveCitiesAsyncTask mActiveCitiesAsyncTask;
    private ActiveHobbysAsyncTask mActiveHobbysAsyncTask;
    private TrendsetterPageAsyncTask mTrendsetterPageAsyncTask;
    private UserPageAsyncTask mUserPageAsyncTask;

    private SelectCityFragment mSelectCityFragment;
    private SelectHobbyFragment mSelectHobbyFragment;
    private ExplorePeopleFragment mExplorePeopleFragment;

    private int mTrendsetterCurrentPage = 1;
    private int mUserCurrentPage = 1;
    private int mHobbyCurrentPage = 1;
    private int mUserId = 1;

    private CustomProgressDialog mLoadingDialog;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private List<Fragment> mFragments = new ArrayList<Fragment>();;

    public int getmTrendsetterCurrentPage() {
        return mTrendsetterCurrentPage;
    }

    public void setmTrendsetterCurrentPage(int mTrendsetterCurrentPage) {
        this.mTrendsetterCurrentPage = mTrendsetterCurrentPage;
    }

    public int getmUserCurrentPage() {
        return mUserCurrentPage;
    }

    public void setmUserCurrentPage(int mUserCurrentPage) {
        this.mUserCurrentPage = mUserCurrentPage;
    }

    public int getmHobbyCurrentPage() {
        return mHobbyCurrentPage;
    }

    public void setmHobbyCurrentPage(int mHobbyCurrentPage) {
        this.mHobbyCurrentPage = mHobbyCurrentPage;
    }

    private String[] mTitles = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //====================初始化视图========================
        setContentView(R.layout.activity_new_user_guide);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTitles = new String[]{
                getResources().getString(R.string.tab_title_city),
                getResources().getString(R.string.tab_title_tag),
                getResources().getString(R.string.tab_title_friend)
        };
        //==================得到当前登陆用户ID============================
        XploraDBHelper dbHelper = new XploraDBHelper(NewUserGuideActivity.this,"XPLORA");
        UserDAO userDAO = new UserDAO(dbHelper);
        UserModel currentUser = userDAO.getLastLoginUser();
        if(currentUser!=null&&currentUser.getUuidInBack()>0){
            mUserId = currentUser.getUuidInBack();
        }else{//未登陆，跳转到登陆页面
            Intent goToLoginIntent = new Intent(NewUserGuideActivity.this,LoginActivity.class);
            startActivity(goToLoginIntent);
        }
        //==================加载Loading动画=====================================
        mLoadingDialog = new CustomProgressDialog(NewUserGuideActivity.this,getString(R.string.loading),R.anim.loading_frame);
        mLoadingDialog.show();
        //=================获取后台城市列表=======================================
        mActiveCitiesAsyncTask = new ActiveCitiesAsyncTask(NewUserGuideActivity.this);
        mActiveCitiesAsyncTask.execute();
        //=================获取后台兴趣列表==========================================
        mActiveHobbysAsyncTask = new ActiveHobbysAsyncTask(NewUserGuideActivity.this);
        mActiveHobbysAsyncTask.execute();
        //=================获取与当前用户兴趣相似的名人列表==============================
        mTrendsetterPageAsyncTask = new TrendsetterPageAsyncTask(NewUserGuideActivity.this,mTrendsetterCurrentPage,mUserId);
        mTrendsetterPageAsyncTask.execute();
        //=================获取与当前用户兴趣相似的用户列表==============================
        mUserPageAsyncTask = new UserPageAsyncTask(NewUserGuideActivity.this,mUserCurrentPage,mUserId);
        mUserPageAsyncTask.execute();

        }

    @Override
    public void doAfterResult(BaseResult result,int taskSource) {

        //==================调用API出错=====================================
        if(!result.isResult()){
            mLoadingDialog.hide();
            mActiveCitiesAsyncTask=null;
            mActiveHobbysAsyncTask=null;
            mTrendsetterPageAsyncTask = null;
            mUserPageAsyncTask=null;
            String errorMsg = result.getErrorMsg();
            Toast.makeText(NewUserGuideActivity.this,errorMsg,Toast.LENGTH_SHORT);
        }else{//=============================未出错，依据任务来源初始化各Fragment=========
            if (taskSource==IConstant.TASK_SOURCE_ACTIVECITIES){
                    if(mSelectCityFragment==null) {
                        mSelectCityFragment = new SelectCityFragment();
                    }
                    ActiveCitiesResult activeCitiesResult = (ActiveCitiesResult)result;
                    List<CityModel> cityList = activeCitiesResult.getCityList();
                    mSelectCityFragment.setCityList(cityList);
                    mFragments.add(mSelectCityFragment);
                    mActiveCitiesAsyncTask = null;

                }
            else if (taskSource==IConstant.TASK_SOURCE_ACTIVEHOBBYS){
                    if(mSelectHobbyFragment==null) {
                        mSelectHobbyFragment = new SelectHobbyFragment();
                        mSelectHobbyFragment.setContext(NewUserGuideActivity.this);
                    }
                    ActiveHobbysResult activeHobbysResult = (ActiveHobbysResult)result;
                    List<HobbyModel> hobbyList = activeHobbysResult.getHobbyList();
                    mSelectHobbyFragment.setHobbyList(hobbyList);
                    mFragments.add(mSelectHobbyFragment);
                    mActiveHobbysAsyncTask = null;
                }
            else if (taskSource==IConstant.TASK_SOURCE_TRENDSETTERPAGE){
                    if(mExplorePeopleFragment==null) {
                        mExplorePeopleFragment = new ExplorePeopleFragment();
                        mExplorePeopleFragment.setContext(NewUserGuideActivity.this);
                    }
                    TrendsetterPageResult trendsetterPageResult = (TrendsetterPageResult)result;
                    List<TrendsetterModel> trendsetterList = trendsetterPageResult.getTrendsetterList();
                    mExplorePeopleFragment.setmTrendsetterList(trendsetterList);
                    if(mUserPageAsyncTask==null){//表示读取用户的任务已经结束，可以将fragment添加到fragmentList中
                        mFragments.add(mExplorePeopleFragment);
                    }
                    mTrendsetterPageAsyncTask = null;
                }
            else if(taskSource==IConstant.TASK_SOURCE_USERPAGE){
                    if(mExplorePeopleFragment==null) {
                        mExplorePeopleFragment = new ExplorePeopleFragment();
                    }
                    UserPageResult userPageResult = (UserPageResult)result;
                    List<UserModel> userList = userPageResult.getUserList();
                    mExplorePeopleFragment.setmUserList(userList);
                    if(mTrendsetterPageAsyncTask==null){//表示读取名人的任务已经结束，可以将fragment添加到fragmentList中
                        mFragments.add(mExplorePeopleFragment);
                    }
                    mUserPageAsyncTask = null;
                }
                if(mActiveHobbysAsyncTask==null&&mUserPageAsyncTask==null&&mActiveCitiesAsyncTask==null&&mTrendsetterPageAsyncTask==null){
                    mLoadingDialog.hide();
                    mViewPager.setAdapter(new TabFragmentAdapter(mFragments, mTitles, getSupportFragmentManager(), this));
                    // 将ViewPager和TabLayout绑定
                    mTabLayout.setupWithViewPager(mViewPager);
                    // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
                    mTabLayout.setTabTextColors(Color.GRAY, Color.WHITE);
                }
        }
    }
}
