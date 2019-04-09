package com.tom.cf.api.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/*
 * @Author: Tom 存储文件类
 * @Date: 2019/4/9 14:13
 */

public class FcFileUtil {

    /**
     * 存储文件
     * @param dir
     * @param module
     * @param userId
     * @param file
     * @throws IOException
     */
    public static void storageFile(String dir,String module, String userId, MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return;
        }
        File targetDir = new File(dir + "/" + userId + "/" + module);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        File targetFile=new File(targetDir, file.getOriginalFilename());
        FileUtils.copyInputStreamToFile(file.getInputStream(),targetFile);
    }

    /**
     * 获取文件
     * @param dir
     * @param module
     * @param userId
     * @param fileName
     * @return
     */
    public static File findFile(String dir,String module, String userId, String fileName){
        return new File(dir + "/" + userId + "/" + module,fileName);
    }

    /**
     * 下载文件
     * @param file
     * @param response
     * @throws IOException
     */
    public static void download(File file, HttpServletResponse response) throws IOException {
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        response.reset();
        response.setContentType("application/x-download");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition","attachment;filename="+ new String(file.getName().getBytes("UTF-8"),"iso-8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[1024 * 1024 * 4];
        int i= -1;
        while ((i=fis.read(buffer))!=-1) {
            toClient.write(buffer, 0, i);
        }
        fis.close();
        toClient.flush();
        toClient.close();
    }
}
