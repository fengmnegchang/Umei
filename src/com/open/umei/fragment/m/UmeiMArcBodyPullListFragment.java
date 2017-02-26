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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.umei.R;
import com.open.umei.activity.m.UmeiMArcBodyListHeadFootActivity;
import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.fragment.UmeiArticleFragment;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.json.m.UmeiMArcBodyJson;
import com.open.umei.jsoup.UmeiArticleService;
import com.open.umei.jsoup.m.UmeiMArcBodyService;

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
public class UmeiMArcBodyPullListFragment extends UmeiArticleFragment implements OnClickListener{
	private View headerview;
	private View footview;
	private TextView txt_arctitle;
	private TextView txt_articlepre;
	private TextView txt_articlenext;
	private TextView txt_arcDESC;
	private TextView txt_artsee;
	private UmeiMArcBodyBean arcbody;
	
	public static UmeiMArcBodyPullListFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMArcBodyPullListFragment fragment = new UmeiMArcBodyPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_arc_body_pulllistview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headerview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_arc_body_list_head, null);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_m_arc_body_list_foot, null);
		txt_arctitle = (TextView) headerview.findViewById(R.id.txt_arctitle);
		
		txt_articlepre = (TextView) footview.findViewById(R.id.txt_articlepre);
		txt_articlenext = (TextView) footview.findViewById(R.id.txt_articlenext);
		txt_arcDESC = (TextView) footview.findViewById(R.id.txt_arcDESC);
		txt_artsee = (TextView) footview.findViewById(R.id.txt_artsee);
		return view;
	}
	 
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View, android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ListView mListView = mPullRefreshListView.getRefreshableView();
			mListView.addHeaderView(headerview);
			mListView.addFooterView(footview);
		
		Fragment fragment = UmeiMArcTagGridFragment.newInstance(url,true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_arc_tags_grid, fragment).commit();
		
		Fragment expandfragment = UmeiMArcBodyExpendExpandableListFragment.newInstance(url,true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_expandablelistview, expandfragment).commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiArticleJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
		mUmeiArticleJson.setList(UmeiArticleService.parseArcBody(url,pageNo));
		mUmeiArticleJson.setmUmeiMArcBodyJson(UmeiMArcBodyService.parseArcBody(url));
		
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

		try {
			arcbody = result.getmUmeiMArcBodyJson().getArcbody();
			txt_arctitle.setText(arcbody.getArctitle());
			txt_articlepre.setText(arcbody.getArticlepre());
			txt_articlenext.setText(arcbody.getArticlenext());
			txt_arcDESC.setText(arcbody.getArcDESC());
			txt_artsee.setText(arcbody.getArtsee());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		txt_articlepre.setOnClickListener(this);
		txt_articlenext.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.txt_articlepre:
			UmeiMArcBodyListHeadFootActivity.startUmeiMArcBodyListHeadFootActivity(getActivity(), arcbody.getArticleprea());
			break;
		case R.id.txt_articlenext:
			UmeiMArcBodyListHeadFootActivity.startUmeiMArcBodyListHeadFootActivity(getActivity(), arcbody.getArticlenexta());
			break;
		default:
			break;
		}
		
	}

}
