package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.customUI.CoverFlowAdapter;


public class MyCoverFlowAdapter extends CoverFlowAdapter {

    private List<Bitmap> bitmapList;
    public MyCoverFlowAdapter(Context context,List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public Bitmap getImage(final int position) {
      return bitmapList.get(position%bitmapList.size());
    }
}
