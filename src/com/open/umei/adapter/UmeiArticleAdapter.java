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
package com.open.umei.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.open.umei.R;
import com.open.umei.activity.m.UmeiMActicleViewPagerActivity;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.json.UmeiArticleJson;

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
public class UmeiArticleAdapter extends CommonAdapter<UmeiArticleBean> {
	public String url;

	public UmeiArticleAdapter(Context mContext, List<UmeiArticleBean> list, String url) {
		super(mContext, list);
		this.url = url;
	}

	// public UmeiArticleAdapter(Context mContext, List<UmeiArticleBean> list )
	// {
	// super(mContext, list);
	// }
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);
	// ViewHolder mViewHolder;
	// if (convertView == null) {
	// mViewHolder = new ViewHolder();
	// convertView =
	// LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_article,
	// null);
	// mViewHolder.imageview = (ImageView)
	// convertView.findViewById(R.id.imageview);
	//
	// convertView.setTag(mViewHolder);
	// } else {
	// mViewHolder = (ViewHolder) convertView.getTag();
	// }
	//
	// if (bean != null) {
	// if (bean.getSrc() != null && bean.getSrc().length() > 0) {
	// DisplayImageOptions options = new
	// DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
	// .cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
	// ImageLoader.getInstance().displayImage(bean.getSrc(),
	// mViewHolder.imageview, options, getImageLoadingListener());
	// }
	//
	// mViewHolder.imageview.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
	// UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
	// mUmeiArticleJson.setList(list);
	// UmeiMActicleViewPagerActivity.startUmeiMActicleViewPagerActivity(mContext,
	// mUmeiArticleJson,url);
	// }
	// });
	// }
	// return convertView;
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);

		View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_article, null);
		ImageView imageview = (ImageView) view.findViewById(R.id.imageview);

		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), imageview, options, getImageLoadingListener());
			}

			imageview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// UmeiWebViewActivity.startUmeiWebViewActivity(mContext,
					// bean.getSrc());
					UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
					mUmeiArticleJson.setList(list);
					UmeiMActicleViewPagerActivity.startUmeiMActicleViewPagerActivity(mContext, mUmeiArticleJson, url,position);
				}
			});
		}
		return view;
	}

	private class ViewHolder {
		ImageView imageview;
	}

}
