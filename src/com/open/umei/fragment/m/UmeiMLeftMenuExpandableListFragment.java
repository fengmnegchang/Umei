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

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMDtExpandableListAdapter;
import com.open.umei.bean.m.UmeiMDtBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.m.UmeiMdtJson;
import com.open.umei.jsoup.m.UmeiMNavService;
import com.open.umei.utils.UrlUtils;

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
public class UmeiMLeftMenuExpandableListFragment extends BaseV4Fragment<UmeiMdtJson, UmeiMLeftMenuExpandableListFragment> {
	private String url = UrlUtils.UMEI_M;
	private ExpandableListView expendablelistview;
	private UmeiMDtExpandableListAdapter mUmeiMDtExpandableListAdapter;
	private List<UmeiMDtBean> list = new ArrayList<UmeiMDtBean>();

	public static UmeiMLeftMenuExpandableListFragment newInstance(String url) {
		UmeiMLeftMenuExpandableListFragment fragment = new UmeiMLeftMenuExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(true);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_expandable_listview, container, false);
		expendablelistview = (ExpandableListView) view.findViewById(R.id.expendablelistview);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		expendablelistview.setGroupIndicator(null);
		mUmeiMDtExpandableListAdapter = new UmeiMDtExpandableListAdapter(getActivity(), list);
		expendablelistview.setAdapter(mUmeiMDtExpandableListAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		expendablelistview.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#call()
	 */
	@Override
	public UmeiMdtJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiMdtJson mUmeiMdtJson = new UmeiMdtJson();
		mUmeiMdtJson.setDtlist(UmeiMNavService.parseUmeiMNav(url));
		return mUmeiMdtJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiMdtJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getDtlist());
		mUmeiMDtExpandableListAdapter.notifyDataSetChanged();

		for (int i = 0; i < mUmeiMDtExpandableListAdapter.getGroupCount(); i++) {
			expendablelistview.expandGroup(i);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}
}