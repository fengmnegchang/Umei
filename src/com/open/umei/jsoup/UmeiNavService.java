/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午1:30:55
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
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.UmeiNavBean;
import com.open.umei.bean.UmeiSubNavBean;
import com.open.umei.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午1:30:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiNavService {
	public static final String TAG = UmeiNavService.class.getSimpleName();

	/**
	 * 解析umei 第一级标签
	 */
	public static ArrayList<UmeiNavBean> parseUmeiNav(String href) {
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element masthead = doc.select("ul.Nav").first();
			Elements liElements = masthead.select("li.NavLi");
			/**
			 * <li class="NavLi on" id="Home"><a href="http://www.umei.cc/"
			 * class="MainNav">首页</a></li> <li class="NavLi">
			 * <a href="http://www.umei.cc/bizhitupian/" title="壁纸图片"
			 * class="MainNav">壁纸图片</a> <div class="ShowNav"> <a
			 * href="http://www.umei.cc/bizhitupian/diannaobizhi/"
			 * title="电脑壁纸">电脑壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/shoujibizhi/"
			 * title="手机壁纸">手机壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/dongtaibizhi/"
			 * title="动态壁纸">动态壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/huyanbizhi/"
			 * title="护眼壁纸">护眼壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/meinvbizhi/"
			 * title="美女壁纸">美女壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/xiaoqingxinbizhi/"
			 * title="小清新壁纸">小清新壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/weimeibizhi/"
			 * title="唯美壁纸">唯美壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/fengjingbizhi/"
			 * title="风景壁纸">风景壁纸</a><a
			 * href="http://www.umei.cc/bizhitupian/keaibizhi/"
			 * title="可爱壁纸">可爱壁纸</a> </div></li>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 1; i < liElements.size(); i++) {
					UmeiNavBean sliderNavBean = new UmeiNavBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						String title = aElement.text();
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl + ";title===" + title);
						sliderNavBean.setTitle(title);
						sliderNavBean.setHref(hrefurl);
						// sliderNavBean.setImageUrl(imageurl);
						list.add(sliderNavBean);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 解析umei 第二级标签 ShowNav
	 */
	public static ArrayList<UmeiNavBean> parseShowNav(String href) {
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();

			Element masthead = doc.select("ul.Nav").first();
			Elements liElements = masthead.select("li.NavLi");
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiNavBean bean = new UmeiNavBean();
					try {
						String title;
						Element h2Element = liElements.get(i).select("h2").first();
						if (h2Element != null) {
							title = h2Element.select("a").first().text();
						} else {
							Element aElement = liElements.get(i).select("a").first();
							title = aElement.text().replace("/", "").replace("|", "");
						}
						bean.setTitle(title);
						Log.i(TAG, "i===" + i + "title===" + title);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						String hrefurl;
						Element h2Element = liElements.get(i).select("h2").first();
						if (h2Element != null) {
							hrefurl = h2Element.select("a").first().attr("href");
						} else {
							Element aElement = liElements.get(i).select("a").first();
							hrefurl = aElement.attr("href");
						}
						bean.setHref(hrefurl);
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
					} catch (Exception e) {
						e.printStackTrace();
					}

					/**
					 * <div class="ShowNav"> <h3><a
					 * href="http://www.umei.cc/bizhitupian/diannaobizhi/"
					 * title="电脑壁纸">电脑壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/shoujibizhi/"
					 * title="手机壁纸">手机壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/dongtaibizhi/"
					 * title="动态壁纸">动态壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/huyanbizhi/"
					 * title="护眼壁纸">护眼壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/meinvbizhi/"
					 * title="美女壁纸">美女壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/xiaoqingxinbizhi/"
					 * title="小清新壁纸">小清新壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/weimeibizhi/"
					 * title="唯美壁纸">唯美壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/fengjingbizhi/"
					 * title="风景壁纸">风景壁纸</a></h3> <h3><a
					 * href="http://www.umei.cc/bizhitupian/keaibizhi/"
					 * title="可爱壁纸">可爱壁纸</a></h3> </div>
					 */
					try {
						Element divElement = liElements.get(i).select("div.ShowNav").first();
						if (divElement != null) {
							Elements h3Elements = divElement.select("h3");
							List<UmeiSubNavBean> subNavList = new ArrayList<UmeiSubNavBean>();
							UmeiSubNavBean subNavBean;
							for (int y = 0; y < h3Elements.size(); y++) {
								subNavBean = new UmeiSubNavBean();
								try {
									Element aElement = h3Elements.get(y).select("a").first();
									String atitle = aElement.attr("title");
									String ahref = aElement.attr("href");
									subNavBean.setTitle(atitle);
									subNavBean.setHref(ahref);
									subNavList.add(subNavBean);

									Log.i(TAG, "i===" + i + ";y==" + y + "atitle===" + atitle + ";ahref===" + ahref);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							bean.setSubNavList(subNavList);
						}
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

	public static String makeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if (url.indexOf("?") < 0)
			url.append('?');
		for (String name : params.keySet()) {
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
			// 不做URLEncoder处理
			// url.append(URLEncoder.encode(String.valueOf(params.get(name)),
			// UTF_8));
		}
		return url.toString().replace("?&", "?");
	}
}
