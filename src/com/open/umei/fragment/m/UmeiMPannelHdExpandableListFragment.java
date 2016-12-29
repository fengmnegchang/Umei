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
import com.open.umei.adapter.m.UmeiMPannelHdExpandableListAdapter;
import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.m.UmeMPannelHdJson;
import com.open.umei.jsoup.m.UmeiMPannelHdService;
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
public class UmeiMPannelHdExpandableListFragment extends BaseV4Fragment<UmeMPannelHdJson, UmeiMPannelHdExpandableListFragment> {
	private String url = UrlUtils.UMEI_M;
	private ExpandableListView expendablelistview;
	private UmeiMPannelHdExpandableListAdapter mUmeiMPannelHdExpandableListAdapter;
	private List<UmeMPannelHdBean> list = new ArrayList<UmeMPannelHdBean>();

	public static UmeiMPannelHdExpandableListFragment newInstance(String url) {
		UmeiMPannelHdExpandableListFragment fragment = new UmeiMPannelHdExpandableListFragment();
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
		mUmeiMPannelHdExpandableListAdapter = new UmeiMPannelHdExpandableListAdapter(getActivity(), list);
		expendablelistview.setAdapter(mUmeiMPannelHdExpandableListAdapter);
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
	public UmeMPannelHdJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeMPannelHdJson mUmeMPannelHdJson = new UmeMPannelHdJson();
		mUmeMPannelHdJson.setList(UmeiMPannelHdService.parseUmeiMPannelHd(url));
		return mUmeMPannelHdJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeMPannelHdJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getList());
		mUmeiMPannelHdExpandableListAdapter.notifyDataSetChanged();

		for (int i = 0; i < mUmeiMPannelHdExpandableListAdapter.getGroupCount(); i++) {
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