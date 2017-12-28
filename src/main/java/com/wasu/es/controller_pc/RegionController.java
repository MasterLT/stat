package com.wasu.es.controller_pc;

import com.wasu.es.common.Constants;
import com.wasu.es.mapper.StatDistrictMapper;
import com.wasu.es.model.StatDistrict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by MASTER_L on 2017/12/5.
 */
@RequestMapping("/region")
@Controller
public class RegionController {

    @Autowired
    StatDistrictMapper statDistrictMapper;

    @RequestMapping()
    public ModelAndView region(ModelAndView mv) {
        mv.addObject("regionList", statDistrictMapper.selectAll());
        mv.setViewName("oss/user/region_ace");
        return mv;
    }

    /**
     * 地域变更弹窗
     *
     * @param request
     * @param response
     * @param id
     * @param activeType 操作类型，1创建，2更新
     * @return
     */
    @RequestMapping(value = "/regionChangeDialog")
    public String regionChangeDialog(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer activeType) {
        StatDistrict district = statDistrictMapper.selectByPrimaryKey(id);
        request.setAttribute("region", district);
        request.setAttribute("id", id);
        request.setAttribute("activeType", activeType);
        return "/oss/user/dialog/regionChangeDialog_ace";
    }

    /**
     * 地域变更
     *
     * @param request
     * @param response
     * @param activeType
     * @return
     */
    @RequestMapping(value = "/regionChange")
    public String regionChange(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer activeType, StatDistrict region) {
        if (activeType == Constants.ACTIVE_TYPE_UPDATE) {
            region.setGmtCreate(new Date());
        }
        region.setGmtUpdate(new Date());
        if (activeType == Constants.ACTIVE_TYPE_UPDATE) {
            statDistrictMapper.updateByPrimaryKeySelective(region);
        } else {
            statDistrictMapper.insert(region);
        }
        request.setAttribute("tabid", "role-list");
        return "redirect:/region";
    }

}
