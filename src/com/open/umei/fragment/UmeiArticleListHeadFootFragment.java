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
package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.adapter.UmeiArticleAdapter;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.bean.UmeiArticleInfoBean;
import com.open.umei.fragment.m.UmeiMPannelHeadPagerFragment;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.UmeiArticleService;

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
public class UmeiArticleListHeadFootFragment extends BaseV4Fragment<UmeiArticleJson, UmeiArticleListHeadFootFragment> {
	public String url;
	public PullToRefreshListView mPullRefreshListView;
	private UmeiArticleAdapter mUmeiArticleAdapter;
	private List<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
	public int pageNo = 1;
	private View headerview;
	private View footview;
	
	private TextView txt_ArticleTitle;
	private TextView txt_time;
	private TextView txt_see;
	private TextView txt_column;
	private TextView txt_tips;
	private TextView txt_ArticleDesc;

	public static UmeiArticleListHeadFootFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiArticleListHeadFootFragment fragment = new UmeiArticleListHeadFootFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_article_head_foot_pulllistview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_article_list_head, null);
		
		txt_ArticleTitle = (TextView)headerview.findViewById(R.id.txt_ArticleTitle);
		txt_time = (TextView) headerview.findViewById(R.id.txt_time);
		txt_see = (TextView) headerview.findViewById(R.id.txt_see);
		txt_column = (TextView) headerview.findViewById(R.id.txt_column);
		txt_tips = (TextView) headerview.findViewById(R.id.txt_tips);
		txt_ArticleDesc = (TextView) headerview.findViewById(R.id.txt_ArticleDesc);
		
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_article_list_foot, null);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ListView mListView = mPullRefreshListView.getRefreshableView();
			mListView.addHeaderView(headerview);
			mListView.addFooterView(footview);
		
//		Fragment fragment = UmeiTypePagerFragment.newInstance(url, true);
		Fragment fragment = UmeiTypeGridHeadFragment.newInstance(url, true);
		Fragment lfragment = UmeiArticleTypeFragment.newInstance(url, true);
		
		getChildFragmentManager().beginTransaction().replace(R.id.layout_viewpager, fragment).commit();
		getChildFragmentManager().beginTransaction().replace(R.id.layout_listview, lfragment).commit();
	
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
		mUmeiArticleAdapter = new UmeiArticleAdapter(getActivity(), list,url);
		mPullRefreshListView.setAdapter(mUmeiArticleAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
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
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
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
		mUmeiArticleJson.setList(UmeiArticleService.parseArticle(url, pageNo));
		mUmeiArticleJson.setArticleInfos(UmeiArticleService.getArticleInfo());
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
		
		try {
			UmeiArticleInfoBean articleInfo = result.getArticleInfos();
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
