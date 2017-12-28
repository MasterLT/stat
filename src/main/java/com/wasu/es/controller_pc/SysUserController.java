package com.wasu.es.controller_pc;

import com.wasu.es.common.Constants;
import com.wasu.es.mapper.*;
import com.wasu.es.model.*;
import com.wasu.es.utils.GodUtils;
import com.wasu.es.utils.TypeChange;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class SysUserController extends PageBeanControl<StatUser>{

	@Resource
	private StatUserMapper sysUserMapper;
	
	@Resource
	private StatRoleMapper sysRoleMapper;
	
	@Resource
	private StatUserRoleMapper sysUserRoleMapper;
	
	@Resource
	private StatDistrictMapper statDistrictMapper;
	
	@Resource
	private StatUserDistrictMapper statUserDistrictMapper;
	
	/**
	 * 获取用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping()
	public String sysUserListFind(HttpServletRequest request , HttpServletResponse response){
		List<StatUser> list = sysUserMapper.selectAll();
		request.setAttribute("userList", list);
		if (!GodUtils.CheckNull(request.getParameter("msg"))) {
			request.setAttribute("msg", request.getParameter("msg"));
		}
		if (!GodUtils.CheckNull(request.getParameter("failMsg"))) {
			request.setAttribute("failMsg", request.getParameter("failMsg"));
		}
		return "/oss/user/userList_ace";
	}
	
	/**
	 * 弹窗，获取用户角色
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/roleDialog")
	public String sysUserRoleListFindDialog(
			HttpServletRequest request ,
			HttpServletResponse response,
			@RequestParam Integer id){
		List<StatRole> urList = sysRoleMapper.findUserRoleList(id);
		request.setAttribute("roleList", urList);
		request.setAttribute("userId", id);
		return "/oss/user/dialog/userRoleDialog_ace";
	}
	
	/**
	 * 更改用户角色信息
	 * 取配置给用户的角色id列和当前用户的角色id列，取交集，根据交集，删去多余的角色、增加需要的角色
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/roleChange")
	public String sysUserRoleChange(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Integer userId){
		String[] checkbox = request.getParameterValues("user_role");
		//配置给用户的角色id列
		List<Integer> roleIds = new ArrayList<Integer>();
		if (checkbox != null && checkbox.length != 0) {
			for (String roleIdStr : checkbox) {
				Integer roleId = TypeChange.stringToInt(roleIdStr);
				roleIds.add(roleId);
			}
		}
		
		//需增加队列
		List<StatUserRole> addList = new ArrayList<StatUserRole>();
		//角色id交集
		List<Integer> bothList = new ArrayList<Integer>();
		//需删除队列
		List<StatUserRole> deleteList = new ArrayList<StatUserRole>();
		//当前用户的全部角色
		List<StatUserRole> userRoleList = sysUserRoleMapper.findUserRole(userId);
		for (int i = 0; i < userRoleList.size(); i++) {
			StatUserRole ur = userRoleList.get(i);
			if (!roleIds.contains(ur.getRoleId())) {
				deleteList.add(ur);
			}else {
				bothList.add(ur.getRoleId());
			}
		}
		
		for (Integer roleId : roleIds) {
			if (!bothList.contains(roleId)) {
				StatUserRole userRole = new StatUserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userId);
				addList.add(userRole);
			}
		}
		
		if (!GodUtils.CheckNull(deleteList)) {
			sysUserRoleMapper.deleteBatchRole(deleteList);
		}
		if (!GodUtils.CheckNull(addList)) {
			sysUserRoleMapper.insertBatchRole(addList);
		}
		return "redirect:/user?msg=success";
	}
	
	
	
	
	@RequestMapping(value = "/userChangeDialog")
	public String userChangeDialog(
			HttpServletRequest request ,
			HttpServletResponse response,
			@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer activeType
			){
		StatUser user = (StatUser) request.getSession().getAttribute("sysUser");
		if (user != null && user.getType() == 1) {
			request.setAttribute("isAdmin", 1);
		}
		request.setAttribute("activeType", activeType);
		if (!GodUtils.CheckNull(id)) {
			StatUser sysUser = sysUserMapper.selectByPrimaryKey(id);
			request.setAttribute("user", sysUser);
		}
		return "/oss/user/dialog/userChangeDialog_ace";
	}
	
	@RequestMapping(value = "/userChange")
	public String userChange(
			HttpServletRequest request ,
			HttpServletResponse response,
			@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer activeType,
			@RequestParam(required = false) String account,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) boolean type,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String nickName,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) String email
			){
		StatUser sysUser = new StatUser();
		if (activeType == Constants.ACTIVE_TYPE_CREATE) {
			sysUser.setAccount(account);
			sysUser.setPassword(new Sha256Hash(password).toHex());
			if (type) {
				sysUser.setType(1);
			}
			sysUser.setName(name);
			sysUser.setNickname(nickName);
			sysUser.setMobile(mobile);
			sysUser.setEmail(email);
			sysUser.setStatus(1);
			sysUserMapper.insert(sysUser);
		}else {
			sysUser = sysUserMapper.selectByPrimaryKey(id);
			sysUser.setName(name);
			sysUser.setNickname(nickName);
			sysUser.setMobile(mobile);
			sysUser.setEmail(email);
			sysUserMapper.updateByPrimaryKey(sysUser);
		}
		return "redirect:/user?msg=success";
	}
	
	@ResponseBody
	@RequestMapping(value = "checkAccount")
	public String checkAccount(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String account) {
		Example example =new Example(StatUser.class);
		example.createCriteria().andEqualTo("account", account);
		List<StatUser> list = sysUserMapper.selectByExample(example);
		if (GodUtils.CheckNull(list)) {
			return "true";
		}
		return "false";
	}
	
	@RequestMapping(value = "/resetPassword")
	public String resetPassword(
			HttpServletRequest request ,
			HttpServletResponse response,
			@RequestParam(required = false) Integer id){
		StatUser admin = (StatUser) request.getSession().getAttribute(
				"sysUser");
		if (admin.getType() != null && admin.getType() == 1) {
			StatUser user = sysUserMapper.selectByPrimaryKey(id);
			user.setPassword(new Sha256Hash("123456").toHex());
			sysUserMapper.updateByPrimaryKey(user);
			return "redirect:/user?msg=success";
		}else {
			return "redirect:/user?failMsg=noPermission";
		}
	}
	
	/**
	 * 弹窗，获取用户区域
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/districtDialog")
	public String statUserDistrictListFindDialog(
			HttpServletRequest request ,
			HttpServletResponse response,
			@RequestParam Integer userId){
		List<StatDistrict> udList = statDistrictMapper.findUserDistrictList(userId);
		request.setAttribute("districtList", udList);
		request.setAttribute("userId", userId);
		return "/oss/user/dialog/userDistrictDialog_ace";
	}
	
	
	@RequestMapping(value = "/districtChange")
	public String sysUserDistrictChange(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Integer userId){
		String[] checkbox = request.getParameterValues("user_district");
		//配置给用户的区域id列
		List<StatUserDistrict> list = new ArrayList<StatUserDistrict>();
		if (checkbox != null && checkbox.length != 0) {
			for (String districtIdStr : checkbox) {
				Integer districtId = TypeChange.stringToInt(districtIdStr);
				
				StatUserDistrict ud = new StatUserDistrict();
				ud.setDistrictId(districtId);
				ud.setUserId(userId);
				list.add(ud);
			}
		}
		
		//删除旧关系
		statUserDistrictMapper.deleteUserDistrict(userId);
		//添加新关系
		if (!GodUtils.CheckNull(list)) {
			statUserDistrictMapper.insertBatchDistrict(list);
		}
		return "redirect:/user?msg=success";
	}
	
}
