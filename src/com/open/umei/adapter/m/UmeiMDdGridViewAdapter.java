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
import com.open.umei.activity.m.UmeiMNavIndicatorViewPagerActivity;
import com.open.umei.activity.m.UmeiMTagGridHeadFootActivity;
import com.open.umei.adapter.CommonAdapter;
import com.open.umei.bean.m.UmeiMDdBean;

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
public class UmeiMDdGridViewAdapter extends CommonAdapter<UmeiMDdBean> {

	public UmeiMDdGridViewAdapter(Context mContext, List<UmeiMDdBean> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_umei_m_dd_gridview, parent, false);
			viewHolder.text_dd = (TextView) convertView.findViewById(R.id.text_dd);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final UmeiMDdBean bean = (UmeiMDdBean) getItem(position);
		if (bean != null) {
			viewHolder.text_dd.setText(bean.getDdName());
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if(bean.getHref().contains("http://m.umei.cc/tushuotianxia")){
//					//图说天下
//				}
//				else if(bean.getHref().contains("http://m.umei.cc/gaoxiaotupian")){
//					//搞笑图片
//				}
//				else 
					if(bean.getHref().contains("http://m.umei.cc/p/gaoqing/")){
					//精品套图
					UmeiMTagGridHeadFootActivity.startUmeiMTagGridHeadFootActivity(mContext, bean.getHref());
				}else{
				    UmeiMNavIndicatorViewPagerActivity.startUmeiMNavIndicatorViewPagerActivity(mContext, bean.getHref());
				}
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView text_dd;
	}

}
