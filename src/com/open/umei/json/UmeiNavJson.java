/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27上午10:25:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json;

import java.util.List;

import com.open.umei.bean.UmeiNavBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27上午10:25:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiNavJson extends CommonJson {
	private List<UmeiNavBean> list;

	public List<UmeiNavBean> getList() {
		return list;
	}

	public void setList(List<UmeiNavBean> list) {
		this.list = list;
	}

}
