package cn.com.xplora.xploraapp;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

import cn.com.xplora.xploraapp.fragments.MyFragment;
import cn.com.xplora.xploraapp.fragments.SelectCityFragment;
import cn.com.xplora.xploraapp.fragments.TabFragmentAdapter;

public class NewUserGuideActivity extends FragmentActivity {

    private String[] mTitles = new String[]{"城市","标签","朋友"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_guide);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < mTitles.length; i++) {
            if(i==0){
                SelectCityFragment selectCityFragment = new SelectCityFragment();
                Bundle bundle = new Bundle();
                bundle.putString("text",mTitles[i]);
                selectCityFragment.setArguments(bundle);
                fragments.add(selectCityFragment);
            }else {
                Fragment fragment = new MyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("text", mTitles[i]);
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        }
        viewPager.setAdapter(new TabFragmentAdapter(fragments, mTitles, getSupportFragmentManager(), this));
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        // 将ViewPager和TabLayout绑定
        tablayout.setupWithViewPager(viewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        tablayout.setTabTextColors(Color.GRAY, Color.WHITE);
        }

}
