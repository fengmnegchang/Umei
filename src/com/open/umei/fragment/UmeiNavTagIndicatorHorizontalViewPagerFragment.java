/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27上午10:11:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.adapter.OpenTabTitleAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.bean.UmeiSubNavBean;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.UmeiNavService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27上午10:11:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiNavTagIndicatorHorizontalViewPagerFragment extends CommonIndicatorHorizontalViewPagerFragment {
	public static UmeiNavTagIndicatorHorizontalViewPagerFragment newInstance(String title, String url) {
		UmeiNavTagIndicatorHorizontalViewPagerFragment fragment = new UmeiNavTagIndicatorHorizontalViewPagerFragment();
		fragment.title = title;
		fragment.url = url;
		fragment.setFragment(fragment);
		return fragment;
	}
	
	public static UmeiNavTagIndicatorHorizontalViewPagerFragment newInstance(String title, String url,boolean isVisibleToUser) {
		UmeiNavTagIndicatorHorizontalViewPagerFragment fragment = new UmeiNavTagIndicatorHorizontalViewPagerFragment();
		fragment.title = title;
		fragment.url = url;
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		return fragment;
	}
	@Override
	public UmeiNavJson call() throws Exception {
		UmeiNavJson mCommonT = new UmeiNavJson();
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		try {
//			if(title.equals("国内")){
//				list = UmeiNavService.parseShowMore(url);
//			}else{
				list = UmeiNavService.parseTagNav(url);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setList(list);
		return mCommonT;
	}
	
	@Override
	public void onCallback(UmeiNavJson result) {
		super.onCallback(result);
		titlerankList.clear();
		titlerankList.addAll(result.getList());
		// 初始化标题栏.
		titleList.clear();
		listRankFragment.clear();

		Fragment fragment = null;
		for (int i = 0; i < result.getList().size(); i++) {
			UmeiNavBean bean = result.getList().get(i);
			for(UmeiSubNavBean subBean :bean.getSubNavList()){
				titleList.add(subBean.getTitle());
//					fragment = UmeiTypeListFragment.newInstance(subBean.getHref(),false);
				fragment = UmeiTypeGridFragment.newInstance(subBean.getHref(),false);
				listRankFragment.add(fragment);
			}
		}
		mCommonPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_HANDLER_COMPLETE, 2000);
	}
}
