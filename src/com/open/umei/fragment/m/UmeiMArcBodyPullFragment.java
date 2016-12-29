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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.fragment.UmeiArticleFragment;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.UmeiArticleService;

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
public class UmeiMArcBodyPullFragment extends UmeiArticleFragment {
	public static UmeiMArcBodyPullFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMArcBodyPullFragment fragment = new UmeiMArcBodyPullFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_arc_body_pulllistview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		return view;
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
		mUmeiArticleJson.setList(UmeiArticleService.parseArcBody(url, pageNo));
		return mUmeiArticleJson;
	}

}
