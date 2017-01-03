package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.activity.UmeiArticleActivity;
import com.open.umei.adapter.UmeiTypeAdapter;
import com.open.umei.adapter.UmeiTypeHeightPagerAdapter;
import com.open.umei.adapter.UmeiTypePagerAdapter;
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
public class UmeiTypeListFragment extends BaseV4Fragment<UmeiTypeJson, UmeiTypeListFragment> {
	private PullToRefreshListView mPullRefreshListView;
	private List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	private UmeiTypeAdapter mUmeiTypeAdapter;
	private String url;

	private ViewPager viewpager;
	private UmeiTypeHeightPagerAdapter mUmeiTypePagerAdapter;
	private List<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
	private int pageNo = 1;
	private TextView txt_ChannelTitle, txt_ListDesc;
	private ImageView image_TypePic;
	private View headview,footview;

	public static UmeiTypeListFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiTypeListFragment fragment = new UmeiTypeListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_type_head_foot_listview, container, false);
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_type_head, null);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_umei_type_foot, null);
		
		viewpager = (ViewPager) footview.findViewById(R.id.viewpager);
		txt_ChannelTitle = (TextView) headview.findViewById(R.id.txt_ChannelTitle);
		txt_ListDesc = (TextView) headview.findViewById(R.id.txt_ListDesc);
		image_TypePic = (ImageView) headview.findViewById(R.id.image_TypePic);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// head
		// Fragment fragment = UmeiTypeHeadFragment.newInstance(url,true);
		// FragmentManager manager = getActivity().getSupportFragmentManager();
		// manager.beginTransaction().replace(R.id.layout_head,
		// fragment).commit();

		initValues();
		bindEvent();
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
		
		ListView listview = mPullRefreshListView.getRefreshableView();
		listview.addHeaderView(headview);
		
		mUmeiTypePagerAdapter = new UmeiTypeHeightPagerAdapter(getActivity(), list2);
		viewpager.setAdapter(mUmeiTypePagerAdapter);
		listview.addFooterView(footview);

		mUmeiTypeAdapter = new UmeiTypeAdapter(getActivity(), list);
		mPullRefreshListView.setAdapter(mUmeiTypeAdapter);

		mPullRefreshListView.setMode(Mode.BOTH);
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
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				} else if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_END) {
					pageNo++;
					weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
				}
			}
		});
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				UmeiArticleActivity.startUmeiArticleActivity(getActivity(), list.get((int)id).getHref());
			}
		});
	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		ArrayList<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
		try {
			list = UmeiTypeListService.parseTypeList(url, pageNo);
			list2 = UmeiTypeListService.parseTypeList2(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setChannelTitle(UmeiTypeListService.getChannelTitle());
		mCommonT.setListDesc(UmeiTypeListService.getListDesc());
		mCommonT.setTypePic(UmeiTypeListService.getTypePic());
		mCommonT.setTypeList(list);
		mCommonT.setTypeList2(list2);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		super.onCallback(result);
		Log.i(TAG, "getMode ===" + mPullRefreshListView.getCurrentMode());
		if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
			list.clear();
			list.addAll(result.getTypeList());
			pageNo = 1;
			
			if (result.getTypeList2() != null && result.getTypeList2().size() > 0) {
				list2.clear();
				list2.addAll(result.getTypeList2());
			}
			mUmeiTypePagerAdapter.notifyDataSetChanged();
			
		} else {
			if (result.getTypeList() != null && result.getTypeList().size() > 0) {
				list.addAll(result.getTypeList());
			}
		}

		mUmeiTypeAdapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullRefreshListView.onRefreshComplete();

		

		txt_ChannelTitle.setText(result.getChannelTitle());
		txt_ListDesc.setText(result.getListDesc());
		if (result.getTypePic() != null && result.getTypePic().length() > 0) {
			DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
					.cacheInMemory().cacheOnDisc().build();
			ImageLoader.getInstance().displayImage(result.getTypePic(), image_TypePic, options, getImageLoadingListener());
		}
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
