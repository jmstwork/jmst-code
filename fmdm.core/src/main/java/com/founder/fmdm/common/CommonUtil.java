package com.founder.fmdm.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.founder.core.AppSettings;

/**
 * 
 * @author wp
 *
 */
public class CommonUtil {

	public static String loadProperty(String propertyName) {
		return AppSettings.getConfig(propertyName);
	}

	public static final String PATTERN_COMPACT_DATETIME = "yyyyMMddHHmmss";
	 
   /* public static Filter createFilter(Map<String, Object> data)
    {
        if (data == null)
        {
            return null;
        }
        Filter filter = new Filter();
        filter.setGroupOp("AND");
        Rule[] rules = new Rule[data.size()];
        int i = 0;
        for (String key : data.keySet())
        {
            Object value = data.get(key);
            Rule rule = new Rule();
            rule.setField(key);
            rule.setOp(RuleOperation.EQUAL);
            rule.setData(value);
            rules[i] = rule;
            i++;
        }
        filter.setRules(rules);
        return filter;
    }*/
    
    /**
     * 处理页面传参问题，例如："/app/dict/FileDownloadControlloer/?fileName=RH"
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Object> getQueryMap(HttpServletRequest request)
            throws UnsupportedEncodingException
    {
        String query = request.getQueryString();
        Map<String, Object> map = new HashMap<String, Object>();
        if (query == null)
        {
            return map;
        }
        query = URLDecoder.decode(query, "utf-8");
        String[] params = query.split("&");
        for (String param : params)
        {
            String[] keyValue = param.split("=");
            String key = null;
            if (keyValue.length > 0)
            {
                key = param.split("=")[0];
            }
            String value = null;
            if (keyValue.length > 1)
            {
                value = param.split("=")[1];
                value = value.trim();
            }
            if (key != null)
            {
                map.put(key, value);
            }
        }
        return map;
    }
    
    /**
     * 将date类型的时间转换成yyyyMMddHHmmss的字符串格式
     * @param date
     * @return
     */
    public static String dateToString(Date date){
    	String dt = "";
    	DateFormat sdf = new SimpleDateFormat(PATTERN_COMPACT_DATETIME);
    	dt = sdf.format(date);
    	return dt;
    }
    
    //处理时间格式问题将long——>2012-8-6 12:12:12
    public static String getTimeText(long val) {
        if (val == 0){
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date date = new Date(val);
        return dateFormat.format(date).toString();
    }
    
    /**   
     * 得到当前日期   
     * @return   
    */  
    public static String getCurrentDate() {   
	    Calendar c = Calendar.getInstance();   
	    Date date = c.getTime();   
	    SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    return simple.format(date);
    }
    
    /**
     * 转换日期格式
     * @param date
     * @param patten
     * @return
     */
    public static String dateFormatToStr(Date date, String patten){
    	if(StringUtils.isEmpty(patten)){
    		patten = "yyyy-MM-dd HH:mm:ss";
    	}
    	SimpleDateFormat simple = new SimpleDateFormat(patten);   
	    return simple.format(date);
    }
    

    /**
     *  根据传入表达式转换为日期
     * @param dateStr
     * @param patten
     * @return
     */
	public static Date formatToDate(String dateStr,String patten){
    	Date date = new Date();
    	if(StringUtils.isEmpty(patten)){
    		patten = "yyyyMMddHHmmss";
    	}
    	try {
    		SimpleDateFormat f = new SimpleDateFormat(patten);
			date = f.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
    
    /**   
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式   
     * @param date2 被比较的时间  为空(null)则为当前时间   
     * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年   , 3为多少小时, 4为多少分钟
     * @return   
    */  
    public static int compareDate(String date1,String date2,int stype){   
	    int n = 0;
	    String formatStyle = "";
	    if(stype==1){
	    	formatStyle = "yyyy-MM";
	    }else if(stype==0 || stype==2){
	    	formatStyle = "yyyy-MM-dd";
	    }else{
	    	formatStyle = "yyyy-MM-dd HH:mm:ss";
	    }
	    date1 = date1==null?getCurrentDate():date1;
	    date2 = date2==null?getCurrentDate():date2;   
	    DateFormat df = new SimpleDateFormat(formatStyle);   
	    Calendar c1 = Calendar.getInstance();   
	    Calendar c2 = Calendar.getInstance();   
	    try {   
		    c1.setTime(df.parse(date1)); 
		    c2.setTime(df.parse(date2));
	    }catch (Exception e){   
	    	System.out.println("wrong occured");   
	    } 
	    while (!c1.after(c2)) {
	    	n++;   
	    	if(stype==1){   
	    		c1.add(Calendar.MONTH, 1);  
	    	}   
	    	else if(stype==0 || stype==2){   
	    		c1.add(Calendar.DATE, 1);
	    	}else if(stype==3){   
	    		c1.add(Calendar.HOUR, 1);
	    	}else{
	    		c1.add(Calendar.MINUTE, 1);
	    	}
	    }         
	    n = n-1;  
	    if(stype==2){   
	    	n = (int)n/365;   
	    }
	    return n;
    }
    
    /**   
     * 得到随机数 
     * @return   
    */  
    public static String random(int codeLength) {
        //因为o和0,l和1很难区分,所以,去掉大小写的o和l
        String str = "";
        str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";//初始化种子
        return RandomStringUtils.random(codeLength, str);//返回6为的字符串
    }

    /**   
     * 手机号验证
    */ 
    public static boolean isMobileNO (String mobiles){
    	if(mobiles==null||"".equals(mobiles))return false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);   
        return m.matches();     
    } 
   
    /**   
     * 邮箱地址验证
    */ 
    public static boolean isEmail (String email){
    	if(email==null||"".equals(email))return false;
    	String str="[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);   
        return m.matches();
    } 
    
    public static String getUUID(){ 
        return UUID.randomUUID().toString().replaceAll("-", ""); 
    } 

}
