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
public class YiYouTuPCShowImageService extends CommonService {
	public static final String TAG = YiYouTuPCShowImageService.class.getSimpleName();

	public static UmeiTypeJson parseTypeList(String href, int pageNo) {
		UmeiTypeJson mCommonT = new UmeiTypeJson();
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			if(pageNo>1){
				//http://www.yiyoutu.com/xingganmeinv/1739_2.html
				http://www.yiyoutu.com/xingganmeinv/1739.html
				href = href.replace(".html", "") +"_"+pageNo+".html";
			}
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			
			/**
			 * <div class="float-left text-big">
 上一组：<a title="泳池浴中性感美女" href="/xingganmeinv/1740.html">泳池浴中性感美女</a></div>
<div class="float-right text-big">
 下一组：<a title="夏日风情比基尼写真照" href="/xingganmeinv/1737.html">夏日风情比基尼写真照</a></div>

			 */
			
			try {
				Element leftElement = doc.select("div.float-left").first();
				if(leftElement!=null){
					mCommonT.setArticlepre(leftElement.text());
					mCommonT.setArticleprehref(UrlUtils.YIYOUTU+leftElement.select("a").first().attr("href"));
				}
				
				
				Element rightElement = doc.select("div.float-right").first();
				if(rightElement!=null){
					mCommonT.setArticlenext(rightElement.text());
					mCommonT.setArticlenexthref(UrlUtils.YIYOUTU+rightElement.select("a").first().attr("href"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Elements liElements = doc.select("div.detail");
			/**
			 * <div class="detail">
  <h1>嫩模Queenie大长腿私房</h1>
<p class="text-center"><a href='/xingganmeinv/1739_2.html' 
id="nextlink"><img src='http://img.yiyoutu.com/xingganmeinv/2015-07-20/507e7979d5beb7a5f85967a0c99cf7f4.jpg' 
alt='嫩模Queenie大长腿私房'><div class='picdesc'></div></a></p>
  <div class="text-center padding">
<ul class="pagination pagination-group"><li class='active'><a>1</a></li><li><a href="/xingganmeinv/1739_2.html">2</a></li><li><a href="/xingganmeinv/1739_3.html">3</a></li><li><a href="/xingganmeinv/1739_4.html">4</a></li><li><a href="/xingganmeinv/1739_5.html">5</a></li><li><a href="/xingganmeinv/1739_2.html">下一页</a></li><li><a href="/xingganmeinv/1739_9.html">尾页</a></li></ul>
</div>

			 */
			// 解析文件
			if (liElements != null && liElements.size() > 0) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(href);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		mCommonT.setTypeList(list);
		return mCommonT;
	}
	
	 
}
