package cn.stylefeng.guns.wechat.controller;

import java.util.Collections;
import java.util.Map;

import cn.stylefeng.guns.wechat.common.JSSDK_Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * ClassName: WeChatController
 * @Description: 前端用户微信配置获取
 * @author
 * @date 2016年3月19日 下午5:57:36
 */
@Slf4j
@Controller
@RequestMapping("/weChatApi")
public class WeChatController {
	/**
	 * @Description: 前端获取微信JSSDK的配置参数
	 * @param @param response
	 * @param @param request
	 * @param @param url
	 * @param @throws Exception
	 * @author
	 * @date 2016年3月19日 下午5:57:52
	 */
	@RequestMapping("/jssdk")
	@ResponseBody
	public Object JSSDK_config(
			@RequestParam(value = "url", required = true) String url) {
		try {
			System.out.println(url);
			Map<String, String> configMap = JSSDK_Config.jsSDK_Sign(url);
			return configMap;
		} catch (Exception e) {
			return Collections.EMPTY_MAP;
		}

	}

}
