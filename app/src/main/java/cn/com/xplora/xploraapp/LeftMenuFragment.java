package cn.com.xplora.xploraapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.com.xplora.xploraapp.fragments.BaseFragment;
import cn.com.xplora.xploraapp.fragments.FocusFragment;
import cn.com.xplora.xploraapp.fragments.LocalFragment;
import cn.com.xplora.xploraapp.fragments.PicsFragment;
import cn.com.xplora.xploraapp.fragments.ReadFragment;
import cn.com.xplora.xploraapp.fragments.TiesFragment;
import cn.com.xplora.xploraapp.fragments.UgcFragment;
import cn.com.xplora.xploraapp.fragments.VoteFragment;
import cn.com.xplora.xploraapp.utils.CommonUtil;

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

		Bundle data = this.getArguments();
		if(data!=null){
			TextView fullNameText = (TextView)view.findViewById(R.id.full_name);
			fullNameText.setText(data.getString("userName"));
			TextView hobbyText = (TextView)view.findViewById(R.id.hobbies);
			if("CHN".equalsIgnoreCase(CommonUtil.getLang(mAct))) {
				hobbyText.setText(data.getString("hobby"));
			}else{
				hobbyText.setText(data.getString("hobbyEn"));
			}
			TextView followingText = (TextView)view.findViewById(R.id.following_count);
			TextView followerText = (TextView)view.findViewById(R.id.follower_count);
			followingText.setText(String.valueOf(data.getInt("followings")));
			followerText.setText(String.valueOf(data.getInt("followers")));

			String imageName = data.getString("imageName");
			String imageUrl = data.getString("imageUrl");
			ImageView profileImage = (ImageView)view.findViewById(R.id.profile_image);

			if(imageName==null||"null".equalsIgnoreCase(imageName)||"".equalsIgnoreCase(imageName)){
				profileImage.setImageResource(R.drawable.profile_image_no);
			}else{
				ImageLoader imageLoader = ImageLoader.getInstance();
				DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
						.cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).showImageForEmptyUri(R.drawable.profile_image_no).build();
				imageLoader.displayImage(imageUrl,profileImage,displayImageOptions);
			}
			view.findViewById(R.id.profile).setVisibility(View.VISIBLE);
			view.findViewById(R.id.profile_no).setVisibility(View.GONE);
			view.findViewById(R.id.logout_btn).setVisibility(View.VISIBLE);
		}else{
			view.findViewById(R.id.profile_no).setVisibility(View.VISIBLE);
			view.findViewById(R.id.profile).setVisibility(View.GONE);
			view.findViewById(R.id.logout_btn).setVisibility(View.GONE);

		}

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
