package com.tom.cf.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping("/ueditor/api/study/mistake")
public class Ok {
    @RequestMapping(value = "/image")
    public void upload(@RequestParam(value = "Type", required = false) String typeStr, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        if (Utils.isEmpty(typeStr)) {
            typeStr = "File";
        }
        JSONObject json = new JSONObject();
        JSONObject ob = validateUpload(request, typeStr);
        json = ob;
        ResponseUtils.renderJson(response, json.toString());
    }

    /**
     * 验证是否能够上传
     *
     * @param request
     * @param typeStr
     * @return
     * @throws JSONException
     */
    private JSONObject validateUpload(HttpServletRequest request, String typeStr) throws JSONException {
        JSONObject result = new JSONObject();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile uplFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
        String filename = FilenameUtils.getName(uplFile.getOriginalFilename());
        int fileSize = (int) (uplFile.getSize() / 1024);//单位KB
        String ext = FilenameUtils.getExtension(filename).toLowerCase(Locale.ENGLISH);

        // 此处可根据系统业务定义的配置项再次进行验证

        return result;
    }

}
