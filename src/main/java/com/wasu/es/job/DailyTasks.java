package com.wasu.es.job;

import com.alibaba.fastjson.JSON;
import com.wasu.es.controller_pc.DisAnalysisController;
import com.wasu.es.enums.AnalysisEnum;
import com.wasu.es.mapper.StatDailyAnalysisMapper;
import com.wasu.es.model.StatDailyAnalysis;
import com.wasu.es.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class DailyTasks {

    Log log = LogFactory.getLog(DailyTasks.class);
    @Autowired
    DisAnalysisController disAnalysisController;
    @Autowired
    StatDailyAnalysisMapper statDailyAnalysisMapper;

    /**
     * 首页数据按日缓存
     */
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void indexAnalysisByDay() {
        Date date = new Date();
        String day = DateUtils.formatDate(DateUtils.getSpecifiedDay(new Date(), -1));
        String region = "gansu";
        try {
            Example example = new Example(StatDailyAnalysis.class);
            example.createCriteria().andEqualTo("region", region).andEqualTo("day", day).andEqualTo("type", AnalysisEnum.INDEX.getType());
            Object object = disAnalysisController.getMapData(region, day, day);
            List<StatDailyAnalysis> list = statDailyAnalysisMapper.selectByExample(example);
            if (list.size() > 0) {
                StatDailyAnalysis statDailyAnalysis = list.get(0);
                statDailyAnalysis.setGmtUpdate(date);
                statDailyAnalysis.setValue(JSON.toJSONString(object));
                statDailyAnalysisMapper.updateByPrimaryKey(statDailyAnalysis);
                log.info("更新首页数据按日缓存-" + region + "--" + day);
                log.info(JSON.toJSONString(statDailyAnalysis));
            } else {
                StatDailyAnalysis statDailyAnalysis = new StatDailyAnalysis(region, AnalysisEnum.INDEX.getType(), day, date, date, JSON.toJSONString(object));
                statDailyAnalysisMapper.insertSelective(statDailyAnalysis);
                log.info("首页数据按日缓存生成功-" + region + "--" + day);
                log.info(JSON.toJSONString(statDailyAnalysis));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("首页数据按日缓存失败-" + region + "--" + day);
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void reportCurrentTime() {
        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat().format(new Date()));
    }

    //每10秒执行一次
    @Scheduled(cron = "0 */10 *  * * * ")
    public void reportCurrentByCron() {
        System.out.println("Scheduling Tasks Examples By Cron: The time is now " + dateFormat().format(new Date()));
    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

}