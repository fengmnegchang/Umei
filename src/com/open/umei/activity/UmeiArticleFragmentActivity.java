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
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.bean.UmeiArticleInfoBean;
import com.open.umei.fragment.UmeiArticleFragment;
import com.open.umei.fragment.UmeiArticleTypeFragment;
import com.open.umei.fragment.UmeiTypePagerFragment;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.UmeiArticleService;
import com.open.umei.weak.WeakActivityReferenceHandler;

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
public class UmeiArticleFragmentActivity extends CommonFragmentActivity<UmeiArticleJson> {
	private TextView txt_ArticleTitle;
	private TextView txt_time;
	private TextView txt_see;
	private TextView txt_column;
	private TextView txt_tips;
	private TextView txt_ArticleDesc;
	private String url = "http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_article_fragment_list);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
		txt_ArticleTitle = (TextView) findViewById(R.id.txt_ArticleTitle);
		txt_time = (TextView) findViewById(R.id.txt_time);
		txt_see = (TextView) findViewById(R.id.txt_see);
		txt_column = (TextView) findViewById(R.id.txt_column);
		txt_tips = (TextView) findViewById(R.id.txt_tips);
		txt_ArticleDesc = (TextView) findViewById(R.id.txt_ArticleDesc);

		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}

	}

	@Override
	protected void initValue() {
		super.initValue();
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);

		Fragment pfragment = UmeiArticleFragment.newInstance(url, true);
		Fragment vfragment = UmeiTypePagerFragment.newInstance(url, true);
		Fragment lfragment = UmeiArticleTypeFragment.newInstance(url, true);
//
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_pull_refresh_list, pfragment).commit();
		manager.beginTransaction().replace(R.id.layout_viewpager, vfragment).commit();
		manager.beginTransaction().replace(R.id.layout_listview, lfragment).commit();
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
		UmeiArticleService.parseArticle(url, 1);
		mUmeiArticleJson.setArticleInfos(UmeiArticleService.getArticleInfo());
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
			UmeiArticleInfoBean articleInfo = UmeiArticleService.getArticleInfo();
			txt_ArticleTitle.setText(articleInfo.getArticleTitle());
			txt_time.setText(articleInfo.getTime());
			txt_see.setText(articleInfo.getSee());
			txt_column.setText(articleInfo.getColumn());
			txt_tips.setText(articleInfo.getTips());
			txt_ArticleDesc.setText(articleInfo.getArticleDesc());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void startUmeiArticleActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiArticleFragmentActivity.class);
		context.startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

}
