/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:11:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.activity.yiyoutu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.open.umei.R;
import com.open.umei.activity.CommonFragmentActivity;
import com.open.umei.fragment.yiyoutu.YiYouTuNavPullListFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 亿优图tab 
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:11:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class YiYouTuNavFragmentActivity extends CommonFragmentActivity<UmeiNavJson>{
	private String url = UrlUtils.YIYOUTU_M_NAV;
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
		 
		YiYouTuNavPullListFragment fragment = YiYouTuNavPullListFragment.newInstance(url, true);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_list, fragment).commit();
	}

	public static void startYiYouTuNavFragmentActivity(Context mContext, String url ) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(mContext, YiYouTuNavFragmentActivity.class);
		mContext.startActivity(intent);
	}

}
