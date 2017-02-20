package com.founder.web.util;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传管理类
 * author wp
 */
public class FileManager {
	
	private static final int bufferSize = 10240;
	
	/**
	 * 单文件上传（记录不保存到数据库中）
	 * author：wp
	 * @return 返回文件绝对路径
	 */
	public static String onlyUpload(HttpServletRequest req) throws Exception {
		//客户端选择的文件名
		String fileName;
		String realFilePath="";
		InputStream input = null;
		OutputStream output = null;
		    MultipartResolver resolver = new CommonsMultipartResolver(req.getSession().getServletContext());
		    MultipartHttpServletRequest multipart = resolver.resolveMultipart(req);
			for(Iterator it = multipart.getFileNames();it.hasNext();) 
			{ 
				if (!it.hasNext()) break;  //如果是最后一个File控件直接跳出循环，因为最后一个肯定是空的
				String key = (String)it.next(); 
						MultipartFile file = multipart.getFile(key); 
						if (file != null && !file.isEmpty()) {
							fileName = file.getOriginalFilename();
							try {
								String destFilePath = req.getSession().getServletContext().getRealPath("/");
								if (!destFilePath.endsWith(java.io.File.separator)) {
							    	destFilePath = destFilePath + java.io.File.separator;
								    }
								String extName = fileName.substring(fileName.lastIndexOf("."));
								String savePath = UUID.randomUUID().toString() + extName;
								// 开始进行上传操作
								File f = new File(destFilePath);
								if(!f.exists()|| f == null) f.mkdirs();
								// 创建文件上传的路径
								input = file.getInputStream();
								// 获得上传文件的输入流:
								realFilePath = destFilePath+savePath;
								output = new BufferedOutputStream(new FileOutputStream(destFilePath+savePath));
								// 写入到服务器的本地文件:
								byte[] buffer = new byte[bufferSize]; 
								int n;
								while ((n = input.read(buffer)) != (-1)) {
									output.write(buffer, 0, n);
								}
							}finally {
								// 必须在finally中关闭输入/输出流:
								if (input != null) {
									try {
										input.close();
									} catch (IOException ioe) {
									}
								}
								if (output != null) {
									try {
										output.close();
									} catch (IOException ioe) {
									}
								}
							}
					}
			} 
		return realFilePath;
	}
	
	/**
	 * 删除服务器上的文件
	 * author wp
	 */
	public static void deleteFile(String filepath) {
		File f = new File(filepath);
		//如果该文件存在，删除文件
		if(f.exists()) f.delete();
	}
}
