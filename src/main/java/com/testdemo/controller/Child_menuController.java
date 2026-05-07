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
import  com.testdemo.service.Child_menuService;
@Api(value = "child_menu", description = "二级菜单", tags = "二级菜单")
@Controller
@RequestMapping("/child_menu")
public class Child_menuController extends BaseController {
@Autowired
private Child_menuService _service;
/** 获取二级菜单页面 */
@RequestMapping(value = "/Child_menu", method = RequestMethod.POST)
@ApiOperation(value = "获取二级菜单页面")
public String goChild_menuPage() {  return ""; }
/**  添加二级菜单 (重复数据不能添加) */
@RequestMapping(value = "/addChild_menuNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加二级菜单(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_href"  , value = "路由地址"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "主菜单"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addChild_menuNo() {
PageData pd =  getPageData();
return _service.addChild_menuNo(this.putUserPd(pd)); }
/**  添加二级菜单 (重复数据可以添加) */
@RequestMapping(value = "/addChild_menuAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加二级菜单(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_href"  , value = "路由地址"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "主菜单"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addChild_menuAll() {
PageData pd =  getPageData();
return _service.addChild_menuAll(this.putUserPd(pd));}
/** 删除二级菜单 */
@RequestMapping(value = "/deleteChild_menu", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除二级菜单", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteChild_menu() {
PageData pd =  getPageData();
return _service.deleteChild_menu(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateChild_menu", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新二级菜单", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_href"  , value = "路由地址"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "主菜单"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateChild_menu() {
PageData pd =  getPageData();
return _service.updateChild_menu(pd);}
/** 获取二级菜单数据(非分页,搜索功能) */
@RequestMapping(value = "/queryChild_menuKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取二级菜单数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "name"  , value = "菜单名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_icon"  , value = "菜单图标"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_href"  , value = "路由地址"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "index_page"  , value = "序号"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "menu_id"  , value = "主菜单"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "create_time"  , value = "创建时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryChild_menuKey() {
PageData pd = this.getPageData();
return _service.queryChild_menuKey(pd);}
/** 获取二级菜单列表数据 */
@RequestMapping(value = "/queryPageChild_menuKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取二级菜单列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageChild_menuKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPd(pd1);
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageChild_menuKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
