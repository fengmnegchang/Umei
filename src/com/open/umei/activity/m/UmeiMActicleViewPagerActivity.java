package com.open.umei.activity.m;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.open.umei.R;
import com.open.umei.activity.CommonFragmentActivity;
import com.open.umei.adapter.UmeiArticlePagerAdapter;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.UmeiArticleService;

/**
 * ViewPager demo： 注意标题栏和viewpager的焦点控制.(在XML布局中控制了, ids) 移动边框的问题也需要注意.
 * 
 */
public class UmeiMActicleViewPagerActivity extends
		CommonFragmentActivity<UmeiArticleJson> {
	ViewPager viewpager;
	public UmeiArticlePagerAdapter mUmeiArticlePagerAdapter;
	private List<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
	private String url = "http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm";
	int pagerno = 1;

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
		mUmeiArticlePagerAdapter = new UmeiArticlePagerAdapter(this, list);
		viewpager.setAdapter(mUmeiArticlePagerAdapter);
		
	}

	@Override
	protected void initValue() {
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		UmeiArticleJson mUmeiArticleJson = (UmeiArticleJson) getIntent()
				.getSerializableExtra("UMEI_ARTICLE_LIST");
		if (mUmeiArticleJson != null && mUmeiArticleJson.getList() != null
				&& mUmeiArticleJson.getList().size() > 0) {
			for (int i = 0; i < mUmeiArticleJson.getList().size(); i++) {
				mUmeiArticleJson.getList().get(i).setSeq(i + 1);
				list.add(mUmeiArticleJson.getList().get(i));
			}
			mUmeiArticlePagerAdapter.notifyDataSetChanged();
		}
		doAsync(this, this, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		// UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
		// mUmeiArticleJson.setList(UmeiArticleService.parseArticlePagerSize(url));
		UmeiArticleJson mUmeiArticleJson = UmeiArticleService
				.parseMArticlePagerSize(url, pagerno);
		return mUmeiArticleJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiArticleJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result.getList() != null && result.getList().size() > 0) {
			for (UmeiArticleBean bean : result.getList()) {
				if (bean.getSrc() != null && bean.getSrc().length() > 0) {
					if(list.size()==0){
						list.add(bean);
					}else{
						list.get(bean.getSeq() - 1).setSrc(bean.getSrc());
						list.get(bean.getSeq() - 1).setAlt(bean.getAlt());
					}
					
				} 
			}
		}
		int size = list.size() + 1;
		for (int i = size; i <= result.getPagersize(); i++) {
			UmeiArticleBean bean = new UmeiArticleBean();
			bean.setSeq(i);
			list.add(bean);
		}
		mUmeiArticlePagerAdapter.notifyDataSetChanged();
		pagerno++;
	}

	@Override
	protected void bindEvent() {
		super.bindEvent();
		// 初始化.
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				UmeiArticleBean bean = list.get(position);
				if (bean != null && bean.getSrc() != null
						&& bean.getSrc().length() > 0) {

				} else {
					doAsync(UmeiMActicleViewPagerActivity.this,
							UmeiMActicleViewPagerActivity.this,
							UmeiMActicleViewPagerActivity.this);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void startUmeiMActicleViewPagerActivity(Context context,
			UmeiArticleJson umeiArticleJson, String url) {
		Intent intent = new Intent();
		intent.putExtra("UMEI_ARTICLE_LIST", umeiArticleJson);
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiMActicleViewPagerActivity.class);
		context.startActivity(intent);
	}
}
