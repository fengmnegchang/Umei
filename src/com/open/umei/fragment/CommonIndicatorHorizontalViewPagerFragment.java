package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.indicator.TabPageIndicator;
import com.open.umei.R;
import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.UmeiNavService;
import com.open.umei.view.OpenTabHost;
import com.open.umei.view.TextViewWithTTF;

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
public class CommonIndicatorHorizontalViewPagerFragment extends BaseV4Fragment<UmeiNavJson, CommonIndicatorHorizontalViewPagerFragment> {
	public static final String TAG = CommonIndicatorHorizontalViewPagerFragment.class.getSimpleName();
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public CommonFragmentPagerAdapter mCommonPagerAdapter;
	public List<UmeiNavBean> titlerankList = new ArrayList<UmeiNavBean>();
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public int position;
	public View view;
	public String url;
	public String title;

	public static CommonIndicatorHorizontalViewPagerFragment newInstance(String title, String url) {
		CommonIndicatorHorizontalViewPagerFragment fragment = new CommonIndicatorHorizontalViewPagerFragment();
		fragment.setFragment(fragment);
		fragment.title = title;
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_common_indicator_horizontal_viewpager, container, false);
		findView();
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initValue();
		doAsync(this, this, this);
		bindEvent();
	}

	/**
	 * 初始化数据 ，在onCreateView调用
	 */
	public void initValue() {
	}

	/**
	 * 初始化view ，在onCreateView调用
	 */
	public void findView() {
		// 初始化viewpager.
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		mCommonPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mCommonPagerAdapter);
		indicator.setViewPager(viewpager);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
						view.setTextColor(res.getColor(R.color.white_50));
						view.setTypeface(null, Typeface.NORMAL);
						view.setSelected(false);
					}
				}
			}
		}
	}

	@Override
	public UmeiNavJson call() throws Exception {
		UmeiNavJson mCommonT = new UmeiNavJson();
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		try {
			list = UmeiNavService.parseShowNav(url);

		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiNavJson result) {
		super.onCallback(result);
	}

	/**
	 * 绑定事件在onViewCreated调用
	 */
	public void bindEvent() {
		// 初始化标题栏.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			volleyJson(url);
			break;
		case MESSAGE_HANDLER_COMPLETE:
			weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 200);
			break;
		case MESSAGE_DEFAULT_POSITION:
			// // 初始化.
			viewpager.setCurrentItem(position);
			break;
		default:
			break;
		}
	}

}
