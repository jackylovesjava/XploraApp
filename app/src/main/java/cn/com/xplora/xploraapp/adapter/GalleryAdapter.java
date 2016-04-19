package cn.com.xplora.xploraapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.xplora.xploraapp.R;
import cn.com.xplora.xploraapp.model.TrendsetterModel;

/**
 * Created by jackylovesjava on 16/4/19.
 */
public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{

    private LayoutInflater mInflater;
    private List<TrendsetterModel> mDatas;

    public GalleryAdapter(Context context, List<TrendsetterModel> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
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
        View view = mInflater.inflate(R.layout.frag_explore_people,
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
//        viewHolder.mImg.setImageResource(mDatas.get(i));
    }

}

