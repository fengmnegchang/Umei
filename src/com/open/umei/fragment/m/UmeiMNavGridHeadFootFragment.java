/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午4:27:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.m;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.HeaderFooterGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshHeaderFooterGridView;
import com.open.umei.R;
import com.open.umei.adapter.m.UmeiMPicGridViewAdapter;
import com.open.umei.json.m.UmeiMPicJson;
import com.open.umei.jsoup.m.UmeiMPannelHdService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午4:27:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMNavGridHeadFootFragment extends UmeiMHeadFootGirdArticleFragment {
	private View headerview;
	private View footview;
	private TextView txt_arctitle;
	private TextView txt_arcDESC;

	public static UmeiMNavGridHeadFootFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMNavGridHeadFootFragment fragment = new UmeiMNavGridHeadFootFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_m_nav_head_foot_grid_view, container, false);
		mPullRefreshGirdView = (PullToRefreshHeaderFooterGridView) view.findViewById(R.id.pull_refresh_grid);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_nav_grid_foot, null);
		headerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_nav_grid_head, null);
		
		txt_arctitle = (TextView) headerview.findViewById(R.id.txt_arctitle);
		txt_arcDESC = (TextView) headerview.findViewById(R.id.txt_arcDESC);
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
		mUmeiMPicGridViewAdapter = new UmeiMPicGridViewAdapter(getActivity(), list);
		mPullRefreshGirdView.setAdapter(mUmeiMPicGridViewAdapter);
		mPullRefreshGirdView.setMode(Mode.BOTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		HeaderFooterGridView mListView = mPullRefreshGirdView.getRefreshableView();
		mListView.addHeaderView(headerview);
		mListView.addFooterView(footview);


		Fragment expandfragment = UmeiMArcBodyExpendExpandableListFragment.newInstance(url,true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_expend_expandablelistview, expandfragment).commit();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiMPicJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiMPicJson mUmeiMPicJson = UmeiMPannelHdService.parseMPic(url, pageNo);
		return mUmeiMPicJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiMPicJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);

		// try {
		// UmeiMArcBodyBean arcbody =
		// result.getmUmeiMArcBodyJson().getArcbody();
		// txt_arctitle.setText(arcbody.getArctitle());
		// // txt_articlepre.setText(arcbody.getArticlepre());
		// // txt_articlenext.setText(arcbody.getArticlenext());
		// txt_arcDESC.setText(arcbody.getArcDESC());
		// // txt_artsee.setText(arcbody.getArtsee());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

}
