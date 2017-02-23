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
package com.open.umei.jsoup.yiyoutu;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.UmeiTypeBean;
import com.open.umei.jsoup.CommonService;
import com.open.umei.utils.UrlUtils;

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
public class YiYouTuNavPullListService extends CommonService {
	public static final String TAG = YiYouTuNavPullListService.class.getSimpleName();

	public static ArrayList<UmeiTypeBean> parseTypeList(String href, int pageNo) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			if(pageNo>1){
				href = href +"index_"+pageNo+".html";
			}
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Elements liElements = doc.select("article.post");
			/**
			 * <article id="post-1" class="post">
      <div class="post-header">
        <h2 class="post-title"><a class="post-title-link" href="/xinggan/2059.html" rel="bookmark">
        性感美女翘臀火辣写</a></h2>
      </div>
      <div class="post-content post-text">
      <a href="/xinggan/2059.html">
      <img src="http://img.linvshen.com/2017/02/23/1ef97973e6836cd2fd98dbb07f70aaec.jpg" 
      data-img="http://img.linvshen.com/2017/02/23/1ef97973e6836cd2fd98dbb07f70aaec.jpg" 
      alt="性感美女翘臀火辣写真图片" /></a>
        <p></p>
      </div>
      <div class="post-footer"><span class="post-meta">2017-02-23 10:45:48</span></div>
    </article>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(UrlUtils.YIYOUTU_M+hrefurl);
						bean.setTypename(aElement.text());
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element imgElement = liElements.get(i).select("img").first();
						String src = imgElement.attr("src");
						Log.i(TAG, "i===" + i + "src==" + src);
						bean.setSrc(src);
						bean.setTypename(imgElement.attr("alt"));
					} catch (Exception e) {
						e.printStackTrace();
					}


					try {
						Element IcoTimeElement = liElements.get(i).select("span.post-meta").first();
						String IcoTime = IcoTimeElement.text();
						Log.i(TAG, "i===" + i + "IcoTime=" + IcoTime);
						bean.setIcoList(IcoTime);
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
