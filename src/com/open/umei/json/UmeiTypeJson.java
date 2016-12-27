/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:23:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json;

import java.util.List;

import com.open.umei.bean.UmeiTypeBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:23:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiTypeJson extends CommonJson {
	private List<UmeiTypeBean> TypeList;
	private List<UmeiTypeBean> TypeList2;
	private String ChannelTitle;
	private String ListDesc;
	private String TypePic;

	public List<UmeiTypeBean> getTypeList() {
		return TypeList;
	}

	public void setTypeList(List<UmeiTypeBean> typeList) {
		TypeList = typeList;
	}

	public List<UmeiTypeBean> getTypeList2() {
		return TypeList2;
	}

	public void setTypeList2(List<UmeiTypeBean> typeList2) {
		TypeList2 = typeList2;
	}

	public String getChannelTitle() {
		return ChannelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		ChannelTitle = channelTitle;
	}

	public String getListDesc() {
		return ListDesc;
	}

	public void setListDesc(String listDesc) {
		ListDesc = listDesc;
	}

	public String getTypePic() {
		return TypePic;
	}

	public void setTypePic(String typePic) {
		TypePic = typePic;
	}

}
