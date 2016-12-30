/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午1:53:21
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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.HeaderFooterGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshHeaderFooterGridView;
import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMPicGridViewAdapter;
import com.open.umei.bean.m.UmeiMPicBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.m.UmeiMPicJson;
import com.open.umei.jsoup.m.UmeiMPannelHdService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午1:53:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMHeadFootGirdArticleFragment extends BaseV4Fragment<UmeiMPicJson, UmeiMHeadFootGirdArticleFragment> {
	public String url;
	public PullToRefreshHeaderFooterGridView mPullRefreshGirdView;
	public UmeiMPicGridViewAdapter mUmeiMPicGridViewAdapter;
	public List<UmeiMPicBean> list = new ArrayList<UmeiMPicBean>();
	public int pageNo = 1;

	public static UmeiMHeadFootGirdArticleFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMHeadFootGirdArticleFragment fragment = new UmeiMHeadFootGirdArticleFragment();
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
		initValues();
		bindEvent();
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
		mUmeiMPicGridViewAdapter = new UmeiMPicGridViewAdapter(getActivity(), list);
		mPullRefreshGirdView.setAdapter(mUmeiMPicGridViewAdapter);
		mPullRefreshGirdView.setMode(Mode.BOTH);
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
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshGirdView.setOnRefreshListener(new OnRefreshListener<HeaderFooterGridView>() {
			@Override
			public void onRefresh(PullToRefreshBase<HeaderFooterGridView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshGirdView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				} else if (mPullRefreshGirdView.getCurrentMode() == Mode.PULL_FROM_END) {
					pageNo++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
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
		UmeiMPicJson mUmeiMPicJson = UmeiMPannelHdService.parseMPic(url,pageNo);
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

		Log.i(TAG, "getMode ===" + mPullRefreshGirdView.getCurrentMode());
//		if (mPullRefreshGirdView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getList());
//			pageNo = 1;
//		} else {
//			if (result.getList() != null && result.getList().size() > 0) {
//				list.addAll(result.getList());
//			}
//		}
		mUmeiMPicGridViewAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshGirdView.onRefreshComplete();
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
