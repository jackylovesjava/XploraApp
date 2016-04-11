package cn.com.xplora.xploraapp.fragments;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import cn.com.xplora.xploraapp.MainActivity;
import cn.com.xplora.xploraapp.R;

public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected Context ct;
	/** SlidingMenu对象 */
	protected SlidingMenu sm;
	public View rootView;
	protected Activity MenuChangeHome;
	/** 左菜单按钮 */
	private ImageButton leftMenuBtn;
	/** 右菜单按钮 */
	private ImageButton rightMenuBtn;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		sm = ((MainActivity) getActivity()).getSlidingMenu();
		initData(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ct = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = initView(inflater);
		leftMenuBtn = (ImageButton) rootView.findViewById(R.id.ib_menu_left);
		rightMenuBtn = (ImageButton) rootView.findViewById(R.id.ib_menu_right);
		leftMenuBtn.setOnClickListener(this);
		rightMenuBtn.setOnClickListener(this);
		setListener();
		return rootView;
	}

	public View getRootView() {
		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ib_menu_left: // 点击左边的按钮，左菜单收放
			sm.toggle();
			break;
		case R.id.ib_menu_right: // 点击右边按钮，右菜单缩放
			sm.showSecondaryMenu();
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化UI
	 * 
	 * @param inflater
	 * @return
	 */
	protected abstract View initView(LayoutInflater inflater);

	/**
	 * 初始化数据
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void initData(Bundle savedInstanceState);

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

}
