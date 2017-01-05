/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午9:47:29
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity;

import com.open.umei.R;
import com.open.umei.fragment.UmeiMainExpandableListFragment;
import com.open.umei.fragment.m.UmeiMPannelHdExpandableListFragment;
import com.open.umei.utils.UrlUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 *****************************************************************************************************************************************************************************
 * tab 首页
 * @author :fengguangjing
 * @createTime:2017-1-5上午9:47:29
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class UmeiMainExpandableActivity extends CommonFragmentActivity{
	private String url = UrlUtils.UMEI;
	/* (non-Javadoc)
	 * @see com.open.umei.activity.CommonFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_main_expandable);
		
		init();
		
	}
	
	/* (non-Javadoc)
	 * @see com.open.umei.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		Fragment fragment = UmeiMainExpandableListFragment.newInstance(url,true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_main_ex, fragment).commit();
	}
	
	public static void startUmeiMainExpandableActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiMainExpandableActivity.class);
		context.startActivity(intent);
	}
}
