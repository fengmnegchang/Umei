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
import com.open.umei.json.UmeiTypeJson;
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
			if (liElements != null && liElements.size() > 0) {
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
	
	public static ArrayList<UmeiTypeBean> parseTypeMainList(String href) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			Document doc = Jsoup.parse(href);
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
			if (liElements != null && liElements.size() > 0) {
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
	
	public static UmeiTypeJson parseTypePCList(String href, int pageNo,int type) {
		UmeiTypeJson mUmeiTypeJson = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			String href2 = href;
			if(pageNo>1){
				href2 = href +"index_"+pageNo+".html";
			}
			Log.i(TAG, "url = " + href2);

			Document doc = Jsoup.connect(href2).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Elements liElements = doc.select("div.padding-bottom");
			/**
			 *  <div class="x2 padding-bottom clearfix">
     <a href="/xingganmeinv/1761.html"
      title="采采芸蕾丝性感诱惑私房照">
      <img src="http://img.yiyoutu.com/titlepic/1761/small.jpg" 
      class="radius-small img-border img-responsive" alt="采采芸蕾丝性感诱惑私房照"></a>
      <div class="media-body">
        <a href="/xingganmeinv/1761.html" title="采采芸蕾丝性感诱惑私房照">采采芸蕾丝性感诱惑私房</a>
      </div>
    </div>

			 */
			// 解析文件
			if (liElements != null && liElements.size() > 0) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					bean.setType(type);
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(UrlUtils.YIYOUTU+hrefurl);
						bean.setTypename(aElement.attr("title"));
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

					list.add(bean);
				}
			}
			
			/**
			 *  <div class="clear"></div>  
		    <ul class="pagination padding-big-top padding-large-bottom">
		&nbsp;<li class='active'><a>1</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_2.html">2</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_3.html">3</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_4.html">4</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_5.html">5</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_6.html">6</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_7.html">7</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_8.html">8</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_9.html">9</a></li>&nbsp;
		<li><a href="/xingganmeinv/index_10.html">10</a></li>
		<li><a href="/xingganmeinv/index_2.html">下一页</a></li>
		<li><a href="/xingganmeinv/index_23.html">尾页</a></li>
		</ul>
		</div>  
			 */
			Element ulElement = doc.select("ul.padding-large-bottom").first();
			if(ulElement!=null){
				Elements liElements2 = ulElement.select("li");
				if(liElements2!=null && liElements2.size()>0){
					Element liElement = liElements2.get(liElements2.size()-1);
					if(liElement!=null){
						Element aElement = liElement.select("a").first();
						String pager =UrlUtils.YIYOUTU+ aElement.attr("href");
						pager = pager.replace(href, "").replace(".html", "").replace("index_", "");
						mUmeiTypeJson.setMaxpageno(Integer.parseInt(pager));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mUmeiTypeJson.setTypeList(list);
		return mUmeiTypeJson;
	}
	
}
