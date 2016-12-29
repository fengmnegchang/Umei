/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午3:44:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json.m;

import java.util.ArrayList;
import java.util.List;

import com.open.umei.bean.m.UmeiMArcTagBean;
import com.open.umei.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午3:44:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcTagJson extends CommonJson {

	private List<UmeiMArcTagBean> list = new ArrayList<UmeiMArcTagBean>();

	public List<UmeiMArcTagBean> getList() {
		return list;
	}

	public void setList(List<UmeiMArcTagBean> list) {
		this.list = list;
	}

}
