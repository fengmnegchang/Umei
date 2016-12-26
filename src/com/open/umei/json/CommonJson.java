/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-26下午3:29:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json;

import java.util.ArrayList;

import com.open.umei.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-26下午3:29:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class CommonJson {
	private ArrayList<CommonBean> list = new ArrayList<CommonBean>();// 导航大图

	public ArrayList<CommonBean> getList() {
		return list;
	}

	public void setList(ArrayList<CommonBean> list) {
		this.list = list;
	}

}
