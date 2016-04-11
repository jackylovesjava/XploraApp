package cn.com.xplora.xploraapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import cn.com.xplora.xploraapp.fragments.BaseFragment;
import cn.com.xplora.xploraapp.fragments.FocusFragment;
import cn.com.xplora.xploraapp.fragments.LocalFragment;
import cn.com.xplora.xploraapp.fragments.PicsFragment;
import cn.com.xplora.xploraapp.fragments.ReadFragment;
import cn.com.xplora.xploraapp.fragments.TiesFragment;
import cn.com.xplora.xploraapp.fragments.UgcFragment;
import cn.com.xplora.xploraapp.fragments.VoteFragment;

public class RightMenuFragment extends Fragment implements OnClickListener {

	private MainActivity mAct;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.menu_right_frag, null);
		mAct = (MainActivity) getActivity();
		view.findViewById(R.id.tab_mynews).setOnClickListener(this);
		view.findViewById(R.id.tab_myread).setOnClickListener(this);
		view.findViewById(R.id.tab_mylocal).setOnClickListener(this);
		view.findViewById(R.id.tab_myties).setOnClickListener(this);
		view.findViewById(R.id.tab_mypics).setOnClickListener(this);
		view.findViewById(R.id.tab_myfocus).setOnClickListener(this);
		view.findViewById(R.id.tab_myvote).setOnClickListener(this);
		view.findViewById(R.id.tab_myugc).setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		BaseFragment fragment = null;
		switch (v.getId()) {
		case R.id.tab_mynews:
			fragment = new HomeFragment();
			break;
		case R.id.tab_myread:
			fragment = new ReadFragment();
			break;
		case R.id.tab_mylocal:
			fragment = new LocalFragment();
			break;
		case R.id.tab_myties:
			fragment = new TiesFragment();
			break;
		case R.id.tab_mypics:
			fragment = new PicsFragment();
			break;
		case R.id.tab_myfocus:
			fragment = new FocusFragment();
			break;
		case R.id.tab_myvote:
			fragment = new VoteFragment();
			break;
		case R.id.tab_myugc:
			fragment = new UgcFragment();
			break;
		default:
			break;
		}
		mAct.switchContent(fragment);
		fragment = null;
	}

}
