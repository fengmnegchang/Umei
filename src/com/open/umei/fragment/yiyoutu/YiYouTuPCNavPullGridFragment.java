/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:28:06
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.yiyoutu;

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

import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.open.umei.R;
import com.open.umei.adapter.yiyoutu.YiYouTuTypeAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.yiyoutu.YiYouTuNavPullListService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:28:06
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class YiYouTuPCNavPullGridFragment extends BaseV4Fragment<UmeiTypeJson, YiYouTuPCNavPullGridFragment> {
	public List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	public YiYouTuTypeAdapter mUmeiTypeAdapter;
	public String url;
	public int pageNo = 1;
	public PullToRefreshHeadGridView mPullToRefreshListView;

	public static YiYouTuPCNavPullGridFragment newInstance(String url, boolean isVisibleToUser) {
		YiYouTuPCNavPullGridFragment fragment = new YiYouTuPCNavPullGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pull_grid_view, container, false);
		mPullToRefreshListView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_grid);
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
		mUmeiTypeAdapter = new YiYouTuTypeAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mUmeiTypeAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
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
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<HeaderGridView>() {
			@Override
			public void onRefresh(PullToRefreshBase<HeaderGridView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				} else if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_END) {
					pageNo++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			list = YiYouTuNavPullListService.parseTypePCList(url, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
		if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getTypeList());
			pageNo = 1;

		} else {
			if (result.getTypeList() != null && result.getTypeList().size() > 0) {
				list.addAll(result.getTypeList());
			}
		}

		mUmeiTypeAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
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
