/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午4:27:22
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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.handmark.pulltorefresh.library.HeaderFooterGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshHeaderFooterGridView;
import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMPannelHdExpandableListAdapter;
import com.open.umei.adapter.m.UmeiMPicGridViewAdapter;
import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.fragment.CommonV4Fragment;
import com.open.umei.json.m.UmeiMPicJson;
import com.open.umei.jsoup.m.UmeiMPannelHdService;
import com.open.umei.view.ExpendExpandableListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午4:27:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMNavGridHeadFootFragment extends UmeiMHeadFootGirdArticleFragment {
	private View headerview;
	private View footview;
	private TextView txt_arctitle;
	private TextView txt_arcDESC;
	private ExpendExpandableListView expendablelistview ;
	public UmeiMPannelHdExpandableListAdapter mUmeiMPannelHdExpandableListAdapter;
	private List<UmeMPannelHdBean> exlist = new ArrayList<UmeMPannelHdBean>();
	
	public static UmeiMNavGridHeadFootFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMNavGridHeadFootFragment fragment = new UmeiMNavGridHeadFootFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_m_nav_head_foot_grid_view, container, false);
		mPullRefreshGirdView = (PullToRefreshHeaderFooterGridView) view.findViewById(R.id.pull_refresh_grid);
		headerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_nav_grid_head, null);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_nav_grid_foot, null);
		expendablelistview = (ExpendExpandableListView) footview.findViewById(R.id.expendablelistview);
		
		txt_arctitle = (TextView) headerview.findViewById(R.id.txt_arctitle);
		txt_arcDESC = (TextView) headerview.findViewById(R.id.txt_arcDESC);
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
		mUmeiMPicGridViewAdapter = new UmeiMPicGridViewAdapter(getActivity(), list);
		mPullRefreshGirdView.setAdapter(mUmeiMPicGridViewAdapter);
		mPullRefreshGirdView.setMode(Mode.BOTH);
		
		expendablelistview.setGroupIndicator(null);
		mUmeiMPannelHdExpandableListAdapter = new UmeiMPannelHdExpandableListAdapter(getActivity(), exlist);
		expendablelistview.setAdapter(mUmeiMPannelHdExpandableListAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		HeaderFooterGridView mListView = mPullRefreshGirdView.getRefreshableView();
		mListView.addHeaderView(headerview);
		mListView.addFooterView(footview);

//		Fragment expandfragment = UmeiMArcBodyExpendExpandableListFragment.newInstance(url, true);
//		Fragment expandfragment = CommonV4Fragment.newInstance();
//		getChildFragmentManager().beginTransaction().replace(R.id.layout_nav_head_grid, expandfragment).commit();

	}
	
	/* (non-Javadoc)
	 * @see com.open.umei.fragment.m.UmeiMHeadFootGirdArticleFragment#bindEvent()
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiMPicJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiMPicJson mUmeiMPicJson = UmeiMPannelHdService.parseMPic(url, pageNo);
		mUmeiMPicJson.setExlist(UmeiMPannelHdService.parseNavGrid(url));
		return mUmeiMPicJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiMPicJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		exlist.clear();
		exlist.addAll(result.getExlist());
		mUmeiMPannelHdExpandableListAdapter.notifyDataSetChanged();
		for (int i = 0; i < mUmeiMPannelHdExpandableListAdapter.getGroupCount(); i++) {
			expendablelistview.expandGroup(i);
		}
		try {
			UmeiMArcBodyBean arcbody = result.getmUmeiMArcBodyJson().getArcbody();
			txt_arctitle.setText(arcbody.getArctitle());
			txt_arcDESC.setText(arcbody.getArcDESC());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
