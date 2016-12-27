package com.open.umei.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.bean.UmeiNavBean;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ******************
 * 
 * @author :fengguangjing
 * @createTime: 16/11/2
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class UmeiNavAdapter extends CommonAdapter<UmeiNavBean> {
	public UmeiNavAdapter(Context mContext, List<UmeiNavBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UmeiNavBean bean = (UmeiNavBean) getItem(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_umei_nav, null);
		TextView text_type_name = (TextView) view.findViewById(R.id.text_type_name);
		text_type_name.setText(bean.getTitle());
		return view;
	}

}