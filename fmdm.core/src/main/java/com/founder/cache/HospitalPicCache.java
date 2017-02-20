package com.founder.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.fmdm.Constants;

public class HospitalPicCache
{

    private static final Logger logger = LoggerFactory.getLogger(HospitalPicCache.class);

    private HospitalPicCache()
    {
    	
    }

    /**
     * 复制logo图片到项目临时目录。
     */
    public void init()
    {
        try
        {
        	logger.debug("复制定制的logo图片到项目中的临时目录，初始化操作开始...");
            initPic();
            logger.debug("复制定制的logo图片到项目中的临时目录，初始化操作完成!");
        }
        catch (Exception e)
        {
            logger.error("复制医院定制的logo图片到项目中临时目录，初始化操作失败！");
            e.printStackTrace();
        }
    }
    
    //允许医院定制个性化logo,将定制化logo复制到项目中
    public void initPic(){
        try {
        	String loginBgPic = Constants.HOSPITAL_LOGIN_BG;
        	String loginBgHospital = Constants.HOSPITAL_LOGIN_BGHOSPITAL;
            String mainBgPic = Constants.HOSPITAL_MAIN_PIC;
	        File loginPicFile = new File("");
	        String filePath = loginPicFile.getCanonicalPath();//getCanonicalPath()返回的就是标准的将符号完全解析的路径
	        //处理windows与linux之间的差异
            if(filePath.contains("bin")){
            	filePath = filePath.substring(0, filePath.indexOf("bin")-1);//tomcat服务器是在bin目录下启动jvm的
            }
            
	        //获取项目根路径
	    	//String newPath = filePath + "/webapps/cdr/" + Constants.HOSPITAL_PIC_FOLDER;
      
            File file = new File(this.getClass().getResource("/").getPath());
            String webPath="";
            String fileAbsolutePath=file.getAbsolutePath();
            //处理windows与linux之间的差异
            if(fileAbsolutePath.contains("WEB-INF")){
             webPath = fileAbsolutePath.substring(0, fileAbsolutePath.indexOf("WEB-INF"));
            }else{
            	webPath = fileAbsolutePath;
            }
	        
	        //logger.debug(webPath);
	        //得到定制图片的临时路径
	    	String newPath = webPath + Constants.HOSPITAL_PIC_FOLDER;
	    	
	    	/**为保障用户修改了图片能够及时体现最新，先对项目中存在的临时目录文件做清除操作。
	    	 * 如果满足条件：(1)当指定路径(properties目录)是否存在该文件，(2)登录定制背景图片配置不为空,(3)文件名与配置名一致。再做插入操作
	    	 */
	    	deleteFile(newPath);
	    	
	    	//然后再执行插入操作
	        if(!StringUtils.isEmpty(loginBgPic)||!StringUtils.isEmpty(loginBgHospital)||!StringUtils.isEmpty(mainBgPic)){
	        	File mainPicFile = new File("");
	        	String loginPicPath = "";
	        	String mainPicPath = "";

	            if(!StringUtils.isEmpty(loginBgPic)){
            		loginPicPath = filePath + Constants.HOSPITAL_PIC_PATH + loginBgPic;
            		loginPicFile = new File(loginPicPath);
            		if(loginPicFile.exists()){
            			copyFile(loginPicPath,newPath,loginBgPic);
            		}
            	}
            	if(!StringUtils.isEmpty(loginBgHospital)){
            		mainPicPath = filePath + Constants.HOSPITAL_PIC_PATH + loginBgHospital;
    	            mainPicFile = new File(mainPicPath);
    	            if(mainPicFile.exists()){
    	            	copyFile(mainPicPath,newPath,loginBgHospital);
    	            }
            	}
            	if(!StringUtils.isEmpty(mainBgPic)){
            		mainPicPath = filePath + Constants.HOSPITAL_PIC_PATH + mainBgPic;
    	            mainPicFile = new File(mainPicPath);
    	            if(mainPicFile.exists()){
    	            	copyFile(mainPicPath,newPath,mainBgPic);
    	            }
            	}
	        }
		} catch (IOException e){
			e.printStackTrace();
		}
    }
    
    //删除临时目录下的所有文件，保障定制图片是最新的
    public void deleteFile(String path){
    	File file = new File(path);
    	if(file.exists()){
    		if(file.isDirectory()){//判断是否为文件夹
    			File files[] = file.listFiles();
    			logger.debug(String.valueOf(file.listFiles().length));
    			for(int i=0;i<files.length;i++){
    				if(files[i].isDirectory()){
    					deleteFile(files[i].getPath());
    				}else if(files[i].isFile()){//判断是否为文件
    					logger.debug(files[i].getPath());
    					files[i].delete();
    				}
    			}
    		}else{
    			file.delete();
    		}
    	}
    }
    
    
    //将properties目录下的文件，复制到项目中的临时目录
    public void copyFile(String oldPath, String newPath,String fileName) { 
        try { 
        	int bytesum = 0; 
            int byteread = 0; 
            File oldfile = new File(oldPath); 
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                File file = new File(newPath);
                //目录要先创建，不然会报异常
                if(!file.exists()){
                	file.mkdirs();
                }
                newPath = newPath + "/" + fileName;
                FileOutputStream fs = new FileOutputStream(newPath); //newPath：输出路径+文件名
                byte[] buffer = new byte[1024]; 
                while ( (byteread = inStream.read(buffer)) != -1) { 
                	bytesum += byteread; //字节数 文件大小 
                    System.out.println(bytesum); 
                    fs.write(buffer, 0, byteread); 
                    
                } 
                inStream.close(); 
            } 
        } 
        catch (Exception e) { 
            System.out.println("复制图片文件操作出错"); 
            e.printStackTrace(); 
        } 

    } 
}
