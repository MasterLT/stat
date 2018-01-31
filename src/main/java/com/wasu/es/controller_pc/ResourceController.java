package com.wasu.es.controller_pc;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.wasu.es.model.EsPosPvUv;
import com.wasu.es.model.StatDistrict;
import com.wasu.es.model.StatRole;
import com.wasu.es.model.dto.DatatablesViewPage;
import com.wasu.es.model.dto.ModelDTO;
import com.wasu.es.model.dto.PieChartDTO;
import com.wasu.es.model.dto.ResourceDTO;
import com.wasu.es.service.IDataService;
import com.wasu.es.utils.GodUtils;
import com.wasu.es.utils.HttpHelper;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends PageBeanControl<StatRole> {

    @Resource
    private IDataService iDataService;

    @Value("${mapDataUrl}")
    private String mapDataUrl;

    /**
     * 来源分析
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping()
    public String resource(Model model, HttpServletRequest request) {
        List<StatDistrict> district = (List<StatDistrict>) request.getSession()
                .getAttribute("userDistrict");
        List<ModelDTO> dis = new ArrayList<ModelDTO>();
        if (!GodUtils.CheckNull(district)) {
            for (StatDistrict statDistrict : district) {
                dis.add(new ModelDTO(statDistrict.getDistrictName(),
                        statDistrict.getDistrictValue()));
            }
        }
        model.addAttribute("dis", dis);
        return "data_resource";
    }

    //    @RequestMapping(value = "/getDetailFromData/{region}", produces = "application/json;charset=UTF-8")
    @RequestMapping("/getDetailFromData")
    @ResponseBody
    public Object getDetailFromData(String region, String beginDate, String endDate, String keyword) throws Exception {
        long start = System.currentTimeMillis();
        PieChartDTO[] res = new PieChartDTO[2];
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            Future<PieChartDTO> fromData = executorService.submit(new Callable<PieChartDTO>() {
                public PieChartDTO call() throws Exception {
                    return iDataService.getFromOrToDetail("logstash-" + region + "-logging-*", keyword, beginDate, endDate, 1);
                }
            });
            Future<PieChartDTO> toData = executorService.submit(new Callable<PieChartDTO>() {
                public PieChartDTO call() throws Exception {
                    return iDataService.getToDetail("logstash-" + region + "-logging-*", keyword, beginDate, endDate);
                }
            });
            res[0] = fromData.get();
            res[1] = toData.get();
            System.out.println(System.currentTimeMillis() - start);
        } finally {
            executorService.shutdown();
        }
        return res;
    }

    @RequestMapping("/getRealName")
    @ResponseBody
    public Object getRealName(String region, String beginDate, String endDate, String keyword) throws Exception {
        List res = iDataService.getRealName("logstash-" + region + "-logging-*", keyword, beginDate, endDate);
        return res;
    }
}
