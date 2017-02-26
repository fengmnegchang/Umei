/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:20:39
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.bean;

/**
 ***************************************************************************************************************************************************************************** 
 * 电脑壁纸 类型 <li>
 * <a href="http://www.umei.cc/bizhitupian/diannaobizhi/14994.htm"
 * class="TypeBigPics" target="_blank"> <img
 * src="http://i1.umei.cc/uploads/tu/201612/838/slt25.png" width="180"
 * height="270" /> <span>清新花草高清电脑桌面壁纸</span></a> <div class="TypePicInfos"> <div
 * class="txtInfo gray"><em class="IcoList">查看：62次</em>
 * <em class="IcoTime">12-23</em></div> </div></li>
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:20:39
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiTypeBean extends CommonBean {
	private String href;
	private String src;
	private String IcoList;
	private String IcoTime;
	private String typename;
	private int type;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getIcoList() {
		return IcoList;
	}

	public void setIcoList(String icoList) {
		IcoList = icoList;
	}

	public String getIcoTime() {
		return IcoTime;
	}

	public void setIcoTime(String icoTime) {
		IcoTime = icoTime;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
