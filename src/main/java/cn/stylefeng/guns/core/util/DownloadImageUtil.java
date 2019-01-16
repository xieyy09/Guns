package cn.stylefeng.guns.core.util;


import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.config.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jasonx
 * @create 1/16/19 10:35 AM
 **/
@Component
@Slf4j
public class DownloadImageUtil {
    @Autowired
    private GunsProperties gunsProperties;
    /**
     * 从微信服务器获取媒体文件
     *
     * @param mediaId
     * @param path
     * @return
     */
    public String getImageFromWechat(String mediaId,String path) {
        // 接口访问凭证
        String accessToken = AuthUtil.ACCESS_TOKEN;
        // 拼接请求地址
        String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + mediaId;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/form-data");
            InputStream inputStream = conn.getInputStream();
            String fileName = null;
            if (inputStream != null) {
                HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                conn1.setDoInput(true);
                conn1.setDoOutput(true);
                conn1.setRequestMethod("GET");
                conn1.setRequestProperty("Content-Type", "application/form-data");
                InputStream inputStream1 = conn1.getInputStream();

                if (inputStream1 != null) {
                    // 根据内容类型获取扩展名
                    String contentType = conn1.getHeaderField("Content-Type");
                    String expandName = getFileexpandedName(contentType);
                    fileName = mediaId + expandName;
                    String fileSavePath = gunsProperties.getFileUploadPath()+path;
                    FileOutputStream fileOutputStream = new FileOutputStream(fileSavePath + File.separator + fileName);
                    int len;
                    byte[] data=new byte[1024];
                    while ((len = inputStream.read(data)) != -1) {
                        fileOutputStream.write(data, 0, len);
                    }
                }
            }
            inputStream.close();
            conn.disconnect();
            return fileName;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据内容类型判断文件扩展名
     * @param contentType 内容类型
     * @return
     */
    public static String getFileexpandedName(String contentType) {

        String fileEndWitsh = "";
        if ("image/jpeg".equals(contentType)) {
            fileEndWitsh = ".jpg";
        } else if ("audio/mpeg".equals(contentType)) {
            fileEndWitsh = ".mp3";
        } else if ("audio/amr".equals(contentType)) {
            fileEndWitsh = ".amr";
        } else if ("video/mp4".equals(contentType)) {
            fileEndWitsh = ".mp4";
        } else if ("video/mpeg4".equals(contentType)) {
            fileEndWitsh = ".mp4";
        }
        return fileEndWitsh;
    }

}