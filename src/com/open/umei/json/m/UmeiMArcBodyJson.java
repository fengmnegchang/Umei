/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午2:59:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json.m;

import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29下午2:59:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMArcBodyJson extends CommonJson {
	private UmeiMArcBodyBean arcbody;

	public UmeiMArcBodyBean getArcbody() {
		return arcbody;
	}

	public void setArcbody(UmeiMArcBodyBean arcbody) {
		this.arcbody = arcbody;
	}

}
