/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:54:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:54:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeMPannelHdJson extends CommonJson {
	private List<UmeMPannelHdBean> list;
	
	private ArrayList<UmeiTypeBean> listv = new ArrayList<UmeiTypeBean>();

	public List<UmeMPannelHdBean> getList() {
		return list;
	}

	public void setList(List<UmeMPannelHdBean> list) {
		this.list = list;
	}

	public ArrayList<UmeiTypeBean> getListv() {
		return listv;
	}

	public void setListv(ArrayList<UmeiTypeBean> listv) {
		this.listv = listv;
	}

}
