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
import  com.testdemo.service.Role_manageService;
@Api(value = "role_manage", description = "角色管理", tags = "角色管理")
@Controller
@RequestMapping("/role_manage")
public class Role_manageController extends BaseController {
@Autowired
private Role_manageService _service;
/** 获取角色管理页面 */
@RequestMapping(value = "/Role_manage", method = RequestMethod.POST)
@ApiOperation(value = "获取角色管理页面")
public String goRole_managePage() {  return ""; }
/**  添加角色管理 (重复数据不能添加) */
@RequestMapping(value = "/addRole_manageNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加角色管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "role_name"  , value = "角色名称"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addRole_manageNo() {
PageData pd =  getPageData();
return _service.addRole_manageNo(this.putUserPd(pd)); }
/**  添加角色管理 (重复数据可以添加) */
@RequestMapping(value = "/addRole_manageAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加角色管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "role_name"  , value = "角色名称"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addRole_manageAll() {
PageData pd =  getPageData();
return _service.addRole_manageAll(this.putUserPd(pd));}
/** 删除角色管理 */
@RequestMapping(value = "/deleteRole_manage", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除角色管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteRole_manage() {
PageData pd =  getPageData();
return _service.deleteRole_manage(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateRole_manage", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新角色管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键ID"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "role_name"  , value = "角色名称"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateRole_manage() {
PageData pd =  getPageData();
return _service.updateRole_manage(pd);}
/** 获取角色管理数据(非分页,搜索功能) */
@RequestMapping(value = "/queryRole_manageKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取角色管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键ID"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "role_name"  , value = "角色名称"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryRole_manageKey() {
PageData pd = this.getPageData();
return _service.queryRole_manageKey(pd);}
/** 获取角色管理列表数据 */
@RequestMapping(value = "/queryPageRole_manageKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取角色管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageRole_manageKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPd(pd1);
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageRole_manageKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
