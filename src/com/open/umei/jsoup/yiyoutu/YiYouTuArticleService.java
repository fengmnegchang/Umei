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
package com.open.umei.jsoup.yiyoutu;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.umei.bean.UmeiArticleBean;
import com.open.umei.json.UmeiArticleJson;
import com.open.umei.jsoup.CommonService;
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
public class YiYouTuArticleService extends CommonService {
	public static final String TAG = YiYouTuArticleService.class.getSimpleName();

	public static UmeiArticleJson parseMArticlePagerSize(String href, int pagerno) {
		UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
		ArrayList<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
		try {
			// http://mm.yiyoutu.com/siwa/2064.html
			//http://mm.yiyoutu.com/siwa/2064_2.html
			if (pagerno > 1) {
				href = href.replace(".html", "") +"_"+ pagerno + ".html";
			}

			Log.i(TAG, "url = " + href);
			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			/**
			 * <div id="page">
		   <div class="shang">
		   </div>
		   <content id="content">
		      <article class="post">
		         <div class="post-header">
		            <h2 class="mm-title">美女模特撩人丝袜私房写真(<span id="now">1</span>/<span id="all">10</span>)</h2>
		            <div class="post-data"></div>
		         </div>
		<div class="album disbox-flex disbox-hor">
		    <ul class="disbox-hor" id="photo_list">
		<li class='disbox-flex disbox-center'><img src='http://img.linvshen.com/2017/02/23/6f48b25a988a0b5c5b3db25aed95b4bf.jpg' alt='美女模特撩人丝袜私房写真'></li>     </ul>
		</div>
		</div>
		      </article>
		   </content>
				 <div class="fenye"  style="margin:30px auto 30px auto;display:none"><a href="javascript:goNext()" class="rw">上一篇</a><span class="rw">1/10</span>&nbsp;<a href="/siwa/2064_2.html">下一页</a></div>
		<div>

			 */
			int size = 1;
			Element NewPagesElement = doc.select("div.post-header").first();
			Element aElement = NewPagesElement.select("h2").first();
			String pagersize = aElement.select("span").get(1).text();
			//<h2 class="mm-title">美女模特撩人丝袜私房写真(<span id="now">1</span>/<span id="all">10</span>)</h2>
			try {
				size = Integer.parseInt(pagersize.replace(" ", ""));
				mUmeiArticleJson.setPagersize(size);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UmeiArticleBean bean = new UmeiArticleBean();
			Element ImageBodyElement = doc.select("div.disbox-flex").first();
			/**
			 * <div class="album disbox-flex disbox-hor">
		    <ul class="disbox-hor" id="photo_list">
		<li class='disbox-flex disbox-center'>
		<img src='http://img.linvshen.com/2017/02/23/6f48b25a988a0b5c5b3db25aed95b4bf.jpg' alt='美女模特撩人丝袜私房写真'></li>     </ul>
		</div>
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
					bean.setType(2);
					bean.setSeq(pagerno);
					bean.setUrl(href);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			list.add(bean);
			mUmeiArticleJson.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mUmeiArticleJson;
	}
	
	public static UmeiArticleJson parsePCArticlePagerSize(String href, int pagerno) {
		UmeiArticleJson mUmeiArticleJson = new UmeiArticleJson();
		ArrayList<UmeiArticleBean> list = new ArrayList<UmeiArticleBean>();
		try {
			// http://www.yiyoutu.com/xingganmeinv/1739.html
			//http://www.yiyoutu.com/xingganmeinv/1739_2.html
			String href2=href;
			if (pagerno > 1) {
				  href2 = href.replace(".html", "") +"_"+ pagerno + ".html";
			} 

			Log.i(TAG, "url = " + href2);
			Document doc = Jsoup.connect(href2).userAgent(UrlUtils.userAgent).timeout(10000).get();
			/**
			<div class="text-center padding">
<ul class="pagination pagination-group">
<li class='active'><a>1</a></li>
<li><a href="/xingganmeinv/1739_2.html">2</a></li>
<li><a href="/xingganmeinv/1739_3.html">3</a></li>
<li><a href="/xingganmeinv/1739_4.html">4</a></li>
<li><a href="/xingganmeinv/1739_5.html">5</a></li>
<li><a href="/xingganmeinv/1739_2.html">下一页</a></li>
<li><a href="/xingganmeinv/1739_9.html">尾页</a></li></ul>
</div>
			 */
			int size = 1;
			Element NewPagesElement = doc.select("ul.pagination").first();
			Elements aElements = NewPagesElement.select("li");
			for(int i=0;i<aElements.size();i++){
				String atitle = aElements.get(i).select("a").first().text();
				if(atitle.equals("尾页")){
					try {
						String ahref = UrlUtils.YIYOUTU+aElements.get(i).select("a").first().attr("href");
						ahref = ahref.replace((href.replace(".html", "") +"_"), "").replace(".html", "");
						size = Integer.parseInt(ahref);
						mUmeiArticleJson.setPagersize(size);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
			
			UmeiArticleBean bean = new UmeiArticleBean();
			Element ImageBodyElement = doc.select("p.text-center").first();
			/**
			<div class="detail">
  <h1>嫩模Queenie大长腿私房</h1>
<p class="text-center"><a href='/xingganmeinv/1739_2.html' id="nextlink"><img 
src='http://img.yiyoutu.com/xingganmeinv/2015-07-20/507e7979d5beb7a5f85967a0c99cf7f4.jpg' 
alt='嫩模Queenie大长腿私房'><div class='picdesc'></div></a></p>

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
					bean.setType(4);
					bean.setSeq(pagerno);
					bean.setUrl(href);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			list.add(bean);
			mUmeiArticleJson.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mUmeiArticleJson;
	}

}
