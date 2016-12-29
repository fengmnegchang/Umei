/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:51:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.adapter.m;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.adapter.CommonAdapter;
import com.open.umei.bean.m.UmeiMArcTagBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:51:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcTagGridViewAdapter extends CommonAdapter<UmeiMArcTagBean> {

	public UmeiMArcTagGridViewAdapter(Context mContext, List<UmeiMArcTagBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_umei_m_arc_tag_gridview, parent, false);
			viewHolder.text_tagname = (TextView) convertView.findViewById(R.id.text_tagname);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final UmeiMArcTagBean bean = (UmeiMArcTagBean) getItem(position);
		if (bean != null) {
			viewHolder.text_tagname.setText(bean.getTagname());
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView text_tagname;
	}

}
