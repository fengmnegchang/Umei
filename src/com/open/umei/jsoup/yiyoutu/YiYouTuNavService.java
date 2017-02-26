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
package com.open.umei.jsoup.yiyoutu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.UmeiNavBean;
import com.open.umei.bean.UmeiSubNavBean;
import com.open.umei.jsoup.CommonService;
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
public class YiYouTuNavService extends CommonService {
	public static final String TAG = YiYouTuNavService.class.getSimpleName();

	/**
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

			Element masthead = doc.select("div.main-nav").first();
			Elements liElements = masthead.select("a");
			UmeiNavBean bean = new UmeiNavBean();
			// 解析文件
			if (liElements != null && liElements.size() > 0) {
				List<UmeiSubNavBean> subNavList = new ArrayList<UmeiSubNavBean>();
				UmeiSubNavBean subNavBean;
				for (int i = 0; i < liElements.size(); i++) {
					try {
						subNavBean = new UmeiSubNavBean();
						try {
							Element aElement = liElements.get(i).select("a").first();
							String atitle = aElement.text();
							String ahref = aElement.attr("href");
							subNavBean.setTitle(atitle);
							subNavBean.setHref(UrlUtils.YIYOUTU_M+ahref);
							subNavList.add(subNavBean);
							Log.i(TAG, "i===" + i + ";atitle===" + atitle + ";ahref===" + ahref);
						} catch (Exception e) {
							e.printStackTrace();
						}
						bean.setSubNavList(subNavList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			list.add(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public static ArrayList<UmeiNavBean> parseShowPCNav(String href) {
		ArrayList<UmeiNavBean> list = new ArrayList<UmeiNavBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();

			Element masthead = doc.select("div.navbar-body").first();
			Elements liElements = masthead.select("a");
			UmeiNavBean bean = new UmeiNavBean();
			// 解析文件
			if (liElements != null && liElements.size() > 0) {
				List<UmeiSubNavBean> subNavList = new ArrayList<UmeiSubNavBean>();
				UmeiSubNavBean subNavBean;
				for (int i = 0; i < liElements.size(); i++) {
					try {
						subNavBean = new UmeiSubNavBean();
						try {
							Element aElement = liElements.get(i).select("a").first();
							String atitle = aElement.text();
							String ahref = aElement.attr("href");
							subNavBean.setTitle(atitle);
							subNavBean.setHref(UrlUtils.YIYOUTU+ahref);
							subNavList.add(subNavBean);
							Log.i(TAG, "i===" + i + ";atitle===" + atitle + ";ahref===" + ahref);
						} catch (Exception e) {
							e.printStackTrace();
						}
						bean.setSubNavList(subNavList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			list.add(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
