/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午3:01:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.bean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2016-12-28下午3:01:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SearchResultBean extends CommonBean{
	/**
	 * <div class="result f s0" style='margin-bottom:15px;'> <h3 class="c-title">
	 * <a rpos="" cpos="title"
	 * href="http://www.umei.cc/p/gaoqing/xiuren_VIP/20151123112346.htm"
	 * style='font-family:Arial,SimSun,sans-serif;font-size:16px;color:#0000cc;
	 * ' target="_blank">【秀人VIP】 2015.11.22 lucky咏丹 (XR20151122N00421) 71P -
	 * ...</a></h3> <div> <div class="c-content"> <div class="c-abstract"
	 * style='font-family:Arial,SimSun,sans-serif;font-size:13px;color:#000000;
	 * margin:0px 0;'> 上一篇:【UXING】 2015.11.22 VOL.028 懒<em>桃子</em> 56P
	 * 下一篇:【MyGirl】 2015.11.25 VOL.177 李雪婷Anna 70P 共2页: 上一页 1 2 下一页推荐秀人模特...
	 * </div> <div> <span class="c-showurl" style=
	 * 'font-family:Arial,SimSun,sans-serif;font-size:13px;color:#2a8055;'>www.umei.cc/p/gaoqing/xiuren_VIP/201...
	 * 2015-11-23</span> </div> </div> </div> </div>
	 */
	private String href;
	private String target;
	private String content;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
