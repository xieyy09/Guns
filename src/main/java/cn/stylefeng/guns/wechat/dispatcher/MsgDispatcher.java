package cn.stylefeng.guns.wechat.dispatcher;


import cn.stylefeng.guns.config.util.AuthUtil;
import cn.stylefeng.guns.wechat.message.resp.Article;
import cn.stylefeng.guns.wechat.message.resp.NewsMessage;
import cn.stylefeng.guns.wechat.message.resp.TextMessage;
import cn.stylefeng.guns.wechat.util.HttpUtils;
import cn.stylefeng.guns.wechat.util.MessageUtil;
import cn.stylefeng.guns.wechat.util.TemplateMessageUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * ClassName: MsgDispatcher
 * @Description: 消息业务处理分发器
 * @author
 * @date 2016年3月7日 下午4:04:21
 */
@Slf4j
public class MsgDispatcher {

	static String ModelMessage(String openid){
		Map wechatTemplate = new HashMap();
		wechatTemplate.put("template_id", "ty-40NAjq_GqQ_Z7XJuyKkPrUBqADnOaKI00CH9gtd8");
		wechatTemplate.put("touser", openid);//获取用户的openid

		Map<String,TemplateMessageUtil> mapdata = new HashMap<>();

		TemplateMessageUtil first  = new TemplateMessageUtil();
		first.setColor("#173177");
		first.setValue("发货通知");
		mapdata.put("first", first);

		TemplateMessageUtil text1  = new TemplateMessageUtil();
		text1.setColor("#173177");
		text1.setValue("您好，您所购买的商品已发货。");
		mapdata.put("text1", text1);

		TemplateMessageUtil text2  = new TemplateMessageUtil();
		text2.setColor("#173177");
		text2.setValue("測試");
		mapdata.put("text2", text2);

		TemplateMessageUtil text3  = new TemplateMessageUtil();
		text3.setColor("#173177");
		text3.setValue("12345678");
		mapdata.put("text3", text3);

		TemplateMessageUtil remark = new TemplateMessageUtil();
		remark.setColor("#173177");
		remark.setValue("请保持电话畅通>>");
		mapdata.put("remark", remark);

		JSONObject json = new JSONObject();
		json.put("data",mapdata);
		json.putAll(wechatTemplate);
		return json.toJSONString();
	}

	public static String processMessage(Map<String, String> map) {
		String openid=map.get("FromUserName"); //用户openid
		String mpid=map.get("ToUserName");   //公众号原始ID
		
		//普通文本消息
		TextMessage txtmsg=new TextMessage();
		txtmsg.setToUserName(openid);
		txtmsg.setFromUserName(mpid);
		txtmsg.setCreateTime(new Date().getTime());
		txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
			String content=map.get("Content");
			if("1".equals(content)){
				txtmsg.setContent("你好，你发送的内容是1！");
			}else if("2".equals(content)){
				txtmsg.setContent("你好，你发送的内容是2！");
			}else if("3".equals(content)){
				txtmsg.setContent("你好，你发送的内容是3！");
			}else if("4".equals(content)){
				txtmsg.setContent("你好，你发送的内容是4！");
			}else{
				txtmsg.setContent("你好，欢迎你！");
			}

			TextMessage txtmsgSend=new TextMessage();
			txtmsgSend.setCreateTime(new Date().getTime());
			txtmsgSend.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			txtmsgSend.setContent("这是一条测试中奖信息");
			txtmsgSend.setFromUserName(mpid);

			try {
				txtmsgSend.setToUserName("oW5mljp0STFOqHwr60iOxJrbC37Q");
				log.debug(AuthUtil.ACCESS_TOKEN);
//				String s = HttpUtils.sendPostBuffer(AuthUtil.SEND_MESSAGE + AuthUtil.ACCESS_TOKEN, "{\"touser\" :\"oW5mljp0STFOqHwr60iOxJrbC37Q\",\"msgtype\":\"text\",\"text\":{\"content\":\"这是一条测试中奖信息\"}}");
				String s = HttpUtils.sendPostBuffer(AuthUtil.SEND_TEMPLATE_MESSAGE + AuthUtil.ACCESS_TOKEN, ModelMessage("oW5mljp0STFOqHwr60iOxJrbC37Q"));
				log.debug(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			txtmsgSend.setToUserName("oW5mljpe9J8HsnTXUUtIirIk9Fh0");
			try {
//				String s = HttpUtils.sendPostBuffer(AuthUtil.SEND_MESSAGE + AuthUtil.ACCESS_TOKEN, "{\"touser\" :\"oW5mljpe9J8HsnTXUUtIirIk9Fh0\",\"msgtype\":\"text\",\"text\":{\"content\":\"这是一条测试中奖信息\"}}");
				String s = HttpUtils.sendPostBuffer(AuthUtil.SEND_TEMPLATE_MESSAGE + AuthUtil.ACCESS_TOKEN, ModelMessage("oW5mljpe9J8HsnTXUUtIirIk9Fh0"));
				log.debug(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return MessageUtil.textMessageToXml(txtmsg);
		}else if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
			//对图文消息
			NewsMessage newmsg=new NewsMessage();
			newmsg.setToUserName(openid);
			newmsg.setFromUserName(mpid);
			newmsg.setCreateTime(new Date().getTime());
			newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			log.debug("==============这是图片消息！");
			Article article=new Article();
			article.setDescription("这是图文消息"); //图文消息的描述
			article.setPicUrl(map.get("PicUrl")); //图文消息图片地址
			article.setTitle("图文消息");  //图文消息标题
			article.setUrl(map.get("PicUrl"));  //图文url链接
			List<Article> list=new ArrayList<Article>();
			list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
			newmsg.setArticleCount(list.size());
			newmsg.setArticles(list);
			return MessageUtil.newsMessageToXml(newmsg);
		}else if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
			txtmsg.setContent("");
			return MessageUtil.textMessageToXml(txtmsg);
		}else if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
			log.debug("==============这是位置消息！");
			txtmsg.setContent("位置已收到");
			return MessageUtil.textMessageToXml(txtmsg);
		}else if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
			log.debug("==============这是视频消息！");
			txtmsg.setContent("视频已收到");
			return MessageUtil.textMessageToXml(txtmsg);
		}else if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
			log.debug("==============这是语音消息！");
			return "";
		}

		return null;
	}
}
