package com.open.umei.activity;

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
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;

import com.open.umei.R;
import com.open.umei.adapter.CommonFragmentPagerAdapter;
import com.open.umei.adapter.OpenTabPagerAdapter;
import com.open.umei.bean.CommonBean;
import com.open.umei.fragment.CommonV4Fragment;
import com.open.umei.json.CommonJson;
import com.open.umei.utils.UrlUtils;
import com.open.umei.view.OpenTabHost;
import com.open.umei.view.OpenTabHost.OnTabSelectListener;
import com.open.umei.view.TextViewWithTTF;

/**
 * ViewPager demo： 注意标题栏和viewpager的焦点控制.(在XML布局中控制了, ids) 移动边框的问题也需要注意.
 * 
 */
public class UmeiMainTabHostViewPagerActivity extends CommonFragmentActivity<CommonJson> implements OnTabSelectListener {
	ArrayList<CommonBean> list = new ArrayList<CommonBean>();
	// private List<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
	ViewPager viewpager;
	OpenTabHost mOpenTabHost;
	OpenTabPagerAdapter mOpenTabPagerAdapter;
	List<String> titleList = new ArrayList<String>();
	// 移动边框.
	// ImageLoader mImageLoader;
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
		// mImageLoader = new ImageLoader(this);
		// mImageLoader.setRequiredSize(5 * (int)
		// getResources().getDimension(R.dimen.litpic_width));
		// 初始化移动边框.

	}

	@Override
	public CommonJson call() throws Exception {
		CommonJson mCommonT = new CommonJson();
		ArrayList<CommonBean> list = new ArrayList<CommonBean>();// 导航大图
		try {
			// 解析网络标签
			list = parseSliderNav(UrlUtils.UMEI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(CommonJson result) {
		super.onCallback(result);
		// 初始化viewpager.
		// LayoutInflater inflater = getLayoutInflater();
		list.clear();
		list.addAll(result.getList());
		titleList.clear();
		for (CommonBean sliderNavBean : result.getList()) {
			// View view = inflater.inflate(R.layout.item_medium_pager, null);
			// ImageView imageView = (ImageView)
			// view.findViewById(R.id.imageview);
			// TextView textView = (TextView) view.findViewById(R.id.textview);
			// textView.setText(sliderNavBean.getTitle());
			// if(sliderNavBean.getImageUrl()!=null &&
			// sliderNavBean.getImageUrl().length()>0){
			// mImageLoader.DisplayImage(sliderNavBean.getImageUrl(),
			// imageView);
			// }
			// viewList.add(view);
			titleList.add(sliderNavBean.getTitle());
			Fragment fragment = CommonV4Fragment.newInstance();
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

	public ArrayList<CommonBean> parseSliderNav(String href) {
		ArrayList<CommonBean> list = new ArrayList<CommonBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element masthead = doc.select("ul.Nav").first();
			Elements liElements = masthead.select("li.NavLi");
			/**
			 * <li class="NavLi on" id="Home"><a href="http://www.umei.cc/" class="MainNav">首页</a></li>
 <li class="NavLi">
		<a href="http://www.umei.cc/bizhitupian/" title="壁纸图片" class="MainNav">壁纸图片</a>
          <div class="ShowNav">
            <a href="http://www.umei.cc/bizhitupian/diannaobizhi/" title="电脑壁纸">电脑壁纸</a><a href="http://www.umei.cc/bizhitupian/shoujibizhi/" title="手机壁纸">手机壁纸</a><a href="http://www.umei.cc/bizhitupian/dongtaibizhi/" title="动态壁纸">动态壁纸</a><a href="http://www.umei.cc/bizhitupian/huyanbizhi/" title="护眼壁纸">护眼壁纸</a><a href="http://www.umei.cc/bizhitupian/meinvbizhi/" title="美女壁纸">美女壁纸</a><a href="http://www.umei.cc/bizhitupian/xiaoqingxinbizhi/" title="小清新壁纸">小清新壁纸</a><a href="http://www.umei.cc/bizhitupian/weimeibizhi/" title="唯美壁纸">唯美壁纸</a><a href="http://www.umei.cc/bizhitupian/fengjingbizhi/" title="风景壁纸">风景壁纸</a><a href="http://www.umei.cc/bizhitupian/keaibizhi/" title="可爱壁纸">可爱壁纸</a>
          </div>
        </li>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 1; i < liElements.size(); i++) {
					CommonBean sliderNavBean = new CommonBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						String title = aElement.text();
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl + ";title===" + title );
						sliderNavBean.setTitle(title);
						sliderNavBean.setHref(hrefurl);
//						sliderNavBean.setImageUrl(imageurl);
						list.add(sliderNavBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
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
