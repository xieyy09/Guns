package cn.stylefeng.guns.wechat.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.stylefeng.guns.wechat.dispatcher.EventDispatcher;
import cn.stylefeng.guns.wechat.dispatcher.MsgDispatcher;
import cn.stylefeng.guns.wechat.util.MessageUtil;
import cn.stylefeng.guns.wechat.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatSecurity {

	/**
	 * 
	 * @Description: 用于接收get参数，返回验证参数
	 * @param @param request
	 * @param @param response
	 * @param @param signature
	 * @param @param timestamp
	 * @param @param nonce
	 * @param @param echostr
	 * @author
	 * @date 2016年3月4日 下午6:20:00
	 */
	@RequestMapping(value = "security", method = RequestMethod.GET)
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr) {
//		try {
//			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
//				PrintWriter out = response.getWriter();
//				out.print(echostr);
//				out.close();
//			} else {
//				log.info("这里存在非法请求！");
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
	}

	/**
	 * @Description: 接收微信端消息处理并做分发
	 * @param @param request
	 * @param @param response   
	 * @author
	 * @date 2016年3月7日 下午4:06:47
	 */
	@RequestMapping(value = "security", method = RequestMethod.POST)
	public void DoPost(HttpServletRequest request,HttpServletResponse response) {
//		response.setCharacterEncoding("utf-8");
//		try{
//			Map<String, String> map= MessageUtil.parseXml(request);
//			String msgtype=map.get("MsgType");
//			if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
//				String msgrsp= EventDispatcher.processEvent(map); //进入事件处理
//				PrintWriter out = response.getWriter();
//				out.print(msgrsp);
//				out.close();
//			}else{
//				String msgrsp= MsgDispatcher.processMessage(map); //进入消息处理
//				PrintWriter out = response.getWriter();
//				out.print(msgrsp);
//				out.close();
//			}
//		}catch(Exception e){
//			log.error(e.getMessage());
//		}
	}
}
