/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:44:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.bean.m;

import java.util.List;

import com.open.umei.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:44:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMDtBean extends CommonBean {
	private String href;
	private String dtName;
	private List<UmeiMDdBean> ddlist;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getDtName() {
		return dtName;
	}

	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	public List<UmeiMDdBean> getDdlist() {
		return ddlist;
	}

	public void setDdlist(List<UmeiMDdBean> ddlist) {
		this.ddlist = ddlist;
	}

}
