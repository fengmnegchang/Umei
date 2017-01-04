/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:59:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.adapter.m;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView.OnTagClickListener;

import com.open.umei.R;
import com.open.umei.activity.m.UmeiMTagGridHeadFootActivity;
import com.open.umei.adapter.CommonExpandableListAdapter;
import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.bean.m.UmeiMArcBean;
import com.open.umei.bean.m.UmeiMPicBean;
import com.open.umei.view.ExpendGridView;
import com.open.umei.view.ExpendListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:59:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMPannelHdExpandableListAdapter extends CommonExpandableListAdapter<UmeMPannelHdBean, UmeiMPicBean> {

	public UmeiMPannelHdExpandableListAdapter(Context mContext, List<UmeMPannelHdBean> list) {
		super(mContext, list);
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// com.open.tencenttv.adapter.CommonExpandableListAdapter#getChild(int,
	// * int)
	// */
	// @Override
	// public UmeiMDdBean getChild(int groupPosition, int childPosition) {
	// // TODO Auto-generated method stub
	// return list.get(groupPosition).getDdlist().get(childPosition);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getChildView(int,
	 * int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ChildViewHolder mChildViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_umei_m_pic_child, null);
			mChildViewHolder = new ChildViewHolder();
			mChildViewHolder.gridView = (ExpendGridView) convertView.findViewById(R.id.gridView);
			mChildViewHolder.listview = (ExpendListView) convertView.findViewById(R.id.listview);
			mChildViewHolder.tagContainerLayout = (TagContainerLayout) convertView.findViewById(R.id.tagcontainerLayout);
			convertView.setTag(mChildViewHolder);
		} else {
			mChildViewHolder = (ChildViewHolder) convertView.getTag();
		}
		
		mChildViewHolder.list = getGroup(groupPosition).getPiclist();
		mChildViewHolder.mUmeiMPicGridViewAdapter = new UmeiMPicGridViewAdapter(mContext, mChildViewHolder.list);
		mChildViewHolder.gridView.setAdapter(mChildViewHolder.mUmeiMPicGridViewAdapter);
		mChildViewHolder.mUmeiMPicGridViewAdapter.notifyDataSetChanged();

		mChildViewHolder.listtag.clear();
		mChildViewHolder.listtaglink.clear();
		mChildViewHolder.tagContainerLayout.setVisibility(View.GONE);
		mChildViewHolder.listview.setVisibility(View.VISIBLE);
		mChildViewHolder.arclist.clear();
		if(getGroup(groupPosition).getPannelhdname().endsWith("标签云")){
			mChildViewHolder.tagContainerLayout.setVisibility(View.VISIBLE);
			mChildViewHolder.listview.setVisibility(View.GONE);
			for(UmeiMArcBean bean:getGroup(groupPosition).getArclist()){
				mChildViewHolder.listtag.add(bean.getTitle());
				mChildViewHolder.listtaglink.add(bean.getHref());
			}
			mChildViewHolder.tagContainerLayout.setTags(mChildViewHolder.listtag);
			mChildViewHolder.tagContainerLayout.setOnTagClickListener(new OnTagClickListener() {
				@Override
				public void onTagLongClick(int position, String text) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onTagCrossClick(int position) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void onTagClick(int position, String text) {
					UmeiMTagGridHeadFootActivity.startUmeiMTagGridHeadFootActivity(mContext, mChildViewHolder.listtaglink.get(position));
				}
			});
		}else{
			mChildViewHolder.arclist = getGroup(groupPosition).getArclist();
			mChildViewHolder.mUmeiMArcAdapter = new UmeiMArcAdapter(mContext, mChildViewHolder.arclist);
			mChildViewHolder.listview.setAdapter(mChildViewHolder.mUmeiMArcAdapter);
			mChildViewHolder.mUmeiMArcAdapter.notifyDataSetChanged();
		}
		return convertView;
	}

	public class ChildViewHolder {
		ExpendGridView gridView;
		UmeiMPicGridViewAdapter mUmeiMPicGridViewAdapter;
		List<UmeiMPicBean> list = new ArrayList<UmeiMPicBean>();

		ExpendListView listview;
		UmeiMArcAdapter mUmeiMArcAdapter;
		List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
		
		TagContainerLayout tagContainerLayout;
		List<String> listtag = new ArrayList<String>();
		List<String> listtaglink = new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getChildrenCount
	 * (int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (getGroup(groupPosition) != null
				&& ((getGroup(groupPosition).getPiclist() != null && getGroup(groupPosition).getPiclist().size() > 0) || (getGroup(groupPosition).getArclist() != null && getGroup(groupPosition)
						.getArclist().size() > 0))) {
			return 1;
		} else {
			return 0;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getGroupView(int,
	 * boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean arg1, View convertView, ViewGroup parent) {
		GroupViewHolder mGroupViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_umei_m_pannel_hd_group, null);
			mGroupViewHolder = new GroupViewHolder();
			mGroupViewHolder.text_hd = (TextView) convertView.findViewById(R.id.text_hd);
			convertView.setTag(mGroupViewHolder);
		} else {
			mGroupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		UmeMPannelHdBean bean = (UmeMPannelHdBean) getGroup(groupPosition);
		if (bean != null) {
			mGroupViewHolder.text_hd.setText(bean.getPannelhdname());
		}
		return convertView;
	}

	public class GroupViewHolder {
		TextView text_hd;
	}
}