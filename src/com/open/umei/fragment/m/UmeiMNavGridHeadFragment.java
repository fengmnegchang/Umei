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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.open.umei.R;
import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.json.m.UmeiMPicJson;
import com.open.umei.jsoup.m.UmeiMArcBodyService;
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
public class UmeiMNavGridHeadFragment extends UmeiMGirdArticleFragment {
	private View headerview;
	// private View footview;
	private TextView txt_arctitle;

	// private TextView txt_articlepre;
	// private TextView txt_articlenext;
	 private TextView txt_arcDESC;
	// private TextView txt_artsee;

	public static UmeiMNavGridHeadFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMNavGridHeadFragment fragment = new UmeiMNavGridHeadFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_m_nav_grid_view, container, false);
		mPullRefreshGirdView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_grid);
		headerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_nav_grid_head, null);
		// footview =
		// LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_arc_body_list_foot,
		// null);
		txt_arctitle = (TextView) headerview.findViewById(R.id.txt_arctitle);

		// txt_articlepre = (TextView)
		// footview.findViewById(R.id.txt_articlepre);
		// txt_articlenext = (TextView)
		// footview.findViewById(R.id.txt_articlenext);
		 txt_arcDESC = (TextView) headerview.findViewById(R.id.txt_arcDESC);
		// txt_artsee = (TextView) footview.findViewById(R.id.txt_artsee);
		return view;
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
		HeaderGridView mListView = mPullRefreshGirdView.getRefreshableView();
		mListView.addHeaderView(headerview);
		// mListView.addFooterView(footview);

		// Fragment fragment = UmeiMArcTagGridFragment.newInstance(url,true);
		// getChildFragmentManager().beginTransaction().replace(R.id.layout_arc_tags_grid,
		// fragment).commit();
		//
		// Fragment expandfragment =
		// UmeiMArcBodyExpendExpandableListFragment.newInstance(url,true);
		// getChildFragmentManager().beginTransaction().replace(R.id.layout_expandablelistview,
		// expandfragment).commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiMPicJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiMPicJson  mUmeiMPicJson = UmeiMPannelHdService.parseMPic(url,pageNo);
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

		try {
			UmeiMArcBodyBean arcbody = result.getmUmeiMArcBodyJson().getArcbody();
			txt_arctitle.setText(arcbody.getArctitle());
			// txt_articlepre.setText(arcbody.getArticlepre());
			// txt_articlenext.setText(arcbody.getArticlenext());
			 txt_arcDESC.setText(arcbody.getArcDESC());
			// txt_artsee.setText(arcbody.getArtsee());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
