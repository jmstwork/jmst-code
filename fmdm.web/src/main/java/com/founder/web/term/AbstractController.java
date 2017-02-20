package com.founder.web.term;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.seasar.doma.jdbc.SelectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.founder.core.service.dto.DefaultUserDetails;
import com.founder.fmdm.Constants;
import com.founder.web.dto.PagingDto;

/**
 * 
 * @author xu_dengfeng
 *
 */
public abstract class AbstractController
{
    protected static Logger logger = LoggerFactory.getLogger(AbstractController.class);
    private static final int PAGE_COUNT = 10;
    private static final int PAGE_COUNT1 = 7;
    /**
     * 
     * @param opts
     * @return
     */
    public SelectOptions getSelectOptions(PagingDto opts)
  	{
  		int jumpToPage = opts.getJumpToPage() == null ? 1 : opts.getJumpToPage();
  		int offset = (jumpToPage - 1) * (StringUtils.isEmpty(opts.getPageCount())?PAGE_COUNT:opts.getPageCount());
  		SelectOptions options = SelectOptions.get().offset(offset).limit(StringUtils.isEmpty(opts.getPageCount())?PAGE_COUNT:opts.getPageCount()).count();

  		return options;
  	}

    protected ModelAndView includeTemplate(Map<String, ?> model, String fragment) throws Exception
    {
		DefaultUserDetails userDetails = (DefaultUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
        ModelAndView mav = new ModelAndView("template-table", model);
        ModelMap mm = mav.getModelMap();
        mm.addAttribute("menus", userDetails.getMenus());
        mm.addAttribute("frag_content", fragment);
        //[BUG]0046046 ADD BEGIN
        handleMainBgPic(mav);
        // $[BUG]0046046 ADD END
        return mav;
    }
    
    //  处理主界面的logo图片问题
    //  $Author :yang_mingjie
    //   $Date : 2014/08/12 8:49$
    //   [BUG]0046046 ADD BEGIN
    public ModelAndView handleMainBgPic(ModelAndView mav){
    	String hospitalLoginStyle = Constants.HOSPITAL_LOGIN_STYLE;
    	String mainBgPic = null;
    	if("PKUIH".equals(hospitalLoginStyle)){
    		mainBgPic = Constants.COMPANY_MAIN_PKUIH_PIC;
    	}else if("FOUNDER".equals(hospitalLoginStyle)){
    		mainBgPic = Constants.COMPANY_MAIN_PIC;
    	}
         //web服务启动时，如果properties目录下存在定制的图片，会按照用户配置的路径复制到项目中，读取项目
         if(!StringUtils.isEmpty(Constants.HOSPITAL_MAIN_PIC)){

 			// 获取cdr项目所在的根路径
 			String webPath = new File(this.getClass().getResource("/").getPath()).getAbsolutePath();
 			// 处理windows与linux之间的差异
 			if (webPath.contains("WEB-INF")) {
 				webPath = webPath.substring(0, webPath.indexOf("WEB-INF"));
 			}

 			String picPath = Constants.HOSPITAL_PIC_FOLDER + "/"
 					+ Constants.HOSPITAL_MAIN_PIC;

 			File file = new File(webPath + picPath);
 			if (file.exists() && file.isFile()) {
 				mainBgPic  = picPath;
 			}
 		
         }
   
        mav.addObject("mainBgPic",mainBgPic);
    	
    	return mav;
    }
    // $[BUG]0046046 ADD END
    
    /**
     * 
     * @param data
     * @param key
     * @return
     */
    public static List<Object> convertSimpleList(List<Map<String, Object>> data, String key)
    {
        List<Object> result = new ArrayList<Object>(data == null ? 0 : data.size());
        Map<String, Object> row = null;
        for(int i = 0; i < data.size(); i ++)
        {
            row = data.get(i);
            result.add(row.get(key));
        }
        return result;
    }
    
    /**
     * 
     * @param data
     * @param keys
     * @return
     */
    public static List<List<Object>> convert(List<Map<String, Object>> data, String[] keys)
    {
        int cc = keys == null ? 0 : keys.length;
        List<List<Object>> result = new ArrayList<List<Object>>(data == null ? 0 : data.size());
        Map<String, Object> row = null;
        for(int i = 0; i < data.size(); i ++)
        {
            row = data.get(i);
            List<Object> item = new ArrayList<Object>(cc);
            for(String key : keys)
            {
                item.add(row.get(key));
            }
            result.add(item);
        }
        return result;
    }
    
    /**
     * 
     * @param data
     * @param partition
     * @param keys
     * @return
     */
    public static Map<String, List<List<Object>>> convertMultiObject(List<Map<String, Object>> data, String partition, String[] keys)
    {
        if(data == null || partition == null || keys == null || keys.length == 0)
            return null;
        
        int cc = keys.length;
        Map<String, List<List<Object>>> result = new HashMap<String, List<List<Object>>>();
        List<List<Object>> obj = new ArrayList<List<Object>>();
        Map<String, Object> row = null;
        Object pValue = null;
        for(int i = 0; i < data.size(); i ++)
        {
            row = data.get(i);
            pValue = row.get(partition);
            if(pValue == null)
            {
                logger.warn("partition value is null. Data : {}", row);
                continue;
            }
            obj = result.get(pValue);
            if(obj == null)
            {
                obj = new ArrayList<List<Object>>();
                result.put(pValue.toString(), obj);
            }
            List<Object> item = new ArrayList<Object>(cc);
            obj.add(item);
            for(String key : keys)
            {
                item.add(row.get(key));
            }
        }
        return result;
    }

    public static List<List<Object>> convertBeanList(List<? extends Object> data, String[] keys)
    {
        if(data == null || keys == null)
            return null;
        
        int cc = keys.length;
        List<List<Object>> result = new ArrayList<List<Object>>(data.size());
        Object row = null;
        ExpressionParser parser = new SpelExpressionParser();
        Expression[] exprs = new Expression[keys.length];
        for(int i = 0; i < keys.length; i ++)
        {
            exprs[i] = parser.parseExpression(keys[i]);
        }
        
        for(int i = 0; i < data.size(); i ++)
        {
            row = data.get(i);
            StandardEvaluationContext itemContext = new StandardEvaluationContext(row);
            List<Object> item = new ArrayList<Object>(cc);
            for(Expression expr : exprs)
            {
                item.add(expr.getValue(itemContext));
            }
            result.add(item);
        }
        return result;
    }
    
    public int calculateMedian(List<Map<String, Object>> data, String keyName)
    {
        int result = 0;
        int size = data == null ? 0 : data.size();
        if(size == 0)
            return result;
        
        ArrayList<Integer> arr = new ArrayList<Integer>();
        Map<String, Object> row = null;
        Object value = null;
        for(int i = 0; i < size; i ++)
        {
            row = data.get(i);
            value = (row == null ? "" : row.get(keyName));
            arr.add(Integer.parseInt(value.toString()));
        }
        Collections.sort(arr);
        int mi = size / 2;
        if(size % 2 == 0)
        {
            result = (arr.get(mi - 1) + arr.get(mi)) / 2;
        }
        else
        {
            result = arr.get(mi) ;
        }
        
        return result;
    }

    public String[] getDateRange(String dateFrom, String dateTo, int interval)
    {
        String[] result = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date d = null;
            Calendar c = GregorianCalendar.getInstance();
            if(dateTo == null || dateTo.trim().equalsIgnoreCase(""))
            {
                result[1] = sdf.format(c.getTime());
            }
            else
            {
                d = sdf.parse(dateTo);
                c.setTime(d);
                result[1] = dateTo;
            }
            
            if(dateFrom == null || dateFrom.trim().equalsIgnoreCase(""))
            {
                c.add(Calendar.DAY_OF_MONTH, interval);
                result[0] = sdf.format(c.getTime());
                logger.debug("start day is {}.", result[0]);
            }
            else
            {
                d = sdf.parse(dateFrom);
                c.setTime(d);
                result[0] = dateFrom;
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException("日期参数的格式不正确。");
        }
        
        return result;
    }
    
    
    public PagingDto pageSetting(PagingDto cond,SelectOptions options){
    	if(cond.getJumpToPage() == null)
			cond.setCurrentPage(1);
		else
			cond.setCurrentPage(cond.getJumpToPage());
		cond.setJumpToPage(0);
		int size = (int) options.getCount();
		cond.setTotalSize(size);
		if(StringUtils.isEmpty(cond.getPageCount())){
		    if(size % PAGE_COUNT == 0)
		    	cond.setTotalPage(size / (StringUtils.isEmpty(cond.getPageCount())?PAGE_COUNT:cond.getPageCount()));
		    else
		    	cond.setTotalPage(size / (StringUtils.isEmpty(cond.getPageCount())?PAGE_COUNT:cond.getPageCount()) + 1);
		}else{
			if(size % cond.getPageCount() == 0)
				cond.setTotalPage(size / cond.getPageCount());
			else
				cond.setTotalPage(size / cond.getPageCount() + 1);
		}
		return cond;
    }
   
    public  String getUUID(){ 
        return UUID.randomUUID().toString().replaceAll("-", ""); 
    } 
}
