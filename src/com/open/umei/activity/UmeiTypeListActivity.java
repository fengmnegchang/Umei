package com.open.umei.activity;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_type_list);
		init();
	}

	@Override
	protected void findView() {
		super.findView();
	}

	@Override
	protected void initValue() {
		super.initValue();
		UmeiTypeListFragment fragment = UmeiTypeListFragment.newInstance(UrlUtils.UMEI_NAV,true);
//		UmeiTypeHeadFragment fragment = UmeiTypeHeadFragment.newInstance(UrlUtils.UMEI_NAV,true);
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.layout_list, fragment).commit();
	}

}
