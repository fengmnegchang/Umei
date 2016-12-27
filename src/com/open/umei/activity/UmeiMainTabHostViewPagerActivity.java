package com.open.umei.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.open.umei.R;
import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.adapter.OpenTabPagerAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.fragment.UmeiNavTabHorizontalViewPagerFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.UmeiNavService;
import com.open.umei.utils.UrlUtils;
import com.open.umei.view.OpenTabHost;
import com.open.umei.view.OpenTabHost.OnTabSelectListener;
import com.open.umei.view.TextViewWithTTF;

/**
 * ViewPager demo： 注意标题栏和viewpager的焦点控制.(在XML布局中控制了, ids) 移动边框的问题也需要注意.
 * 
 */
public class UmeiMainTabHostViewPagerActivity extends CommonFragmentActivity<UmeiNavJson> implements OnTabSelectListener {
	ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
	ViewPager viewpager;
	OpenTabHost mOpenTabHost;
	OpenTabPagerAdapter mOpenTabPagerAdapter;
	List<String> titleList = new ArrayList<String>();
	// 移动边框.
	private List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	private List<Integer> ids = new ArrayList<Integer>();
	CommonFragmentPagerAdapter mRankPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_main_tabhost_viewpager);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		// 初始化标题栏.
		mOpenTabHost = (OpenTabHost) findViewById(R.id.openTabHost);
		// 初始化viewpager.
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		doAsync(this, this, this);
	}

	@Override
	protected void initValue() {
		super.initValue();

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
			Fragment fragment = UmeiNavTabHorizontalViewPagerFragment.newInstance(sliderNavBean.getTitle(), UrlUtils.UMEI);
			listRankFragment.add(fragment);
			ids.add(R.id.title_bar);
		}
		// 初始化标题栏.
		mOpenTabPagerAdapter = new OpenTabPagerAdapter(this, titleList, ids);
		mOpenTabHost.setAdapter(mOpenTabPagerAdapter);
		mOpenTabPagerAdapter.notifyDataSetChanged();
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		// viewpager.setAdapter(new MediumPagerAdapter(this,list));
	}

	@Override
	protected void bindEvent() {
		super.bindEvent();
		// 初始化标题栏.
		mOpenTabHost.setOnTabSelectListener(this);

		viewpager.setOffscreenPageLimit(4);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				switchTab(mOpenTabHost, position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// viewPager 正在滚动中.
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		// 初始化.
		viewpager.setCurrentItem(0);
		switchTab(mOpenTabHost, 0);
	}

	@Override
	public void onTabSelect(OpenTabHost openTabHost, View titleWidget, int position) {
		if (viewpager != null) {
			viewpager.setCurrentItem(position);
		}
	}

	/**
	 * demo (翻页的时候改变状态) 将标题栏的文字颜色改变. <br>
	 * 你可以写自己的东西，我这里只是DEMO.
	 */
	public void switchTab(OpenTabHost openTabHost, int postion) {
		List<View> viewList = openTabHost.getAllTitleView();
		for (int i = 0; i < viewList.size(); i++) {
			TextViewWithTTF view = (TextViewWithTTF) openTabHost.getTitleViewIndexAt(i);
			if (view != null) {
				Resources res = view.getResources();
				if (res != null) {
					if (i == postion) {
						view.setTextColor(res.getColor(android.R.color.white));
						view.setTypeface(null, Typeface.BOLD);
						view.setSelected(true); // 为了显示 失去焦点，选中为 true的图片.
					} else {
						view.setTextColor(res.getColor(R.color.black));
						view.setTypeface(null, Typeface.NORMAL);
						view.setSelected(false);
					}
				}
			}
		}
	}

}
