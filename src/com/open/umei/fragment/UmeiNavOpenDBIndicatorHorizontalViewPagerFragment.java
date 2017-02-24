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
import java.util.List;

import android.support.v4.app.Fragment;

import com.open.umei.bean.UmeiNavBean;
import com.open.umei.bean.UmeiSubNavBean;
import com.open.umei.json.UmeiNavJson;

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
public class UmeiNavOpenDBIndicatorHorizontalViewPagerFragment extends CommonIndicatorHorizontalViewPagerFragment {

	public static UmeiNavOpenDBIndicatorHorizontalViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiNavOpenDBIndicatorHorizontalViewPagerFragment fragment = new UmeiNavOpenDBIndicatorHorizontalViewPagerFragment();
		fragment.url = url;
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		return fragment;
	}

	@Override
	public UmeiNavJson call() throws Exception {
		UmeiNavJson mCommonT = new UmeiNavJson();
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		UmeiNavBean navBean = new UmeiNavBean();
		navBean.setHref(url);
		navBean.setTitle("");

		List<UmeiSubNavBean> subNavList = new ArrayList<UmeiSubNavBean>();
		UmeiSubNavBean sbean = new UmeiSubNavBean();
		sbean.setHref(url);
		sbean.setTitle("图片");
		sbean.setType(0);
		subNavList.add(sbean);

		sbean = new UmeiSubNavBean();
		sbean.setHref(url);
		sbean.setTitle("网页");
		sbean.setType(1);
		subNavList.add(sbean);
		
		sbean = new UmeiSubNavBean();
		sbean.setHref(url);
		sbean.setTitle("亿图片");
		sbean.setType(2);
		subNavList.add(sbean);
		
		sbean = new UmeiSubNavBean();
		sbean.setHref(url);
		sbean.setTitle("亿网页");
		sbean.setType(3);
		subNavList.add(sbean);
		
		sbean = new UmeiSubNavBean();
		sbean.setHref(url);
		sbean.setTitle("pc亿图");
		sbean.setType(4);
		subNavList.add(sbean);
		
		sbean = new UmeiSubNavBean();
		sbean.setHref(url);
		sbean.setTitle("pc亿网");
		sbean.setType(5);
		subNavList.add(sbean);

		navBean.setSubNavList(subNavList);
		list.add(navBean);
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
			fragment = UmeiNavOpenDBTypeFragment.newInstance(subBean.getHref(),subBean.getType() ,false);
			listRankFragment.add(fragment);
		}
		mCommonPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_HANDLER_COMPLETE, 2000);
	}
}
