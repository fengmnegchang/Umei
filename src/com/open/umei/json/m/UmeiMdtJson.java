/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:49:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json.m;

import java.util.List;

import com.open.umei.bean.m.UmeiMDtBean;
import com.open.umei.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午9:49:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMdtJson extends CommonJson {
	private List<UmeiMDtBean> dtlist;

	public List<UmeiMDtBean> getDtlist() {
		return dtlist;
	}

	public void setDtlist(List<UmeiMDtBean> dtlist) {
		this.dtlist = dtlist;
	}

}
