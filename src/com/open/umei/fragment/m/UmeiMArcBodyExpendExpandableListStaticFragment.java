/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.fragment.m;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.umei.R;
import com.open.umei.utils.UrlUtils;
import com.open.umei.view.ExpendExpandableListView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:03:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcBodyExpendExpandableListStaticFragment extends UmeiMPannelHdExpandableListStaticFragment {
	public String url = UrlUtils.UMEI_M_NAV;
	public static UmeiMArcBodyExpendExpandableListStaticFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiMArcBodyExpendExpandableListStaticFragment fragment = new UmeiMArcBodyExpendExpandableListStaticFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_arc_body_expend_expandable_listview, container, false);
		expendablelistview = (ExpendExpandableListView) view.findViewById(R.id.expendablelistview);
		doAsync(this, this, this);
		return view;
	}

}