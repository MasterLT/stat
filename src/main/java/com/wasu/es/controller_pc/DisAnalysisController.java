package com.wasu.es.controller_pc;

import com.wasu.es.model.StatDistrict;
import com.wasu.es.model.StatRole;
import com.wasu.es.model.dto.ModelDTO;
import com.wasu.es.utils.GodUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/disAnalysis")
public class DisAnalysisController extends PageBeanControl<StatRole>{

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping()
	public String map(Model model, HttpServletRequest request) {
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
		model.addAttribute("district", "gansu");
		return "data_index";
	}
	
	@RequestMapping("/chart")
	public String chart2(Model model, HttpServletRequest request) {
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		String region=request.getParameter("region");
		
		model.addAttribute("endDate", endDate);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("region", region);
		return "chart";
	}

}
