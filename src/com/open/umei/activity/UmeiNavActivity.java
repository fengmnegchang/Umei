/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27上午11:04:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.open.umei.R;
import com.open.umei.adapter.UmeiNavAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.fragment.UmeiNavIndicatorHorizontalViewPagerFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.utils.UrlUtils;
import com.open.umei.weak.WeakActivityReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27上午11:04:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiNavActivity extends CommonFragmentActivity<UmeiNavJson> {
	private ListView listView;
	private List<UmeiNavBean> data = new ArrayList<UmeiNavBean>();
	private List<Fragment> fragments;
	private UmeiNavAdapter mUmeiNavAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_nav);
		init();

	}

	@Override
	protected void findView() {
		super.findView();
		listView = (ListView) findViewById(R.id.listview);
		doAsync(this, this, this);
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
	}

	@Override
	protected void initValue() {
		super.initValue();
		mUmeiNavAdapter = new UmeiNavAdapter(this, data);
		listView.setAdapter(mUmeiNavAdapter);
	}

	@Override
	protected void bindEvent() {
		super.bindEvent();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i(TAG, "listView item" + (int) id + " ========onItemClick ");
				Toast.makeText(getApplicationContext(), "position : " + position, Toast.LENGTH_LONG).show();
				setSelectedFragment((int) id);
			}
		});
		
	}

	/**
	 * 选择fragment
	 * 
	 * @param position
	 */
	private void setSelectedFragment(int position) {
		Fragment fragment = fragments.get(position);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_umei_nav, fragment).commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiNavJson call() throws Exception {
		UmeiNavJson mCommonT = new UmeiNavJson();
		List<UmeiNavBean> list = parseSliderNav(UrlUtils.UMEI);
		mCommonT.setList(list);
		return mCommonT;
	}
	
	/* (non-Javadoc)
	 * @see com.open.umei.activity.BaseFragmentActivity#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_DEFAULT_POSITION:
			listView.setSelection(0);
			setSelectedFragment(0);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.BaseFragmentActivity#onCallback(com.open.tencenttv
	 * .bean.CommonT)
	 */
	@Override
	public void onCallback(UmeiNavJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		data.clear();
		data.addAll(result.getList());
		mUmeiNavAdapter.notifyDataSetChanged();
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < data.size(); i++) {
			fragments.add(UmeiNavIndicatorHorizontalViewPagerFragment.newInstance(data.get(i).getTitle(), UrlUtils.UMEI));
		}
		weakReferenceHandler.sendEmptyMessageDelayed(MESSAGE_DEFAULT_POSITION, 1000);
	}

	public ArrayList<UmeiNavBean> parseSliderNav(String href) {
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
			/**
			 * <li class="NavLi on" id="Home"><a href="http://www.umei.cc/"
			 * class="MainNav">首页</a></li> <li class="NavLi">
			 * <a href="http://www.umei.cc/bizhitupian/" title="壁纸图片"
			 * class="MainNav">壁纸图片</a> <div class="ShowNav"> <a
			 * href="http://www.umei.cc/bizhitupian/diannaobizhi/"
			 * title="电脑壁纸">电脑壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/shoujibizhi/"
			 * title="手机壁纸">手机壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/dongtaibizhi/"
			 * title="动态壁纸">动态壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/huyanbizhi/"
			 * title="护眼壁纸">护眼壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/meinvbizhi/"
			 * title="美女壁纸">美女壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/xiaoqingxinbizhi/"
			 * title="小清新壁纸">小清新壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/weimeibizhi/"
			 * title="唯美壁纸">唯美壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/fengjingbizhi/"
			 * title="风景壁纸">风景壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/keaibizhi/"
			 * title="可爱壁纸">可爱壁纸</a> </div></li>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 1; i < liElements.size(); i++) {
					UmeiNavBean sliderNavBean = new UmeiNavBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						String title = aElement.text();
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl + ";title===" + title);
						sliderNavBean.setTitle(title);
						sliderNavBean.setHref(hrefurl);
						// sliderNavBean.setImageUrl(imageurl);
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
}
