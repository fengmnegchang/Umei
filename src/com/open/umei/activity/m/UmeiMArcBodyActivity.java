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
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.open.umei.R;
import com.open.umei.activity.CommonFragmentActivity;
import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.fragment.m.UmeiMArcBodyExpendExpandableListFragment;
import com.open.umei.fragment.m.UmeiMArcBodyExpendListFragment;
import com.open.umei.fragment.m.UmeiMArcBodyPagerFragment;
import com.open.umei.fragment.m.UmeiMArcTagGridFragment;
import com.open.umei.fragment.m.UmeiMPannelHdExpandableListFragment;
import com.open.umei.json.m.UmeiMArcBodyJson;
import com.open.umei.jsoup.m.UmeiMArcBodyService;
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
public class UmeiMArcBodyActivity extends CommonFragmentActivity<UmeiMArcBodyJson>{
	private String url = UrlUtils.UMEI_M_TU_PIAN;
	private TextView txt_arctitle;
	private TextView txt_articlepre;
	private TextView txt_articlenext;
	private TextView txt_arcDESC;
	private TextView txt_artsee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umei_m_arc_body);
		init();
	}
	
	/* (non-Javadoc)
	 * @see com.open.umei.activity.CommonFragmentActivity#findView()
	 */
	@Override
	protected void findView() {
		// TODO Auto-generated method stub
		super.findView();
		txt_arctitle = (TextView) findViewById(R.id.txt_arctitle);
		txt_articlepre = (TextView) findViewById(R.id.txt_articlepre);
		txt_articlenext = (TextView) findViewById(R.id.txt_articlenext);
		txt_arcDESC = (TextView) findViewById(R.id.txt_arcDESC);
		txt_artsee = (TextView) findViewById(R.id.txt_artsee);
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
		weakReferenceHandler = new WeakActivityReferenceHandler(this);
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		
//		Fragment pullfragment = UmeiMArcBodyExpendListFragment.newInstance(url,true);
		Fragment pullfragment = UmeiMArcBodyPagerFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_pull_refresh_list, pullfragment).commit();
		
		Fragment fragment = UmeiMArcTagGridFragment.newInstance(url,true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_arc_tags_grid, fragment).commit();
		
		Fragment expandfragment = UmeiMArcBodyExpendExpandableListFragment.newInstance(url,true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_expandablelistview, expandfragment).commit();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.activity.BaseFragmentActivity#call()
	 */
	@Override
	public UmeiMArcBodyJson call() throws Exception {
		// TODO Auto-generated method stub
		UmeiMArcBodyJson mUmeiMArcBodyJson = UmeiMArcBodyService.parseArcBody(url);
		return mUmeiMArcBodyJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.umei.activity.BaseFragmentActivity#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(UmeiMArcBodyJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);

		try {
			UmeiMArcBodyBean arcbody = result.getArcbody();
			txt_arctitle.setText(arcbody.getArctitle());
			txt_articlepre.setText(arcbody.getArticlepre());
			txt_articlenext.setText(arcbody.getArticlenext());
			txt_arcDESC.setText(arcbody.getArcDESC());
			txt_artsee.setText(arcbody.getArtsee());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void startUmeiMArcBodyActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, UmeiMArcBodyActivity.class);
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
