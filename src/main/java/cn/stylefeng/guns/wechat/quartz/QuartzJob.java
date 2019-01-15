package cn.stylefeng.guns.wechat.quartz;


import cn.stylefeng.guns.wechat.common.WeChatTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class QuartzJob{
	/**
	 * @Description: 任务执行获取token
	 * @param    
	 * @author
	 * @date 2016年3月10日 下午4:34:26
	 */
	@Scheduled(cron="0 0 */2  * * ? ")
	@PostConstruct
	public void workForToken() {
		try {
			WeChatTask timer = new WeChatTask();
			timer.getToken_getTicket();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}


}
