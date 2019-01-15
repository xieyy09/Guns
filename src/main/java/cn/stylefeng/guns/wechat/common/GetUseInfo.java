package cn.stylefeng.guns.wechat.common;

import java.util.HashMap;

import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.wechat.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: GetUseInfo
 * @Description: 获取微信用户信息
 * @author
 * @date 2016年3月18日 下午2:00:52
 */
@Slf4j
public class GetUseInfo {
	/**
	 * @Description: 通过openid获取用户微信信息
	 * @param @param openid
	 * @param @return
	 * @param @throws Exception   
	 * @author
	 * @date 2016年3月18日 下午2:01:30
	 */
	public static HashMap<String, String> Openid_userinfo(String openid)
			throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("access_token", AuthUtil.ACCESS_TOKEN);  //定时器中获取到的token
		params.put("openid", openid);  //需要获取的用户的openid
		params.put("lang", "zh_CN");
		String subscribers = HttpUtils.sendGet(AuthUtil.OPENIDUSERINFOURL, params);
		log.debug(subscribers);
		params.clear();
		//这里返回参数只取了昵称、头像、和性别
		params.put("nickname", JSON.parseObject(subscribers).getString("nickname")); //昵称
		params.put("headimgurl",JSON.parseObject(subscribers).getString("headimgurl"));  //图像
		params.put("sex", JSON.parseObject(subscribers).getString("sex"));  //性别
		return params;
	}

}
