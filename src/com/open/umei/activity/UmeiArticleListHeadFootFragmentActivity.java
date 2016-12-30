/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:04:52
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.open.umei.R;
import com.open.umei.fragment.UmeiArticleListHeadFootFragment;
import com.open.umei.json.UmeiArticleJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:04:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiArticleListHeadFootFragmentActivity extends CommonFragmentActivity<UmeiArticleJson> {
	private String url = "http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_article_list_head_foot_fragment_list);
		init();
	}

	@Override
	protected void findView() {
		super.findView();

		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}

	}

	@Override
	protected void initValue() {
		super.initValue();

		Fragment pfragment = UmeiArticleListHeadFootFragment.newInstance(url, true);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_pull_refresh_list, pfragment).commit();
	}

	public static void startUmeiArticleActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiArticleListHeadFootFragmentActivity.class);
		context.startActivity(intent);
	}

}
