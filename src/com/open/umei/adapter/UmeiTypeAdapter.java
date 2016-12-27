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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
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
public class UmeiTypeAdapter extends CommonAdapter<UmeiTypeBean> {

	public UmeiTypeAdapter(Context mContext, List<UmeiTypeBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UmeiTypeBean bean = (UmeiTypeBean) getItem(position);
		ViewHolder mViewHolder;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_type, null);
			mViewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
			mViewHolder.txt_typename = (TextView) convertView.findViewById(R.id.txt_typename);
			mViewHolder.txt_IcoList = (TextView) convertView.findViewById(R.id.txt_IcoList);
			mViewHolder.txt_IcoTime = (TextView) convertView.findViewById(R.id.txt_IcoTime);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), mViewHolder.imageview, options, getImageLoadingListener());
			}
			mViewHolder.txt_typename.setText(bean.getTypename());
			mViewHolder.txt_IcoList.setText("查看：" + bean.getIcoList() + "次");
			mViewHolder.txt_IcoTime.setText(bean.getIcoTime());
		}
		return convertView;
	}

	private class ViewHolder {
		ImageView imageview;
		TextView txt_typename;
		TextView txt_IcoList;
		TextView txt_IcoTime;
	}

}
