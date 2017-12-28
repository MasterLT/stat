package com.wasu.es.controller_pc;

import com.alibaba.fastjson.JSON;
import com.wasu.es.common.Constants;
import com.wasu.es.mapper.StatPermissionMapper;
import com.wasu.es.mapper.StatRoleMapper;
import com.wasu.es.mapper.StatRolePermissionMapper;
import com.wasu.es.model.StatPermission;
import com.wasu.es.model.StatRole;
import com.wasu.es.model.StatRolePermission;
import com.wasu.es.model.dto.TreeBean;
import com.wasu.es.utils.GodUtils;
import com.wasu.es.utils.SysPermissionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by MASTER_L on 2017/12/21.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private StatRoleMapper sysRoleMapper;

    @Resource
    private StatPermissionMapper sysPermissionMapper;

    @Resource
    private StatRolePermissionMapper sysRolePermissionMapper;


    /**
     * 获取角色信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping()
    public String sysRoleListFind(HttpServletRequest request, HttpServletResponse response) {
        List<StatRole> list = sysRoleMapper.selectAll();
        if (!GodUtils.CheckNull(request.getParameter("msg"))) {
            request.setAttribute("msg", request.getParameter("msg"));
        }
        request.setAttribute("roleList", list);
        return "/oss/user/roleList_ace";
    }

    /**
     * 获取角色变更弹窗
     *
     * @param request
     * @param response
     * @param id
     * @param activeType 操作类型，1创建，2更新
     * @return
     */
    @RequestMapping(value = "/roleChangeDialog")
    public String roleUpdateDialog(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer activeType) {
        StatRole role = sysRoleMapper.selectByPrimaryKey(id);
        request.setAttribute("role", role);
        request.setAttribute("id", id);
        request.setAttribute("activeType", activeType);
        return "/oss/user/dialog/roleChangeDialog_ace";
    }

    /**
     * 角色变更
     *
     * @param request
     * @param response
     * @param id
     * @param activeType
     * @param roleName
     * @param level
     * @return
     */
    @RequestMapping(value = "/roleChange")
    public String roleUpdate(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer activeType,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Integer level) {
        StatRole role = new StatRole();
        if (activeType == Constants.ACTIVE_TYPE_UPDATE) {
            role = sysRoleMapper.selectByPrimaryKey(id);
            role.setGmtCreate(new Date());
        }
        role.setRole(roleName);
        role.setLevel(level);
        role.setGmtUpdate(new Date());
        if (activeType == Constants.ACTIVE_TYPE_UPDATE) {
            sysRoleMapper.updateByPrimaryKeySelective(role);
        } else {
            sysRoleMapper.insert(role);
        }
        request.setAttribute("tabid", "role-list");
        return "redirect:/role";
    }

    /**
     * 权限配置
     *
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/permission")
    public String permissionData(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam Integer id) {
        List<StatPermission> list = sysPermissionMapper.findRolePermission(id);
        StatPermission sp = SysPermissionUtils.getTreeMenuPermission(list);

        TreeBean tb = new TreeBean();
        TreeBean.permissionTreeBean(sp, tb);
        String permission_json = JSON.toJSONString(tb);
        permission_json = permission_json.replaceAll("\"", "'");

        request.setAttribute("permission_json", permission_json);
        request.setAttribute("roleId", id);
        return "/oss/user/dialog/rolePermission_ace";
    }

    /**
     * 权限更新
     *
     * @param request
     * @param response
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/permission/update")
    public Object permissionDataUpdate(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer roleId) {
        String menus = request.getParameter("menus");
        List<TreeBean> tbList = JSON.parseArray(menus, TreeBean.class);
        TreeBean tb = tbList.get(0);

        List<Integer> spList = new ArrayList<Integer>();
        clearTreeBean(tb, spList);
        //补上第一条权限变更情况
        if (tb.isChecked() != tb.isCheckedOld()) {
            spList.add(tb.getId());
        }
        List<StatRolePermission> addList = new ArrayList<StatRolePermission>();
        List<StatRolePermission> deleteList = new ArrayList<StatRolePermission>();
        for (Integer permissionId : spList) {
            StatRolePermission rolePermission = sysRolePermissionMapper.getRolePermission(roleId, permissionId);
            if (rolePermission == null || rolePermission.getId() == null) {
                StatRolePermission sRolePermission = new StatRolePermission();
                sRolePermission.setPermissionId(permissionId);
                sRolePermission.setRoleId(roleId);
                sRolePermission.setGmtCreate(new Date());
                addList.add(sRolePermission);
            } else {
                deleteList.add(rolePermission);
            }
        }
        //批量新增、删除
        if (!GodUtils.CheckNull(addList)) {
            sysRolePermissionMapper.insertBatchRolePermission(addList);
        }
        if (!GodUtils.CheckNull(deleteList)) {
            sysRolePermissionMapper.deleteBatchRolePermission(deleteList);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", Constants.JSON_RESULT_SUCCESS);
        return result;
    }

    private void clearTreeBean(TreeBean parent, List<Integer> spList) {
        List<TreeBean> tbList = parent.getChildren();
        if (GodUtils.CheckNull(tbList)) {
            return;
        }
        for (int i = 0; i < tbList.size(); i++) {
            TreeBean tb = tbList.get(i);
            //只筛选有改动的
            if (tb.isChecked() != tb.isCheckedOld()) {
                if (!tb.isChecked()) {
                    //未勾选情况下，只删除level等级为3的权限信息
                    if (tb.getLevel() == Constants.PERMISSION_LEVEL_3) {
                        spList.add(tb.getId());
                    }
                } else {
                    spList.add(tb.getId());
                }
            }
            clearTreeBean(tb, spList);
        }
    }

}
