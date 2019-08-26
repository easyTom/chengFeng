package com.tom.cf.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

public class UploadUtils {
	// 上传文件名
	private String fileName;
	// 上传文件的保存路径
	private String upPath;
	// 上传文件的路径(包含上传文件的文件名)
	private String filePath;
	// Request对象
	private HttpServletRequest request;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUpPath() {
		return upPath;
	}

	public void setUpPath(String upPath) {
		this.upPath = upPath;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	//获取上传文件的路径(包含上传文件的文件名)
	public String getFilePath(){
		return filePath;
	}

	/**
	 * 
	 * @param upPath	用于保存上传文件的路径,以"/"开头或包含":"的路径会被认为是绝对路径,否则会被认为是相对路径
	 * @param fileName	上传文件的文件名,如果想使用上传文件原来的名字或多文件上传或上传文件的路径中已包含文件名则该参数应传入null
	 * @param request	HttpServletRequest请求对象
	 * @throws IOException
	 */
	public void doServletUpload(String upPath, String fileName, HttpServletRequest request) throws IOException {
		this.upPath = upPath;
		this.request = request;
		// 创建文件上传工厂类
		FileItemFactory fac = new DiskFileItemFactory();
		// 设置临时目录(上传大文件时会先一点一点地上传到临时目录,上传完毕后再移动到目标目录,不设置临时目录上传大文件时会自动使用默认的临时目录)
		// ((DiskFileItemFactory)fac).setRepository(new File(request.getServletContext().getRealPath("./temp")));
		// 创建文件上传核心类对象
		ServletFileUpload upload = new ServletFileUpload(fac);
		// 设置了upload.setHeaderEncoding("utf-8");后下面调用getFieldName()或getName()方法时就会使用"utf-8"编码解析数据
		// upload.setHeaderEncoding("utf-8");
		// 设置上传的单个文件最大字节数
		// upload.setFileSizeMax(fileSizeMax);
		// 设置整个表单上传数据的最大字节数
		// upload.setSizeMax(sizeMax);
		// 判断是普通表单,还是带文件上传的表单(即使是带文件上传的表单,但使用的是get请求,则也会被判断为普通表单)
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				// 把request对象中的请求数据转换为FileItem对象的集合
				List<FileItem> list = upload.parseRequest(request);
				// 遍历集合得到每一个上传项
				for (FileItem item : list) {
					// 判断是不是普通表单项,是则返回true,否则返回false
					if (item.isFormField()) {
						/***** 普通表单项 *****/
						/* 
						//表单项的name属性名称 
						String fieldName = item.getFieldName(); 
						//getString()方法用来获取表单项的内容,upload.setHeaderEncoding("utf-8");对getString()方法无效,getString()方法默认用iso-8859-1编码 
						String content = item.getString();
						System.out.println(fieldName + "=" + new String(content.getBytes("iso-8859-1"),"utf-8"));
						//通过表单项的字节输入流来获取表单项的内容 
						InputStream in = item.getInputStream();
						byte[] buf = new byte[1024 * 1024]; 
						int len = -1; 
						while ((len = in.read(buf)) != -1) { 
							System.out.println(new String(buf, 0, len)); 
						}
						*/
					} else {
						String tempPath = this.upPath;
						/***** 文件上传表单项 *****/
						// 表单项的name属性名称
						// String fieldName = item.getFieldName();
						// 表单项的类型
						// String contentType = item.getContentType();
						// 将tempPath字符串中所有的\\替换成/
						tempPath = tempPath.replaceAll("\\\\\\\\", "/");
						// 将tempPath字符串中所有的\替换成/
						tempPath = tempPath.replaceAll("\\\\", "/");
						if (fileName == null || fileName.equals("")) {
							// 如果tempPath字符串以/结尾,则说明tempPath字符串中不包含上传文件的文件名
							if (tempPath.endsWith("/")) {
								// 获取所上传文件的文件名(包含扩展名)
								fileName = item.getName();
								//如果上传路径不是以/结尾,先给上传路径末尾添加一个/然后再拼接上传文件的文件名
								if(tempPath.lastIndexOf("/") == -1){
									tempPath += "/";
								}
								// 将上传文件的文件名拼接到上传路径中
								tempPath += fileName;
							}
						}else{
							//如果上传路径不是以/结尾,先给上传路径末尾添加一个/然后再拼接上传文件的文件名
							if(tempPath.lastIndexOf("/") == -1){
								tempPath += "/";
							}
							// 将上传文件的文件名拼接到上传路径中
							tempPath += fileName;
						}
						this.fileName = fileName;
						// 获取表单项的内容(也就是获取所上传文件的内容),upload.setHeaderEncoding("utf-8");对getString()方法无效,getString()方法默认用iso-8859-1解析
						// String content = item.getString();
						// System.out.println(fieldName + "," + contentType + "," + name + ","+ new String(content.getBytes("iso-8859-1"), "gbk"));
						// 判断temppath字符串是绝对路径还是相对路径
						if (!(tempPath.startsWith("/") || tempPath.contains(":"))) {
							tempPath = request.getServletContext().getRealPath("/") + tempPath;
						}
						this.filePath = tempPath;
						File file = new File(tempPath);
						// 如果用于保存上传文件的目录不存在则创建该目录
				        if(!file.getParentFile().exists()){
				        	file.getParentFile().mkdirs();
				        }
						// 获取表单项的字节输入流
						InputStream in = item.getInputStream();
						// 创建字节输出流对象
						OutputStream os = new FileOutputStream(file);
						// 创建一个长度为1M的字节数组
						byte[] buf = new byte[1024 * 1024];
						int len = -1;
						while ((len = in.read(buf)) != -1) {
							os.write(buf, 0, len);
						}
						os.close();
						//清空文件名
						fileName = null;
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param upPath	用于保存上传文件的路径,以"/"开头或包含":"的路径会被认为是绝对路径,否则会被认为是相对路径
	 * @param fileName	上传文件的文件名,如果想使用上传文件原来的名字或多文件上传或上传文件的路径中已包含文件名则该参数应传入null
	 * @param inputname	客户端<input type="file" name="xxx">标签的name属性值
	 * @param request	HttpServletRequest请求对象
	 * @throws IOException
	 */
	public void doSpringMVCUpload(String upPath, String fileName,String inputname, HttpServletRequest request) throws IOException {
		this.upPath = upPath;
		this.request = request;
		//将HttpServletRequest转成MultipartHttpServletRequest
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        //使用MultipartHttpServletRequest对象获取上传的文件
        List<MultipartFile> fileList = multipartRequest.getFiles(inputname);
        if(fileList != null){
	        for (MultipartFile multipartFile : fileList) {
	        	String tempPath = this.upPath;
	        	// 将tempPath字符串中所有的\\替换成/
				tempPath = tempPath.replaceAll("\\\\\\\\", "/");
				// 将tempPath字符串中所有的\替换成/
				tempPath = tempPath.replaceAll("\\\\", "/");
				if (fileName == null || fileName.equals("")) {
					// 如果tempPath字符串以/结尾,则说明tempPath字符串中不包含上传文件的文件名
					if (tempPath.endsWith("/")) {
						// 获取上传文件的文件名(带扩展名)
			            fileName = multipartFile.getOriginalFilename();
						//如果上传路径不是以/结尾,先给上传路径末尾添加一个/然后再拼接上传文件的文件名
						if(tempPath.lastIndexOf("/") == -1){
							tempPath += "/";
						}
						// 将上传文件的文件名拼接到上传路径中
						tempPath += fileName;
					}
				}else{
					//如果上传路径不是以/结尾,先给上传路径末尾添加一个/然后再拼接上传文件的文件名
					if(tempPath.lastIndexOf("/") == -1){
						tempPath += "/";
					}
					// 将上传文件的文件名拼接到上传路径中
					tempPath += fileName;
				}
				this.fileName = fileName;
	            // 表单中<input type="file" name="file"/>标签的name属性值
	            //System.out.println(multipartFile.getName());
	            // 获取上传的文件的大小
	            //System.out.println(multipartFile.getSize());
	            // 获取上传的文件的文件类型
	            //System.out.println(multipartFile.getContentType());
	            // 获取上传的文件的二进制字节数组
	            //System.out.println(multipartFile.getBytes());
	            // 获取上传的文件的字节读取流
	            //System.out.println(multipartFile.getInputStream());
	            // 使用UUID为上传的文件生成文件名
	            //String fileName = UUID.randomUUID().toString();
	            // 获取上传的文件的文件名的后缀名(扩展名)
	            //String extName = uploadName.substring(uploadName.lastIndexOf("."));
				// 判断tempPath字符串是绝对路径还是相对路径
				if (!(tempPath.startsWith("/") || tempPath.contains(":"))) {
					tempPath = request.getServletContext().getRealPath("/") + tempPath;
				}
				this.filePath = tempPath;
				File file = new File(tempPath);
				// 如果用于保存上传文件的目录不存在则创建该目录
		        if(!file.getParentFile().exists()){
		        	file.getParentFile().mkdirs();
		        }
	            multipartFile.transferTo(file);
	            //清空文件名
	            fileName = null;
	        }
        }
	}
	
}
