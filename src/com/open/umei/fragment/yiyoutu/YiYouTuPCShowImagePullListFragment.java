/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-24下午3:17:00
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.activity.yiyoutu.YiYouTuPCShowImagePullListFragmentActivity;
import com.open.umei.adapter.yiyoutu.YiYouTuPCTypeAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.yiyoutu.YiYouTuPCShowImageService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-24下午3:17:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class YiYouTuPCShowImagePullListFragment extends BaseV4Fragment<UmeiTypeJson, YiYouTuPCShowImagePullListFragment> {
	public List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	public YiYouTuPCTypeAdapter mUmeiTypeAdapter;
	public String url;
	public int pageNo = 1;
	public PullToRefreshListView mPullToRefreshListView;
	private View footview;
	private TextView txt_articlepre,txt_articlenext;

	public static YiYouTuPCShowImagePullListFragment newInstance(String url, boolean isVisibleToUser) {
		YiYouTuPCShowImagePullListFragment fragment = new YiYouTuPCShowImagePullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_yiyoutu_nav_pull_listview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_yiyoutu_pc_image_list_foot, null);
		txt_articlepre = (TextView) footview.findViewById(R.id.txt_articlepre);
		txt_articlenext = (TextView) footview.findViewById(R.id.txt_articlenext);
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
		mPullToRefreshListView.getRefreshableView().addFooterView(footview);
		YiYouTuPCNavExpendGridFragment fragment = YiYouTuPCNavExpendGridFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_expandablegridview, fragment).commit();
		
		mUmeiTypeAdapter = new YiYouTuPCTypeAdapter(getActivity(), list);
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
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
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
		txt_articlepre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YiYouTuPCShowImagePullListFragmentActivity.startYiYouTuPCShowImagePullListFragmentActivity(getActivity(), txt_articlepre.getTag().toString());
			}
		});
		txt_articlenext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YiYouTuPCShowImagePullListFragmentActivity.startYiYouTuPCShowImagePullListFragmentActivity(getActivity(), txt_articlenext.getTag().toString());
			}
		});
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT =  YiYouTuPCShowImageService.parseTypeList(url, pageNo);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		try {
			txt_articlenext.setText(result.getArticlenext());
			txt_articlenext.setTag(result.getArticlenexthref());
			
			txt_articlepre.setText(result.getArticlepre());
			txt_articlepre.setTag(result.getArticleprehref());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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

