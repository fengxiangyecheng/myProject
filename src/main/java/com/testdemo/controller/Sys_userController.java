package com.testdemo.controller;
import com.testdemo.base.BaseController;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import  com.testdemo.service.Sys_userService;
@Api(value = "sys_user", description = "管理员管理", tags = "管理员管理")
@Controller
@RequestMapping("/sys_user")
public class Sys_userController extends BaseController {
@Autowired
private Sys_userService _service;
/** 获取管理员管理页面 */
@RequestMapping(value = "/Sys_user", method = RequestMethod.POST)
@ApiOperation(value = "获取管理员管理页面")
public String goSys_userPage() {  return ""; }
/**  添加管理员管理 (重复数据不能添加) */
@RequestMapping(value = "/addSys_userNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加管理员管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "USERNAME"  , value = "用户名"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "PASSWORD"  , value = "密码"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "NAME"  , value = "昵称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "signature"  , value = "备注(可为空)"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addSys_userNo() {
PageData pd =  getPageData();
return _service.addSys_userNo(this.putUserPd(pd)); }
/**  添加管理员管理 (重复数据可以添加) */
@RequestMapping(value = "/addSys_userAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加管理员管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "USERNAME"  , value = "用户名"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "PASSWORD"  , value = "密码"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "NAME"  , value = "昵称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "signature"  , value = "备注(可为空)"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addSys_userAll() {
PageData pd =  getPageData();
return _service.addSys_userAll(this.putUserPd(pd));}
/** 删除管理员管理 */
@RequestMapping(value = "/deleteSys_user", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除管理员管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteSys_user() {
PageData pd =  getPageData();
return _service.deleteSys_user(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateSys_user", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新管理员管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "USERNAME"  , value = "用户名"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "PASSWORD"  , value = "密码"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "NAME"  , value = "昵称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = true  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "signature"  , value = "备注(可为空)"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateSys_user() {
PageData pd =  getPageData();
return _service.updateSys_user(pd);}

@RequestMapping(value = "/setAccountStatus", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "禁用/启用账号（系统管理员）")
public ServerResponse<String> setAccountStatus() {
return _service.setAccountStatus(getPageData(), getUserPd());}

@RequestMapping(value = "/adminResetPassword", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "管理员重置用户密码")
public ServerResponse<String> adminResetPassword() {
return _service.adminResetPassword(getPageData(), getUserPd());}
/** 获取管理员管理数据(非分页,搜索功能) */
@RequestMapping(value = "/querySys_userKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取管理员管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "USERNAME"  , value = "用户名"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "PASSWORD"  , value = "密码"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "NAME"  , value = "昵称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "create_time"  , value = "建立时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "signature"  , value = "备注(可为空)"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> querySys_userKey() {
PageData pd = this.getPageData();
return _service.querySys_userKey(pd);}
/** 获取管理员管理列表数据 */
@RequestMapping(value = "/queryPageSys_userKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取管理员管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageSys_userKeyList() {
PageData pd = getPageData();
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageSys_userKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
