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

import com.open.umei.bean.UmeiTypeBean;
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
public class UmeiTypeListService extends CommonService {
	public static final String TAG = UmeiTypeListService.class.getSimpleName();
	private static String ChannelTitle;
	private static String ListDesc;
	private static String TypePic;

	public static ArrayList<UmeiTypeBean> parseTypeList(String href, int pageNo) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			// http://www.umei.cc/bizhitupian/shoujibizhi/1.htm
			// http://www.umei.cc/bizhitupian/shoujibizhi/
			if (href.contains("search.php?")) {
				if(pageNo>1){
					//https://www.umei.cc/umplus/search.php?keyword=%E7%BE%8E%E5%A5%B3&searchtype=titlekeyword&channeltype=0&orderby=&kwtype=0&pagesize=24&typeid=0&TotalResult=3575&PageNo=2
					href = href.replace("q=", "keyword=") +"&PageNo="+pageNo;
				} 
				//https://www.umei.cc/umplus/search.php?q=%E7%BE%8E%E5%A5%B3&pagesize=24
				
			} else {
				if (href.contains(".htm?")) {
					if (pageNo > 1) {
						href = href.replace(".htm?", "_") + pageNo + ".htm";
					}
				} else if (href.contains(".htm")) {
					if (pageNo > 1) {
						href = href.replace(".htm", "_") + pageNo + ".htm";
					}
				} else {
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
				Element TypePicElement = doc.select("div.TypePic").first();
				if (TypePicElement != null) {
					TypePic = TypePicElement.select("img").first().attr("src");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Element ChannelTitleElement = doc.select("div.ChannelTitle").first();
				if (ChannelTitleElement != null) {
					ChannelTitle = ChannelTitleElement.text();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Element ListDescElement = doc.select("p.ListDesc").first();
				if (ListDescElement != null) {
					ListDesc = ListDescElement.text();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Element masthead = doc.select("div.TypeList").first();
			if (masthead == null) {
				masthead = doc.select("ul.list_article").first();
			}
			Elements liElements = masthead.select("li");
			/**
			 * <li>
			 * <a href="http://www.umei.cc/bizhitupian/diannaobizhi/14994.htm"
			 * class="TypeBigPics" target="_blank"> <img
			 * src="http://i1.umei.cc/uploads/tu/201612/838/slt25.png"
			 * width="180" height="270" /> <span>清新花草高清电脑桌面壁纸</span></a> <div
			 * class="TypePicInfos"> <div class="txtInfo gray">
			 * <em class="IcoList">查看：62次</em><em class="IcoTime">12-23</em>
			 * </div> </div></li>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(hrefurl);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element imgElement = liElements.get(i).select("img").first();
						String src = imgElement.attr("src");
						Log.i(TAG, "i===" + i + "src==" + src);
						bean.setSrc(src);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element spanElement = liElements.get(i).select("a").first().select("span").first();
						if (spanElement == null) {
							/**
							 * 
							 <a href=
							 * "http://www.umei.cc/meinvtupian/meinvmote/26533.htm"
							 * class="TypeBigPics" target="_blank"> <img src=
							 * "http://i1.umei.cc/uploads/tu/201701/68/c15.jpg"
							 * width="180" height="270" /> <div
							 * class="ListTit">[
							 * TouTiao头条女神]性感美女麦苹果制服诱人写真</div></a> <div
							 * class="TypePicInfos"> <div class="TypePicTags"><a
							 * href='/tags/TouTiaonvshen.htm'
							 * title=头条女神>头条女神</a> <a href='/tags/zhifu.htm'
							 * title=制服>制服</a><a href='/tags/xiezhen.htm'
							 * title=写真>写真</a></div> <div class="txtInfo gray">
							 * <em class="IcoList">查看：61次</em>
							 * <em class="IcoTime">01-04</em></div> </div> </li>
							 * <li>
							 */
							spanElement = liElements.get(i).select("a").first().select("div.ListTit").first();
						}
						String typename = spanElement.text();
						Log.i(TAG, "i===" + i + "typename=" + typename);
						bean.setTypename(typename);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element IcoListElement = liElements.get(i).select("em.IcoList").first();
						if (IcoListElement == null) {
							IcoListElement = liElements.get(i).select("div.RightInfoBox").first();
						}
						String IcoList = IcoListElement.text();
						Log.i(TAG, "i===" + i + "IcoList=" + IcoList);
						bean.setIcoList(IcoList);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element IcoTimeElement = liElements.get(i).select("em.IcoTime").first();
						String IcoTime = IcoTimeElement.text();
						Log.i(TAG, "i===" + i + "IcoTime=" + IcoTime);
						bean.setIcoTime(IcoTime);
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

	/**
	 * 推荐内容
	 */
	public static ArrayList<UmeiTypeBean> parseTypeList2(String href) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element masthead = doc.select("div.TypeList_2").first();
			if(masthead==null && href.contains("search.php?")){
				masthead= doc.select("div.TypeList").first();
			}
			Elements liElements = masthead.select("li");
			/**
			 * <li><a
			 * href="http://www.umei.cc/bizhitupian/diannaobizhi/7614.htm"
			 * title="男歌手张艺兴桌面壁纸图片"> <div class="Pix-box"><img
			 * src="http://i1.umei.cc/uploads/tu/201608/434/slt2.jpg"
			 * width="180" title="男歌手张艺兴桌面壁纸图片">
			 * </div><span>男歌手张艺兴桌面壁纸图片</span></a></li>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(hrefurl);

						String typename = aElement.attr("title");
						Log.i(TAG, "i===" + i + "typename=" + typename);
						bean.setTypename(typename);

					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element imgElement = liElements.get(i).select("img").first();
						String src = imgElement.attr("src");
						Log.i(TAG, "i===" + i + "src==" + src);
						bean.setSrc(src);
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

	public static ArrayList<UmeiTypeBean> relaxarc(String href) {
		ArrayList<UmeiTypeBean> list = new ArrayList<UmeiTypeBean>();
		try {
			// http://www.umei.cc/bizhitupian/shoujibizhi/1.htm
			// http://www.umei.cc/bizhitupian/shoujibizhi/
			href = makeURL(href, new HashMap<String, Object>() {
				{
				}
			});
			Log.i(TAG, "url = " + href);

			Document doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Element masthead = doc.select("div.relax-arc").first();
			Elements liElements = masthead.select("li");
			/**
			 * <li><a
			 * href="http://www.umei.cc/bizhitupian/diannaobizhi/7614.htm"
			 * title="男歌手张艺兴桌面壁纸图片"> <div class="Pix-box"> <img
			 * src="http://i1.umei.cc/uploads/tu/201608/434/slt2.jpg"
			 * width="180" title="男歌手张艺兴桌面壁纸图片" /></div>
			 * <span>男歌手张艺兴桌面壁纸图片</span></a></li>
			 */
			// 解析文件
			if (liElements != null && liElements.size() > 1) {
				for (int i = 0; i < liElements.size(); i++) {
					UmeiTypeBean bean = new UmeiTypeBean();
					try {
						Element aElement = liElements.get(i).select("a").first();
						String hrefurl = aElement.attr("href");
						Log.i(TAG, "i===" + i + "hrefurl==" + hrefurl);
						bean.setHref(hrefurl);

						String typename = aElement.attr("title");
						Log.i(TAG, "i===" + i + "typename=" + typename);
						bean.setTypename(typename);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element imgElement = liElements.get(i).select("img").first();
						String src = imgElement.attr("src");
						Log.i(TAG, "i===" + i + "src==" + src);
						bean.setSrc(src);
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						Element spanElement = liElements.get(i).select("a").first().select("span").first();
						String typename = spanElement.text();
						Log.i(TAG, "i===" + i + "typename=" + typename);
						bean.setTypename(typename);
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

	public static String getChannelTitle() {
		return ChannelTitle;
	}

	public static void setChannelTitle(String channelTitle) {
		ChannelTitle = channelTitle;
	}

	public static String getListDesc() {
		return ListDesc;
	}

	public static void setListDesc(String listDesc) {
		ListDesc = listDesc;
	}

	public static String getTypePic() {
		return TypePic;
	}

	public static void setTypePic(String typePic) {
		TypePic = typePic;
	}

}
