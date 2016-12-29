/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:10:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.jsoup.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.bean.m.UmeiMArcBean;
import com.open.umei.bean.m.UmeiMPicBean;
import com.open.umei.jsoup.CommonService;
import com.open.umei.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-29上午10:10:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiMPannelHdService extends CommonService {
	public static final String TAG = UmeiMPannelHdService.class.getSimpleName();

	/**
	 * 解析umei m
	 */
	public static ArrayList<UmeMPannelHdBean> parseUmeiMPannelHd(String href) {
		ArrayList<UmeMPannelHdBean> list = new ArrayList<UmeMPannelHdBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			Elements h2Elements = doc.select("h2.pannel-hd-line");
			Elements h2NewElements = doc.select("h2.New-PL_blank");
			Elements strongElements = doc.select("strong.pannel-hd");
			// <h2 class="New-PL_blank"><a
			// href="http://m.umei.cc/katongdongman/">动画图片</a></h2>
			// h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			/**
			 * <h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			 * <div class="pic-list pic-list-2 pic-list-shadow">
			 */
			parseH2Elements(h2Elements, list);
			parseH2Elements(h2NewElements, list);
			parseStrongElements(strongElements, list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private static void parseH2Elements(Elements h2Elements, ArrayList<UmeMPannelHdBean> list) {
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				UmeMPannelHdBean pannelhdbean = new UmeMPannelHdBean();
				try {
					Element hElement = h2Elements.get(i).select("h2").first();
					if (hElement != null) {
						String pannelhdname = hElement.text();
						Log.i(TAG, "i===" + i + "pannelhdname==" + pannelhdname);
						pannelhdbean.setPannelhdname(pannelhdname);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Element hElement = h2Elements.get(i).select("h2").first();
					if (hElement != null) {
						Element divElement = hElement.nextElementSibling();
						/**
						 * <li><a href=
						 * "http://m.umei.cc/meinvtupian/waiguomeinv/12662.htm"
						 * class="New-PL_blank"> <img class="lazy"
						 * alt="日本丰腴少女销魂傲人双胸诱惑你" data-original=
						 * "http://i1.umei.cc/uploads/tu/201612/262/c26.jpg">
						 * <div class="New-PL-tit">日本丰腴少女销魂傲人双胸诱惑你</div></a></li>
						 */
						if (divElement.attr("class").contains("pic-list-shadow")) {
							Elements liElements = divElement.select("li");
							if (liElements != null && liElements.size() > 0) {
								List<UmeiMPicBean> piclist = new ArrayList<UmeiMPicBean>();
								UmeiMPicBean picbean;
								for (int y = 0; y < liElements.size(); y++) {
									picbean = new UmeiMPicBean();
									try {
										Element aElement = liElements.get(y).select("a").first();
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

										String dataoriginal = imgElement.attr("data-original");
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									piclist.add(picbean);
								}
								pannelhdbean.setPiclist(piclist);
							}

							/**
							 * <div class="arc-list arc-list-type">
							 * <ul>
							 * <li><a href=
							 * "http://m.umei.cc/gaoxiaotupian/nahantupian/"
							 * >内涵图片</a> <a href=
							 * "http://m.umei.cc/gaoxiaotupian/nahantupian/17526.htm"
							 * >葫芦娃高清邪恶内涵图片</a> <span>2016-12-27</span></li>
							 * <li><a href=
							 * "http://m.umei.cc/gaoxiaotupian/baoxiaotupian/"
							 * >爆笑图片</a><a href=
							 * "http://m.umei.cc/gaoxiaotupian/baoxiaotupian/9190.htm"
							 * >不能开你他妈装什么门爆笑图片</a><span>2016-12-27</span></li>
							 * <li><a href=
							 * "http://m.umei.cc/gaoxiaotupian/gaoxiaodongtaitupian/"
							 * >搞笑动态图片</a><a href=
							 * "http://m.umei.cc/gaoxiaotupian/gaoxiaodongtaitupian/19980.htm"
							 * >这个球把奶奶坑了搞笑动态图片</a><span>2016-12-27</span></li>
							 * <li><a href=
							 * "http://m.umei.cc/gaoxiaotupian/baoxiaotupian/"
							 * >爆笑图片</a><a href=
							 * "http://m.umei.cc/gaoxiaotupian/baoxiaotupian/9191.htm"
							 * >得罪老婆的下场爆笑图片大全</a><span>2016-12-21</span></li>
							 * 
							 * </ul>
							 * </div>
							 */
							Element divarcElement = divElement.nextElementSibling();
							if (divarcElement.attr("class").contains("arc-list-type")) {
								Elements liarcElements = divarcElement.select("li");
								if (liarcElements != null && liarcElements.size() > 0) {
									List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
									UmeiMArcBean arcbean;
									for (int y = 0; y < liarcElements.size(); y++) {
										arcbean = new UmeiMArcBean();
										try {
											Element aElement = liarcElements.get(y).select("a").get(0);
											String ahref = aElement.attr("href");
											String atitle = aElement.text();
											arcbean.setHref(ahref);
											arcbean.setTitle(atitle);
											Log.i(TAG, "i===" + i + ";y==" + y + ";ahref==" + ahref + ";atitle==" + atitle);
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											Element aElement = liarcElements.get(y).select("a").get(1);
											String ahref = aElement.attr("href");
											String atitle = aElement.text();
											arcbean.setHref2(ahref);
											arcbean.setTitle2(atitle);
											Log.i(TAG, "i===" + i + ";y==" + y + ";ahref==" + ahref + ";atitle==" + atitle);
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										try {
											Element aElement = liarcElements.get(y).select("span").first();
											String atime = aElement.text();
											arcbean.setArctime(atime);
											Log.i(TAG, "i===" + i + ";y==" + y + ";atime==" + atime );
										} catch (Exception e) {
											e.printStackTrace();
										}
										arclist.add(arcbean);
									}
									pannelhdbean.setArclist(arclist);
								}
							}
						}

						if (divElement.attr("class").contains("arc-tags")) {
							Elements aElements = divElement.select("a");
							if (aElements != null && aElements.size() > 0) {
								List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
								UmeiMArcBean arcbean;
								for (int y = 0; y < aElements.size(); y++) {
									arcbean = new UmeiMArcBean();
									try {
										// <a
										// href="http://m.umei.cc/tags/nenmo.htm">嫩模</a>
										Element aElement = aElements.get(y).select("a").first();
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
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(pannelhdbean);
			}
		}
	}
	
	private static void parseStrongElements(Elements h2Elements, ArrayList<UmeMPannelHdBean> list) {
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				UmeMPannelHdBean pannelhdbean = new UmeMPannelHdBean();
				try {
					Element hElement = h2Elements.get(i).select("strong").first();
					if (hElement != null) {
						String pannelhdname = hElement.text();
						Log.i(TAG, "i===" + i + "pannelhdname==" + pannelhdname);
						pannelhdbean.setPannelhdname(pannelhdname);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Element hElement = h2Elements.get(i).select("strong").first();
					if (hElement != null) {
						Element divElement = hElement.nextElementSibling();
						/**
						 * <strong class="pannel-hd pannel-hd-line pannel-hd-noline">友情链接</strong> 
12-29 12:32:52.738: I/System.out(28488):   <div class="pic-list pic-list-2 pic-list-shadow index-flink"> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.mmonly.cc/" target="_blank">唯一图库</a> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.27270.com/" target="_blank">美女图片</a> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.ituba.cc" target="_blank">爱图吧</a> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.uumnt.com/" target="_blank">美女图片</a> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.uumeitu.com" target="_blank">优优美图</a> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.tesetu.com" target="_blank">特色图</a> 
12-29 12:32:52.738: I/System.out(28488):    <a href="http://m.aitaotu.com" target="_blank">爱套图</a> 
12-29 12:32:52.738: I/System.out(28488):   </div> 

						 */
						if (divElement.attr("class").contains("pic-list-shadow")) {
							Elements aElements = divElement.select("a");
							if (aElements != null && aElements.size() > 0) {
								List<UmeiMArcBean> arclist = new ArrayList<UmeiMArcBean>();
								UmeiMArcBean arcbean;
								for (int y = 0; y < aElements.size(); y++) {
									arcbean = new UmeiMArcBean();
									try {
										// <a
										// href="http://m.umei.cc/tags/nenmo.htm">嫩模</a>
										Element aElement = aElements.get(y).select("a").first();
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
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(pannelhdbean);
			}
		}
	}


}
