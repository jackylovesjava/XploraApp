package cn.com.xplora.xploraapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.com.xplora.xploraapp.fragments.BaseFragment;
import cn.com.xplora.xploraapp.fragments.FocusFragment;
import cn.com.xplora.xploraapp.fragments.LocalFragment;
import cn.com.xplora.xploraapp.fragments.PicsFragment;
import cn.com.xplora.xploraapp.fragments.ReadFragment;
import cn.com.xplora.xploraapp.fragments.TiesFragment;
import cn.com.xplora.xploraapp.fragments.UgcFragment;
import cn.com.xplora.xploraapp.fragments.VoteFragment;

public class LeftMenuFragment extends Fragment implements OnClickListener {

	private MainActivity mAct;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.menu_left_frag, null);
		mAct = (MainActivity) getActivity();
		view.findViewById(R.id.tab_news).setOnClickListener(this);
		view.findViewById(R.id.tab_read).setOnClickListener(this);
		view.findViewById(R.id.tab_local).setOnClickListener(this);
		view.findViewById(R.id.tab_ties).setOnClickListener(this);
		view.findViewById(R.id.tab_pics).setOnClickListener(this);
		view.findViewById(R.id.tab_focus).setOnClickListener(this);
		view.findViewById(R.id.tab_vote).setOnClickListener(this);
		view.findViewById(R.id.tab_ugc).setOnClickListener(this);
		view.findViewById(R.id.login_label).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		BaseFragment fragment = null;
		switch (v.getId()) {
		case R.id.tab_news:
			fragment = new HomeFragment();
			break;
		case R.id.tab_read:
			fragment = new ReadFragment();
			break;
		case R.id.tab_local:
			fragment = new LocalFragment();
			break;
		case R.id.tab_ties:
			fragment = new TiesFragment();
			break;
		case R.id.tab_pics:
			fragment = new PicsFragment();
			break;
		case R.id.tab_focus:
			fragment = new FocusFragment();
			break;
		case R.id.tab_vote:
			fragment = new VoteFragment();
			break;
		case R.id.tab_ugc:
			fragment = new UgcFragment();
			break;
		case R.id.login_label:
			Intent intent = new Intent(mAct,LoginActivity.class);
			startActivity(intent);
				break;
		default:
			break;
		}
		if(fragment!=null) {
			mAct.switchContent(fragment);
		}
		fragment = null;
	}

}
