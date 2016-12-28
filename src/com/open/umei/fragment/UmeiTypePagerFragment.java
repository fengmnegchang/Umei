package com.open.umei.fragment;

import java.util.ArrayList;

import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.UmeiTypeListService;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ******************
 * 
 * @author :fengguangjing
 * @createTime: 16/11/18
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class UmeiTypePagerFragment extends UmeiTypeHeadFragment {

	public static UmeiTypePagerFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiTypePagerFragment fragment = new UmeiTypePagerFragment();
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
			list = UmeiTypeListService.relaxarc(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}

}
