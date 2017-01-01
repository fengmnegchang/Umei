/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:26:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.adapter.m;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.adapter.CommonPagerAdapter;
import com.open.umei.bean.UmeiArticleBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:26:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArticlePagerAdapter extends
		CommonPagerAdapter<UmeiArticleBean> {

	public UmeiMArticlePagerAdapter(Context mContext, List<UmeiArticleBean> list) {
		super(mContext, list);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);
		ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(
				R.layout.adapter_umei_article_pager, null);
		mViewHolder.imageview = (ImageView) convertView
				.findViewById(R.id.imageview);

		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.showStubImage(R.drawable.common_v4)
						.showImageForEmptyUri(R.drawable.common_v4)
						.showImageOnFail(R.drawable.common_v4).cacheInMemory()
						.cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(),
						mViewHolder.imageview, options,
						getImageLoadingListener());
			}
		}

		// mViewHolder.imageview.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// UmeiWebViewActivity.startUmeiWebViewActivity(mContext,
		// bean.getSrc());
		// }
		// });
		container.addView(convertView);
		return convertView;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
 
    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }

	private class ViewHolder {
		ImageView imageview;
	}

}
