package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
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
public class UmeiTypeListHeadFragment extends BaseV4Fragment<UmeiTypeJson, UmeiTypeListHeadFragment> {
	private String url;
	private ViewPager viewpager;
	private UmeiTypeHeightPagerAdapter mUmeiTypePagerAdapter;
	private List<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
	private TextView txt_ChannelTitle, txt_ListDesc;
	private ImageView image_TypePic;

	public static UmeiTypeListHeadFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiTypeListHeadFragment fragment = new UmeiTypeListHeadFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_type_list_head, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		txt_ChannelTitle = (TextView) view.findViewById(R.id.txt_ChannelTitle);
		txt_ListDesc = (TextView) view.findViewById(R.id.txt_ListDesc);
		image_TypePic = (ImageView) view.findViewById(R.id.image_TypePic);
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

		mUmeiTypePagerAdapter = new UmeiTypeHeightPagerAdapter(getActivity(), list2);
		viewpager.setAdapter(mUmeiTypePagerAdapter);

	}

	@Override
	public UmeiTypeJson call() throws Exception {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
		try {
			list2 = UmeiTypeListService.parseTypeList2(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setChannelTitle(UmeiTypeListService.getChannelTitle());
		mCommonT.setListDesc(UmeiTypeListService.getListDesc());
		mCommonT.setTypePic(UmeiTypeListService.getTypePic());
		mCommonT.setTypeList2(list2);
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		super.onCallback(result);

		if (result.getTypeList2() != null && result.getTypeList2().size() > 0) {
			list2.clear();
			list2.addAll(result.getTypeList2());
		}
		mUmeiTypePagerAdapter.notifyDataSetChanged();

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
