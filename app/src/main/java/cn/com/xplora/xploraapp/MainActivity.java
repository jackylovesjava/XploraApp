package cn.com.xplora.xploraapp;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;

import cn.com.xplora.xploraapp.fragments.SettingFragment;

public class MainActivity extends SlidingFragmentActivity {

	/** 侧滑菜单 */
	private SlidingMenu sm;
	/** 左边菜单 */
	private LeftMenuFragment mLeftMenu;
	/** 右边菜单 */
	private RightMenuFragment mRightMenu;
	/** 主界面 */
	private HomeFragment mHomeFragment;
	/** 动画类 */
	private CanvasTransformer mTransformer;
	/** 保存Fragment的状态 */
	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initAnimation();
		sm = getSlidingMenu();
		setContentView(R.layout.content_frame);
		setBehindContentView(R.layout.menu_left_frag);
		sm.setSecondaryMenu(R.layout.menu_right_frag);


		if (savedInstanceState == null) {
			mLeftMenu = new LeftMenuFragment();
			mRightMenu = new RightMenuFragment();
			mHomeFragment = new HomeFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.menu_left_frag, mLeftMenu, "Left").commit();
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mHomeFragment, "Home").commit();
			getSupportFragmentManager().beginTransaction().replace(R.id.menu_right_frag, mRightMenu, "Right").commit();
		}
		sm.setSecondaryShadowDrawable(R.drawable.rightshadow); // 设置右边菜单的阴影
		sm.setShadowDrawable(R.drawable.shadow); // 设置阴影图片
		sm.setShadowWidthRes(R.dimen.shadow_width); // 设置阴影图片的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset); // 显示主界面的宽度
		sm.setFadeDegree(0f); // SlidingMenu滑动时的渐变程度
		sm.setBehindScrollScale(0f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); // 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
		sm.setMode(SlidingMenu.LEFT_RIGHT); // 设置菜单同时兼具左右滑动
		sm.setBehindCanvasTransformer(mTransformer); // 设置动画

		Intent intent = getIntent();
		if("SETTING".equalsIgnoreCase(intent.getStringExtra("DESTINY_FRAGMENT"))){

			SettingFragment settingFragment = new SettingFragment();
			settingFragment.setmContext(MainActivity.this);
			switchContent(settingFragment);

		}
	}

	/**
	 * 初始化菜单滑动的效果动画
	 */
	private void initAnimation() {
		mTransformer = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.scale(interp.getInterpolation(percentOpen), interp.getInterpolation(percentOpen), canvas.getWidth() / 2, canvas.getHeight() / 2);
				// canvas.translate(0, canvas.getHeight() * (1 -
				// interp.getInterpolation(percentOpen))); //平移动画
				// canvas.scale(percentOpen, 1, 0, 0); //缩放动画
			}

		};
	}

	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};

	/**
	 * 切换到主界面
	 * 
	 * @param fragment
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	/**
	 * 保存Fragment的状态
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if(mContent!=null) {
			getSupportFragmentManager().putFragment(outState, "Home", mContent);
		}
	}

}
