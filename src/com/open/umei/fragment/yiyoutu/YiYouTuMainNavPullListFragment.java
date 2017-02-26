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

import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.yiyoutu.YiYouTuNavPullListService;
import com.open.umei.utils.UrlUtils;

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
public class YiYouTuMainNavPullListFragment extends YiYouTuNavPullListFragment {

	public static YiYouTuMainNavPullListFragment newInstance(String url, boolean isVisibleToUser) {
		YiYouTuMainNavPullListFragment fragment = new YiYouTuMainNavPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			list = YiYouTuNavPullListService.parseTypeList(url, pageNo);
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
		switch (msg.what) {
		case MESSAGE_HANDLER:
			if (pageNo == 1) {
				doAsync(this, this, this);
			} else {
				// http://mm.yiyoutu.com/more.php?page=2
				volleyJson(UrlUtils.YIYOUTU_M + "/more.php?page=" + pageNo);
			}
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	@Override
	public void volleyJson(String href) {
		// TODO Auto-generated method stub
		super.volleyJson(href);
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href,   new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				UmeiTypeJson mCommonT = new UmeiTypeJson();
				ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
				try {
					list = YiYouTuNavPullListService.parseTypeMainList(response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mCommonT.setTypeList(list);
				onCallback(mCommonT);
			}
		}, this);
		requestQueue.add(jsonObjectRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.fragment.BaseV4Fragment#onErrorResponse(com.android.volley
	 * .VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}

}
