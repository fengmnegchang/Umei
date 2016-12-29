/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午1:53:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.m;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMArcTagGridViewAdapter;
import com.open.umei.bean.m.UmeiMArcTagBean;
import com.open.umei.fragment.BaseV4Fragment;
import com.open.umei.json.m.UmeiMArcTagJson;
import com.open.umei.jsoup.m.UmeiMArcBodyService;
import com.open.umei.view.ExpendGridView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午1:53:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcTagGridFragment extends BaseV4Fragment<UmeiMArcTagJson, UmeiMArcTagGridFragment> {
	private String url;
	private ExpendGridView gridView;
	private UmeiMArcTagGridViewAdapter mUmeiMArcTagGridViewAdapter;
	private List<UmeiMArcTagBean> list = new ArrayList<UmeiMArcTagBean>();

	public static UmeiMArcTagGridFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMArcTagGridFragment fragment = new UmeiMArcTagGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_m_arc_tag_gridview, container, false);
		gridView = (ExpendGridView) view.findViewById(R.id.gridView);
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
		initValues();
		bindEvent();
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
		mUmeiMArcTagGridViewAdapter = new UmeiMArcTagGridViewAdapter(getActivity(), list);
		gridView.setAdapter(mUmeiMArcTagGridViewAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiMArcTagJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiMArcTagJson mUmeiMArcTagJson = new UmeiMArcTagJson();
		mUmeiMArcTagJson.setList(UmeiMArcBodyService.parseArcTag(url));
		return mUmeiMArcTagJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiMArcTagJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);

		list.clear();
		list.addAll(result.getList());
		mUmeiMArcTagGridViewAdapter.notifyDataSetChanged();

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
