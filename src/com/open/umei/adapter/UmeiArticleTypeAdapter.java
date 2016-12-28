/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午11:29:35
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
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.bean.UmeiArticleTypeBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午11:29:35
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiArticleTypeAdapter extends CommonAdapter<UmeiArticleTypeBean> {

	public UmeiArticleTypeAdapter(Context mContext, List<UmeiArticleTypeBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UmeiArticleTypeBean bean = (UmeiArticleTypeBean) getItem(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_article_type, null);
		TextView text_title = (TextView) view.findViewById(R.id.text_title);
		TextView text_title2 = (TextView) view.findViewById(R.id.text_title2);
		TextView text_time = (TextView) view.findViewById(R.id.text_time);
		text_title.setText(bean.getTitle());
		text_title2.setText(bean.getTitle2());
		text_time.setText(bean.getTime());
		return view;
	}
}
