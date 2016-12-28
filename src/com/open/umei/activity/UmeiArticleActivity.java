/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:04:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.adapter.UmeiArticleAdapter;
import com.open.umei.adapter.UmeiArticleTypeAdapter;
import com.open.umei.adapter.UmeiTypePagerAdapter;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.bean.UmeiArticleInfoBean;
import com.open.umei.bean.UmeiArticleTypeBean;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.UmeiArticleService;
import com.open.umei.jsoup.UmeiTypeListService;
import com.open.umei.weak.WeakActivityReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:04:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiArticleActivity extends CommonFragmentActivity<UmeiArticleJson> {
	private TextView txt_ArticleTitle;
	private TextView txt_time;
	private TextView txt_see;
	private TextView txt_column;
	private TextView txt_tips;
	private TextView txt_ArticleDesc;

	private ViewPager viewpager;
	private UmeiTypePagerAdapter mUmeiTypePagerAdapter;
	private List<UmeiTypeBean> relaxarclist = new ArrayList<UmeiTypeBean>();

	private ListView listview;
	private UmeiArticleTypeAdapter mUmeiArticleTypeAdapter;
	private List<UmeiArticleTypeBean> articleTypeList = new ArrayList<UmeiArticleTypeBean>();

	private PullToRefreshListView mPullRefreshListView;
	private UmeiArticleAdapter mUmeiArticleAdapter;
	private List<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();

	private String url = "http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm";
	private int pageNo = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_article_list);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		txt_ArticleTitle = (TextView) findViewById(R.id.txt_ArticleTitle);
		txt_time = (TextView) findViewById(R.id.txt_time);
		txt_see = (TextView) findViewById(R.id.txt_see);
		txt_column = (TextView) findViewById(R.id.txt_column);
		txt_tips = (TextView) findViewById(R.id.txt_tips);
		txt_ArticleDesc = (TextView) findViewById(R.id.txt_ArticleDesc);

		viewpager = (ViewPager) findViewById(R.id.viewpager);
		listview = (ListView) findViewById(R.id.listview);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		 
	}

	@Override
	protected void initValue() {
		super.initValue();
		weakReferenceHandler = new WeakActivityReferenceHandler(this);

		mUmeiTypePagerAdapter = new UmeiTypePagerAdapter(this, relaxarclist);
		viewpager.setAdapter(mUmeiTypePagerAdapter);

		mUmeiArticleTypeAdapter = new UmeiArticleTypeAdapter(this, articleTypeList);
		listview.setAdapter(mUmeiArticleTypeAdapter);

		mUmeiArticleAdapter = new UmeiArticleAdapter(this, list);
		mPullRefreshListView.setAdapter(mUmeiArticleAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
		
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
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
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(UmeiArticleActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
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
			}
		});
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
		mUmeiArticleJson.setArticleInfos(UmeiArticleService.getArticleInfo());
		mUmeiArticleJson.setList(UmeiArticleService.parseArticle(url, pageNo));
		mUmeiArticleJson.setRelaxarc(UmeiTypeListService.relaxarc(url));
		mUmeiArticleJson.setArticleTypeList(UmeiArticleService.articleTypeList(url));
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

		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getList());
			pageNo = 1;
		} else {
			if (result.getList() != null && result.getList().size() > 0) {
				list.addAll(result.getList());
			}
		}
		mUmeiArticleAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();

		relaxarclist.clear();
		relaxarclist.addAll(result.getRelaxarc());
		mUmeiTypePagerAdapter.notifyDataSetChanged();

		articleTypeList.clear();
		articleTypeList.addAll(result.getArticleTypeList());
		mUmeiArticleTypeAdapter.notifyDataSetChanged();

		try {
			UmeiArticleInfoBean articleInfo = UmeiArticleService.getArticleInfo();
			txt_ArticleTitle.setText(articleInfo.getArticleTitle());
			txt_time.setText(articleInfo.getTime());
			txt_see.setText(articleInfo.getSee());
			txt_column.setText(articleInfo.getColumn());
			txt_tips.setText(articleInfo.getTips());
			txt_ArticleDesc.setText(articleInfo.getArticleDesc());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void startUmeiArticleActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiArticleActivity.class);
		context.startActivity(intent);
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
