package com.open.umei.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.open.umei.R;
import com.open.umei.activity.m.UmeiMArcBodyListHeadFootActivity;
import com.open.umei.activity.m.UmeiMTagGridHeadFootActivity;
import com.open.umei.bean.UmeiNavBean;
import com.open.umei.json.UmeiNavJson;
import com.open.umei.jsoup.UmeiNavService;
import com.open.umei.utils.ScreenUtils;
import com.open.umei.utils.UrlUtils;

/**
 * 
 ***************************************************************************************************************************************************************************** 
 * 主tab页面
 * 
 * @author :fengguangjing
 * @createTime:2016-10-28上午10:35:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MainTabActivity extends CommonTabActivity<UmeiNavJson> {
	private TabHost mTabHost;
	RadioGroup mRadioGroup;
	ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 透明状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 透明导航栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		setContentView(R.layout.activity_tab_main);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

		doAsync(this, this, this);

		mTabHost = getTabHost();

		// TabSpec tab_main1 =
		// mTabHost.newTabSpec(getString(R.string.tab_main1));
		// TabSpec tab_main2 =
		// mTabHost.newTabSpec(getString(R.string.tab_main2));
		// TabSpec tab_main3 =
		// mTabHost.newTabSpec(getString(R.string.tab_main3));
		// TabSpec tab_main4 =
		// mTabHost.newTabSpec(getString(R.string.tab_main4));
		//
		// tab_main1.setContent(new Intent(this,
		// QianBaiLuMMainFragmentActivity.class)).setIndicator(getString(R.string.tab_main1));
		// tab_main2.setContent(new Intent(this,
		// QianBaiLuMIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main2));
		// tab_main3.setContent(new Intent(this,
		// QianBaiLuMBIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main3));
		// tab_main4.setContent(new Intent(this,
		// QianBaiLuMSIndicatorFragmentActivity.class)).setIndicator(getString(R.string.tab_main4));
		//
		// mTabHost.addTab(tab_main1);
		// mTabHost.addTab(tab_main2);
		// mTabHost.addTab(tab_main3);
		// mTabHost.addTab(tab_main4);

	}

	@Override
	public UmeiNavJson call() throws Exception {
		UmeiNavJson mCommonT = new UmeiNavJson();
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();// 导航大图
		try {
			// 解析网络标签
			list = UmeiNavService.parseUmeiAllNav(UrlUtils.UMEI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiNavJson result) {
		super.onCallback(result);
		// 初始化viewpager.
		list.clear();
		list.addAll(result.getList());
		Intent intent;
		for (int i=0;i< result.getList().size();i++) {
			UmeiNavBean sliderNavBean = result.getList().get(i);
			TabSpec tab_main1 = mTabHost.newTabSpec(sliderNavBean.getTitle());
			if(sliderNavBean.getTitle().equals("首页")){
				intent = new Intent(this, UmeiMainExpandableActivity.class);
			}else{
				intent = new Intent(this, UmeiNavIndicatorHorizontalViewPagerActivity.class);
			}
			intent.putExtra("URL", sliderNavBean.getHref());
			intent.putExtra("TITLE", sliderNavBean.getTitle());
			tab_main1.setContent(intent).setIndicator(sliderNavBean.getTitle());
			mTabHost.addTab(tab_main1);

			View viewRadio = LayoutInflater.from(this).inflate(R.layout.layout_tab_main_radio, null);
			RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
			radio.setText(sliderNavBean.getTitle());
			
			final int position = i;
			radio.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mTabHost.setCurrentTab(position);
					setCurrentTab(position);
				}
			});
			if(i==0){
				radio.setChecked(true);
			}else {
				radio.setChecked(false);
			}
			LayoutParams params = new LayoutParams((int)ScreenUtils.getIntToDip(this, 80), LayoutParams.WRAP_CONTENT);
			mRadioGroup.addView(viewRadio,params);
		}
	}
	
	
	protected void setCurrentTab(int position){
		for(int i=0;i<mRadioGroup.getChildCount();i++){
			View viewRadio = mRadioGroup.getChildAt(i);
			RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
			if(i!=position){
				radio.setChecked(false);
			} else{
				radio.setChecked(true);
			}
		}
	}
	
	protected int getCurrentTab(){
		int position = 0;
		for(int i=0;i<mRadioGroup.getChildCount();i++){
			View viewRadio = mRadioGroup.getChildAt(i);
			RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
			if(radio.isChecked()){
				position = i;
			}
		}
		return position;
	}

}