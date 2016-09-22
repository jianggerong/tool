/*
 * @(#)Helper.java 1.0 2011-9-28
 * 
 * Copyright 2011 isi Company,Inc.All rights reserved.
 * ISITEAM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kit.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * The General Helper.
 * 
 * @author Lrwin
 * @version 1.0, 10/17/10
 * @see casia.isiteam.sigroup.util.GeneralHelper#ALL
 * @since JDK1.6
 */
public class Helper {

	/** The Constant DATETYPE_YYYY_MM_DD. */
	public static final String DATETYPE_YYYY_MM_DD = "yyyy-MM-dd";

	// Initialization
	static {
		// Set time zone.
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
	}

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static Object[] getDate() {
		Calendar instance = Calendar.getInstance();
		instance.setTime(new Date());
		int i = instance.get(Calendar.DAY_OF_WEEK);
		i--;
		String d = null;
		if (i == -1) {
			d = "日";
		}
		switch (i) {
		case 1:
			d = "一";
			break;
		case 2:
			d = "二";
			break;
		case 3:
			d = "三";
			break;
		case 4:
			d = "四";
			break;
		case 5:
			d = "五";
			break;
		case 6:
			d = "六";
			break;

		}
		return new Object[] { instance.getTime(), d };
	}
	
	/**
	 * Checks if is today.
	 * 
	 * @param date the date
	 * 
	 * @return true, if is today
	 */
	public static boolean isToday(Date date){
		try {
			return date.getTime()>=getYesdayEmpty(0).getTime();
		} catch (Exception e) {
			return false;
		}
	}

	public static String getParentUrl(String url) {

		/** The Constant URL_HEADER. Is 'https' required? */
		String URL_HEADER = "http://";

		/** The Constant URL_SHORTEST. */
		String URL_SHORTEST = URL_HEADER + "x.xx";

		if (isEmpty(url) || url.length() < URL_SHORTEST.length())
			return null;
		final String opper = url.startsWith(URL_HEADER) ? url.substring(URL_HEADER.length(), url.length()) : url;
		final int index = opper.indexOf("/");
		return index == -1 ? opper : opper.substring(0, index);
	}
	

	public static String getMD5ImagePath(String path) {
//		String string = Md5Util.MD5Encode(path.split("#")[0].trim()) + AppConstanta.FORMAT_JPG;
		return null;
	}

	public static int[] getCutPaging(Integer currentPage, Integer totalPage, Integer paging_space) {
		int start;
		int end;
		end = totalPage - (currentPage + paging_space) > 0 ? currentPage + paging_space : totalPage;
		start = currentPage - paging_space <= 0 ? 1 : currentPage - paging_space;
		return new int[] { start, end };
	}

	public static Long getTotalPage(Long totalRows, int pageSize) {
		Long totalPage = totalRows / pageSize;
		return totalRows % pageSize == 0 && totalPage != 0 ? totalPage : totalPage + 1;
	}

	/**
	 * Gets the total page.
	 * 
	 * @param totalRows
	 *            the total rows
	 * @param pageSize
	 *            the page size
	 * 
	 * @return the total page
	 */
	public static int getTotalPage(int totalRows, int pageSize) {
		int totalPage = totalRows / pageSize;
		return totalRows % pageSize == 0 && totalPage != 0 ? totalPage : totalPage + 1;
	}

	/**
	 * Gets the first result.
	 * 
	 * @param pageNo
	 *            the page no
	 * @param pageSize
	 *            the page size
	 * 
	 * @return the first result
	 */
	public static int getFirstResult(Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageSize == null)
			return 0;
		return (pageNo - 1) * pageSize;
	}

	/**
	 * Equest the string is empty. 判断String 是否 is null
	 * 
	 * @param str
	 *            the str
	 * 
	 * @return true, if checks if is empty
	 */
	public static boolean isEmpty(String str) {
		return str.equals("null")|| str == null || str.trim().length() == 0;
	}

	/**
	 * Dialog doctor. 清理影响需在dialog显示的文本
	 * 
	 * @param text
	 *            the text
	 * 
	 * @return the string
	 */
	public static String dialogDoctor(String text) {
		return text.replace("\"", "").replace("'", "");
	}

	/**
	 * Cut String operator （matcher all blank for String）截字符串
	 * <p>
	 * or use char[i] length
	 * </p> .
	 * 
	 * @param original
	 *            original string
	 * @param length
	 *            after newString length
	 * @param suffix
	 *            suffix
	 * 
	 * @return ret newString
	 */
	public static String cutOffString(String original, int length, String suffix) {
		if (isEmpty(original) || length <= 0)
			return "";
		int endindex = (original.length() <= length) ? original.length() : length;
		// Pattern pattern = Pattern.compile("\\s");
		// Matcher matcher = pattern.matcher(original.substring(0, endindex));
		// String temp = matcher.replaceAll("");
		// if(original.toLowerCase().startsWith("http://")){
		// if(original.length()>length){
		// return original.substring(0, endindex)+suffix;
		// }
		// }	
		String temp = original.substring(0, endindex);
		byte[] byteTemp = temp.getBytes();
		char[] array = temp.toCharArray();
		if (byteTemp.length <= length)
			return temp;
		else if (array.length == byteTemp.length)
			return temp.substring(0, endindex) + suffix;

		String ret = "";
		for (int i = 0, j = 0; j < length && i < array.length; i++, j++)
			if (byteTemp[j] == array[i])
				ret += array[i];
			else {
				if (++j >= length)
					break;
				else
					ret += array[i];
		}
		//判断<span>标签是否被破坏
		int s = ret.lastIndexOf("<span");
		int e = ret.lastIndexOf("</span");
		if (s > e) {
			ret = ret.substring(0, s);
		}
		return ret + suffix;
	}
	public static String KeywordsReplace(String keywords){
		String k = keywords.replaceAll(" ", "_");
		return k;
	}
	public static String KeywordsReplace2(String keywords){
		String k = keywords.replaceAll(" ", ",");
		return k;
	}
	/**
	 * get current yyyy-MM-dd HH:mm. 取得当前时间
	 * 
	 * @return the curren time
	 */
	public static String getCurrenTime() {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date());
	}

	/**
	 * 去除html元素, 在String中.
	 * 
	 * @param str
	 *            the str
	 * 
	 * @return the string
	 */
	public static String CheckAllHtml(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		else {
			str = str.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
			// str = str.replaceAll("\\&[a-zA-Z]{1,10};",
			// "").replaceAll("<[^>]*>", "");
			// str = str.replaceAll("[(/>)<]", "");
			return str.trim();
		}
	}

	/**
	 * get yesterday yyyy-MM-dd.
	 * 
	 * @return the yesday empty
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static Date getYesdayEmpty() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow.get(Calendar.DAY_OF_MONTH) - 1);
		// rightNow.set(Calendar.HOUR_OF_DAY, 9);
		return sdf.parse(sdf.format(rightNow.getTime()));
		// return Calendar.getInstance().getTime();
	}

	/**
	 * Gets the appli content path. 取得项目根目录(abs)
	 * 
	 * @return the appli content path
	 * 
	 * @throws Exception
	 *             the exception
	 */
	

	/**
	 * Gets the text from struts resources.
	 * 
	 * @author Bill
	 * @param key
	 *            the key
	 * @return the text value of the key
	 */
	public static String getText(String key) {
		Locale locale = Locale.getAvailableLocales()[0];
		return LocalizedTextUtil.findDefaultText(key, locale);
	}

	/**
	 * Gets the yesday empty. 返回(Integer)beforeDay前的日期yyyy-MM-dd
	 * 
	 * @param beforeDay
	 *            the before day
	 * 
	 * @return the yesday empty
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static Date getYesdayEmpty(int beforeDay) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.DAY_OF_MONTH, rightNow.get(Calendar.DAY_OF_MONTH) - beforeDay);
		return sdf.parse(sdf.format(rightNow.getTime()));
	}

	/**
	 * Format date.
	 * 
	 * @param date
	 *            the date
	 * @param pattern
	 *            the pattern
	 * 
	 * @return the string
	 */
	public static String formatDate(Date date, String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return simpleDateFormat.format(date);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/**
	 * Format date string.
	 * 
	 * @param date
	 *            the date
	 * @param pattern
	 *            the pattern
	 * 
	 * @return the date
	 */
	public static Date formatDateString(String date, String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return simpleDateFormat.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	
	/**
	 * 半小时 ，一小时， 半天 一天前
	 */
	public static String getTimeRange(Date date){
		// 昨天
		//确切时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy/MM/dd");
		Date now = new Date();
		if(sdfDay.format(date).endsWith(sdfDay.format(now))){
			//当天内 
//			if(sdf.format(date).equals("00:00:00")){
//				return sdfDay.format(date);
//			}else{
				return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
//			}
		}else{
			return sdfDay.format(date);
//			return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
		}
	}
	/**
	 * 修改时间格式为yyyy/MM/dd HH:mm  JGR
	 */
	public static String getTimeRange_Hm(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date now = new Date();
		if(sdfDay.format(date).endsWith(sdfDay.format(now))){
				return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
		}else{
			return sdfDay.format(date);
		}
	}
	/**
	 * 修改入库时间格式  20160301 JGR
	 */
	public static String getTimeRangeToday(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdfDay = new SimpleDateFormat("MM-dd HH:mm:ss");
		if(isToday(date)){
			return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
		}else{
			return sdfDay.format(date);
		}
	}
	
	public static String getTimeRange_2(Date date){
		// 昨天
		//确切时间
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		if(sdfDay.format(date).endsWith(sdfDay.format(now))){
			//当天内 
			return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
		}else{
			return "<span style='color:#05f'>"+sdf.format(date)+"</span>";
		}
	}
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DATE, -1);
//		Date yesterday =c.getTime();
//			long range = new Date().getTime()-date.getTime();
//			if(range<=1800000){
//				return "<span style='color:#cc0000'>半小时前</span>";
//			}else if(18000000<range && range<=3600000){
//				return "<span style='color:#cc0000'>一小时前</span>";
//			}else if(3600000<range && range<=7200000){
//				return "<span style='color:#cc0000'>两小时前</span>";
//			}else if(7200000<range && range<=3600000*3){
//				return "<span style='color:#cc0000'>三小时前</span>";
//			}else if(3600000*3<range && range<=3600000*4){
//				return "<span style='color:#cc0000'>四小时前</span>";
//			}else if(3600000*4<range && range<=3600000*5){
//				return "<span style='color:#cc0000'>五小时前</span>";
//			}else if(3600000*5<range && range<=3600000*6){
//				return "<span style='color:#cc0000'>六小时前</span>";
//			}else{
//				return "<span style='color:#cc0000'>"+sdf.format(date)+"</span>";
//			}
//		}else{
//			return sdfDay.format(date);
//		}
//		else if(sdfDay.format(date).endsWith(sdfDay.format(yesterday))){
//			return "<span style='color:red'>昨天</span>";
//		}
	
	
	public static String setToday(Date date){
		SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		if(sdfDay.format(date).endsWith(sdfDay.format(now))){
				return "<span style='color:red'>今日</span>";
		}else{
			return sdfDay.format(date);
		}
	
	}
	
	/**
	 * 获得域名
	 * @param srcUrl
	 * @return
	 */
	public static String getHost(String srcUrl) {
		// 对srcUrl进行处理得到主机名
		String url = null;
		try {
			if (srcUrl == null) {
				return null;
			}
			url = srcUrl.trim().toLowerCase().replace(" ", "");
			if (url.startsWith("http://")) {
				url = url.replace("http://", "");
			}
			if (url.startsWith("https://")) {
				url = url.replace("https://", "");
			}
			if (url.startsWith("/")) {
				url = url.replaceFirst("/", "");
			}
			if (url.contains("/")) {
				url = url.substring(0, url.indexOf("/"));
			}
			if (url.contains(":")) {
				url = url.substring(0, url.indexOf(":"));
			}
			String[] urls = url.split("\\.");
			String first = "aero,arts,biz,com,coop,edu,firm,gov,idv,info,int,mil,museum,name,net,nom,org,pro,rec,store,web,xxx";
			String second = "aa,at,au,be,ca,cn,dk,fr,it,jp,nl,nz,uk,za,ad,ae,af,ag,ai,al,an,ao,aq,"
					+ "ar,as,at,au,aw,az,ba,bb,bd,be,bf,bg,bh,bi,bj,bm,bn,bo,br,bs,bt,bv,bw,"
					+ "by,bz,ca,cc,cf,cg,ch,ci,ck ,cl,cm,cn,co,cq,cr,cu,cv,cx,cy,cz,de,dj,dk,dm,"
					+ "do,dz,ec,ee,eg,eh,es,et,eu,ev,fi,fj,fk,fm,fo,fr,ga,gb,gd,ge,gf,gh,gi,gl,gm,gn,"
					+ "gp,gr,gt,gu,gw,gy,hk,hm,hn,hr,ht,hu,id,ie,il,in,io,iq,ir,is,it,jm,jo,jp,ke,"
					+ "kg,kh,ki,km,kn,kp,kr,kw,ky,kz,la,lb,lc,li,lk,lr,ls,lt,lu,lv,ly,ma,mc,md,me,mg,mh,"
					+ "ml,mm,mn,mo,mp,mq,mr,ms,mt,mv,mw,mx,my,mz,na,nc,ne,nf,ng,ni,nl,no,np,nr,nt,nu,"
					+ "nz,om,pa,pe,pf,pg,ph,pk,pl,pm,pn,pr,pt,pw,py,qa,re,ro,ru,rw,sa,sb,sc,sd,se,sg,"
					+ "sh,si,sj,sk,sl,sm,sn,so,sr,st,su,sy,sz,tc,td,tf,tg,th,tj,tk,tm,tn,to,tp,tr,tt,"
					+ "tv,tw,tz,ua,ug,uk,us,uy,va,vc,ve,vg,vn,vu,wf,ws,ye,yu,za,zm,zr,zw";
			if (first.contains(urls[urls.length - 1])) {
				return urls[urls.length - 2] + "." + urls[urls.length - 1];
			}
			if (second.contains(urls[urls.length - 1])) {
				if (urls.length > 2 && first.contains(urls[urls.length - 2])) {
					return urls[urls.length - 3] + "." + urls[urls.length - 2]
							+ "." + urls[urls.length - 1];
				} else {
					return urls[urls.length - 2] + "." + urls[urls.length - 1];
				}
			}
		} catch (Exception e) {
			return null;
		}
		return url;
	}
	
}
