package com.open.umei.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.open.umei.R;
import com.open.umei.adapter.UmeiTypePagerAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.UmeiTypeListService;
import com.open.umei.utils.UrlUtils;

/**
 * ViewPager demo： 注意标题栏和viewpager的焦点控制.(在XML布局中控制了, ids) 移动边框的问题也需要注意.
 * 
 */
public class UmeiViewPagerActivity extends CommonFragmentActivity<UmeiTypeJson> {
	ViewPager viewpager;
	UmeiTypePagerAdapter mUmeiTypePagerAdapter;
	private List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_viewpager);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		// 初始化viewpager.
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		doAsync(this, this, this);
	}

	@Override
	protected void initValue() {
		super.initValue();
		// 初始化移动边框.
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();// 导航大图
		try {
			// 解析网络标签
			list = UmeiTypeListService.parseTypeList2(UrlUtils.UMEI_NAV);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		super.onCallback(result);
		list.clear();
		list.addAll(result.getTypeList());
		mUmeiTypePagerAdapter = new UmeiTypePagerAdapter(this, list);
		viewpager.setAdapter(mUmeiTypePagerAdapter);
	}

	@Override
	protected void bindEvent() {
		super.bindEvent();
		// 初始化.
		viewpager.setCurrentItem(0);
	}

}
