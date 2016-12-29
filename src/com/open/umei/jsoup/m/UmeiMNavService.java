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

import com.open.umei.bean.m.UmeiMDdBean;
import com.open.umei.bean.m.UmeiMDtBean;
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
public class UmeiMNavService extends CommonService {
	public static final String TAG = UmeiMNavService.class.getSimpleName();

	/**
	 * 解析umei m
	 */
	public static ArrayList<UmeiMDtBean> parseUmeiMNav(String href) {
		ArrayList<UmeiMDtBean> list = new ArrayList<UmeiMDtBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			Element masthead = doc.select("div.nav").first();
			Elements dlElements = masthead.select("dl");
			/**
			 * <dt><a href="http://m.umei.cc/tushuotianxia/">图说天下</a></dt> <dd>
			 * <a href="http://m.umei.cc/tushuotianxia/yulebagua/">娱乐八卦</a></dd>
			 * <dd><a
			 * href="http://m.umei.cc/tushuotianxia/shehuibaitai/">社会百态</a></dd>
			 * <dd><a href="http://m.umei.cc/tushuotianxia/qiwenyishi/">奇闻异事</a>
			 * </dd> <dd><a
			 * href="http://m.umei.cc/tushuotianxia/qinggankoushu/">情感口述</a></dd>
			 * </dl>
			 * <dl>
			 */
			// 解析文件
			if (dlElements != null && dlElements.size() > 1) {
				for (int i = 0; i < dlElements.size(); i++) {
					UmeiMDtBean dtbean = new UmeiMDtBean();
					try {
						Element dtElement = dlElements.get(i).select("dt").first();
						if (dtElement != null) {
							String hrefurl = dtElement.attr("href");
							String dtname = dtElement.text();
							Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl + ";dtname===" + dtname);
							dtbean.setDtName(dtname);
							dtbean.setHref(hrefurl);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Elements ddElements = dlElements.get(i).select("dd");
						if (ddElements != null && ddElements.size() > 0) {
							List<UmeiMDdBean> ddlist = new ArrayList<UmeiMDdBean>();
							UmeiMDdBean ddbean;
							for (int y = 0; y < ddElements.size(); y++) {
								try {
									ddbean = new UmeiMDdBean();
									Element aElement = ddElements.get(y).select("a").first();
									String ddhref = aElement.attr("href");
									String ddname = aElement.text();
									Log.i(TAG, "i===" + i+";y=="+y + "href==" + href + ";ddname===" + ddname);
									ddbean.setDdName(ddname);
									ddbean.setHref(ddhref);
									ddlist.add(ddbean);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							dtbean.setDdlist(ddlist);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					list.add(dtbean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
