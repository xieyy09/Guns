package cn.stylefeng.guns.wechat.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.wechat.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: WeChatTask
 * @Description: 微信两小时定时任务体
 * @date 2016年3月10日 下午1:42:29
 */
@Slf4j
public class WeChatTask {
	/**
	 * @Description: 任务执行体
	 * @param @throws Exception
	 * @author
	 * @date 2016年3月10日 下午2:04:37
	 */
	public void getToken_getTicket() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		//获取token执行体
		params.put("grant_type", "client_credential");
		params.put("appid", AuthUtil.APPID);
		params.put("secret", AuthUtil.APPSECRET);
		String jstoken = HttpUtils.sendGet(AuthUtil.TOKENURL, params);
		String access_token = JSON.parseObject(jstoken).getString(
				"access_token"); // 获取到token并赋值保存
		AuthUtil.ACCESS_TOKEN = access_token;
		
		//获取jsticket的执行体
		params.clear();
		params.put("access_token", access_token);
		params.put("type", "jsapi");
		String jsticket = HttpUtils.sendGet(AuthUtil.TICKETURL, params);
		String jsapi_ticket = JSON.parseObject(jsticket).getString(
				"ticket");
		AuthUtil.JSAPI_TICKET = jsapi_ticket;  // 获取到js-SDK的ticket并赋值保存
		
		log.debug("jsapi_ticket================================================" + jsapi_ticket);
		log.debug(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
	}

}
