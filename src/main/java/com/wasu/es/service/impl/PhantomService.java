package com.wasu.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wasu.es.Thread.PhantomRun;
import com.wasu.es.mapper.StatDistrictMapper;
import com.wasu.es.model.StatDistrict;
import com.wasu.tool.common.util.PhantomTool;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MASTER_L on 2017/12/7.
 */
@Service
@Log4j
public class PhantomService {

    @Value("${baseFilePath}")
    private String baseFilePath;

    @Autowired
    StatDistrictMapper statDistrictMapper;

    /**
     * 刷新所有站点截图及json
     *
     * @return 刷新成功地区列表
     */
    public List<String> phantom(String regin) {
        List<String> success = Lists.newArrayList();
        List<PhantomRun> threads = Lists.newArrayList();
        List<StatDistrict> list = null;
        if (StringUtils.isEmpty(regin)){
            list=statDistrictMapper.selectAll();
        }
        else {
            Example example = new Example(StatDistrict.class);
            example.createCriteria().andEqualTo("districtValue",regin);
            list=statDistrictMapper.selectByExample(example);
        }
        //多线程执行
        CountDownLatch latch = new CountDownLatch(list.size());
        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        for (StatDistrict statDistrict : list) {
            //为空继续循环
            if (StringUtils.isEmpty(statDistrict.getDistrictValue()) || StringUtils.isEmpty(statDistrict.getIndexUrl()))
                continue;
            PhantomRun run = new PhantomRun(statDistrict.getIndexUrl(), statDistrict.getDistrictValue(), baseFilePath, latch);
            executorService.execute(run);
            threads.add(run);
        }
        try {
            latch.await();
            for (PhantomRun run : threads) {
                if (run.isServiceUp())
                    success.add(run.getServiceName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return success;
    }

    /**
     * 刷新所有站点截图及json
     *
     * @return 刷新成功地区列表
     */
    public List<String> phantom1() {
        List<String> success = Lists.newArrayList();
        List<StatDistrict> list = statDistrictMapper.selectAll();
        for (StatDistrict statDistrict : list) {
            if (StringUtils.isEmpty(statDistrict.getDistrictValue()) || StringUtils.isEmpty(statDistrict.getIndexUrl()))
                continue;
            if (freshResource(statDistrict.getIndexUrl(), statDistrict.getDistrictValue()))
                success.add(statDistrict.getDistrictName());
        }
        return success;
    }

    /**
     * 刷新当前站点截图及json
     *
     * @param url
     * @param key
     * @return 成功失败
     */
    private boolean freshResource(String url, String key) {
        boolean res = false;
        String picFilePath = baseFilePath + key + ".png";
        String jsonFilePath = baseFilePath + key + ".json";
        try {
            boolean photoRes = PhantomTool.capture(url, picFilePath, null);
            List<PhantomTool.PositionInfo> list = PhantomTool.position(url, null);
            if (CollectionUtils.isNotEmpty(list)) {
                FileUtils.writeStringToFile(new File(jsonFilePath), JSON.toJSONString(list), Charset.defaultCharset(),
                        false);
            }
            if (photoRes && CollectionUtils.isNotEmpty(list)) {
                log.info(key + "截取成功!!");
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
