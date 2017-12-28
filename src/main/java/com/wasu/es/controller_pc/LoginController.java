package com.wasu.es.controller_pc;

import com.wasu.es.mapper.StatConfigMapper;
import com.wasu.es.mapper.StatDistrictMapper;
import com.wasu.es.mapper.StatPermissionMapper;
import com.wasu.es.mapper.StatUserMapper;
import com.wasu.es.model.StatConfig;
import com.wasu.es.model.StatDistrict;
import com.wasu.es.model.StatPermission;
import com.wasu.es.model.StatUser;
import com.wasu.es.utils.GodUtils;
import com.wasu.es.utils.SysPermissionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    private StatUserMapper sysUserMapper;

    @Resource
    private StatPermissionMapper sysPermissionMapper;

    @Resource
    private StatConfigMapper sysConfigMapper;

    @Resource
    private StatDistrictMapper statDistrictMapper;

    @Value("${mapDataUrl}")
    private String mapDataUrl;

    @RequestMapping("/oss/login")
    public Object ossLogin(@RequestParam(required = false) String username,
                           @RequestParam(required = false) String password,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        setSysName(request);

//		System.out.println("username:" + username + " password:" + password);
        if (GodUtils.CheckNull(username) || GodUtils.CheckNull(password)) {
            return "login";
        }
//		Map<String, Object> r = new HashMap<String, Object>();
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
            // sha256加密
            password = new Sha256Hash(password).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(username,
                    password);
            subject.login(token);// 触发 UserRealm.java - doGetAuthenticationInfo
            subject.getSession().setTimeout(2 * 60 * 60 * 1000);
        } catch (UnknownAccountException e) {
//			r.put("code", 1);// 账号或密码不正确
            request.setAttribute("message", "账号或密码不正确");
            return "login";
        } catch (IncorrectCredentialsException e) {
//			r.put("code", 2);// 账号或密码不正确
            request.setAttribute("message", "账号或密码不正确");
            return "login";
        } catch (LockedAccountException e) {
//			r.put("code", 3);// 账号已被锁定,请联系管理员
            request.setAttribute("message", "账号已被锁定,请联系管理员");
            return "login";
        } catch (AuthenticationException e) {
//			r.put("code", 4);// 账户验证失败
            request.setAttribute("message", "账户验证失败");
            return "login";
        }
//		r.put("code", 0);

        if (!subject.isAuthenticated()) {
            return "login";
        }
        Example example = new Example(StatUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("account", username);
        List<StatUser> sysUsers = sysUserMapper.selectByExample(example);
        if (sysUsers.size() > 0) {
            StatUser sysUser = sysUserMapper.selectByExample(example).get(0);
            request.getSession().setAttribute("sysUser", sysUser);
            request.getSession().setAttribute("mapDataUrl", mapDataUrl);
            List<StatPermission> permission;
            if (sysUser.getType() != null && sysUser.getType() == 1) {
                permission = sysPermissionMapper.selectAll();
            } else {
                permission = sysPermissionMapper.findUserPermission(sysUser
                        .getId());
            }
            permission = SysPermissionUtils.getTreeMenu(permission);
            request.getSession().removeAttribute("userMenu");
            request.getSession().setAttribute("userMenu", permission);

            List<StatDistrict> district = new ArrayList<StatDistrict>();
            if (sysUser.getType() == null /*&& sysUser.getType() == 1*/) {
                district = statDistrictMapper.selectAll();
            } else {
                district = statDistrictMapper.findUserDistrict(sysUser.getId());
            }
            request.getSession().removeAttribute("userDistrict");
            request.getSession().setAttribute("userDistrict", district);
        }
        // return r;// TODO 定向页面
        // http://localhost:8080/demo/login?username=&password= (异常)
        return "redirect:/oss/main";
    }

    /**
     * 退出
     */
    @RequestMapping(value = {"/oss/logOut"}, method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        setSysName(request);

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null) {
            subject.logout();
            request.getSession().removeAttribute("sysUser");
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/oss", "/main", "/oss/main"}, method = RequestMethod.GET)
    public String mianOrLogin(HttpServletRequest request,
                              HttpServletResponse response) {
        setSysName(request);
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null) {
            try {
                StatUser user = (StatUser) request.getSession().getAttribute(
                        "sysUser");
                List<StatPermission> permission;
                if (user.getType() != null && user.getType() == 1) {
                    permission = sysPermissionMapper.selectAll();
                } else {
                    permission = sysPermissionMapper.findUserPermission(user.getId());
                }
                permission = SysPermissionUtils.getTreeMenu(permission);
                request.setAttribute("userMenu", permission);

                String name = user.getNickname();
                if (GodUtils.CheckNull(name)) {
                    name = user.getName();
                }
                if (GodUtils.CheckNull(name)) {
                    name = user.getAccount();
                }
                request.setAttribute("name", name);

                return "ace_myIndex";
            } catch (Exception e) {
                e.printStackTrace();
                return "login";
            }
        } else {
            return "login";
        }
    }

    /**
     * 设置系统名
     *
     * @param request
     */
    private void setSysName(HttpServletRequest request) {
        StatConfig sysConfig = sysConfigMapper.getConfigByKey("sys_name");
        if (sysConfig != null && sysConfig.getSysValue() != null) {
            request.setAttribute("sysName", sysConfig.getSysValue());
        } else {
            request.setAttribute("sysName", "拨测管理系统");
        }
    }

    @RequestMapping("{url}.html")
    public String page_app(@PathVariable("url") String url) {
        return url + ".html";
    }

    @RequestMapping(value = "/oss/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        return "oss/data_index";
    }


}
