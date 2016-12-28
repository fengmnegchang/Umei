/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午3:03:14
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json;

import java.util.List;

import com.open.umei.bean.SearchResultBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午3:03:14
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SearchResultJson extends CommonJson {

	private List<SearchResultBean> list;

	public List<SearchResultBean> getList() {
		return list;
	}

	public void setList(List<SearchResultBean> list) {
		this.list = list;
	}

}
