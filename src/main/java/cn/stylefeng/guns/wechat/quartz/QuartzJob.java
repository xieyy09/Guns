package cn.stylefeng.guns.wechat.quartz;


import cn.stylefeng.guns.wechat.common.WeChatTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuartzJob{
	/**
	 * @Description: 任务执行获取token
	 * @param    
	 * @author
	 * @date 2016年3月10日 下午4:34:26
	 */
	public void workForToken() {
		try {
			WeChatTask timer = new WeChatTask();
			timer.getToken_getTicket();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}


}
