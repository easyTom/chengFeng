package com.tom.cf.api;

import com.tom.cf.api.v2.drawer.DataDrawer;
import com.tom.cf.api.v2.drawer.EcgPicWriter;
import com.tom.cf.api.v2.drawer.PaperDrawer;
import com.tom.cf.api.v2.model.Ecg;
import com.tom.cf.api.v2.parser.Hl7Parser;
import com.tom.cf.api.v2.util.EcgUtil;
import com.tom.cf.core.entity.FcUpload;
import com.tom.cf.core.service.UploadService;
import com.tom.cf.core.utils.IdGen;
import com.tom.cf.core.utils.ResizeImageUtils;
import com.tom.cf.core.utils.UploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @method:无需验证的方法
 */
@Controller
@RequestMapping("/api")
public class TomApi {


    @Value("${tom.files.path}")
    private String path;
    @Autowired
    private UploadService uploadService;

    //从时间中获取路径
    public String getCalendarPath(Calendar c){
        return c.get(Calendar.YEAR) + File.separator + (((c.get(Calendar.MONTH) + 1) + "").length() < 2 ? ("0" + (c.get(Calendar.MONTH) + 1)) : (c.get(Calendar.MONTH) + 1)) + File.separator + ((c.get(Calendar.DAY_OF_MONTH) + "").length() < 2 ? ("0" + c.get(Calendar.DAY_OF_MONTH)) : c.get(Calendar.DAY_OF_MONTH)) + File.separator;
    }

    /**
     * 对心电xml进行解析,并生成缓存图片,存放在xml同目录下
     * @param file
     * @return
     */
    private File processEcgFile(File file){
        String name = file.getName().substring(0,file.getName().lastIndexOf("."));
        File outFile = new File(file.getParentFile().getPath(),name + ".png");
        if(outFile.exists()){
            return outFile;
        }
        int scale = 10;
        double paperSpeed = 25;
        double sensitivity = 0.01;
        Ecg ecgData = new Hl7Parser(file).ecgParse();
        //自动算出宽高
        int width = EcgUtil.getWidth(scale, ecgData.getIncrement(), ecgData.getDigits().get(0).getDigits().length,paperSpeed);
        int height = EcgUtil.getHeight(scale,ecgData.getDigits().size());
        PaperDrawer paper = new PaperDrawer(width , height);
        paper.setScale(scale);
        DataDrawer drawer = new DataDrawer(paper,ecgData,paperSpeed,sensitivity);
        //写入到picture文件
        return new EcgPicWriter(outFile,drawer.draw()).write();
    }

    /**
     * 为图片生成缩略图
     * @return
     */
    public boolean toMinPic(String filePath,String outputFolder) {
        try {
            Map<String, Object> imageMap = ResizeImageUtils.getImage(filePath);
            ResizeImageUtils.writeThumbnails(((String) imageMap.get("imageName")).substring(0, ((String) imageMap.get("imageName")).lastIndexOf(".")),ResizeImageUtils.zoomImage((BufferedImage) imageMap.get("imageData")), outputFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @ResponseBody
    @RequestMapping("/ecgFile")
    public Map<String,Object> ecgFile( String type, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("result", false);
        // 保存上传的文件
        try {
            if( StringUtils.isBlank(type)) {
                throw new IOException("类型不能为空");
            }
            Calendar c = Calendar.getInstance();
            String basePath = path;
            String filePath = getCalendarPath(c) + type + File.separator;
            UploadUtils uploadUtils = new UploadUtils();
            uploadUtils.doSpringMVCUpload(basePath + filePath, IdGen.uuid() + ".xml", "ecgFile", request);
            File file = new File(uploadUtils.getFilePath());
            // 将心电图XML文件解析成心电图图片文件
            File pic = processEcgFile(file);
            // 为心电图图片文件生成缩略图
            toMinPic(pic.getPath(),pic.getParent());
            //处理逻辑
            FcUpload up = new FcUpload();
            up.setFileName(file.getName());
            up.setFilePath(filePath + file.getName());
            up.setFileSize(file.length());
            up.setType(type);
            uploadService.save(up);
            map.put("result", true);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    // 上传附件
    @PostMapping("/photoFile")
    public ResponseEntity uploadFiles(String type, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("result", false);
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
            // 获取上传的文件
            List<MultipartFile> fileList = multipartRequest.getFiles("photoFile");
            if(fileList != null){
                if(path.lastIndexOf("/") == -1 && path.lastIndexOf("\\\\") == -1){
                    path += File.separator;
                }
                for (MultipartFile multipartFile : fileList) {
                    Calendar c = Calendar.getInstance();
                    String tempPath = path + getCalendarPath(c) +  "photo" + File.separator;
                    // 将tempPath字符串中所有的\\替换成/
                    tempPath = tempPath.replaceAll("\\\\\\\\", "/");
                    // 将tempPath字符串中所有的\替换成/
                    tempPath = tempPath.replaceAll("\\\\", "/");
                    //用本来的名不安全
                    //String fileName = multipartFile.getOriginalFilename();
                    String fileName =  IdGen.uuid() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.')) ;
                    tempPath += fileName;
                    // 判断tempPath字符串是绝对路径还是相对路径
                    if (!(tempPath.startsWith("/") || tempPath.contains(":"))) {
                        tempPath = request.getServletContext().getRealPath(File.separator) + tempPath;
                    }
                    File f = new File(tempPath);
                    // 如果用于保存上传文件的目录不存在则创建该目录
                    if(!f.getParentFile().exists()){
                        f.getParentFile().mkdirs();
                    }
                    multipartFile.transferTo(f);
                    Map<String, Object> imageMap = ResizeImageUtils.getImage(f.getPath());
                    ResizeImageUtils.writeThumbnails(((String) imageMap.get("imageName")).substring(0, ((String) imageMap.get("imageName")).lastIndexOf(".")),ResizeImageUtils.zoomImage((BufferedImage) imageMap.get("imageData")), f.getParent());
                    //处理逻辑
                    FcUpload up = new FcUpload();
                    up.setFileName(f.getName());
                    up.setFilePath(tempPath.replace(path,""));
                    up.setFileSize(f.length());
                    up.setType(type);
                    uploadService.save(up);
                    result.put("result", true);
                }
            }
        return ResponseEntity.ok(result);
    }

}
