/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3下午4:46:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.open.umei.R;
import com.open.umei.fragment.UmeiNavIndicatorHorizontalViewPagerFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-3下午4:46:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class UmeiNavIndicatorHorizontalViewPagerActivity extends CommonFragmentActivity<UmeiNavJson>{
	private String url = UrlUtils.UMEI_NAV;
	private String title = "壁纸图片";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_nav_indicator_horizontal_view);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
	}

	@Override
	protected void initValue() {
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}
		if (getIntent().getStringExtra("TITLE") != null) {
			title = getIntent().getStringExtra("TITLE");
		}
		UmeiNavIndicatorHorizontalViewPagerFragment fragment = UmeiNavIndicatorHorizontalViewPagerFragment.newInstance(title,url, true);
		// UmeiTypeHeadFragment fragment =
		// UmeiTypeHeadFragment.newInstance(UrlUtils.UMEI_NAV,true);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_list, fragment).commit();
	}

	public static void startUmeiNavIndicatorHorizontalViewPagerActivity(Context mContext, String url, String title) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.putExtra("TITLE", title);
		intent.setClass(mContext, UmeiNavIndicatorHorizontalViewPagerActivity.class);
		mContext.startActivity(intent);
	}

}
