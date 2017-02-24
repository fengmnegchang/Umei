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

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.umei.adapter.yiyoutu.YiYouTuTypeAdapter;
import com.open.umei.bean.UmeiTypeBean;
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
public class YiYouTuMainNavPullGridFragment extends YiYouTuPCNavPullGridFragment {

	public static YiYouTuMainNavPullGridFragment newInstance(String url, boolean isVisibleToUser) {
		YiYouTuMainNavPullGridFragment fragment = new YiYouTuMainNavPullGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mUmeiTypeAdapter = new YiYouTuTypeAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mUmeiTypeAdapter);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			list = YiYouTuNavPullListService.parseTypePCList(url, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}

}
