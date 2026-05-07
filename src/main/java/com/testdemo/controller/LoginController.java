package com.testdemo.controller;

import com.testdemo.base.BaseController;
import com.testdemo.common.ServerResponse;
import com.testdemo.config.AllConfig;
import com.testdemo.entity.system.PageData;
import com.testdemo.service.App_userService;
import com.testdemo.service.Operation_logService;
import com.testdemo.util.Tools;
import com.testdemo.service.user.SysUserOldService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//@ApiIgnore
@Api(value = "login", description = "登录")
@Controller
public class LoginController extends BaseController {

    private final SysUserOldService sysuserService;
    private final App_userService appUserService;

    @Autowired
    private Operation_logService operationLogService;

    public LoginController(SysUserOldService sysuserService, App_userService appUserService) {
        this.sysuserService = sysuserService;
        this.appUserService = appUserService;
    }

    private static boolean isAccountDisabled(PageData user) {
        if (user == null) {
            return false;
        }
        Object s = user.get("account_status");
        if (s == null) {
            return false;
        }
        return "0".equals(String.valueOf(s).trim());
    }

    /** 去掉敏感字段再写入 session / 返回给前端 */
    private static void stripPasswordFields(PageData user) {
        if (user == null) {
            return;
        }
        user.remove("PASSWORD");
        user.remove("password");
    }

    /**
     * 将 app_user 登录行转为与后台 session 一致的字段（role_manage_id=3 表示个人用户，与 role_manage 表一致）
     */
    /** 统一登录账号参数名，并去掉首尾空格（避免 app_user 与请求不一致） */
    private static void normalizeLoginCredentials(PageData pd) {
        if (pd == null) {
            return;
        }
        String u = firstNonEmptyTrim(pd.getString("USERNAME"), pd.getString("username"));
        String p = firstNonEmptyTrim(pd.getString("PASSWORD"), pd.getString("password"));
        if (u != null) {
            pd.put("USERNAME", u);
        }
        if (p != null) {
            pd.put("PASSWORD", p);
        }
    }

    private static String firstNonEmptyTrim(String a, String b) {
        if (!Tools.isEmpty(a)) {
            return a.trim();
        }
        if (!Tools.isEmpty(b)) {
            return b.trim();
        }
        return null;
    }

    private static PageData toSessionFromAppUser(PageData row) {
        PageData u = new PageData();
        u.put("id", row.get("id"));
        u.put("USERNAME", row.getString("username"));
        u.put("NAME", row.get("nickname"));
        u.put("role_manage_id", "3");
        u.put("login_user_type", "personal");
        Object st = row.get("account_status");
        u.put("account_status", st != null ? String.valueOf(st).trim() : "1");
        u.put("phone", row.get("phone"));
        u.put("sex", row.get("sex"));
        u.put("h_img", row.get("h_img"));
        Object prefs = row.get("user_prefs");
        u.put("user_prefs", prefs != null ? String.valueOf(prefs) : "");
        return u;
    }

    @ApiOperation(value = "跳转到用户登录页面")
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ApiOperation(value = "跳转到用户注册页面")
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @ApiOperation(value = "跳转到前台主页页面")
    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @ApiOperation(value = "跳转到菜单管理页面")
    @GetMapping("/idx_app_menu")
    public String idx_app_menu() {
        return "idx_app_menu";
    }

    @ApiOperation(value = "跳转到角色管理页面")
    @GetMapping("/idx_role_manage")
    public String idx_role_manage() {
        return "idx_role_manage";
    }

    @ApiOperation(value = "跳转到角色管理页面")
    @GetMapping("/demo_table")
    public String demo_table() {
        return "demo_table";
    }

    @ApiOperation(value = "跳转到角色权限页面")
    @GetMapping("/idx_jurisdiction_manage")
    public String idx_jurisdiction_manage() {
        return "idx_jurisdiction_manage";
    }


    @ApiOperation(value = "跳转到后台登录页面")
    @GetMapping("/idx_login")
    public String idxLogin() {
        return "idx_login";
    }

    @ApiOperation(value = "跳转到后台注册页面")
    @GetMapping("/idx_register")
    public String idxRegister() {
        return "idx_register";
    }

    @GetMapping("/idx_index")
    @ApiOperation(value = "跳转到后台主页面")
    public String idxIndex(ModelMap model) {
        //验证session是否有效
        HttpSession session = request.getSession();
        PageData user = (PageData) session.getAttribute(AllConfig.SESSION_KEY);
        if (user != null) {
            //先读取session里面是否包含菜单，如果包含则直接使用，不包含则重新获取并设置进入session
            model.addAttribute("name", user.getString("NAME"));//用户名称
            return "idx_index";
        } else {
            //返回到首页
            return "idx_login";
        }

    }

    @GetMapping("/loginPost")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "USERNAME", value = "用户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "PASSWORD", value = "密码", required = true, dataType = "String"),
    })
    public @ResponseBody
    Map<String, Object> loginPost(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        // 前端传入数据
        PageData pd = getPageData();
        normalizeLoginCredentials(pd);
        if (Tools.isEmpty(pd.getString("USERNAME")) || Tools.isEmpty(pd.getString("PASSWORD"))) {
            map.put("msg", "500");
            map.put("result", "请输入账号和密码");
            return map;
        }
        // 获取浏览器信息  2018/06/07 修改
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        // 获取浏览器版本号
        Version version = browser.getVersion(request.getHeader("User-Agent"));
        String versionInfo = null;
        try {
            versionInfo = browser.getName() + "/" + version.getVersion();
        } catch (NullPointerException e) {
        }

        String ip = request.getRemoteAddr();
        String tryUser = pd.getString("USERNAME");
        // 先校验后台管理员（sys_user），再校验个人用户（app_user），与业务库双轨账号模型对齐
        PageData user = sysuserService.getLoginValidation(pd);
        if (user == null) {
            PageData appRow = appUserService.getLoginValidation(pd);
            if (appRow == null) {
                operationLogService.saveLog(null, tryUser, "LOGIN_FAIL", "用户名或密码错误", ip);
                map.put("msg", "500");
                map.put("result", "密码错误");
                return map;
            }
            if (isAccountDisabled(appRow)) {
                operationLogService.saveLog(String.valueOf(appRow.get("id")), appRow.getString("username"),
                        "LOGIN_DENIED", "账号已禁用", ip);
                map.put("msg", "503");
                map.put("result", "账号已被禁用，请联系管理员");
                return map;
            }
            user = toSessionFromAppUser(appRow);
        } else if (isAccountDisabled(user)) {
            operationLogService.saveLog(String.valueOf(user.get("id")), user.getString("USERNAME"),
                    "LOGIN_DENIED", "账号已禁用", ip);
            map.put("msg", "503");
            map.put("result", "账号已被禁用，请联系管理员");
            return map;
        }
        //判定当前用户是否已经登录过，如果登录不让重新登录
        PageData userPd = (PageData) session.getAttribute(AllConfig.SESSION_KEY);
        user.put("version", versionInfo);//浏览器版本号
        stripPasswordFields(user);
        if (userPd != null) {//表示登录过则不需要多次登录直接提醒!
            map.put("msg", "200");
            map.put("result", "你已经登录过无需再次登录!");
        } else {
            session.setAttribute(AllConfig.SESSION_KEY, user);
            map.put("msg", "200");
            map.put("result", "登录成功!");
            map.put("data", user);
            operationLogService.saveLog(String.valueOf(user.get("id")), user.getString("USERNAME"),
                    "LOGIN_OK", "登录成功", ip);
        }

        return map;
    }

    //    @ApiIgnore
    @GetMapping("/logout")
    @ApiOperation(value = "退出登录")
    public String logout(HttpSession session) {
        // 移除session账户信息
        session.removeAttribute(AllConfig.SESSION_KEY);
        return "login";
    }

    @GetMapping("/registerUser")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "USERNAME", value = "用户", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "PASSWORD", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "NAME", value = "昵称", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "jurisdiction_manage_id", value = "角色ID", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "signature", value = "签名", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "student_number", value = "学生号", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "性别", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "major", value = "专业", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = false, dataType = "String"),
    })
    @ApiOperation(value = "注册")
    public ServerResponse registerUser() {
        PageData pd = this.getPageData();
        return sysuserService.registerUser(pd);
    }

    /**
     * 上传
     */
    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "上传")
    public JSONObject upload(@RequestParam("file") MultipartFile file) {
        JSONObject json = this.uploadPhoto(file);
        //文件不能为空
        if (json.get("msg").equals(500)) return this.getFalJson();
        return json;
    }
}
