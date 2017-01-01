/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:01:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.json;

import java.util.List;

import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.bean.UmeiArticleInfoBean;
import com.open.umei.bean.UmeiArticleTypeBean;
import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.json.m.UmeiMArcBodyJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:01:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiArticleJson extends CommonJson {
	// 推荐
	private List<UmeiTypeBean> relaxarc;
	// 中心图片
	private List<UmeiArticleBean> list;
	// 头部
	private UmeiArticleInfoBean ArticleInfos;

	private List<UmeiArticleTypeBean> articleTypeList;
	
	private UmeiMArcBodyJson mUmeiMArcBodyJson;
	
	private int pagersize;

	public List<UmeiTypeBean> getRelaxarc() {
		return relaxarc;
	}

	public void setRelaxarc(List<UmeiTypeBean> relaxarc) {
		this.relaxarc = relaxarc;
	}

	public List<UmeiArticleBean> getList() {
		return list;
	}

	public void setList(List<UmeiArticleBean> list) {
		this.list = list;
	}

	public UmeiArticleInfoBean getArticleInfos() {
		return ArticleInfos;
	}

	public void setArticleInfos(UmeiArticleInfoBean articleInfos) {
		ArticleInfos = articleInfos;
	}

	public List<UmeiArticleTypeBean> getArticleTypeList() {
		return articleTypeList;
	}

	public void setArticleTypeList(List<UmeiArticleTypeBean> articleTypeList) {
		this.articleTypeList = articleTypeList;
	}

	public UmeiMArcBodyJson getmUmeiMArcBodyJson() {
		return mUmeiMArcBodyJson;
	}

	public void setmUmeiMArcBodyJson(UmeiMArcBodyJson mUmeiMArcBodyJson) {
		this.mUmeiMArcBodyJson = mUmeiMArcBodyJson;
	}

	public int getPagersize() {
		return pagersize;
	}

	public void setPagersize(int pagersize) {
		this.pagersize = pagersize;
	}

}
