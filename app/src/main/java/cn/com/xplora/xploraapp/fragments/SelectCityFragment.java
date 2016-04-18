package cn.com.xplora.xploraapp.fragments;

/**
 * Created by jackylovesjava on 16/4/16.
 */
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

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.adapter.MyCoverFlowAdapter;
import cn.com.xplora.xploraapp.customUI.CoverFlowView;

/**
 * Created by lt on 2015/12/14.
 */
public class SelectCityFragment extends Fragment{


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_city, null);
        final CoverFlowView<MyCoverFlowAdapter> mCoverFlowView = (CoverFlowView<MyCoverFlowAdapter>)view.findViewById(R.id.coverflow);

        final MyCoverFlowAdapter adapter = new MyCoverFlowAdapter(getActivity());
        mCoverFlowView.setAdapter(adapter);
        mCoverFlowView
                .setCoverFlowListener(new CoverFlowView.CoverFlowListener<MyCoverFlowAdapter>() {

                    @Override
                    public void imageOnTop(
                            CoverFlowView<MyCoverFlowAdapter> view,
                            int position, float left, float top, float right,
                            float bottom) {
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
}
