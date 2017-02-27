package com.open.umei.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.HeaderFooterGridView;
import com.handmark.pulltorefresh.library.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshHeaderFooterGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.umei.R;
import com.open.umei.activity.UmeiArticleGridHeadActivity;
import com.open.umei.activity.m.UmeiMArcBodyListHeadFootActivity;
import com.open.umei.adapter.UmeiTypeAdapter;
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
public class UmeiTypeHeadGridFragment extends BaseV4Fragment<UmeiTypeJson, UmeiTypeHeadGridFragment> implements OnClickListener{
	private PullToRefreshHeaderFooterGridView mPullToRefreshHeadGridView;
	private List<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
	private UmeiTypeAdapter mUmeiTypeAdapter;
	private String url;
	private int pageNo = 1;
	private View headview;
	
	private List<UmeiTypeBean> list2 = new ArrayList<UmeiTypeBean>();
	private TextView txt_ChannelTitle, txt_ListDesc;
	private ImageView image_TypePic;
	
	private View footview;
	public Button text_fisrt;
	public Button text_pre;
	public EditText edit_current;
	public Button text_current;
	public Button text_next;
	public Button text_last;
	public boolean isautomatic;
	public int maxPageNo;

	public static UmeiTypeHeadGridFragment newInstance(String url, boolean isVisibleToUser) {
		UmeiTypeHeadGridFragment fragment = new UmeiTypeHeadGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_umei_type_head_gridview, container, false);
		mPullToRefreshHeadGridView = (PullToRefreshHeaderFooterGridView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_umei_type_head_list_head, null);
		txt_ChannelTitle = (TextView) headview.findViewById(R.id.txt_ChannelTitle);
		txt_ListDesc = (TextView) headview.findViewById(R.id.txt_ListDesc);
		image_TypePic = (ImageView) headview.findViewById(R.id.image_TypePic);
		
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_yiyoutu_pc_grid_page_foot, null);
		text_fisrt = (Button) footview.findViewById(R.id.text_fisrt);
		text_pre = (Button) footview.findViewById(R.id.text_pre);
		edit_current = (EditText) footview.findViewById(R.id.edit_current);
		text_current = (Button) footview.findViewById(R.id.text_current);
		text_next = (Button) footview.findViewById(R.id.text_next);
		text_last = (Button) footview.findViewById(R.id.text_last);
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

		HeaderFooterGridView listview = mPullToRefreshHeadGridView.getRefreshableView();
		listview.addHeaderView(headview);
		listview.addFooterView(footview);
		
//		Fragment fragment = UmeiTypeListHeadFragment.newInstance(url, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.layout_umei_type_list_head, fragment).commit();

		mUmeiTypeAdapter = new UmeiTypeAdapter(getActivity(), list);
		mPullToRefreshHeadGridView.setAdapter(mUmeiTypeAdapter);
		mPullToRefreshHeadGridView.setMode(Mode.BOTH);
		edit_current.setText("" + pageNo);
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
		mPullToRefreshHeadGridView.setOnRefreshListener(new OnRefreshListener<HeaderFooterGridView>() {
			@Override
			public void onRefresh(PullToRefreshBase<HeaderFooterGridView> refreshView) {
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
		text_fisrt.setOnClickListener(this);
		text_pre.setOnClickListener(this);
		text_current.setOnClickListener(this);
		text_next.setOnClickListener(this);
		text_last.setOnClickListener(this);
		edit_current.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
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
		mCommonT.setChannelTitle(UmeiTypeListService.getChannelTitle());
		mCommonT.setListDesc(UmeiTypeListService.getListDesc());
		mCommonT.setTypePic(UmeiTypeListService.getTypePic());
		mCommonT.setTypeList2(list2);
		mCommonT.setTypeList(list);
		mCommonT.setMaxpageno(UmeiTypeListService.getMaxpage());
		return mCommonT;
	}

	@Override
	public void onCallback(UmeiTypeJson result) {
		Log.i(TAG, "getMode ===" + mPullToRefreshHeadGridView.getCurrentMode());
		if (mPullToRefreshHeadGridView.getCurrentMode() == Mode.PULL_FROM_START) {
			if (isautomatic) {
				if (result.getTypeList() != null && result.getTypeList().size() > 0) {
					list.addAll(result.getTypeList());
				}
			}else{
				if (result.getTypeList2() != null && result.getTypeList2().size() > 0) {
					list2.clear();
					list2.addAll(result.getTypeList2());
				}
				list.clear();
				list.addAll(list2);
				list.addAll(result.getTypeList());
				pageNo = 1;
			}

		} else {
			if (result.getTypeList() != null && result.getTypeList().size() > 0) {
				list.addAll(result.getTypeList());
			}
		}

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
		maxPageNo = result.getMaxpageno();
		edit_current.setText("" + pageNo);
		isautomatic = false;
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_fisrt:
			pageNo = 1;
			break;
		case R.id.text_last:
			pageNo = maxPageNo;
			break;
		case R.id.text_pre:
			if (pageNo <= 1) {
				pageNo = 1;
			}
			pageNo = pageNo - 1;
			break;
		case R.id.text_next:
			pageNo = pageNo + 1;
			if (pageNo >= maxPageNo) {
				pageNo = maxPageNo;
			}
			break;
		case R.id.text_current:
			String pageNostr = edit_current.getText().toString();
			if (pageNostr != null) {
				pageNo = Integer.parseInt(pageNostr.replace(" ", ""));
			}
			break;
		default:
			break;
		}
		isautomatic = true;
		weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
	}
}
