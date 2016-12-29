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
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.umei.R;
import com.open.umei.adapter.UmeiArticleAdapter;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.UmeiArticleService;
import com.open.umei.view.ExpendListView;

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
public class UmeiMArcBodyExpendListFragment extends BaseV4Fragment<UmeiArticleJson, UmeiMArcBodyExpendListFragment> {
	private ExpendListView mPullRefreshListView;
	public String url;
	private UmeiArticleAdapter mUmeiArticleAdapter;
	private List<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();

	public static UmeiMArcBodyExpendListFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMArcBodyExpendListFragment fragment = new UmeiMArcBodyExpendListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_arc_body_expend_istview, container, false);
		mPullRefreshListView = (ExpendListView) view.findViewById(R.id.pull_refresh_list);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.UmeiArticleFragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
	}

	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mUmeiArticleAdapter = new UmeiArticleAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mUmeiArticleAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
		mUmeiArticleJson.setList(UmeiArticleService.parseAllArcBody(url));
		return mUmeiArticleJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiArticleJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);

		list.clear();
		list.addAll(result.getList());

		mUmeiArticleAdapter.notifyDataSetChanged();
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
