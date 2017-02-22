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
public class SearchResultTagActivity extends CommonFragmentActivity<SearchResultJson> {
	private EditText edit_search;
	private Button btn_search;
	private String search;
	private String url = UrlUtils.UMEI_SEARCH_TAG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_search_result_tag);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		edit_search = (EditText) findViewById(R.id.edit_search);
		btn_search = (Button) findViewById(R.id.btn_search);

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
					url = UrlUtils.UMEI_SEARCH_TAG + "&q=" + URLEncoder.encode(search, "UTF-8");
					UmeiNavTagIndicatorHorizontalViewPagerActivity.startUmeiNavIndicatorHorizontalViewPagerActivity(this, url, "首页");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
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
