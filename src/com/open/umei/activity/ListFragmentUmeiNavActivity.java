package com.open.umei.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.open.umei.R;
import com.open.umei.fragment.UmeiNavIndicatorHorizontalViewPagerFragment;
import com.open.umei.fragment.UmeiNavListFragment;
import com.open.umei.utils.UrlUtils;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ******************
 * 
 * @author :fengguangjing
 * @createTime: 16/11/2
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class ListFragmentUmeiNavActivity extends CommonFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_fragment_umei_nav);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
	}

	@Override
	protected void initValue() {
		super.initValue();

		UmeiNavListFragment leftFragment = UmeiNavListFragment.newInstance();
		UmeiNavIndicatorHorizontalViewPagerFragment rightFragment = UmeiNavIndicatorHorizontalViewPagerFragment.newInstance("壁纸图片", UrlUtils.UMEI);

		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.frame_listview, leftFragment).commit();
		manager.beginTransaction().replace(R.id.frame_umei_nav, rightFragment).commit();
	}

}
