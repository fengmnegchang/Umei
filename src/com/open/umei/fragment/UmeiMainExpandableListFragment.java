/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午9:56:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.open.umei.R;
import com.open.umei.activity.SearchResultActivity;
import com.open.umei.adapter.UmeiMainExpandableListAdapter;
import com.open.umei.adapter.UmeiMainPagerAdapter;
import com.open.umei.adapter.UmeiTypeHeightPagerAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.fragment.m.UmeiMPannelHdExpandableListFragment;
import com.open.umei.json.m.UmeMPannelHdJson;
import com.open.umei.jsoup.UmeiMainExService;
import com.open.umei.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午9:56:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class UmeiMainExpandableListFragment extends UmeiMPannelHdExpandableListFragment{
	public String url = UrlUtils.UMEI;
	private EditText edit_search;
	private Button btn_search;
	
	private ViewPager viewpager;
	private UmeiMainPagerAdapter mUmeiMainPagerAdapter;
	private List<UmeiTypeBean> listv = new ArrayList<UmeiTypeBean>();
	private UmeiMainExpandableListAdapter mUmeiMainExpandableListAdapter;
	
	public static UmeiMainExpandableListFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMainExpandableListFragment fragment = new UmeiMainExpandableListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_m_pannel_hd_expandable_listview, container, false);
		expendablelistview = (ExpandableListView) view.findViewById(R.id.expendablelistview);
		headerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_main_ex_header, null);
		edit_search = (EditText) headerview.findViewById(R.id.edit_search);
		btn_search = (Button) headerview.findViewById(R.id.btn_search);
		viewpager = (ViewPager) headerview.findViewById(R.id.viewpager);
		return view;
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
		if (expendablelistview.getHeaderViewsCount() == 0) {
			expendablelistview.addHeaderView(headerview);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		expendablelistview.setGroupIndicator(null);
		mUmeiMainExpandableListAdapter = new UmeiMainExpandableListAdapter(getActivity(), list);
		expendablelistview.setAdapter(mUmeiMainExpandableListAdapter);
		
		mUmeiMainPagerAdapter = new UmeiMainPagerAdapter(getActivity(), listv);
		viewpager.setAdapter(mUmeiMainPagerAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.umei.fragment.m.UmeiMPannelHdExpandableListFragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		edit_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchResultActivity.class);
				startActivity(intent);
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SearchResultActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeMPannelHdJson result) {
		// TODO Auto-generated method stub
		list.clear();
		list.addAll(result.getList());
		mUmeiMainExpandableListAdapter.notifyDataSetChanged();
		for (int i = 0; i < mUmeiMainExpandableListAdapter.getGroupCount(); i++) {
			expendablelistview.expandGroup(i);
		}
		
		if (result.getListv() != null && result.getListv().size() > 0) {
			listv.clear();
			listv.addAll(result.getListv());
		}
		mUmeiMainPagerAdapter.notifyDataSetChanged();
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
		mUmeMPannelHdJson.setList(UmeiMainExService.parseUmeiEx(url));
		
		ArrayList<UmeiTypeBean> listv = new ArrayList<UmeiTypeBean>();
		try {
			listv = UmeiMainExService.parseTypeList(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mUmeMPannelHdJson.setListv(listv);
		return mUmeMPannelHdJson;
	}
}
