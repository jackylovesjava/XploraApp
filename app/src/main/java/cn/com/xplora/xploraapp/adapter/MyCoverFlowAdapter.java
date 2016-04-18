package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.customUI.CoverFlowAdapter;


public class MyCoverFlowAdapter extends CoverFlowAdapter {

    private boolean dataChanged;

    public MyCoverFlowAdapter(Context context) {

        image1 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.img01);

        image2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.img02);

        image3 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.img03);
    }

    public void changeBitmap() {
        dataChanged = true;

        notifyDataSetChanged();
    }

    private Bitmap image1 = null;

    private Bitmap image2 = null;

    private Bitmap image3 = null;

    @Override
    public int getCount() {
        return dataChanged ? 3 : 8;
    }

    @Override
    public Bitmap getImage(final int position) {
        if(position%3==0){
            return image1;
        }else if(position%3==1){
            return image2;
        }else{
            return image3;
        }
//        return (dataChanged && position == 0) ? image2 : image1;
    }
}
