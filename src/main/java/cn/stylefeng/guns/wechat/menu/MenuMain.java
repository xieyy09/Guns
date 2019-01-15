package cn.stylefeng.guns.wechat.menu;

import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.wechat.util.HttpUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuMain {

	public static void main(String[] args) {
	
		ClickButton cbt=new ClickButton();
		cbt.setKey("image");
		cbt.setName("回复图片");
		cbt.setType("click");
		
		
		ViewButton vbt=new ViewButton();
		vbt.setUrl("http://www.baidu.com");
		vbt.setName("博客");
		vbt.setType("view");
		
		JSONArray sub_button=new JSONArray();
		sub_button.add(cbt);
		sub_button.add(vbt);
		
		
		JSONObject buttonOne=new JSONObject();
		buttonOne.put("name", "菜单");
		buttonOne.put("sub_button", sub_button);
		
		JSONArray button=new JSONArray();
		button.add(vbt);
		button.add(buttonOne);
		button.add(cbt);
		
		JSONObject menujson=new JSONObject();
		menujson.put("button", button);
		log.debug(menujson.toJSONString());
		//这里为请求接口的url   +号后面的是token，这里就不做过多对token获取的方法解释
		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ AuthUtil.ACCESS_TOKEN;
		
		try{
			String rs= HttpUtils.sendPostBuffer(url, menujson.toJSONString());
			log.debug(rs);
		}catch(Exception e){
			log.debug("请求错误！");
		}
	
	}

}
