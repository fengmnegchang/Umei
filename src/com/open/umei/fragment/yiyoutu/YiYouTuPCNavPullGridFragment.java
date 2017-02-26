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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.handmark.pulltorefresh.library.HeaderFooterGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshHeaderFooterGridView;
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
public class YiYouTuPCNavPullGridFragment extends BaseV4Fragment<UmeiTypeJson, YiYouTuPCNavPullGridFragment> implements OnClickListener {
	public List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	public YiYouTuTypeAdapter mUmeiTypeAdapter;
	public String url;
	public int pageNo = 1;
	public PullToRefreshHeaderFooterGridView mPullToRefreshListView;
	private View footview;
	public Button text_fisrt;
	public Button text_pre;
	public EditText edit_current;
	public Button text_current;
	public Button text_next;
	public Button text_last;
	public boolean isautomatic;
	public int maxPageNo;
	
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
		View view = inflater.inflate(R.layout.fragment_common_pull_head_foot_grid_view, container, false);
		mPullToRefreshListView = (PullToRefreshHeaderFooterGridView) view.findViewById(R.id.pull_refresh_grid);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_yiyoutu_pc_grid_page_foot, null);
		text_fisrt = (Button) footview.findViewById(R.id.text_fisrt);
		text_pre = (Button) footview.findViewById(R.id.text_pre);
		edit_current = (EditText) footview.findViewById(R.id.edit_current);
		text_current = (Button) footview.findViewById(R.id.text_current);
		text_next = (Button) footview.findViewById(R.id.text_next);
		text_last = (Button) footview.findViewById(R.id.text_last);
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
		mUmeiTypeAdapter = new YiYouTuTypeAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mUmeiTypeAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
		edit_current.setText("" + pageNo);
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
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<HeaderFooterGridView>() {
			@Override
			public void onRefresh(PullToRefreshBase<HeaderFooterGridView> refreshView) {
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
		text_fisrt.setOnClickListener(this);
		text_pre.setOnClickListener(this);
		text_current.setOnClickListener(this);
		text_next.setOnClickListener(this);
		text_last.setOnClickListener(this);
		edit_current.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT =   YiYouTuNavPullListService.parseTypePCList(url, pageNo,1);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
		if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			if (isautomatic) {
				if (result.getTypeList() != null && result.getTypeList().size() > 0) {
					list.addAll(result.getTypeList());
				}
			} else {
				list.clear();
				list.addAll(result.getTypeList());
				pageNo = 1;
			}

		} else {
			if (result.getTypeList() != null && result.getTypeList().size() > 0) {
				list.addAll(result.getTypeList());
			}
		}

		mUmeiTypeAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
		maxPageNo = result.getMaxpageno();
		edit_current.setText("" + pageNo);
		isautomatic = false;
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_fisrt:
			pageNo = 1;
			break;
		case R.id.text_last:
			pageNo = maxPageNo;
			break;
		case R.id.text_pre:
			if (pageNo <= 1) {
				pageNo = 1;
			}
			pageNo = pageNo - 1;
			break;
		case R.id.text_next:
			pageNo = pageNo + 1;
			if (pageNo >= maxPageNo) {
				pageNo = maxPageNo;
			}
			break;
		case R.id.text_current:
			String pageNostr = edit_current.getText().toString();
			if (pageNostr != null) {
				pageNo = Integer.parseInt(pageNostr.replace(" ", ""));
			}
			break;
		default:
			break;
		}
		isautomatic = true;
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
	}
}
