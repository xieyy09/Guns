/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns;

import cn.stylefeng.guns.modular.activity.service.IActivityDetailsService;
import cn.stylefeng.guns.modular.system.model.ActivityDetails;
import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(exclude = WebAutoConfiguration.class)
@EnableScheduling
public class GunsApplication {
    @Autowired
    private IActivityDetailsService activityDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(GunsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GunsApplication.class, args);
        logger.info("GunsApplication is success!");
    }

    @Scheduled(fixedRate = 300000)
    public void schActivityState(){
        Map<String, Object> paramMap=new HashMap<>();
        List<ActivityDetails> activityDetailsList = activityDetailsService.selectByMap(paramMap);
        activityDetailsList.forEach(a->{
            Date beginTime = a.getBeginTime();
            Date endTime = a.getEndTime();
            Date now=new Date();
            if(beginTime.getTime()<now.getTime()&&now.getTime()<endTime.getTime()){
                a.setActivityState(1);
            }else if(now.getTime()>endTime.getTime()){
                a.setActivityState(-1);
            }else{
                a.setActivityState(0);
            }
            activityDetailsService.updateById(a);
        });
    }
}
