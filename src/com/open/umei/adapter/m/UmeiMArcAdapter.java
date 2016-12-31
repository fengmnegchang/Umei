/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午11:06:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.adapter.m;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.activity.UmeiWebViewActivity;
import com.open.umei.activity.m.UmeiMArcBodyListHeadFootActivity;
import com.open.umei.activity.m.UmeiMNavGridHeadFootActivity;
import com.open.umei.activity.m.UmeiMNavIndicatorViewPagerActivity;
import com.open.umei.activity.m.UmeiMTagGridHeadFootActivity;
import com.open.umei.adapter.CommonAdapter;
import com.open.umei.bean.m.UmeiMArcBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午11:06:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcAdapter extends CommonAdapter<UmeiMArcBean> {

	public UmeiMArcAdapter(Context mContext, List<UmeiMArcBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final UmeiMArcBean bean = (UmeiMArcBean) getItem(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_m_arc_type, null);
		TextView text_title = (TextView) view.findViewById(R.id.text_title);
		TextView text_title2 = (TextView) view.findViewById(R.id.text_title2);
		TextView text_time = (TextView) view.findViewById(R.id.text_time);
		text_title.setText(bean.getTitle());
		text_title2.setText(bean.getTitle2());
		text_time.setText(bean.getArctime());

		text_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//友情链接
				if(bean.getHref().endsWith("http://m.mmonly.cc/")||bean.getHref().endsWith("http://m.27270.com/")
						||bean.getHref().endsWith("http://m.ituba.cc")||bean.getHref().endsWith("http://m.uumnt.com/")	
					||bean.getHref().endsWith("http://m.uumeitu.com")||bean.getHref().endsWith("http://m.tesetu.com")
					||bean.getHref().endsWith("http://m.aitaotu.com")
						){
					UmeiWebViewActivity.startUmeiWebViewActivity(mContext, bean.getHref());
				}else if(bean.getHref().contains("http://m.umei.cc/p/gaoqing/cn/")
						|| bean.getHref().contains("http://m.umei.cc/tags/")){
					//标签云 国内
					UmeiMTagGridHeadFootActivity.startUmeiMTagGridHeadFootActivity(mContext, bean.getHref());
				}else{
					UmeiMNavIndicatorViewPagerActivity.startUmeiMNavIndicatorViewPagerActivity(mContext, bean.getHref());
				}
			}
		});
		text_title2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmeiMArcBodyListHeadFootActivity.startUmeiMArcBodyListHeadFootActivity(mContext, bean.getHref2());
			}
		});
		return view;
	}
}