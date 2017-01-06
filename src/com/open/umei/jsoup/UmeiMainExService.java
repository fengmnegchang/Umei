/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午10:05:50
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.bean.m.UmeiMArcBean;
import com.open.umei.bean.m.UmeiMPicBean;
import com.open.umei.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-1-5上午10:05:50
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMainExService extends CommonService {
	public static final String TAG = UmeiMainExService.class.getSimpleName();

	/**
	 * 解析umei m
	 */
	public static ArrayList<UmeMPannelHdBean> parseUmeiEx(String href) {
		ArrayList<UmeMPannelHdBean> list = new ArrayList<UmeMPannelHdBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			Elements divElements = doc.select("div.ChannelTitle");
			if (divElements != null && divElements.size() > 0) {
				for (int i = 0; i < divElements.size(); i++) {
					UmeMPannelHdBean pannelhdbean = new UmeMPannelHdBean();
					try {
						Elements aElements = divElements.get(i).select("a");
						if (aElements != null && aElements.size() > 0) {
							List<UmeiMPicBean> nameList = new ArrayList<UmeiMPicBean>();
							UmeiMPicBean nameTagbean;
							for (int j = 0; j < aElements.size(); j++) {
								nameTagbean = new UmeiMPicBean();
								try {
									// <a href="http://www.umei.cc/meinvtupian/"
									// title="美女图片">美女图片</a>
									Element aElement = aElements.get(j).select("a").first();
									String ahref = aElement.attr("href");
									String atitle = aElement.text();
									nameTagbean.setHref(ahref);
									nameTagbean.setAlt(atitle);
								} catch (Exception e) {
									e.printStackTrace();
								}
								nameList.add(nameTagbean);
							}
							pannelhdbean.setPannelhdname(nameList.get(0).getAlt());
							pannelhdbean.setNameList(nameList);
						} else {
							pannelhdbean.setPannelhdname(divElements.get(i).text());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// 图片
					Element PicListElement = divElements.get(i).nextElementSibling();
					if (PicListElement != null) {
						if (PicListElement.attr("class").contains("PicList")) {
							Elements liElements = PicListElement.select("li");
							List<UmeiMPicBean> piclist = new ArrayList<UmeiMPicBean>();
							UmeiMPicBean picbean;
							for (int y = 0; y < liElements.size(); y++) {
								Element liElement = liElements.get(y).select("li").first();
								if ("BigTag".equals(liElement.attr("class"))) {
									try {
										List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
										UmeiMArcBean arcbean;
										Elements aaElements = liElement.select("a");
										for (int j = 0; j < aaElements.size(); j++) {
											// 友情链接
											arcbean = new UmeiMArcBean();
											try {
												// <li class="BigTag">
												// <a
												// href="http://www.umei.cc/tags/xiezhen.htm"
												// target="_blank"
												// title="写真">写真</a>
												Element aElement = aaElements.get(j).select("a").first();
												String ahref = aElement.attr("href");
												String atitle = aElement.text();
												arcbean.setHref(ahref);
												arcbean.setTitle(atitle);
												Log.i(TAG, "y===" + y + ";j==" + j + ";ahref==" + ahref + ";atitle==" + atitle);
											} catch (Exception e) {
												e.printStackTrace();
											}
											arclist.add(arcbean);
										}
										pannelhdbean.setArclist(arclist);
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									/**
									 * <li class="BigPic">
									 * <a href=
									 * "http://www.umei.cc/meinvtupian/meinvmote/13386.htm"
									 * title="[Ugirls尤果网]美女王司桐酒店性感写真美胸诱人"
									 * target="_blank"> <img src=
									 * "http://i1.umei.cc/uploads/tu/201612/324/c16.jpg"
									 * width="380" height="270"
									 * alt="[Ugirls尤果网]美女王司桐酒店性感写真美胸诱人" />
									 * <span>
									 * [Ugirls尤果网]美女王司桐酒店性感写真美胸诱人</span></a></li>
									 */

									/**
									 * <li><a href=
									 * "http://www.umei.cc/weimeitupian/oumeitupian/20365.htm"
									 * title="性感黑白欧美女生图片" target="_blank"> <img
									 * src=
									 * "http://i1.umei.cc/uploads/tu/201701/35/slt7.png"
									 * alt="性感黑白欧美女生图片" width="180" height="270"
									 * /></a> <span><a href=
									 * "http://www.umei.cc/weimeitupian/oumeitupian/20365.htm"
									 * title="性感黑白欧美女生图片"
									 * target="_blank">性感黑白欧美女生图片</a>
									 * </span><div class="txtInfo gray">
									 * <em class="IcoList">
									 *  <a href="http://www.umei.cc/weimeitupian/oumeitupian/" title="欧美图片">欧美图片</a>
									 *  </em><em class="IcoTime">01-03</em>
									 * </div></li>
									 */

									try {
										picbean = new UmeiMPicBean();
										try {
											Element aElement = liElement.select("a").first();
											String ahref = aElement.attr("href");
											picbean.setHref(ahref);
											Log.i(TAG, "i===" + i + ";y==" + y + "ahref==" + ahref);
										} catch (Exception e) {
											e.printStackTrace();
										}

										try {
											Element imgElement = liElements.get(y).select("img").first();
											String alt = imgElement.attr("alt");
											picbean.setAlt(alt);

											String dataoriginal = imgElement.attr("src");
											picbean.setDataoriginal(dataoriginal);
											Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
										} catch (Exception e) {
											e.printStackTrace();
										}
										piclist.add(picbean);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							pannelhdbean.setPiclist(piclist);
						} else if (PicListElement.attr("class").contains("FindLink")) {
							try {
								Elements aaElements = PicListElement.select("a");
								List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
								UmeiMArcBean arcbean;
								for (int y = 0; y < aaElements.size(); y++) {
									// 友情链接
									arcbean = new UmeiMArcBean();
									try {
										// <a href='http://www.27270.com/'
										// target='_blank'>27270图片大全</a>
										Element aElement = aaElements.get(y).select("a").first();
										String ahref = aElement.attr("href");
										String atitle = aElement.text();
										arcbean.setHref(ahref);
										arcbean.setTitle(atitle);
										Log.i(TAG, "i===" + i + ";y==" + y + ";ahref==" + ahref + ";atitle==" + atitle);
									} catch (Exception e) {
										e.printStackTrace();
									}
									arclist.add(arcbean);
								}
								pannelhdbean.setArclist(arclist);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
					
					if(pannelhdbean.getPannelhdname().equals("国内")){
						Element neiLinkElement = doc.select("div.neiLink").first();
						if(neiLinkElement!=null){
							try {
								//<a href="http://www.umei.cc/tags/juru.htm" target="_blank" title="巨乳">巨乳</a>
								Elements aaElements = neiLinkElement.select("a");
								List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
								UmeiMArcBean arcbean;
								for (int y = 0; y < aaElements.size(); y++) {
									arcbean = new UmeiMArcBean();
									try {
										Element aElement = aaElements.get(y).select("a").first();
										String ahref = aElement.attr("href");
										String atitle = aElement.text();
										arcbean.setHref(ahref);
										arcbean.setTitle(atitle);
										Log.i(TAG, "i===" + i + ";y==" + y + ";ahref==" + ahref + ";atitle==" + atitle);
									} catch (Exception e) {
										e.printStackTrace();
									}
									arclist.add(arcbean);
								}
								pannelhdbean.setArclist(arclist);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					list.add(pannelhdbean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static ArrayList<UmeiTypeBean> parseTypeList(String href) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			// <div class="SpecBox indexSpec fc">
			Element divElement = doc.select("div.SpecBox").first();
			if (divElement != null) {
				Elements liElements = divElement.select("li");
				if (liElements != null && liElements.size() > 0) {
					UmeiTypeBean typebean;
					for (int i = 0; i < liElements.size(); i++) {
						typebean = new UmeiTypeBean();

						try {
							// <a
							// href="/meinvtupian/waiguomeinv/hanguomeinv.htm"
							// title="韩国美女">
							// <img
							// src="http://i1.umei.cc/uploads/tu/201608/164/hanguomeinv.jpg"
							// width="220" height="170" alt="韩国美女">
							// <span>韩国美女</span></a>
							Element aElement = liElements.get(i).select("a").first();
							String ahref = aElement.attr("href");
							String atitle = aElement.text();
							typebean.setHref(UrlUtils.UMEI_HTTP + ahref);
							typebean.setTypename(atitle);
							Log.i(TAG, "i===" + i + ";ahref==" + ahref + ";atitle==" + atitle);
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							// <a
							// href="/meinvtupian/waiguomeinv/hanguomeinv.htm"
							// title="韩国美女">
							// <img
							// src="http://i1.umei.cc/uploads/tu/201608/164/hanguomeinv.jpg"
							// width="220" height="170" alt="韩国美女">
							// <span>韩国美女</span></a>
							Element imgElement = liElements.get(i).select("img").first();
							String src = imgElement.attr("src");
							typebean.setSrc(src);
							Log.i(TAG, "i===" + i + ";src==" + src);
						} catch (Exception e) {
							e.printStackTrace();
						}

						list.add(typebean);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
