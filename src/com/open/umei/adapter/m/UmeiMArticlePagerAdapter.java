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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.adapter.CommonPagerAdapter;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.bean.db.OpenDBBean;
import com.open.umei.db.service.UmeiOpenDBService;
import com.open.umei.utils.ImageAsyncTask;
import com.open.umei.weak.WeakActivityReferenceHandler;

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
public class UmeiMArticlePagerAdapter extends CommonPagerAdapter<UmeiArticleBean> {
	private WeakActivityReferenceHandler weakReferenceHandler;

	public UmeiMArticlePagerAdapter(Context mContext, List<UmeiArticleBean> list, WeakActivityReferenceHandler weakReferenceHandler) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);
		final ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_article_pager, null);
		mViewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), mViewHolder.imageview, options, null);
			}
		}

		mViewHolder.imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// UmeiWebViewActivity.startUmeiWebViewActivity(mContext,
				// bean.getSrc());
				weakReferenceHandler.sendEmptyMessage(7000);
			}
		});
		mViewHolder.imageview.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setItems(new String[] { mContext.getResources().getString(R.string.save_picture) }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mViewHolder.imageview.setDrawingCacheEnabled(true);
						Bitmap imageBitmap = mViewHolder.imageview.getDrawingCache();
						if (imageBitmap != null) {
							new ImageAsyncTask(mContext, mViewHolder.imageview, bean.getSrc()).execute(imageBitmap);
						}

						OpenDBBean openbean = new OpenDBBean();
						openbean.setUrl(bean.getUrl());
						openbean.setType(0);
						openbean.setImgsrc(bean.getSrc());
						openbean.setTitle(bean.getAlt());
						openbean.setTypename("");
						openbean.setTime("");
						UmeiOpenDBService.insert(mContext, openbean);
					}
				});
				builder.show();
				return true;
			}
		});
		container.addView(convertView);
		return convertView;
	}

	// @Override
	// public Object instantiateItem(ViewGroup container, int position) {
	// final UmeiArticleBean bean = (UmeiArticleBean) getItem(position);
	// final ImageView imageview = new ImageView (mContext);
	// if (bean != null) {
	// if (bean.getSrc() != null && bean.getSrc().length() > 0) {
	// DisplayImageOptions options = new
	// DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
	// .cacheInMemory().cacheOnDisc().build();
	// ImageLoader.getInstance().displayImage(bean.getSrc(),imageview, options,
	// null);
	// }
	// }
	//
	// imageview.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getSrc());
	// weakReferenceHandler.sendEmptyMessage(7000);
	// }
	// });
	// imageview.setOnLongClickListener(new View.OnLongClickListener() {
	// @Override
	// public boolean onLongClick(View v) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	// builder.setItems(new
	// String[]{mContext.getResources().getString(R.string.save_picture)}, new
	// DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// imageview.setDrawingCacheEnabled(true);
	// Bitmap imageBitmap = imageview.getDrawingCache();
	// if (imageBitmap != null) {
	// new ImageAsyncTask(mContext,
	// imageview,bean.getSrc()).execute(imageBitmap);
	// }
	// }
	// });
	// builder.show();
	// return true;
	// }
	// });
	// container.addView(imageview, LayoutParams.MATCH_PARENT,
	// LayoutParams.WRAP_CONTENT);
	// return imageview;
	// }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
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
