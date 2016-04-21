package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.model.TrendsetterModel;
import cn.com.xplora.xploraapp.utils.CommonUtil;

/**
 * Created by jackylovesjava on 16/4/19.
 */
public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<TrendsetterModel> mDatas;
    private Context mContext;
    public GalleryAdapter(Context context, List<TrendsetterModel> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.trendsetter_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position)
    {
//        viewHolder.mImg.setImageResource(mDatas.get(i));
        ImageLoader imageLoader = CommonUtil.getImageLoader(mContext);
        DisplayImageOptions displayImageOptions = CommonUtil.getDefaultImageLoadOption();
        TrendsetterModel trendsetterModel = mDatas.get(position);
        imageLoader.displayImage(trendsetterModel.getImageUrl(),viewHolder.mImg);
    }

}

