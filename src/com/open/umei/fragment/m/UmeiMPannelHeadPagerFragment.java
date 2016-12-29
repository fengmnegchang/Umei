package com.open.umei.fragment.m;

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
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMPannelHeadPagerAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.m.UmeiMPannelHdService;

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
public class UmeiMPannelHeadPagerFragment extends BaseV4Fragment<UmeiTypeJson, UmeiMPannelHeadPagerFragment> implements OnPageChangeListener{
	public UmeiMPannelHeadPagerAdapter mUmeiTypePagerAdapter;
	public ViewPager viewpager;
	public List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	public String url;
	private ImageView[] dots;
	private int currentIndex;
	private int size;
	private LinearLayout layout_dot;
	
	public static UmeiMPannelHeadPagerFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMPannelHeadPagerFragment fragment = new UmeiMPannelHeadPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_m_pannel_hd_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		layout_dot = (LinearLayout) view.findViewById(R.id.layout_dot);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mUmeiTypePagerAdapter = new UmeiMPannelHeadPagerAdapter(getActivity(), list);
		viewpager.setAdapter(mUmeiTypePagerAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		super.onCallback(result);
		list.clear();
		list.addAll(result.getTypeList());
		mUmeiTypePagerAdapter.notifyDataSetChanged();
		viewpager.setOnPageChangeListener(this);
		
	    size = result.getTypeList().size();
		dots = new ImageView[size];
		for (int i = 0; i < size; i++) {
			ImageView img = new ImageView(getActivity());
			img.setImageResource(R.drawable.dot);
			img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			img.setPadding(15, 15, 15, 15);
			img.setClickable(true);
			dots[i] = img;
			dots[i].setEnabled(true);
			dots[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					setCurView(position);
					setCurDot(position);
				}
			});
			dots[i].setTag(i);

			layout_dot.addView(dots[i]);
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);
		viewpager.setCurrentItem(0);
	}
	
	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= size) {
			return;
		}

		viewpager.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > size - 1
				|| currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
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

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			list = UmeiMPannelHdService.pannelHead(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurDot(arg0);
	}

}
