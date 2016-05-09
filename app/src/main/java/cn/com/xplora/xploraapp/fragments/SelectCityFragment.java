package cn.com.xplora.xploraapp.fragments;

/**
 * Created by jackylovesjava on 16/4/16.
 */
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.adapter.MyCoverFlowAdapter;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterConfirm;
import cn.com.xplora.xploraapp.asyncTasks.DoAfterSelectCity;
import cn.com.xplora.xploraapp.customUI.CoverFlowView;
import cn.com.xplora.xploraapp.customUI.CustomProgressDialog;
import cn.com.xplora.xploraapp.db.UserDAO;
import cn.com.xplora.xploraapp.db.XploraDBHelper;
import cn.com.xplora.xploraapp.json.BaseJsonResolver;
import cn.com.xplora.xploraapp.json.BaseResult;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.model.HobbyModel;
import cn.com.xplora.xploraapp.model.UserModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;
import cn.com.xplora.xploraapp.utils.HttpUtil;
import cn.com.xplora.xploraapp.utils.IConstant;

/**
 * Created by lt on 2015/12/14.
 */
public class SelectCityFragment extends Fragment{


    private List<CityModel> cityList;
    private UserModel mCurrentUser;
    private Button mConfirmBtn;
    private CustomProgressDialog mLoadingDialog;
    private CoverFlowView<MyCoverFlowAdapter> mCoverFlowView;
    private CityModel mSelectedCity;
    public UserModel getmCurrentUser() {
        return mCurrentUser;
    }

    public void setmCurrentUser(UserModel mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }

    public CustomProgressDialog getmLoadingDialog() {
        return mLoadingDialog;
    }

    public void setmLoadingDialog(CustomProgressDialog mLoadingDialog) {
        this.mLoadingDialog = mLoadingDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_city, null);
         mCoverFlowView = (CoverFlowView<MyCoverFlowAdapter>)view.findViewById(R.id.coverflow);
        final TextView cityNameTextView = (TextView)view.findViewById(R.id.select_city_cityName);
        mConfirmBtn = (Button)view.findViewById(R.id.btn_select_city_confirm);
        List<Bitmap> bitmapList = new ArrayList<Bitmap>();
        int userSelectedCityPosition = 0;
        if(cityList!=null){

            for (int i=0;i<cityList.size();i++)
            {
                CityModel cityModel=cityList.get(i);
                Bitmap cityImage =  cityModel.getBitmap();
                bitmapList.add(cityImage);
                if(cityModel.getUuidInBack()==mCurrentUser.getCityId()){
                    userSelectedCityPosition = i;
                }
            }

        }
        final MyCoverFlowAdapter adapter = new MyCoverFlowAdapter(getActivity(),bitmapList);
        mCoverFlowView.setAdapter(adapter);
        mCoverFlowView
                .setCoverFlowListener(new CoverFlowView.CoverFlowListener<MyCoverFlowAdapter>() {

                    @Override
                    public void imageOnTop(
                            CoverFlowView<MyCoverFlowAdapter> view,
                            int position, float left, float top, float right,
                            float bottom) {

                        Log.i("XPLORA", "CITY POSITION: " + position);
                        CityModel cityModel = cityList.get(position);
                        String cityName = "";
                        if ("CHN".equalsIgnoreCase(CommonUtil.getLang(getActivity()))) {

                            cityName = cityModel.getCityName();
                        } else {
                            cityName = cityModel.getCityNameEn().toUpperCase();
                        }
                        cityNameTextView.setText(cityName);
                    }

                    @Override
                    public void topImageClicked(
                            CoverFlowView<MyCoverFlowAdapter> view, int position) {
                    }

                    @Override
                    public void invalidationCompleted() {

                    }
                });
        mCoverFlowView.setSelection(userSelectedCityPosition);

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new UpdateCityTask().execute();
            }
        });
        return view;
    }

    private class UpdateCityTask extends AsyncTask<Void, Void, BaseResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            int position = mCoverFlowView.getTopImageIndex();
            mSelectedCity = cityList.get(position);
            mLoadingDialog.show();
        }

        @Override
        protected BaseResult doInBackground(Void... params) {
            // Simulates a background job.

            String lang = CommonUtil.getLang(getActivity());

            HttpUtil http = new HttpUtil("http://www.xplora.com.cn/admin/api/profile/modify_city");
            String result = http.doGet("userId=" + mCurrentUser.getUuidInBack() + "&cityId=" + mSelectedCity.getUuidInBack()+"&lang="+lang);
            BaseResult apiResult = BaseJsonResolver.parseSimpleResult(result);
            return apiResult;
        }

        @Override
        protected void onPostExecute(BaseResult result) {
            super.onPostExecute(result);
            mLoadingDialog.hide();

            mCurrentUser.setCityId(mSelectedCity.getUuidInBack());
            mCurrentUser.setCityName(mSelectedCity.getCityName());
            mCurrentUser.setCityNameEn(mSelectedCity.getCityNameEn());

            XploraDBHelper dbHelper = new XploraDBHelper(getActivity(),"XPLORA");
            UserDAO userDAO = new UserDAO(dbHelper);

            userDAO.updateUser(mCurrentUser);

            SharedPreferences.Editor editor = CommonUtil.getSharedPreferenceEditor(getActivity());
            editor.putInt(IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_UUID,mSelectedCity.getUuidInBack());
            editor.putString(IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME, mSelectedCity.getCityName());
            editor.putString(IConstant.SHARE_PREFERENCE_KEY_LOCATECITY_NAME_EN,mSelectedCity.getCityNameEn());
            editor.commit();

            ((DoAfterSelectCity)getActivity()).doAfterSelectCity();
        }
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }
}
