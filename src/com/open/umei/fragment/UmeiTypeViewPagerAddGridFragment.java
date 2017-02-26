package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshHeadGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.activity.UmeiArticleGridHeadActivity;
import com.open.umei.activity.m.UmeiMArcBodyListHeadFootActivity;
import com.open.umei.adapter.UmeiTypeAdapter;
import com.open.umei.adapter.UmeiTypeHeightPagerAdapter;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.UmeiTypeJson;
import com.open.umei.jsoup.UmeiTypeListService;

/**
 * *****************************************************************************
 * *****************************************************************************
 * ******************
 * 
 * @author :fengguangjing
 * @createTime: 16/11/18
 * @version:
 * @modifyTime:
 * @modifyAuthor:
 * @description: 
 *               ****************************************************************
 *               ***************************************************************
 *               *********************************************
 */
public class UmeiTypeViewPagerAddGridFragment extends BaseV4Fragment<UmeiTypeJson, UmeiTypeViewPagerAddGridFragment> {
	private PullToRefreshHeadGridView mPullToRefreshHeadGridView;
	private List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	private UmeiTypeAdapter mUmeiTypeAdapter;
	private String url;
	private int pageNo = 1;
	private View headview;
	
//	private ViewPager viewpager;
//	private UmeiTypeHeightPagerAdapter mUmeiTypePagerAdapter;
	private List<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
	private TextView txt_ChannelTitle, txt_ListDesc;
	private ImageView image_TypePic;

	public static UmeiTypeViewPagerAddGridFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiTypeViewPagerAddGridFragment fragment = new UmeiTypeViewPagerAddGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_type_head_gridview, container, false);
		mPullToRefreshHeadGridView = (PullToRefreshHeadGridView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_type_viewpager_all_head, null);
//		viewpager = (ViewPager) headview.findViewById(R.id.viewpager);
		txt_ChannelTitle = (TextView) headview.findViewById(R.id.txt_ChannelTitle);
		txt_ListDesc = (TextView) headview.findViewById(R.id.txt_ListDesc);
		image_TypePic = (ImageView) headview.findViewById(R.id.image_TypePic);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();

		HeaderGridView listview = mPullToRefreshHeadGridView.getRefreshableView();
		listview.addHeaderView(headview);
		
//		Fragment fragment = UmeiTypeListHeadFragment.newInstance(url, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.layout_umei_type_list_head, fragment).commit();

		mUmeiTypeAdapter = new UmeiTypeAdapter(getActivity(), list);
		mPullToRefreshHeadGridView.setAdapter(mUmeiTypeAdapter);
		mPullToRefreshHeadGridView.setMode(Mode.BOTH);
		
//		mUmeiTypePagerAdapter = new UmeiTypeHeightPagerAdapter(getActivity(), list2);
//		viewpager.setAdapter(mUmeiTypePagerAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.umei.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshHeadGridView.setOnRefreshListener(new OnRefreshListener<HeaderGridView>() {
			@Override
			public void onRefresh(PullToRefreshBase<HeaderGridView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullToRefreshHeadGridView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				} else if (mPullToRefreshHeadGridView.getCurrentMode() == Mode.PULL_FROM_END) {
					pageNo++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		mPullToRefreshHeadGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(list.get((int) id).getHref().contains("http://www.umei.cc/tushuotianxia/")){
					UmeiMArcBodyListHeadFootActivity.startUmeiMArcBodyListHeadFootActivity(getActivity(), list.get((int) id).getHref().replace("http://www.umei.cc/", "http://m.umei.cc/"));
				}else{
					UmeiArticleGridHeadActivity.startUmeiArticleGridHeadActivity(getActivity(), list.get((int) id).getHref());
				}
			}
		});
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			list = UmeiTypeListService.parseTypeList(url, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
		try {
			list2 = UmeiTypeListService.parseTypeList2(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			mCommonT.setChannelTitle(UmeiTypeListService.getChannelTitle());
			mCommonT.setListDesc(UmeiTypeListService.getListDesc());
			mCommonT.setTypePic(UmeiTypeListService.getTypePic());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mCommonT.setTypeList2(list2);
		mCommonT.setTypeList(list);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		Log.i(TAG, "getMode ===" + mPullToRefreshHeadGridView.getCurrentMode());
		if (mPullToRefreshHeadGridView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			if (result.getTypeList2() != null && result.getTypeList2().size() > 0) {
				list2.clear();
				list2.addAll(result.getTypeList2());
			}
			list.addAll(list2);
			list.addAll(result.getTypeList());
			pageNo = 1;

		} else {
			if (result.getTypeList() != null && result.getTypeList().size() > 0) {
				list.addAll(result.getTypeList());
			}
		}

		
//		mUmeiTypePagerAdapter.notifyDataSetChanged();

		txt_ChannelTitle.setText(result.getChannelTitle());
		txt_ListDesc.setText(result.getListDesc());
		if (result.getTypePic() != null && result.getTypePic().length() > 0) {
			DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
					.cacheInMemory().cacheOnDisc().build();
			ImageLoader.getInstance().displayImage(result.getTypePic(), image_TypePic, options, getImageLoadingListener());
		}
		
		mUmeiTypeAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshHeadGridView.onRefreshComplete();
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
