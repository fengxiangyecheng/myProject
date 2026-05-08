package com.testdemo.controller;
import com.testdemo.base.BaseController;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;
import java.util.List;
import  com.testdemo.service.App_userService;
@Slf4j
@Api(value = "app_user", description = "用户管理", tags = "用户管理")
@Controller
@RequestMapping("/app_user")
public class App_userController extends BaseController {
@Autowired
private App_userService _service;
/** 获取用户管理页面 */
@RequestMapping(value = "/App_user", method = RequestMethod.POST)
@ApiOperation(value = "获取用户管理页面")
public String goApp_userPage() {  return ""; }
/**  添加用户管理 (重复数据不能添加) */
@RequestMapping(value = "/addApp_userNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加用户管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "username"  , value = "用户名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "password"  , value = "密码"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "nickname"  , value = "姓名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sex"  , value = "性别"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "phone"  , value = "联系方式"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "h_img"  , value = "头像"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_userNo() {
PageData pd =  getPageData();
return _service.addApp_userNo(this.putUserPd(pd)); }
/**  添加用户管理 (重复数据可以添加) */
@RequestMapping(value = "/addApp_userAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加用户管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "username"  , value = "用户名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "password"  , value = "密码"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "nickname"  , value = "姓名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sex"  , value = "性别"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "phone"  , value = "联系方式"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "h_img"  , value = "头像"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_userAll() {
PageData pd =  getPageData();
return _service.addApp_userAll(this.putUserPd(pd));}
/** 删除用户管理 */
@RequestMapping(value = "/deleteApp_user", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除用户管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteApp_user() {
PageData pd =  getPageData();
return _service.deleteApp_user(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateApp_user", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新用户管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "username"  , value = "用户名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "password"  , value = "密码"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "nickname"  , value = "姓名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sex"  , value = "性别"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "phone"  , value = "联系方式"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "h_img"  , value = "头像"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_prefs"  , value = "个人偏好JSON"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateApp_user() {
PageData pd =  getPageData();
return _service.updateApp_user(pd);}

/** 个人账号：当前登录用户资料（需 session，与 sys_user 个人中心分离） */
@GetMapping("/me")
@ResponseBody
@ApiOperation(value = "个人用户当前资料", notes = "登录类型为 personal 时返回 app_user 行（无密码）")
public ServerResponse<PageData> me() {
return _service.getMeForSession(getUserPd());}

/** 个人账号：保存自己的资料与 user_prefs(JSON) */
@RequestMapping(value = "/updateMe", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "个人用户保存资料")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "必须与当前登录 id 一致", required = true, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = false, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "password", value = "新密码（留空不改）", required = false, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "nickname", value = "姓名", required = false, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "sex", value = "性别", required = false, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "phone", value = "手机", required = false, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "user_prefs", value = "偏好 JSON 字符串", required = false, dataType = "String"),
})
public ServerResponse<String> updateMe() {
return _service.updateMeForSession(getUserPd(), getPageData());}

@RequestMapping(value = "/setAccountStatus", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "禁用/启用个人用户（系统管理员）")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "account_status", value = "1正常0禁用", required = true, dataType = "String"),
})
public ServerResponse<String> setAccountStatus() {
return _service.setAccountStatus(getPageData(), getUserPd());}

@RequestMapping(value = "/adminResetPassword", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "管理员重置个人用户密码")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "PASSWORD", value = "新密码", required = true, dataType = "String"),
})
public ServerResponse<String> adminResetPassword() {
return _service.adminResetPassword(getPageData(), getUserPd());}
/** 获取用户管理数据(非分页,搜索功能) */
@RequestMapping(value = "/queryApp_userKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取用户管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "username"  , value = "用户名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "password"  , value = "密码"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "nickname"  , value = "姓名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sex"  , value = "性别"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "phone"  , value = "联系方式"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "h_img"  , value = "头像"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryApp_userKey() {
PageData pd = this.getPageData();
return _service.queryApp_userKey(pd);}
/** 获取用户管理列表数据 */
@RequestMapping(value = "/queryPageApp_userKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取用户管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageApp_userKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPd(pd1);
Page page = getPage();
page.setPd(pd);
JSONObject json;
try{
List<PageData> systemUserList=_service.queryPageApp_userKeyList(page);
if(systemUserList==null){systemUserList=Collections.emptyList();}
json=viewReturnPageData(page,systemUserList);
}catch(Exception e){
log.error("queryPageApp_userKeyList",e);
page.setTotalResult(0);
json=viewReturnPageData(page,Collections.emptyList());
Throwable r=e;
while(r.getCause()!=null&&r.getCause()!=r){r=r.getCause();}
String msg=r.getMessage();
if(msg==null||msg.isEmpty()){msg=e.getClass().getSimpleName();}
if(msg.length()>500){msg=msg.substring(0,500);}
json.put("loadError",msg);
}
return json;
}
}
