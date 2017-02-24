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
import com.open.umei.fragment.yiyoutu.YiYouTuPCShowImageViewPagerFragment;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 亿优图pc 看图viewpager
 * @author :fengguangjing
 * @createTime:2017-2-23下午5:11:12
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class YiYouTuPCShowImageViewPagerFragmentActivity extends CommonFragmentActivity<UmeiNavJson>{
	private String url = UrlUtils.YIYOUTU_IMAGE;
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
		 
		YiYouTuPCShowImageViewPagerFragment fragment = YiYouTuPCShowImageViewPagerFragment.newInstance(url, true);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_list, fragment).commit();
	}

	public static void startYiYouTuPCShowImageViewPagerFragmentActivity(Context mContext, String url ) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(mContext, YiYouTuPCShowImageViewPagerFragmentActivity.class);
		mContext.startActivity(intent);
	}

}
