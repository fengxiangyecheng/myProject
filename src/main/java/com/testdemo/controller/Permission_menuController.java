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
import  com.testdemo.service.Permission_menuService;
@Api(value = "permission_menu", description = "权限管理", tags = "权限管理")
@Controller
@RequestMapping("/permission_menu")
public class Permission_menuController extends BaseController {
@Autowired
private Permission_menuService _service;
/** 获取权限管理页面 */
@RequestMapping(value = "/Permission_menu", method = RequestMethod.POST)
@ApiOperation(value = "获取权限管理页面")
public String goPermission_menuPage() {  return ""; }
/**  添加权限管理 (重复数据不能添加) */
@RequestMapping(value = "/addPermission_menuNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加权限管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "一级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "child_menu_id"  , value = "二级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_add"  , value = "新增权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_edit"  , value = "修改权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_delete"  , value = "删除权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_query"  , value = "查询权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sys_user_id"  , value = "所属账号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addPermission_menuNo() {
PageData pd =  getPageData();
return _service.addPermission_menuNo(this.putUserPd(pd)); }
/**  添加权限管理 (重复数据可以添加) */
@RequestMapping(value = "/addPermission_menuAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加权限管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "一级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "child_menu_id"  , value = "二级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_add"  , value = "新增权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_edit"  , value = "修改权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_delete"  , value = "删除权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_query"  , value = "查询权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sys_user_id"  , value = "所属账号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addPermission_menuAll() {
PageData pd =  getPageData();
return _service.addPermission_menuAll(this.putUserPd(pd));}
/** 删除权限管理 */
@RequestMapping(value = "/deletePermission_menu", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除权限管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deletePermission_menu() {
PageData pd =  getPageData();
return _service.deletePermission_menu(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updatePermission_menu", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新权限管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "一级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "child_menu_id"  , value = "二级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_add"  , value = "新增权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_edit"  , value = "修改权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_delete"  , value = "删除权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_query"  , value = "查询权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sys_user_id"  , value = "所属账号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updatePermission_menu() {
PageData pd =  getPageData();
return _service.updatePermission_menu(pd);}
/** 获取权限管理数据(非分页,搜索功能) */
@RequestMapping(value = "/queryPermission_menuKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取权限管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "一级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "child_menu_id"  , value = "二级菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_add"  , value = "新增权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_edit"  , value = "修改权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_delete"  , value = "删除权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "name_query"  , value = "查询权限"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "sys_user_id"  , value = "所属账号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "role_manage_id"  , value = "角色"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "create_time"  , value = "创建时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryPermission_menuKey() {
PageData pd = this.getPageData();
return _service.queryPermission_menuKey(pd);}
/** 获取权限管理列表数据 */
@RequestMapping(value = "/queryPagePermission_menuKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取权限管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPagePermission_menuKeyList() {
PageData pd = getPageData();
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPagePermission_menuKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
/** 获取权限管理数据() */
@RequestMapping(value = "/queryPermission_menuKey1", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取权限管理数据()")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query" 
 , name = "id" 
 , value = "主键id" 
 , required = false 
 , dataType = "int"),
@ApiImplicitParam(paramType = "query" 
 , name = "menu_id" 
 , value = "一级菜单" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "child_menu_id" 
 , value = "二级菜单" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "name_add" 
 , value = "新增权限" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "name_edit" 
 , value = "修改权限" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "name_delete" 
 , value = "删除权限" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "name_query" 
 , value = "查询权限" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "sys_user_id" 
 , value = "所属账号" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "role_manage_id" 
 , value = "角色" 
 , required = false 
 , dataType = "varchar"),
@ApiImplicitParam(paramType = "query" 
 , name = "create_time" 
 , value = "创建时间" 
 , required = false 
 , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryPermission_menuKey1() {
PageData pd = this.getPageData();
return _service.queryPermission_menuKey1(pd);}
}
