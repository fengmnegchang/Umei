/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:25:31
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

import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.bean.UmeiArticleInfoBean;
import com.open.umei.bean.UmeiArticleTypeBean;
import com.open.umei.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28上午10:25:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UmeiArticleService extends CommonService {
	public static final String TAG = UmeiArticleService.class.getSimpleName();
	private static UmeiArticleInfoBean articleInfo = new UmeiArticleInfoBean();

	public static ArrayList<UmeiArticleBean> parseArticle(String href, int pageNo) {
		ArrayList<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
		try {
			// http://www.umei.cc/bizhitupian/diannaobizhi/7628_2.htm
			// http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm
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

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			
			try {
				Element ArticleTitleElement = doc.select("div.ArticleTitle").first();
				if(ArticleTitleElement!=null){
					articleInfo.setArticleTitle(ArticleTitleElement.text());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Element ArticleInfosElement = doc.select("div.ArticleInfos").first();
				if(ArticleInfosElement!=null){
					 /**
					  * <span class="time">更新时间：2016-08-18</span>
	<span class="see">浏览：114次</span>
	<span class="column">所属栏目：<a href="http://www.umei.cc/bizhitupian/diannaobizhi/">电脑壁纸</a></span>
	<span class="tips">温馨提示：点击图片进入下一页</span>
	</div>
	<div class="hr10"></div>
	<p class="ArticleDesc"><b class="Tit">图片介绍 : </b>优美图库《电脑壁纸》栏目小编精心为您提供一组《清纯非主流美女图片电脑壁纸》图集，优美图库以高清美图为宗旨倾力打造一个品牌图库网站，更多高清、美图尽在优美图库网（umei.cc）。</p>
					  */
					try {
						String time = ArticleInfosElement.select("span.time").first().text();
						articleInfo.setTime(time);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						String see = ArticleInfosElement.select("span.see").first().text();
						articleInfo.setSee(see);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						String column = ArticleInfosElement.select("span.column").first().text();
						articleInfo.setColumn(column);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						String tips = ArticleInfosElement.select("span.tips").first().text();
						articleInfo.setTips(tips);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Element ArticleDescElement = doc.select("p.ArticleDesc").first();
				if(ArticleDescElement!=null){
					articleInfo.setArticleDesc(ArticleDescElement.text());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Element ImageBodyElement = doc.select("div.ImageBody").first();
			/**
			 * <div class="ImageBody" id="ArticleId60">
			 * <p align="center">
			 * <img alt="清纯非主流美女图片电脑壁纸"
			 * src="http://i1.umei.cc/uploads/tu/201608/2/o1vy4ps11yr.jpg" />
			 * </p>
			 * 
			 * </div>
			 */
			// 解析文件
			if (ImageBodyElement != null) {
				try {
					UmeiArticleBean bean = new UmeiArticleBean();
					Element imgElement = ImageBodyElement.select("img").first();
					String alt = imgElement.attr("alt");
					String src = imgElement.attr("src");
					Log.i(TAG, "alt==" + alt + ";src==" + src);
					bean.setAlt(alt);
					bean.setSrc(src);
					list.add(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static UmeiArticleInfoBean getArticleInfo() {
		return articleInfo;
	}

	public static void setArticleInfo(UmeiArticleInfoBean articleInfo) {
		UmeiArticleService.articleInfo = articleInfo;
	}
	
	public static ArrayList<UmeiArticleTypeBean> articleTypeList(String href) {
		ArrayList<UmeiArticleTypeBean> list = new ArrayList<UmeiArticleTypeBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element articleTypeListElement = doc.select("div.articleTypeList").first();
			Elements liElements = articleTypeListElement.select("li");
			/**
		<li><a href="http://www.umei.cc/bizhitupian/diannaobizhi/" title="电脑壁纸">【电脑壁纸】</a>
		<a href="http://www.umei.cc/bizhitupian/diannaobizhi/14993.htm" title="唯美小清新花卉图片大全">唯美小清新花卉图片大全
		</a><em>2016-12-26</em></li><li><a href="http://www.umei.cc/bizhitupian/shoujibizhi/" title="手机壁纸">【手机壁纸】</a>
		<a href="http://www.umei.cc/bizhitupian/shoujibizhi/15077.htm" title="村上水军可爱MM手机壁纸">村上水军可爱MM手机壁纸</a>
		<em>2016-12-26</em></li>
			 * 
			 * </div>
			 */
			// 解析文件
			if (liElements != null) {
				for(int i=0;i<liElements.size();i++){
					UmeiArticleTypeBean bean = new UmeiArticleTypeBean();
					Elements aElements = liElements.get(i).select("a");
					try {
						String ahref = aElements.get(0).attr("href");
						String title = aElements.get(0).attr("title");
						Log.i(TAG, "href==" + ahref + ";title==" + title);
						bean.setHref(ahref);
						bean.setTitle(title);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						String ahref1 = aElements.get(1).attr("href");
						String title1 = aElements.get(1).attr("title");
						Log.i(TAG, "href==" + ahref1 + ";title==" + title1);
						bean.setHref2(ahref1);
						bean.setTitle2(title1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					try {
						String time = liElements.get(i).select("em").text();
						Log.i(TAG, "time==" + time );
						bean.setTime(time);
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
	
	
	public static ArrayList<UmeiArticleBean> parseArticlePagerSize(String href) {
		ArrayList<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
		try {
			// http://www.umei.cc/bizhitupian/diannaobizhi/7628_2.htm
			// http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);
			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			/**
			 * <div class="NewPages">
				<ul>
					<li><a>共6页: </a></li>
					<li><a href='#'>上一页</a></li>
					<li class="thisclass"><a>1</a></li>
					<li><a href='/bizhitupian/diannaobizhi/7628_2.htm'>2</a></li>
					<li><a href='/bizhitupian/diannaobizhi/7628_3.htm'>3</a></li>
					<li><a href='/bizhitupian/diannaobizhi/7628_4.htm'>4</a></li>
					<li><a href='/bizhitupian/diannaobizhi/7628_5.htm'>5</a></li>
					<li><a href='/bizhitupian/diannaobizhi/7628_2.htm'>下一页</a></li>
				</ul>
			</div>
			 */
			int  size = 1;
			Element NewPagesElement = doc.select("div.NewPages").first();
			if(NewPagesElement!=null){
				Elements liElements = NewPagesElement.select("li");
				if(liElements!=null && liElements.size()>0){
					for(int i=0;i<liElements.size();i++){
						Element aElement = liElements.get(i).select("a").first();
						String pagersize = aElement.text();
						if(pagersize.contains("共") && pagersize.contains("页:")){
							try {
								size = Integer.parseInt(pagersize.replace("共", "").replace("页:", "").replace(" ", ""));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			
			Element ImageBodyElement = doc.select("div.ImageBody").first();
			/**
			 * <div class="ImageBody" id="ArticleId60">
			 * <p align="center">
			 * <img alt="清纯非主流美女图片电脑壁纸"
			 * src="http://i1.umei.cc/uploads/tu/201608/2/o1vy4ps11yr.jpg" />
			 * </p>
			 * 
			 * </div>
			 */
			// 解析文件
			if (ImageBodyElement != null) {
				try {
					UmeiArticleBean bean = new UmeiArticleBean();
					Element imgElement = ImageBodyElement.select("img").first();
					String alt = imgElement.attr("alt");
					String src = imgElement.attr("src");
					Log.i(TAG, "alt==" + alt + ";src==" + src);
					bean.setAlt(alt);
					bean.setSrc(src);
					list.add(bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for(int i=2;i<=size;i++){
				String url = href.replace(".htm?", "").replace(".htm", "")+"_"+i+".htm";
				System.out.println("url=="+url);
				list.add(parseAllArticle(url));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public static UmeiArticleBean parseAllArticle(String href) {
		UmeiArticleBean bean = new UmeiArticleBean();
		try {
			// http://www.umei.cc/bizhitupian/diannaobizhi/7628_2.htm
			// http://www.umei.cc/bizhitupian/diannaobizhi/7628.htm
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);
			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element ImageBodyElement = doc.select("div.ImageBody").first();
			/**
			 * <div class="ImageBody" id="ArticleId60">
			 * <p align="center">
			 * <img alt="清纯非主流美女图片电脑壁纸"
			 * src="http://i1.umei.cc/uploads/tu/201608/2/o1vy4ps11yr.jpg" />
			 * </p>
			 * 
			 * </div>
			 */
			// 解析文件
			if (ImageBodyElement != null) {
				try {
					Element imgElement = ImageBodyElement.select("img").first();
					String alt = imgElement.attr("alt");
					String src = imgElement.attr("src");
					Log.i(TAG, "alt==" + alt + ";src==" + src);
					bean.setAlt(alt);
					bean.setSrc(src);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}
	
}
