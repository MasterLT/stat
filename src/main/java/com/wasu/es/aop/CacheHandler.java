package com.wasu.es.aop;

import com.wasu.es.service.IDataService;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by MASTER_L on 2018/2/2.
 */
@Component
@Aspect
@Log4j
public class CacheHandler {

    @Resource
    private IDataService iDataService;


    /**
     * 首页数据缓存查询
     *
     * @param pjp
     * @return
     */
    @Around("execution(* com.wasu.es.controller_pc.DisAnalysisController.getMapData(..))&&(args(region,beginDate, endDate))")
    public Object around(ProceedingJoinPoint pjp, String region, String beginDate, String endDate) {
        Object object = null;
        try {
            if (beginDate.equals(endDate)) {
                Object cach = iDataService.getAnalysis(region, beginDate);
                if (cach != null) {
                    log.info("获取缓存成功" + cach);
                    return cach;
                }
            }
            object = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }

}
