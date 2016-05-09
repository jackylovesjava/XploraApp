package cn.com.xplora.xploraapp;



import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import cn.com.xplora.xploraapp.fragments.BaseFragment;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.AbDialogUtil;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.IConstant;
import cn.com.xplora.xploraapp.wheel.AbNumericWheelAdapter;
import cn.com.xplora.xploraapp.wheel.AbWheelUtil;
import cn.com.xplora.xploraapp.wheel.AbWheelView;

public class HomeFragment extends BaseFragment {

	private ImageView mDateFilterIV;
	private ImageView mDistrictFilterIV;
	private ImageView mHobbyFilterIV;
	private View mDateWheelView;
	private View mDistrictWheelView;
	private View mHobbyWheelView;
	private CityModel mLocateCityModel;
	private LayoutInflater mInflater;
	private TextView mPageTitleTV;
	@Override
	protected View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub

		View root = inflater.inflate(R.layout.layout_content, null);

		mDateFilterIV = (ImageView)root.findViewById(R.id.iv_date_filter);
		mDistrictFilterIV = (ImageView)root.findViewById(R.id.iv_district_filter);
		mHobbyFilterIV = (ImageView)root.findViewById(R.id.iv_hobby_filter);
		mInflater = inflater;
		mPageTitleTV = (TextView)root.findViewById(R.id.tv_load_course);
		return root;
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//初始化当前城市数据
		mLocateCityModel = new CityModel();
		int cityId = CommonUtil.getSharedPreferencesIntValue(getActivity(), IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_UUID);
		String cityName = CommonUtil.getSharedPreferencesStringValue(getActivity(), IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME);
		String cityNameEn = CommonUtil.getSharedPreferencesStringValue(getActivity(), IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME_EN);
		mLocateCityModel.setCityName(cityName);
		mLocateCityModel.setCityNameEn(cityNameEn);
		mLocateCityModel.setUuidInBack(cityId);

		//根据语言，设置标题栏文本
		if("CHN".equalsIgnoreCase(CommonUtil.getLang(getActivity()))){
			mPageTitleTV.setText(getResources().getString(R.string.title_start_event)+cityName);
		}else{
			mPageTitleTV.setText(getResources().getString(R.string.title_start_event)+cityNameEn);

		}

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

		mDateFilterIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDateWheelView = mInflater.inflate(R.layout.choose_two, null);
				initWheelTime2(mDateWheelView);
				AbDialogUtil.showDialog(mDateWheelView, Gravity.BOTTOM);
			}
		});
		mDistrictFilterIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDistrictWheelView = mInflater.inflate(R.layout.choose_one, null);
				initWheelData1(mDistrictWheelView);
				AbDialogUtil.showDialog(mDistrictWheelView,Gravity.BOTTOM);
			}
		});
		mHobbyFilterIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHobbyWheelView = mInflater.inflate(R.layout.choose_one, null);
				initWheelData1(mHobbyWheelView);
				AbDialogUtil.showDialog(mHobbyWheelView,Gravity.BOTTOM);
			}
		});

	}

	public void initWheelTime2(View mTimeView){
		final AbWheelView mWheelViewMM = (AbWheelView)mTimeView.findViewById(R.id.wheelView1);
		final AbWheelView mWheelViewHH = (AbWheelView)mTimeView.findViewById(R.id.wheelView2);
		Button okBtn = (Button)mTimeView.findViewById(R.id.okBtn);
		Button cancelBtn = (Button)mTimeView.findViewById(R.id.cancelBtn);

		mWheelViewMM.setCenterSelectDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.wheel_select));
		mWheelViewHH.setCenterSelectDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.wheel_select));
		AbWheelUtil.initWheelTimePicker2(getActivity(), mWheelViewMM, mWheelViewHH, okBtn, cancelBtn, 1, 1, true);
		//AbWheelUtil.initWheelTimePicker2(this, mText,mWheelViewMM, mWheelViewHH,okBtn,cancelBtn,16,23,false);

	}

	public void initWheelData1(View mDataView1){
		final AbWheelView mWheelView1 = (AbWheelView)mDataView1.findViewById(R.id.wheelView1);
		mWheelView1.setAdapter(new AbNumericWheelAdapter(40, 190));
		// 可循环滚动
		mWheelView1.setCyclic(true);
		// 添加文字
//		mWheelView1.setLabel(getResources().getString(R.string.data1_unit));
		// 初始化时显示的数据
		mWheelView1.setCurrentItem(40);
		mWheelView1.setValueTextSize(35);
		mWheelView1.setLabelTextSize(35);
		mWheelView1.setLabelTextColor(0x80000000);
		mWheelView1.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.wheel_select));

		Button okBtn = (Button)mDataView1.findViewById(R.id.okBtn);
		Button cancelBtn = (Button)mDataView1.findViewById(R.id.cancelBtn);
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(v.getContext());
				int index = mWheelView1.getCurrentItem();
				String val = mWheelView1.getAdapter().getItem(index);
//				mDataTextView1.setText(val);
			}

		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(v.getContext());
			}

		});
	}
}
