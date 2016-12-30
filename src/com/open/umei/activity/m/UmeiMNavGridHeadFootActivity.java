/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午2:54:29
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity.m;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.open.umei.R;
import com.open.umei.activity.CommonFragmentActivity;
import com.open.umei.fragment.m.UmeiMNavGridHeadFootStaticFragment;
import com.open.umei.json.m.UmeiMArcBodyJson;
import com.open.umei.utils.UrlUtils;
import com.open.umei.weak.WeakActivityReferenceHandler;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午2:54:29
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMNavGridHeadFootActivity extends CommonFragmentActivity<UmeiMArcBodyJson> {
	private String url = UrlUtils.UMEI_M_NAV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_m_nav_grid_head_foot);
		init();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);

		Fragment pullfragment = UmeiMNavGridHeadFootStaticFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_pull_refresh_grid, pullfragment).commit();
		 
	}

	public static void startUmeiMNavGridHeadFootActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiMNavGridHeadFootActivity.class);
		context.startActivity(intent);
	}

}
