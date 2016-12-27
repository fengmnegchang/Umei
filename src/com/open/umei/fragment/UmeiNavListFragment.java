package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.open.umei.R;
import com.open.umei.adapter.UmeiNavAdapter;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.UmeiNavService;
import com.open.umei.utils.UrlUtils;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ****************** 频道内容 ListFragment
 * 
 * @author :fengguangjing
 * @createTime: 16/11/2
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class UmeiNavListFragment extends BaseV4ListFragment<UmeiNavJson> {
	private List<UmeiNavBean> data = new ArrayList<UmeiNavBean>();
	private UmeiNavAdapter mUmeiNavAdapter;

	public static UmeiNavListFragment newInstance() {
		UmeiNavListFragment fragment = new UmeiNavListFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_umei_nav, container, false);
		// 设置ListFragment默认的ListView，即@id/android:list
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mUmeiNavAdapter = new UmeiNavAdapter(getActivity(), data);
		this.setListAdapter(mUmeiNavAdapter);
		doAsync(this, this, this);

	}

	public void onListItemClick(ListView parent, View view, int position, long id) {
		Log.d(TAG, "onListItemClick");
		Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();
		setSelectedFragment((int) id);

	}

	/**
	 * 选择fragment
	 * 
	 * @param position
	 */
	private void setSelectedFragment(int position) {
		UmeiNavIndicatorHorizontalViewPagerFragment rightFragment = UmeiNavIndicatorHorizontalViewPagerFragment.newInstance(data.get(position).getTitle(), UrlUtils.UMEI);
		FragmentManager manager = getActivity().getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.frame_umei_nav, rightFragment).commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.tencenttv.BaseV4ListFragment#call()
	 */
	@Override
	public UmeiNavJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiNavJson mCommonT = new UmeiNavJson();
		List<UmeiNavBean> list = UmeiNavService.parseUmeiNav(UrlUtils.UMEI);
		mCommonT.setList(list);
		return mCommonT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.BaseV4ListFragment#onCallback(com.open.tencenttv.bean
	 * .CommonT)
	 */
	@Override
	public void onCallback(UmeiNavJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		data.clear();
		data.addAll(result.getList());
		mUmeiNavAdapter.notifyDataSetChanged();
		// // 延时请求其它位置的item.
		// Handler handler = new Handler() {
		// @Override
		// public void handleMessage(Message msg) {
		// getListView().requestFocusFromTouch();
		// getListView(). setSelection(0);
		// }
		// };
		// handler.sendMessageDelayed(handler.obtainMessage(), 5000);

	}

}
