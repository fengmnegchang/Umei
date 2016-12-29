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

import com.open.umei.bean.m.UmeiMArcBodyBean;
import com.open.umei.bean.m.UmeiMArcTagBean;
import com.open.umei.json.m.UmeiMArcBodyJson;
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
public class UmeiMArcBodyService extends CommonService {
	public static final String TAG = UmeiMArcBodyService.class.getSimpleName();

	/**
	 * 解析umei m
	 */
	public static UmeiMArcBodyJson parseArcBody(String href) {
		UmeiMArcBodyJson arcbodyJson = new UmeiMArcBodyJson();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);
			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			Element masthead = doc.select("div.arc-bodys").first();
			UmeiMArcBodyBean arcbody = new UmeiMArcBodyBean();
			/**
			 * <div class="arc-bodys" id="ArticleBox">
			 * <p align="center">
			 * <img alt="美女尤物张梓柔私房性感写真身姿诱人"
			 * src="http://i1.umei.cc/uploads/tu/201611/819/lc21tgqr4e3.jpg" />
			 * </p>
			 * </div>
			 */
			// 解析文件
			if (masthead != null) {
				try {
					Element imgElement = masthead.select("img").first();
					if (imgElement != null) {
						String src = imgElement.attr("src");
						String alt = imgElement.attr("alt");
						Log.i(TAG, "src==" + src + ";alt===" + alt);
						arcbody.setArctitle(alt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			/**
			 * <div class="article-pre">
			 * <p>
			 * 上一篇：<a href=
			 * 'http://m.umei.cc/meinvtupian/xingganmeinv/24471.htm'>性感尤物薇薇安私房人体写真秀美乳翘臀</
			 * a >
			 * </p>
			 * <div class="hr"></div>
			 * <p>
			 * 下一篇：<a href=
			 * 'http://m.umei.cc/meinvtupian/xingganmeinv/24473.htm'>性感嫩模Candi情趣内衣写真美臀诱人</
			 * a >
			 * </p>
			 * </div>
			 */
			try {
				Element preElement = doc.select("div.article-pre").first();
				if (preElement != null) {
					try {
						Element pElement = preElement.select("p").first();
						arcbody.setArticlepre(pElement.text());
						arcbody.setArticleprea(pElement.select("a").first().attr("href"));
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element pElement = preElement.select("p").get(1);
						arcbody.setArticlenext(pElement.text());
						arcbody.setArticlenexta(pElement.select("a").first().attr("href"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			 * <div m="arcDESC" class="arc-txt">
			 * <p>
			 * 张梓柔，内地油画画师、兼职模特、头条女神麻豆。美女模特带来一组私房性感写真，上半身赤裸，穿着蕾丝内裤的模特，身材非常火辣诱人，
			 * 美乳和翘臀，让人看了陶醉其中，不能自拔。
			 * </p>
			 * </div>
			 */
			try {
				Element arcElement = doc.select("div.arc-txt").first();
				arcbody.setArcDESC(arcElement.text());
			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			 * <div class="arc-tags"> <a
			 * href="http://m.umei.cc/tags/meiru.htm">美乳</a> <a
			 * href="http://m.umei.cc/tags/meinv.htm">美女</a> <a
			 * href="http://m.umei.cc/tags/xingganxiezhen.htm">性感写真</a>< div
			 * class="art-see">浏览:67次</div></div> </div>
			 */
			try {
				Element seeElement = doc.select("div.art-see").first();
				arcbody.setArtsee(seeElement.text());
			} catch (Exception e) {
				e.printStackTrace();
			}

			arcbodyJson.setArcbody(arcbody);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arcbodyJson;
	}

	/**
	 * 解析umei m
	 */
	public static List<UmeiMArcTagBean> parseArcTag(String href) {
		List<UmeiMArcTagBean> list = new ArrayList<UmeiMArcTagBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);
			Document doc = Jsoup.connect(href).userAgent(UrlUtils.umeiAgent).timeout(10000).get();
			/**
			 * <div class="arc-tags"> <a
			 * href="http://m.umei.cc/tags/meiru.htm">美乳</a> <a
			 * href="http://m.umei.cc/tags/meinv.htm">美女</a> <a
			 * href="http://m.umei.cc/tags/xingganxiezhen.htm">性感写真</a>< div
			 * class="art-see">浏览:67次</div></div> </div>
			 */
			try {
				Element arcElement = doc.select("div.arc-tags").first();
				Elements aElements = arcElement.select("a");
				if (aElements != null && aElements.size() > 0) {
					UmeiMArcTagBean arcTagBean;
					for (int i = 0; i < aElements.size(); i++) {
						arcTagBean = new UmeiMArcTagBean();
						try {
							Element aElement = aElements.get(i).select("a").first();
							arcTagBean.setHref(aElement.attr("href"));
							arcTagBean.setTagname(aElement.text());
							list.add(arcTagBean);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
