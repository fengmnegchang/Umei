/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:12:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.yiyoutu;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.open.umei.bean.UmeiNavBean;
import com.open.umei.bean.UmeiSubNavBean;
import com.open.umei.fragment.CommonIndicatorHorizontalViewPagerFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.yiyoutu.YiYouTuNavService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:12:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class YiYouTuNavIndicatorHorizontalViewPagerFragment extends CommonIndicatorHorizontalViewPagerFragment {

	public static YiYouTuNavIndicatorHorizontalViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		YiYouTuNavIndicatorHorizontalViewPagerFragment fragment = new YiYouTuNavIndicatorHorizontalViewPagerFragment();
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
			list = YiYouTuNavService.parseShowNav(url);
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
		UmeiNavBean bean = result.getList().get(0);
		for (UmeiSubNavBean subBean : bean.getSubNavList()) {
			titleList.add(subBean.getTitle());
			if(subBean.getTitle().equals("首页")){
				fragment = YiYouTuMainNavPullListFragment.newInstance(subBean.getHref(), true);
			}else{
				fragment = YiYouTuNavPullListFragment.newInstance(subBean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mCommonPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_HANDLER_COMPLETE, 2000);
	}
}
