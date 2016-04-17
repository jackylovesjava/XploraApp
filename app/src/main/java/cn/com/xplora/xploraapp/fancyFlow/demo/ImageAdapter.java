package cn.com.xplora.xploraapp.fancyFlow.demo;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.com.xplora.xploraapp.fancyFlow.FancyCoverFlowAdapter;

/**
 * @author LittleLiByte
 * 
 */
public class ImageAdapter extends FancyCoverFlowAdapter {
	private Context context;
	private List<FilmInfo> filmList;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ImageAdapter(Context context, List<FilmInfo> filmList,
			DisplayImageOptions options, ImageLoader imageLoader) {
		this.context = context;
		this.filmList = filmList;
		this.options = options;
		this.imageLoader = imageLoader;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return filmList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position % filmList.size();
	}

	@Override
	public View getCoverFlowItem(int position, View reusableView,
			ViewGroup parent) {
		ImageView imageView = (ImageView) reusableView;

		if (imageView == null) {
			imageView = new ImageView(context);
		}
		// ps.��Ӱ������߱���һ��Ϊ3��4
		imageView.setLayoutParams(new Gallery.LayoutParams(180, 240));
		// �첽����ͼƬ
		imageLoader.displayImage(filmList.get(position % filmList.size())
				.getFilmImageLink(), imageView, options);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		return imageView;
	}

}