/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-30上午11:05:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.umei.bean.m.UmeiMPicBean;
import com.open.umei.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-30上午11:05:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMPicJson extends CommonJson {
	private List<UmeiMPicBean> list = new ArrayList<UmeiMPicBean>();
	private UmeiMArcBodyJson mUmeiMArcBodyJson;

	public List<UmeiMPicBean> getList() {
		return list;
	}

	public void setList(List<UmeiMPicBean> list) {
		this.list = list;
	}

	public UmeiMArcBodyJson getmUmeiMArcBodyJson() {
		return mUmeiMArcBodyJson;
	}

	public void setmUmeiMArcBodyJson(UmeiMArcBodyJson mUmeiMArcBodyJson) {
		this.mUmeiMArcBodyJson = mUmeiMArcBodyJson;
	}

}
