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
import  com.testdemo.service.App_teamService;
@Api(value = "app_team", description = "团队管理", tags = "团队管理")
@Controller
@RequestMapping("/app_team")
public class App_teamController extends BaseController {
@Autowired
private App_teamService _service;
/** 获取团队管理页面 */
@RequestMapping(value = "/App_team", method = RequestMethod.POST)
@ApiOperation(value = "获取团队管理页面")
public String goApp_teamPage() {  return ""; }
/**  添加团队管理 (重复数据不能添加) */
@RequestMapping(value = "/addApp_teamNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加团队管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "team_name"  , value = "团队名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "creator_id"  , value = "创建人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "admin_id"  , value = "团队管理员ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "description"  , value = "团队描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1正常 0解散"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_teamNo() {
PageData pd =  getPageData();
pd.put("session_user",getUserPd());
return _service.addApp_teamNo(this.putUserPd(pd)); }
/**  添加团队管理 (重复数据可以添加) */
@RequestMapping(value = "/addApp_teamAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加团队管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "team_name"  , value = "团队名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "creator_id"  , value = "创建人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "admin_id"  , value = "团队管理员ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "description"  , value = "团队描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1正常 0解散"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_teamAll() {
PageData pd =  getPageData();
pd.put("session_user",getUserPd());
return _service.addApp_teamAll(this.putUserPd(pd));}
/** 删除团队管理 */
@RequestMapping(value = "/deleteApp_team", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除团队管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteApp_team() {
PageData pd =  getPageData();
pd.put("session_user",getUserPd());
return _service.deleteApp_team(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateApp_team", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新团队管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "team_name"  , value = "团队名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "creator_id"  , value = "创建人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "admin_id"  , value = "团队管理员ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "description"  , value = "团队描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1正常 0解散"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateApp_team() {
PageData pd =  getPageData();
pd.put("session_user",getUserPd());
return _service.updateApp_team(pd);}

@RequestMapping(value = "/approveTeamAudit", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "系统管理员审核团队")
public ServerResponse<String> approveTeamAudit() {
return _service.approveTeamAudit(getPageData(), getUserPd());}
/** 获取团队管理数据(非分页,搜索功能) */
@RequestMapping(value = "/queryApp_teamKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取团队管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "team_name"  , value = "团队名称"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "creator_id"  , value = "创建人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "admin_id"  , value = "团队管理员ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "description"  , value = "团队描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1正常 0解散"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "create_time"  , value = "创建时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryApp_teamKey() {
PageData pd = this.getPageData();
return _service.queryApp_teamKey(pd);}
/** 获取团队管理列表数据 */
@RequestMapping(value = "/queryPageApp_teamKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取团队管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageApp_teamKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPdDataScope(pd1);
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageApp_teamKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
