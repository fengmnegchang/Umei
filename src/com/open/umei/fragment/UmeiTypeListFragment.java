package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.open.umei.R;
import com.open.umei.adapter.UmeiTypeAdapter;
import com.open.umei.adapter.UmeiTypePagerAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.UmeiTypeListService;

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
public class UmeiTypeListFragment extends BaseV4Fragment<UmeiTypeJson, UmeiTypeListFragment> {
	private ListView listView;
	private List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	private UmeiTypeAdapter mUmeiTypeAdapter;
	private String url;

	private ViewPager viewpager;
	private UmeiTypePagerAdapter mUmeiTypePagerAdapter;
	private List<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();

	public static UmeiTypeListFragment newInstance(String url,boolean isVisibleToUser) {
		UmeiTypeListFragment fragment = new UmeiTypeListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_listview, container, false);
		listView = (ListView) view.findViewById(R.id.listview);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
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
		// head
		// Fragment fragment = UmeiTypeHeadFragment.newInstance(url,true);
		// FragmentManager manager = getActivity().getSupportFragmentManager();
		// manager.beginTransaction().replace(R.id.layout_head,
		// fragment).commit();
		mUmeiTypePagerAdapter = new UmeiTypePagerAdapter(getActivity(), list2);
		viewpager.setAdapter(mUmeiTypePagerAdapter);

		mUmeiTypeAdapter = new UmeiTypeAdapter(getActivity(), list);
		listView.setAdapter(mUmeiTypeAdapter);
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		ArrayList<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
		try {
			list = UmeiTypeListService.parseTypeList(url);
			list2 = UmeiTypeListService.parseTypeList2(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		mCommonT.setTypeList2(list2);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		super.onCallback(result);
		list.clear();
		list.addAll(result.getTypeList());
		mUmeiTypeAdapter.notifyDataSetChanged();

		list2.clear();
		list2.addAll(result.getTypeList2());
		mUmeiTypePagerAdapter.notifyDataSetChanged();
		viewpager.setCurrentItem(0);
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
}
