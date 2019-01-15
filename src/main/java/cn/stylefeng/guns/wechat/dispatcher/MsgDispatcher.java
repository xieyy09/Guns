package cn.stylefeng.guns.wechat.dispatcher;


import cn.stylefeng.guns.wechat.message.resp.Article;
import cn.stylefeng.guns.wechat.message.resp.NewsMessage;
import cn.stylefeng.guns.wechat.message.resp.TextMessage;
import cn.stylefeng.guns.wechat.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MsgDispatcher
 * @Description: 消息业务处理分发器
 * @author
 * @date 2016年3月7日 下午4:04:21
 */
@Slf4j
public class MsgDispatcher {
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
			return MessageUtil.textMessageToXml(txtmsg);
		}
		
		//对图文消息
		NewsMessage newmsg=new NewsMessage();
		newmsg.setToUserName(openid);
		newmsg.setFromUserName(mpid);
		newmsg.setCreateTime(new Date().getTime());
		newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
			log.debug("==============这是图片消息！");
			Article article=new Article();
			article.setDescription("这是图文消息1"); //图文消息的描述
			article.setPicUrl(" "); //图文消息图片地址
			article.setTitle("图文消息1");  //图文消息标题
			article.setUrl("");  //图文url链接
			List<Article> list=new ArrayList<Article>();
			list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
			newmsg.setArticleCount(list.size());
			newmsg.setArticles(list);
			return MessageUtil.newsMessageToXml(newmsg);
		}
		
		
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
			txtmsg.setContent("");
			return MessageUtil.textMessageToXml(txtmsg);
		}
		
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
			log.debug("==============这是位置消息！");
			return "";
		}
		
		
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
			log.debug("==============这是视频消息！");
			return "";
		}
		
		
		if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
			log.debug("==============这是语音消息！");
			return "";
		}

		return null;
	}
}
