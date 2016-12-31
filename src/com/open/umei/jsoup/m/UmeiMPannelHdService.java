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

import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.bean.m.UmeMPannelHdBean;
import com.open.umei.bean.m.UmeiMArcBean;
import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.bean.m.UmeiMPicBean;
import com.open.umei.json.m.UmeiMArcBodyJson;
import com.open.umei.json.m.UmeiMPicJson;
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
											Log.i(TAG, "i===" + i + ";y==" + y + ";atime==" + atime);
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
						 * <strong
						 * class="pannel-hd pannel-hd-line pannel-hd-noline"
						 * >友情链接</strong> 12-29 12:32:52.738:
						 * I/System.out(28488): <div class=
						 * "pic-list pic-list-2 pic-list-shadow index-flink">
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.mmonly.cc/" target="_blank">唯一图库</a>
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.27270.com/" target="_blank">美女图片</a>
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.ituba.cc" target="_blank">爱图吧</a>
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.uumnt.com/" target="_blank">美女图片</a>
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.uumeitu.com" target="_blank">优优美图</a>
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.tesetu.com" target="_blank">特色图</a>
						 * 12-29 12:32:52.738: I/System.out(28488): <a
						 * href="http://m.aitaotu.com" target="_blank">爱套图</a>
						 * 12-29 12:32:52.738: I/System.out(28488): </div>
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

	public static ArrayList<UmeiTypeBean> pannelHead(String href) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			// http://www.umei.cc/bizhitupian/shoujibizhi/1.htm
			// http://www.umei.cc/bizhitupian/shoujibizhi/
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element masthead = doc.select("div.slide").first();
			Elements liElements = masthead.select("li");
			/**
			 * <div id="slideBox" class="slide loading"> 12-29 12:32:52.697:
			 * I/System.out(28488): <div class="db"> 12-29 12:32:52.697:
			 * I/System.out(28488):
			 * <ul>
			 * 12-29 12:32:52.697: I/System.out(28488):
			 * <li><a
			 * href="http://m.umei.cc/meinvtupian/xingganmeinv/24472.htm"> <img
			 * origin="http://i1.umei.cc/uploads/tu/201612/956/c18.jpg"
			 * alt="美女尤物张梓柔私房性感写真身姿诱人"></a></li>
			 * 12-29 12:32:52.697: I/System.out(28488):
			 * <li><a
			 * href="http://m.umei.cc/weimeitupian/feizhuliutupian/25568.htm"
			 * ><img
			 * origin="http://i1.umei.cc/uploads/tu/201612/703/slt010101.png"
			 * alt="森女系唯美意境图片高清壁纸"></a></li>
			 * 12-29 12:32:52.697: I/System.out(28488):
			 * <li><a
			 * href="http://m.umei.cc/meinvtupian/nayimeinv/24787.htm"><img
			 * origin="http://i1.umei.cc/uploads/tu/201612/230/c17.jpg"
			 * alt="美女模特夏小秋秋秋内衣诱惑秀爆乳"></a></li>
			 * 12-29 12:32:52.697: I/System.out(28488):
			 * <li><a
			 * href="http://m.umei.cc/meinvtupian/xingganmeinv/15543.htm"><img
			 * origin="http://i1.umei.cc/uploads/tu/201612/68/c12.jpg"
			 * alt="[IMISS爱蜜社]性感女神sugar小甜心CC内衣写真白皙迷"></a></li>
			 * 12-29 12:32:52.697: I/System.out(28488):
			 * <li><a
			 * href="http://m.umei.cc/meinvtupian/xingganmeinv/21406.htm"><img
			 * origin="http://i1.umei.cc/uploads/tu/201611/640/c16.jpg"
			 * alt="美女少妇真希私房内衣写真秀极致身材"></a></li>
			 * 12-29 12:32:52.697: I/System.out(28488):
			 * </ul>
			 * 12-29 12:32:52.697: I/System.out(28488): </div>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(hrefurl);

					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element imgElement = liElements.get(i).select("img").first();
						String src = imgElement.attr("origin");
						Log.i(TAG, "i===" + i + "src==" + src);
						bean.setSrc(src);

						String typename = imgElement.attr("alt");
						Log.i(TAG, "i===" + i + "typename=" + typename);
						bean.setTypename(typename);
					} catch (Exception e) {
						e.printStackTrace();
					}

					list.add(bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 解析umei m
	 */
	public static ArrayList<UmeMPannelHdBean> parseArcBodyPannelHd(String href) {
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
			Elements divElements = doc.select("div.pannel-hd-line");
			// <h2 class="New-PL_blank"><a
			// href="http://m.umei.cc/katongdongman/">动画图片</a></h2>
			// h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			/**
			 * <h2 class="pannel-hd pannel-hd-line pannel-hd-noline">相关性感美女推荐</h2>
			 * <div class="pic-list pic-list-tag PL-time-TR">
			 * 
			 * <div
			 * class="pannel-hd pannel-hd-line pannel-hd-noline">热门美女图片推荐</div>
			 * <div class="pic-list pic-list-tag pic-list-shadow">
			 */
			parseH2ArcElements(h2Elements, list);
			parseDivElements(divElements, list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * 解析umei m
	 */
	public static ArrayList<UmeMPannelHdBean> parseNavGrid(String href) {
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
			// <h2 class="New-PL_blank"><a
			// href="http://m.umei.cc/katongdongman/">动画图片</a></h2>
			// h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			/**
			 * <h2 class="pannel-hd pannel-hd-line pannel-hd-noline">相关性感美女推荐</h2>
			 * <div class="pic-list pic-list-tag PL-time-TR">
			 * 
			 * <div
			 * class="pannel-hd pannel-hd-line pannel-hd-noline">热门美女图片推荐</div>
			 * <div class="pic-list pic-list-tag pic-list-shadow">
			 */
			parseH2NavElements(h2Elements, list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * 解析umei m
	 */
	public static ArrayList<UmeMPannelHdBean> parseTagGrid(String href) {
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
			// <h2 class="New-PL_blank"><a
			// href="http://m.umei.cc/katongdongman/">动画图片</a></h2>
			// h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			/**
			 * <h2 class="pannel-hd pannel-hd-line pannel-hd-noline">相关性感美女推荐</h2>
			 * <div class="pic-list pic-list-tag PL-time-TR">
			 * 
			 * <div
			 * class="pannel-hd pannel-hd-line pannel-hd-noline">热门美女图片推荐</div>
			 * <div class="pic-list pic-list-tag pic-list-shadow">
			 */
			parseH2TagElements(h2Elements, list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private static void parseDivElements(Elements h2Elements, ArrayList<UmeMPannelHdBean> list) {
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				UmeMPannelHdBean pannelhdbean = new UmeMPannelHdBean();
				try {
					Element hElement = h2Elements.get(i).select("div.pannel-hd").first();
					if (hElement != null) {
						String pannelhdname = hElement.text();
						Log.i(TAG, "i===" + i + "pannelhdname==" + pannelhdname);
						pannelhdbean.setPannelhdname(pannelhdname);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Element hElement = h2Elements.get(i).select("div.pannel-hd").first();
					if (hElement != null) {
						Element divElement = hElement.nextElementSibling();
						/**
						 * <li><a href=
						 * "http://m.umei.cc/meinvtupian/nayimeinv/20764.htm">
						 * <img class="lazy" alt="美女模特蕾丝内衣写真肌肤白皙迷人"
						 * src="http://i1.umei.cc/uploads/tu/201611/16/c6.jpg"
						 * /> <div class="PL-tit">美女模特蕾丝内衣写真肌肤白皙迷人</div></a></li>
						 */
						if (divElement.attr("class").contains("pic-list-tag")) {
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

										String dataoriginal = imgElement.attr("src");
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									piclist.add(picbean);
								}
								pannelhdbean.setPiclist(piclist);
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

	private static void parseH2ArcElements(Elements h2Elements, ArrayList<UmeMPannelHdBean> list) {
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
						 */
						if (divElement.attr("class").contains("pic-list-tag")) {
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
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				list.add(pannelhdbean);
			}
		}
	}
	
	private static void parseH2NavElements(Elements h2Elements, ArrayList<UmeMPannelHdBean> list) {
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
						 */
						if (divElement.attr("class").contains("pic-list-shadow")
								|| divElement.attr("class").endsWith("pic-list pic-list-2")
								|| divElement.attr("class").endsWith("pic-list pic-list-2 pic-list-shadow")) {
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
										if(dataoriginal==null || dataoriginal.length()==0){
											dataoriginal = imgElement.attr("lazysrc");
										}
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									piclist.add(picbean);
								}
								pannelhdbean.setPiclist(piclist);
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
	
	private static void parseH2TagElements(Elements h2Elements, ArrayList<UmeMPannelHdBean> list) {
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
						 */
						if (divElement.attr("class").contains("pic-list-shadow")
								|| divElement.attr("class").contains("pic-list-2")) {
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

										String dataoriginal = imgElement.attr("lazysrc");
										if(dataoriginal==null || dataoriginal.length()==0){
											dataoriginal = imgElement.attr("data-original");
										}
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									piclist.add(picbean);
								}
								pannelhdbean.setPiclist(piclist);
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

	/**
	 * 解析umei m
	 */
	public static UmeiMPicJson parseMPic(String href,int pageNo) {
		UmeiMPicJson mUmeiMPicJson = new UmeiMPicJson();
		ArrayList<UmeiMPicBean> list = new ArrayList<UmeiMPicBean>();
		try {
			if(href.contains(".htm")){
				href = href.replace(".htm", "") + "_" + pageNo + ".htm";
			}else{
				//http://www.umei.cc/bizhitupian/diannaobizhi/_1.htm?
				if(pageNo>1){
					href = href + pageNo + ".htm";
				}
			}
			
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			Elements divElements = doc.select("h1.pannel-hd");
			Elements pagesElements = doc.select("div.pages-list");
			// <h2 class="New-PL_blank"><a
			// href="http://m.umei.cc/katongdongman/">动画图片</a></h2>
			// h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			/**
			 * <h2 class="pannel-hd pannel-hd-line pannel-hd-noline">相关性感美女推荐</h2>
			 * <div class="pic-list pic-list-tag PL-time-TR">
			 * 
			 * <div
			 * class="pannel-hd pannel-hd-line pannel-hd-noline">热门美女图片推荐</div>
			 * <div class="pic-list pic-list-tag pic-list-shadow"> <div
			 * class="pages-list" id="pages-list">
			 */
			parseDivPagesElements(pagesElements, list);

			mUmeiMPicJson.setmUmeiMArcBodyJson(parseMArcBody(divElements));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mUmeiMPicJson.setList(list);

		return mUmeiMPicJson;
	}
	
	/**
	 * 解析umei m
	 */
	public static UmeiMPicJson parseMTagPic(String href,int pageNo) {
		UmeiMPicJson mUmeiMPicJson = new UmeiMPicJson();
		ArrayList<UmeiMPicBean> list = new ArrayList<UmeiMPicBean>();
		try {
			if(href.contains(".htm")){
				href = href.replace(".htm", "") + "_" + pageNo + ".htm";
			}else{
				//http://www.umei.cc/bizhitupian/diannaobizhi/1.htm?
				if(pageNo>1){
					href = href + pageNo + ".htm";
				}
			}
			
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			Elements divElements = doc.select("div.pic-list-tag");
			Elements div2Elements = doc.select("div.pic-list-2");
			Elements h1Elements = doc.select("h1.pannel-hd");
			// <h2 class="New-PL_blank"><a
			// href="http://m.umei.cc/katongdongman/">动画图片</a></h2>
			// h2 class="pannel-hd pannel-hd-line pannel-hd-noline">美女图片推荐</h2>
			/**
			 * <h2 class="pannel-hd pannel-hd-line pannel-hd-noline">相关性感美女推荐</h2>
			 * <div class="pic-list pic-list-tag PL-time-TR">
			 * 
			 * <div
			 * class="pannel-hd pannel-hd-line pannel-hd-noline">热门美女图片推荐</div>
			 * <div class="pic-list pic-list-tag pic-list-shadow"> <div
			 * class="pages-list" id="pages-list">
			 */
			parseDivTagPagesElements(divElements, list);
			parseDiv2TagPagesElements(div2Elements, list);

			mUmeiMPicJson.setmUmeiMArcBodyJson(parseMArcBody(h1Elements));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mUmeiMPicJson.setList(list);

		return mUmeiMPicJson;
	}
	
	private static void parseDivTagPagesElements(Elements h2Elements, ArrayList<UmeiMPicBean> list) {
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				try {
					Element hElement = h2Elements.get(i).select("div.pic-list-tag").first();
					if (hElement != null) {
						if (hElement.attr("class").contains("pic-list-tag")) {
							/**
							 * <li><a href="http://m.umei.cc/p/gaoqing/cn/20160520195233.htm" class="New-PL_blank">
							 * <img alt="美少女制服高清图集 [96P]" lazysrc="http://i1.umei.cc/small/files/s7263.jpg">
							 * <div class="New-PL-tit">美少女制服高清图集 [96P]</div></a></li>
							 */
							Elements liElements = hElement.select("li");
							if (liElements != null && liElements.size() > 0) {
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

										String dataoriginal = imgElement.attr("lazysrc");
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									list.add(picbean);
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
	private static void parseDiv2TagPagesElements(Elements h2Elements, ArrayList<UmeiMPicBean> list) {
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				try {
					Element hElement = h2Elements.get(i).select("div.pic-list-2").first();
					if (hElement != null) {
						if (hElement.attr("class").contains("pic-list-2")) {
							/**
							 * <li><a href="http://m.umei.cc/p/gaoqing/cn/20160520195233.htm" class="New-PL_blank">
							 * <img alt="美少女制服高清图集 [96P]" lazysrc="http://i1.umei.cc/small/files/s7263.jpg">
							 * <div class="New-PL-tit">美少女制服高清图集 [96P]</div></a></li>
							 */
							Elements liElements = hElement.select("li");
							if (liElements != null && liElements.size() > 0) {
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

										String dataoriginal = imgElement.attr("lazysrc");
										if(dataoriginal==null || dataoriginal.length()==0){
											dataoriginal = imgElement.attr("data-original");
										}
										
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									list.add(picbean);
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	private static void parseDivPagesElements(Elements h2Elements, ArrayList<UmeiMPicBean> list) {
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				try {
					Element hElement = h2Elements.get(i).select("div.pages-list").first();
					if (hElement != null) {
						Element divElement = hElement.previousElementSibling().previousElementSibling();
						/*
						 * <li><a href=
						 * "http://m.umei.cc/meinvtupian/xingganmeinv/24515.htm"
						 * class="New-PL_blank"> <img class="lazy"
						 * alt="性感尤物李宓儿酒店大尺度写真巨乳诱人" data-original=
						 * "http://i1.umei.cc/uploads/tu/201612/236/c2.jpg">
						 * <div
						 * class="New-PL-tit">性感尤物李宓儿酒店大尺度写真巨乳诱人</div></a></li>
						 */
						if (divElement.attr("class").contains("pic-list-shadow")
								|| divElement.attr("class").endsWith("pannel")) {
							Elements liElements = divElement.select("li");
							if (liElements != null && liElements.size() > 0) {
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
										if(dataoriginal==null || dataoriginal.length()==0){
											 dataoriginal = imgElement.attr("lazysrc");
										}
										picbean.setDataoriginal(dataoriginal);
										Log.i(TAG, "i===" + i + ";y==" + y + "alt==" + alt + ";dataoriginal==" + dataoriginal);
									} catch (Exception e) {
										e.printStackTrace();
									}
									list.add(picbean);
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	private static UmeiMArcBodyJson parseMArcBody(Elements h2Elements) {
		UmeiMArcBodyJson mUmeiMArcBodyJson = new UmeiMArcBodyJson();
		// 解析文件
		if (h2Elements != null && h2Elements.size() > 0) {
			for (int i = 0; i < h2Elements.size(); i++) {
				try {
					UmeiMArcBodyBean arcbody = new UmeiMArcBodyBean();
					Element hElement = h2Elements.get(i).select("h1.pannel-hd").first();
					if (hElement != null) {
						String pannelhdname = hElement.text();
						Log.i(TAG, "i===" + i + "pannelhdname==" + pannelhdname);
						arcbody.setArctitle(pannelhdname);

						try {
							/**
							 * <div class="spec-box-top arc-txt" m="arcDESC">
							 * <p>
							 * 性感的美女通常是美丽中散发出一些性感，或穿着一些性感服饰，包括低胸上衣，比基尼泳装，内衣外穿，热裤
							 * ，深V上衣，迷你裙等。这些充满性感的美女，总会使男人销魂落魄。
							 * 优美图库性感美女栏目提供海量高清性感美女图片、美女性感图片、性感美女写真、性感美女图片大全等。
							 * </p>
							 * </div>
							 */
							Element specElement = hElement.nextElementSibling();
							String txt_arcDESC = specElement.text();
							Log.i(TAG, "i===" + i + "txt_arcDESC==" + txt_arcDESC);
							arcbody.setArcDESC(txt_arcDESC);
						} catch (Exception e) {

						}
					}
					mUmeiMArcBodyJson.setArcbody(arcbody);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return mUmeiMArcBodyJson;
	}

}
