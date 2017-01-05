/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:50:50
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.bean.m;

import java.util.ArrayList;
import java.util.List;

import com.open.umei.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:50:50
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeMPannelHdBean extends CommonBean {
	private String pannelhdname;
	private List<UmeiMPicBean> piclist = new ArrayList<UmeiMPicBean>();
	private List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
	
	//头列表
	private List<UmeiMPicBean> nameList = new ArrayList<UmeiMPicBean>();

	public String getPannelhdname() {
		return pannelhdname;
	}

	public void setPannelhdname(String pannelhdname) {
		this.pannelhdname = pannelhdname;
	}

	public List<UmeiMPicBean> getPiclist() {
		return piclist;
	}

	public void setPiclist(List<UmeiMPicBean> piclist) {
		this.piclist = piclist;
	}

	public List<UmeiMArcBean> getArclist() {
		return arclist;
	}

	public void setArclist(List<UmeiMArcBean> arclist) {
		this.arclist = arclist;
	}

	public List<UmeiMPicBean> getNameList() {
		return nameList;
	}

	public void setNameList(List<UmeiMPicBean> nameList) {
		this.nameList = nameList;
	}

}
