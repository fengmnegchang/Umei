package com.open.umei.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.open.umei.R;
import com.open.umei.fragment.UmeiTypeListFragment;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.utils.UrlUtils;

/**
 * 
 */
public class UmeiTypeListActivity extends CommonFragmentActivity<UmeiTypeJson> {
	private String url = UrlUtils.UMEI_NAV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_type_list);
		init();
	}

 
	@Override
	protected void initValue() {
		super.initValue();
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		}

		UmeiTypeListFragment fragment = UmeiTypeListFragment.newInstance(url, true);
		// UmeiTypeHeadFragment fragment =
		// UmeiTypeHeadFragment.newInstance(UrlUtils.UMEI_NAV,true);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_list, fragment).commit();
	}

	public static void startUmeiTypeListActivity(Context mContext, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(mContext, UmeiTypeListActivity.class);
		mContext.startActivity(intent);
	}

}
