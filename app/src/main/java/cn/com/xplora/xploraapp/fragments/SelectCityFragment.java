package cn.com.xplora.xploraapp.fragments;

/**
 * Created by jackylovesjava on 16/4/16.
 */
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.adapter.MyCoverFlowAdapter;
import cn.com.xplora.xploraapp.customUI.CoverFlowView;
import cn.com.xplora.xploraapp.model.CityModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * Created by lt on 2015/12/14.
 */
public class SelectCityFragment extends Fragment{


    private List<CityModel> cityList;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_city, null);
        final CoverFlowView<MyCoverFlowAdapter> mCoverFlowView = (CoverFlowView<MyCoverFlowAdapter>)view.findViewById(R.id.coverflow);
        final TextView cityNameTextView = (TextView)view.findViewById(R.id.select_city_cityName);
        List<Bitmap> bitmapList = new ArrayList<Bitmap>();
        if(cityList!=null){

            for (CityModel cityModel:cityList){
                Bitmap cityImage =  cityModel.getBitmap();
                bitmapList.add(cityImage);
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
                        }else{
                            cityName = cityModel.getCityNameEn();
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

        return view;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }
}
