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
import  com.testdemo.service.MenuService;
@Api(value = "menu", description = "系统菜单", tags = "系统菜单")
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
@Autowired
private MenuService _service;
/** 获取系统菜单页面 */
@RequestMapping(value = "/Menu", method = RequestMethod.POST)
@ApiOperation(value = "获取系统菜单页面")
public String goMenuPage() {  return ""; }
/**  添加系统菜单 (重复数据不能添加) */
@RequestMapping(value = "/addMenuNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加系统菜单(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addMenuNo() {
PageData pd =  getPageData();
return _service.addMenuNo(this.putUserPd(pd)); }
/**  添加系统菜单 (重复数据可以添加) */
@RequestMapping(value = "/addMenuAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加系统菜单(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addMenuAll() {
PageData pd =  getPageData();
return _service.addMenuAll(this.putUserPd(pd));}
/** 删除系统菜单 */
@RequestMapping(value = "/deleteMenu", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除系统菜单", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteMenu() {
PageData pd =  getPageData();
return _service.deleteMenu(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新系统菜单", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateMenu() {
PageData pd =  getPageData();
return _service.updateMenu(pd);}
/** 获取系统菜单数据(非分页,搜索功能) */
@RequestMapping(value = "/queryMenuKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取系统菜单数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "create_time"  , value = "创建时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryMenuKey() {
PageData pd = this.getPageData();
return _service.queryMenuKey(pd);}
/** 获取系统菜单列表数据 */
@RequestMapping(value = "/queryPageMenuKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取系统菜单列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageMenuKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPd(pd1);
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageMenuKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
