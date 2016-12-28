/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:31:25
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
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.activity.UmeiArticleFragmentActivity;
import com.open.umei.bean.SearchResultBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-11-25下午4:31:25
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SearchResultAdapter extends CommonAdapter<SearchResultBean> {

	public SearchResultAdapter(Context mContext, List<SearchResultBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SearchResultBean bean = (SearchResultBean) getItem(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_search_result, null);
		TextView text_target = (TextView) view.findViewById(R.id.text_target);
		TextView text_content = (TextView) view.findViewById(R.id.text_content);
		text_target.setText(bean.getTarget());
		text_content.setText(bean.getContent());
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UmeiArticleFragmentActivity.startUmeiArticleActivity(mContext, bean.getHref());				
			}
		});
		return view;
	}

}
