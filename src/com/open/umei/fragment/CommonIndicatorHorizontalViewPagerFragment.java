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

import com.open.indicator.TabPageIndicator;
import com.open.umei.R;
import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.bean.UmeiSubNavBean;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.utils.UrlUtils;
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
			list = parseNav(url);

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

	public ArrayList<UmeiNavBean> parseNav(String href) {
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();

			Element masthead = doc.select("ul.Nav").first();
			Elements liElements = masthead.select("li.NavLi");
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiNavBean bean = new UmeiNavBean();
					try {
						String title;
						Element h2Element = liElements.get(i).select("h2").first();
						if (h2Element != null) {
							title = h2Element.select("a").first().text();
						} else {
							Element aElement = liElements.get(i).select("a").first();
							title = aElement.text().replace("/", "").replace("|", "");
						}
						bean.setTitle(title);
						Log.i(TAG, "i===" + i + "title===" + title);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						String hrefurl;
						Element h2Element = liElements.get(i).select("h2").first();
						if (h2Element != null) {
							hrefurl = h2Element.select("a").first().attr("href");
						} else {
							Element aElement = liElements.get(i).select("a").first();
							hrefurl = aElement.attr("href");
						}
						bean.setHref(hrefurl);
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
					} catch (Exception e) {
						e.printStackTrace();
					}

					/**
					 * <div class="ShowNav"> <h3><a
					 * href="http://www.umei.cc/bizhitupian/diannaobizhi/"
					 * title="电脑壁纸">电脑壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/shoujibizhi/"
					 * title="手机壁纸">手机壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/dongtaibizhi/"
					 * title="动态壁纸">动态壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/huyanbizhi/"
					 * title="护眼壁纸">护眼壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/meinvbizhi/"
					 * title="美女壁纸">美女壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/xiaoqingxinbizhi/"
					 * title="小清新壁纸">小清新壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/weimeibizhi/"
					 * title="唯美壁纸">唯美壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/fengjingbizhi/"
					 * title="风景壁纸">风景壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/keaibizhi/"
					 * title="可爱壁纸">可爱壁纸</a></h3> </div>
					 */
					try {
						Element divElement = liElements.get(i).select("div.ShowNav").first();
						if (divElement != null) {
							Elements h3Elements = divElement.select("h3");
							List<UmeiSubNavBean> subNavList = new ArrayList<UmeiSubNavBean>();
							UmeiSubNavBean subNavBean;
							for (int y = 0; y < h3Elements.size(); y++) {
								subNavBean = new UmeiSubNavBean();
								try {
									Element aElement = h3Elements.get(y).select("a").first();
									String atitle = aElement.attr("title");
									String ahref = aElement.attr("href");
									subNavBean.setTitle(atitle);
									subNavBean.setHref(ahref);
									subNavList.add(subNavBean);

									Log.i(TAG, "i===" + i + ";y==" + y + "title===" + title + ";href===" + href);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							bean.setSubNavList(subNavList);
						}
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
