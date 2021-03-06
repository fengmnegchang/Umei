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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.activity.UmeiArticleActivity;
import com.open.umei.activity.UmeiNavIndicatorHorizontalViewPagerActivity;
import com.open.umei.activity.UmeiNavTagIndicatorHorizontalViewPagerActivity;
import com.open.umei.activity.m.UmeiMNavIndicatorViewPagerActivity;
import com.open.umei.bean.UmeiTypeBean;

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
public class UmeiMainPagerAdapter extends CommonPagerAdapter<UmeiTypeBean> {

	public UmeiMainPagerAdapter(Context mContext, List<UmeiTypeBean> list) {
		super(mContext, list);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final UmeiTypeBean bean = (UmeiTypeBean) getItem(position);
		ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_type_height_head, null);
		mViewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
		mViewHolder.txt_typename = (TextView) convertView.findViewById(R.id.txt_typename);

		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), mViewHolder.imageview, options, getImageLoadingListener());
			}
			mViewHolder.txt_typename.setText(bean.getTypename());
		}
		
		mViewHolder.imageview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				String title = "";
//				if(bean.getHref().contains("meinvtupian")){
//					title = "美女图片";
//				}else if(bean.getHref().contains("weimeitupian")){
//					title = "唯美图片";
//				}else if(bean.getHref().contains("bizhitupian")){
//					title = "壁纸图片";
//				}else if(bean.getHref().contains("touxiangtupian")){
//					title = "头像图片";
//				}else if(bean.getHref().contains("gaoxiaotupian")){
//					title = "搞笑图片";
//				}else if(bean.getHref().contains("tushuotianxia")){
//					title = "图说天下";
//				}else if(bean.getHref().contains("faxingtupian")){
//					title = "发型图片";
//				}else if(bean.getHref().contains("katongdongman")){
//					title = "动画图片";
//				}else if(bean.getHref().contains("tupiandaquan")){
//					title = "图片大全";
//				}else if(bean.getHref().contains("p/gaoqing")){
//					title = "国内";
//				}
//				UmeiNavIndicatorHorizontalViewPagerActivity.startUmeiNavIndicatorHorizontalViewPagerActivity(mContext, bean.getHref(),title);
 		       UmeiNavTagIndicatorHorizontalViewPagerActivity.startUmeiNavIndicatorHorizontalViewPagerActivity(mContext, bean.getHref(), "");
			
			}
		});
		container.addView(convertView);
		return convertView;
	}

	private class ViewHolder {
		ImageView imageview;
		TextView txt_typename;
	}

}
