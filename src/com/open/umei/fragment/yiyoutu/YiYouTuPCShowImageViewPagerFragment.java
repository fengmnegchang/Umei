/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-24上午10:06:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.yiyoutu;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMArticlePagerAdapter;
import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.bean.db.OpenDBBean;
import com.open.umei.db.service.UmeiOpenDBService;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.yiyoutu.YiYouTuArticleService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-2-24上午10:06:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class YiYouTuPCShowImageViewPagerFragment extends BaseV4Fragment<UmeiArticleJson, YiYouTuPCShowImageViewPagerFragment> {
	public String url;
	public int pageNo = 0;
	private ViewPager viewpager;
	public UmeiMArticlePagerAdapter mUmeiArticlePagerAdapter;
	private List<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
	private TextView text_page_foot;

	public static YiYouTuPCShowImageViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		YiYouTuPCShowImageViewPagerFragment fragment = new YiYouTuPCShowImageViewPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		text_page_foot = (TextView) view.findViewById(R.id.text_page_foot);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mUmeiArticlePagerAdapter = new UmeiMArticlePagerAdapter(getActivity(), list, null);
		viewpager.setAdapter(mUmeiArticlePagerAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiArticleJson call() throws Exception {
		UmeiArticleJson mUmeiArticleJson = YiYouTuArticleService.parsePCArticlePagerSize(url, pageNo + 1);
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
					if (list.size() == 0) {
						list.add(bean);
					} else {
						list.get(bean.getSeq() - 1).setUrl(bean.getUrl());
						list.get(bean.getSeq() - 1).setSrc(bean.getSrc());
						list.get(bean.getSeq() - 1).setAlt(bean.getAlt());
						list.get(bean.getSeq() - 1).setType(bean.getType());
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
		pageNo++;
		text_page_foot.setText(pageNo + " / " + list.size() + " 页");
	}

	@Override
	public void bindEvent() {
		super.bindEvent();
		// 初始化.
		viewpager.setCurrentItem(pageNo);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				UmeiArticleBean bean = list.get(position);
				if (bean != null && bean.getSrc() != null && bean.getSrc().length() > 0) {
				} else {
					doAsync(YiYouTuPCShowImageViewPagerFragment.this, YiYouTuPCShowImageViewPagerFragment.this, YiYouTuPCShowImageViewPagerFragment.this);
				}
				text_page_foot.setText((position + 1) + " / " + list.size() + " 页");
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
		text_page_foot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(list!=null && list.size()>0){
					OpenDBBean openbean = new OpenDBBean();
					openbean.setUrl(list.get(0).getUrl());
					openbean.setType(5);
					openbean.setImgsrc(list.get(0).getSrc());
					openbean.setTitle(list.get(0).getAlt());
					openbean.setTypename("yiyoutu");
					openbean.setTime("");
					UmeiOpenDBService.insert(getActivity(), openbean);
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#handlerMessage(android.os
	 * .Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

}
