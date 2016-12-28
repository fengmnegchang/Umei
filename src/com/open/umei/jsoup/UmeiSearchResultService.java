/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:45:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.jsoup;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.SearchResultBean;
import com.open.umei.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * http://www.umei.cc/bizhitupian/diannaobizhi/
 * 
 * @author :fengguangjing
 * @createTime:2016-12-27下午2:45:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiSearchResultService extends CommonService {
	public static final String TAG = UmeiSearchResultService.class.getSimpleName();

	public static ArrayList<SearchResultBean> parseSearch(String href, int pageNo) {
		ArrayList<SearchResultBean> list = new ArrayList<SearchResultBean>();
		try {
			href = href + "&p="+pageNo+"&nsid=";
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();

			Element masthead = doc.select("div.content-main").first();
			Elements liElements = masthead.select("div.result");
			/**
			 * <div class="result f s0" style='margin-bottom:15px;'> <h3 class="c-title">
			 * <a rpos="" cpos="title" href=
			 * "http://www.umei.cc/p/gaoqing/xiuren_VIP/20151123105629.htm"
			 * style=
			 * 'font-family:Arial,SimSun,sans-serif;font-size:16px;color:#0000cc
			 * ; ' target="_blank">【UXING】 2015.11.22 VOL.028 懒<em>桃子</em> 56P -
			 * 优美图库</a></h3> <div> <div class="c-content"> <div
			 * class="c-abstract" style=
			 * 'font-family:Arial,SimSun,sans-serif;font-size:13px;color:#000000
			 * ; margin:0px 0;'> 优美图库《秀人模特》栏目小编精心为您提供一组《【UXING】 2015.11.22
			 * VOL.028 懒<em>桃子</em> 56P》图集,优美图库以高清美图为宗旨倾力打造一个品牌图库网站,更多... </div>
			 * <div> <span class="c-showurl" style=
			 * 'font-family:Arial,SimSun,sans-serif;font-size:13px;color:#2a8055;'>www.umei.cc/p/gaoqing/xiuren_VIP/201..
			 * . 2015-11-23</span> </div> </div> </div> </div>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					SearchResultBean bean = new SearchResultBean();
					try {
						Element aElement = liElements.get(i).select("h3.c-title").first().select("a").first();
						String hrefurl = aElement.attr("href");
						String target = aElement.text();
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl + ";target=" + target);
						bean.setHref(hrefurl);
						bean.setTarget(target);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element contentElement = liElements.get(i).select("div.c-content").first();
						if(contentElement==null){
							contentElement = liElements.get(i).select("div.c-abstract-image").first();
						}
						String content = contentElement.text();
						Log.i(TAG, "i===" + i + "content==" + content);
						bean.setContent(content);
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

}
