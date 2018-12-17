package cn.stylefeng.guns.modular.api.weChatApi;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/weChatApi")
@Slf4j
public class LoginApi  extends BaseController {
    @Autowired
    private GunsProperties gunsProperties;
    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.GET, path = "/loadImg")
    @ResponseBody
    public byte[] loadImg(@RequestParam("filename") String filename, @RequestParam("path") String path) {

        String fileSavePath = gunsProperties.getFileUploadPath();
        try {
            String pathname = fileSavePath + path+ File.separator+filename;
            return FileUtil.toByteArray(pathname);
        } catch (Exception e) {
            String pathname = fileSavePath + "noimage.png";
            return FileUtil.toByteArray(pathname);
        }

    }
}
