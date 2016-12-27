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

import android.support.v4.app.Fragment;

import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.adapter.OpenTabTitleAdapter;
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
public class UmeiNavTabHorizontalViewPagerFragment extends CommonTabHorizontalViewPagerFragment {
	public static UmeiNavTabHorizontalViewPagerFragment newInstance(String title, String url) {
		UmeiNavTabHorizontalViewPagerFragment fragment = new UmeiNavTabHorizontalViewPagerFragment();
		fragment.title = title;
		fragment.url = url;
		fragment.setFragment(fragment);
		return fragment;
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
			if(title.equals(bean.getTitle())){
				for(UmeiSubNavBean subBean :bean.getSubNavList()){
					titleList.add(subBean.getTitle());
					fragment = CommonV4Fragment.newInstance();
					listRankFragment.add(fragment);
				}
			}
		}
		
		mOpenTabTitleAdapter = new OpenTabTitleAdapter(getActivity(), titleList);
		mOpenTabHost.setAdapter(mOpenTabTitleAdapter);

		mCommonPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mCommonPagerAdapter);
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_HANDLER_COMPLETE, 2000);
	}
}
