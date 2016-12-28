/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午3:09:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.adapter.SearchResultAdapter;
import com.open.umei.bean.SearchResultBean;
import com.open.umei.json.SearchResultJson;
import com.open.umei.jsoup.UmeiSearchResultService;
import com.open.umei.utils.UrlUtils;
import com.open.umei.weak.WeakActivityReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午3:09:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SearchResultActivity extends CommonFragmentActivity<SearchResultJson> {
	private PullToRefreshListView mPullRefreshListView;
	private SearchResultAdapter mSearchResultAdapter;
	private List<SearchResultBean> list = new ArrayList<SearchResultBean>();
	private int pageNo = 0;
	private EditText edit_search;
	private Button btn_search;
	private String search;
	private String url = UrlUtils.UMEI_SEARCH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_search_result);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		edit_search = (EditText) findViewById(R.id.edit_search);
		btn_search = (Button) findViewById(R.id.btn_search);

		mSearchResultAdapter = new SearchResultAdapter(this, list);
		mPullRefreshListView.setAdapter(mSearchResultAdapter);

		mPullRefreshListView.setMode(Mode.BOTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.CommonFragmentActivity#bindEvent()
	 */
	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		btn_search.setOnClickListener(this);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(SearchResultActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				} else if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_END) {
					pageNo++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch ((int)(Math.random()*(3))) {
				case 0:
					UmeiArticleFragmentActivity.startUmeiArticleActivity(SearchResultActivity.this, list.get((int) id).getHref());
					break;
				case 1:
					UmeiTypeListActivity.startUmeiTypeListActivity(SearchResultActivity.this, list.get((int) id).getHref());
					break;
				case 2:
					UmeiWebViewActivity.startUmeiWebViewActivity(SearchResultActivity.this, list.get((int) id).getHref());
					break;
				default:
					break;
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.CommonFragmentActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_search:
			search = edit_search.getText().toString();
			if (search.length() > 0) {
				try {
					// http://zhannei.baidu.com/cse/search?q=%E6%A1%83%E5%AD%90&click=1&entry=1&s=6545247087170373156&nsid=
					url = UrlUtils.UMEI_SEARCH + "&q=" + URLEncoder.encode(search, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public SearchResultJson call() throws Exception {
		SearchResultJson mCommonT = new SearchResultJson();
		ArrayList<SearchResultBean> list = new ArrayList<SearchResultBean>();// 导航大图
		try {
			// 解析网络标签
			list = UmeiSearchResultService.parseSearch(url, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(SearchResultJson result) {
		super.onCallback(result);
		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getList());
			pageNo = 0;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}

		mSearchResultAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
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
