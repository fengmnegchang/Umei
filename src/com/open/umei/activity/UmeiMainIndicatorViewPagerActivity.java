package com.open.umei.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.open.indicator.TabPageIndicator;
import com.open.umei.R;
import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.fragment.UmeiNavIndicatorHorizontalViewPagerFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.UmeiNavService;
import com.open.umei.utils.UrlUtils;

/**
 * ViewPager demo： 注意标题栏和viewpager的焦点控制.(在XML布局中控制了, ids) 移动边框的问题也需要注意.
 * 
 */
public class UmeiMainIndicatorViewPagerActivity extends CommonFragmentActivity<UmeiNavJson> {
	ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
	ViewPager viewpager;
	TabPageIndicator indicator;
	List<String> titleList = new ArrayList<String>();
	private List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	CommonFragmentPagerAdapter mRankPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_main_indicator_viewpager);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		// 初始化viewpager.
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
		doAsync(this, this, this);
	}

	@Override
	public UmeiNavJson call() throws Exception {
		UmeiNavJson mCommonT = new UmeiNavJson();
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();// 导航大图
		try {
			// 解析网络标签
			list = UmeiNavService.parseUmeiNav(UrlUtils.UMEI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiNavJson result) {
		super.onCallback(result);
		// 初始化viewpager.
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		for (UmeiNavBean sliderNavBean : result.getList()) {
			titleList.add(sliderNavBean.getTitle());
			Fragment fragment = UmeiNavIndicatorHorizontalViewPagerFragment.newInstance(sliderNavBean.getTitle(), UrlUtils.UMEI);
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}

}
