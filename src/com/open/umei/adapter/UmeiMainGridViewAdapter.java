/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午11:07:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.activity.UmeiArticleGridHeadActivity;
import com.open.umei.activity.m.UmeiMArcBodyListHeadFootActivity;
import com.open.umei.bean.m.UmeiMPicBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午11:07:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class UmeiMainGridViewAdapter extends CommonAdapter<UmeiMPicBean> {

	public UmeiMainGridViewAdapter(Context mContext, List<UmeiMPicBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_umei_m_pic_gridview, parent, false);
			viewHolder.text_alt = (TextView) convertView.findViewById(R.id.text_alt);
			viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final UmeiMPicBean bean = (UmeiMPicBean) getItem(position);
		if (bean != null) {
			viewHolder.text_alt.setText(bean.getAlt());
			if (bean.getDataoriginal() != null && bean.getDataoriginal().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getDataoriginal(), viewHolder.imageview, options, getImageLoadingListener());
			}
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(bean.getHref().contains("http://www.umei.cc/tushuotianxia/")){
					UmeiMArcBodyListHeadFootActivity.startUmeiMArcBodyListHeadFootActivity(mContext, bean.getHref().replace("http://www.umei.cc/", "http://m.umei.cc/"));
				}else{
					UmeiArticleGridHeadActivity.startUmeiArticleGridHeadActivity(mContext, bean.getHref());
				}
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView text_alt;
		ImageView imageview;
	}

}