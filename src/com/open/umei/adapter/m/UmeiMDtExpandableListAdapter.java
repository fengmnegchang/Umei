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

import com.open.umei.R;
import com.open.umei.adapter.CommonExpandableListAdapter;
import com.open.umei.bean.m.UmeiMDdBean;
import com.open.umei.bean.m.UmeiMDtBean;
import com.open.umei.view.ExpendGridView;

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
public class UmeiMDtExpandableListAdapter extends CommonExpandableListAdapter<UmeiMDtBean, UmeiMDdBean> {

	public UmeiMDtExpandableListAdapter(Context mContext, List<UmeiMDtBean> list) {
		super(mContext, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.adapter.CommonExpandableListAdapter#getChild(int,
	 * int)
	 */
	@Override
	public UmeiMDdBean getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getDdlist().get(childPosition);
	}

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
		ChildViewHolder mChildViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_umei_m_dd_child, null);
			mChildViewHolder = new ChildViewHolder();
			mChildViewHolder.gridView = (ExpendGridView) convertView.findViewById(R.id.gridView);
			convertView.setTag(mChildViewHolder);
		} else {
			mChildViewHolder = (ChildViewHolder) convertView.getTag();
		}

		mChildViewHolder.list = getGroup(groupPosition).getDdlist();
		mChildViewHolder.mUmeiMDdGridViewAdapter = new UmeiMDdGridViewAdapter(mContext, mChildViewHolder.list);
		mChildViewHolder.gridView.setAdapter(mChildViewHolder.mUmeiMDdGridViewAdapter);
		mChildViewHolder.mUmeiMDdGridViewAdapter.notifyDataSetChanged();
		return convertView;
	}

	public class ChildViewHolder {
		ExpendGridView gridView;
		UmeiMDdGridViewAdapter mUmeiMDdGridViewAdapter;
		List<UmeiMDdBean> list = new ArrayList<UmeiMDdBean>();
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
		if (getGroup(groupPosition) != null && getGroup(groupPosition).getDdlist() != null && getGroup(groupPosition).getDdlist().size() > 0) {
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
			convertView = mInflater.inflate(R.layout.adapter_umei_m_dt_group, null);
			mGroupViewHolder = new GroupViewHolder();
			mGroupViewHolder.text_dt = (TextView) convertView.findViewById(R.id.text_dt);
			convertView.setTag(mGroupViewHolder);
		} else {
			mGroupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		UmeiMDtBean bean = (UmeiMDtBean) getGroup(groupPosition);
		if (bean != null) {
			mGroupViewHolder.text_dt.setText(bean.getDtName());
		}
		return convertView;
	}

	public class GroupViewHolder {
		TextView text_dt;
	}
}