package com.wasu.es.Thread;

import com.alibaba.fastjson.JSON;
import com.wasu.es.utils.DateUtils;
import com.wasu.tool.common.util.PhantomTool;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by MASTER_L on 2017/12/11.
 */
@Log4j
public class PhantomRun extends BaseCountDowLatch {

    private String url;
    private String key;
    private String baseFilePath;

    public PhantomRun(String url, String serviceName, String baseFilePath, CountDownLatch latch) {
        super(serviceName, latch);
        this.key = serviceName;
        this.url = url;
        this.baseFilePath = baseFilePath;
    }

    @Override
    public boolean doService() {
        boolean res = false;
        String picFilePath = baseFilePath + key + "-" + DateUtils.getDate() + ".png";
        String jsonFilePath = baseFilePath + key + "-" + DateUtils.getDate() + ".json";
        try {
            boolean photoRes = PhantomTool.capture(url, picFilePath, null);
            List<PhantomTool.PositionInfo> list = PhantomTool.position(url, null);
            if (CollectionUtils.isNotEmpty(list)) {
                FileUtils.writeStringToFile(new File(jsonFilePath), JSON.toJSONString(list), Charset.defaultCharset(),
                        false);
            }
            if (photoRes && CollectionUtils.isNotEmpty(list)) {
                log.info(Thread.currentThread() + "-" + key + "截取成功!!");
                res = true;
            } else {
                log.info(Thread.currentThread() + "-" + key + "截取失败!!----" +
                        "photoRes:" + photoRes + "--position:" + JSON.toJSON(list));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
