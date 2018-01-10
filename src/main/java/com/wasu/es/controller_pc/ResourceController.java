package com.wasu.es.controller_pc;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.wasu.es.model.EsPosPvUv;
import com.wasu.es.model.StatDistrict;
import com.wasu.es.model.StatRole;
import com.wasu.es.model.dto.DatatablesViewPage;
import com.wasu.es.model.dto.ModelDTO;
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
    public Object getDetailFromData(String region, String beginDate, String endDate, String keyword, Integer type) throws Exception {
        DatatablesViewPage<ResourceDTO> res = iDataService.getFromOrToDetail("logstash-" + region + "-logging-*", keyword, beginDate, endDate,type);
        return res;
    }
    @RequestMapping("/getRealName")
    @ResponseBody
    public Object getRealName(String region, String beginDate, String endDate, String keyword) throws Exception {
        List res = iDataService.getRealName("logstash-" + region + "-logging-*", keyword, beginDate, endDate);
        return res;
    }
}
