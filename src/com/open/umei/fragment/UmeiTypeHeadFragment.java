package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.open.umei.R;
import com.open.umei.adapter.UmeiTypeAdapter;
import com.open.umei.adapter.UmeiTypePagerAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.UmeiTypeListService;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ******************
 * 
 * @author :fengguangjing
 * @createTime: 16/11/18
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class UmeiTypeHeadFragment extends BaseV4Fragment<UmeiTypeJson, UmeiTypeHeadFragment> {
	public ViewPager viewpager;
	public UmeiTypePagerAdapter mUmeiTypePagerAdapter;
	public List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	public String url;

	public static UmeiTypeHeadFragment newInstance(String url,boolean isVisibleToUser) {
		UmeiTypeHeadFragment fragment = new UmeiTypeHeadFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mUmeiTypePagerAdapter = new UmeiTypePagerAdapter(getActivity(), list);
		viewpager.setAdapter(mUmeiTypePagerAdapter);
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			list = UmeiTypeListService.parseTypeList2(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		super.onCallback(result);
		list.clear();
		list.addAll(result.getTypeList());
		mUmeiTypePagerAdapter.notifyDataSetChanged();
		viewpager.setCurrentItem(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
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
