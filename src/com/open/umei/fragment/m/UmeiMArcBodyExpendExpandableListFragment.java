/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.m;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.open.umei.R;
import com.open.umei.json.m.UmeMPannelHdJson;
import com.open.umei.view.ExpendExpandableListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcBodyExpendExpandableListFragment extends UmeiMPannelHdExpandableListFragment {

	public static UmeiMArcBodyExpendExpandableListFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMArcBodyExpendExpandableListFragment fragment = new UmeiMArcBodyExpendExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_arc_body_expend_expandable_listview, container, false);
		expendablelistview = (ExpendExpandableListView) view.findViewById(R.id.expendablelistview);
		return view;
	}
	@Override
	public void onCallback(UmeMPannelHdJson result) {
		// TODO Auto-generated method stub
//		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		mUmeiMPannelHdExpandableListAdapter.notifyDataSetChanged();

//		for (int i = 0; i < mUmeiMPannelHdExpandableListAdapter.getGroupCount(); i++) {
//			expendablelistview.expandGroup(i);
//		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
//		super.bindEvent();
		expendablelistview.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return false;
			}
		});
		expendablelistview.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {

			}
		});
		expendablelistview.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {

			}
		});
	}

}