package cn.com.xplora.xploraapp;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import cn.com.xplora.xploraapp.fragments.BaseFragment;

public class HomeFragment extends BaseFragment {

	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_content, null);
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

}
