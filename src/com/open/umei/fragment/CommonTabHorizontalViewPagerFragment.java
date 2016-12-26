package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.adapter.CommonPagerAdapter;
import com.open.umei.adapter.OpenTabTitleAdapter;
import com.open.umei.bean.CommonBean;
import com.open.umei.json.CommonJson;
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
public class CommonTabHorizontalViewPagerFragment extends BaseV4Fragment<CommonJson, CommonTabHorizontalViewPagerFragment> implements OpenTabHost.OnTabSelectListener {
	public static final String TAG = CommonTabHorizontalViewPagerFragment.class.getSimpleName();
	public ViewPager viewpager;
	// 移动边框.
	public View mNewFocus;
	public CommonFragmentPagerAdapter mCommonPagerAdapter;
	// RankViewPagerAdapter mRankViewPagerAdapter;
	public List<CommonBean> titlerankList = new ArrayList<CommonBean>();
	public OpenTabHost mOpenTabHost;
	public OpenTabTitleAdapter mOpenTabTitleAdapter;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public int position;
	public View view;
	public String url;
	public String selectElement;
	public String liElement;
	public String astrElement;

	public static CommonTabHorizontalViewPagerFragment newInstance(String url, String selectElement, String liElement, String astrElement) {
		CommonTabHorizontalViewPagerFragment fragment = new CommonTabHorizontalViewPagerFragment();
		fragment.setFragment(fragment);
		fragment.url = url;
		fragment.selectElement = selectElement;
		fragment.liElement = liElement;
		fragment.astrElement = astrElement;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_common_tab_horizontal_viewpager, container, false);
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
		mOpenTabHost = (OpenTabHost) view.findViewById(R.id.openTabHost);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// Handler mFocusHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// // // 初始化.
	// switchTab(mOpenTabHost, position);
	// viewpager.setCurrentItem(position);
	// }
	// };

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
	public void onTabSelect(OpenTabHost openTabHost, View titleWidget, int position) {
		if (viewpager != null) {
			viewpager.setCurrentItem(position);
		}
	}

	@Override
	public CommonJson call() throws Exception {
		CommonJson mCommonT = new CommonJson();
		ArrayList<CommonBean> list = new ArrayList<CommonBean>();
		try {
			list = parseTitleRank(url);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// mCommonT.setList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(CommonJson result) {
		super.onCallback(result);
	}

	/**
	 * 绑定事件在onViewCreated调用
	 */
	public void bindEvent() {
		// 初始化标题栏.
		mOpenTabHost.setOnTabSelectListener(this);
	}

	public ArrayList<CommonBean> parseTitleRank(String href) {
		ArrayList<CommonBean> list = new ArrayList<CommonBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href)
			// .userAgent(UrlUtils.userAgent)
					.timeout(10000).get();

			Element masthead = doc.select(selectElement).first();
			Elements liElements = masthead.select(liElement);
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					CommonBean bean = new CommonBean();
					try {
						Element aElement = liElements.get(i).select(astrElement).first();
						String title = aElement.text().replace("/", "").replace("|", "");
						// bean.setRankName(title);
						Log.i(TAG, "i===" + i + "title===" + title);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element aElement = liElements.get(i).select(astrElement).first();
						String hrefurl = aElement.attr("href");
						// bean.setRankurl(hrefurl);
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
					} catch (Exception e) {
						e.printStackTrace();
					}
					list.add(bean);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public void setSelectElement(String selectElement) {
		this.selectElement = selectElement;
	}

	public void setLiElement(String liElement) {
		this.liElement = liElement;
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
			switchTab(mOpenTabHost, position);
			viewpager.setCurrentItem(position);
			break;
		default:
			break;
		}
	}

}
