package com.wasu.es.controller_pc;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wasu.es.utils.GodUtils;
import com.wasu.es.utils.PageBean;
import com.wasu.es.utils.TypeChange;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PageBeanControl<T>{

	public PageBean getPageBean(HttpServletRequest request){
		int pageSize = TypeChange.stringToInt(request.getParameter("pageSize"));
		int currentPage = TypeChange.stringToInt(request.getParameter("currentPage"));
		return getPageBean(pageSize, currentPage);
	}
	
	public PageBean getPageBean(Integer pageSize, Integer currentPage) {
		if (GodUtils.CheckNull(pageSize) || pageSize == 0) {
			pageSize = 20;
		}
		if (GodUtils.CheckNull(currentPage) || currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(pageSize);
		pageBean.setCurrentPage(currentPage != null ? currentPage : 1);
		
		PageHelper.startPage(currentPage, pageSize);
		return pageBean;
	}
	
	public PageBean getPageBean(List<T> list , PageBean pageBean) {
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		pageBean.setRecordCount(pageInfo.getTotal());
		pageBean.setCurrentPage(pageInfo.getPageNum());
		return pageBean;
	}
	
}
