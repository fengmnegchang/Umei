/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23上午10:33:39
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
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.activity.UmeiArticleGridHeadActivity;
import com.open.umei.activity.UmeiWebViewActivity;
import com.open.umei.adapter.db.OpenDBListTypeAdapter;
import com.open.umei.bean.db.OpenDBBean;
import com.open.umei.db.service.UmeiOpenDBService;
import com.open.umei.jsoup.db.OpenDBJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23上午10:33:39
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiNavOpenDBTypeFragment extends BaseV4Fragment<OpenDBJson, UmeiNavOpenDBTypeFragment> implements OnItemClickListener {
	private String url;
	private int type;
	private PullToRefreshListView mPullRefreshListView;
	private OpenDBListTypeAdapter mOpenDBListAdapter;
	private List<OpenDBBean> list = new ArrayList<OpenDBBean>();
	private View headview;
	private Button btn_date, btn_seq;
	private boolean isDateDesc, isseqDesc;

	public static UmeiNavOpenDBTypeFragment newInstance(String url, int type, boolean isVisibleToUser) {
		UmeiNavOpenDBTypeFragment fragment = new UmeiNavOpenDBTypeFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.type = type;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_article_pulllistview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);

		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_open_db_type_head, null);
		btn_date = (Button) headview.findViewById(R.id.btn_date);
		btn_seq = (Button) headview.findViewById(R.id.btn_seq);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mPullRefreshListView.getRefreshableView().addHeaderView(headview);
		mOpenDBListAdapter = new OpenDBListTypeAdapter(getActivity(), weakReferenceHandler, list);
		mPullRefreshListView.setAdapter(mOpenDBListAdapter);
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullRefreshListView.setOnItemClickListener(this);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		btn_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isDateDesc) {
					isDateDesc = false;
				} else {
					isDateDesc = true;
				}
				weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
			}
		});
		btn_seq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isseqDesc) {
					isseqDesc = false;
				} else {
					isseqDesc = true;
				}
				weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.activity.BaseFragmentActivity#call()
	 */
	@Override
	public OpenDBJson call() throws Exception {
		// TODO Auto-generated method stub
		OpenDBJson mOpenDBJson = new OpenDBJson();
		if (isDateDesc) {
			mOpenDBJson.setList(UmeiOpenDBService.queryListTypeByDateDesc(getActivity(), type));
		} else {
			mOpenDBJson.setList(UmeiOpenDBService.queryListType(getActivity(), type, isseqDesc));
		}
		return mOpenDBJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.activity.BaseFragmentActivity#onCallback(java.lang
	 * .Object)
	 */
	@Override
	public void onCallback(OpenDBJson result) {
		// TODO Auto-generated method stub
		list.clear();
		list.addAll(result.getList());
		mOpenDBListAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.BaseFragmentActivity#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		case MESSAGE_ADAPTER_CALL_ONITEM:
			onItemClick(null, null, msg.arg1, msg.arg1);
			break;
		case MESSAGE_ADAPTER_UN_COLLECTION:
			UmeiOpenDBService.delete(getActivity(), list.get(msg.arg1));
			list.remove(msg.arg1);
			mOpenDBListAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if ((id) != -1 && list != null && list.size() > 0) {
			list.get((int) id).setState(1);
			mOpenDBListAdapter.notifyDataSetChanged();
			OpenDBBean bean = list.get((int) id);
			switch (bean.getType()) {
			case 1:
				UmeiArticleGridHeadActivity.startUmeiArticleGridHeadActivity(getActivity(), bean.getUrl());
				break;
			default:
				UmeiWebViewActivity.startUmeiWebViewActivity(getActivity(), bean.getImgsrc());
				break;
			}
		}

	}

}
