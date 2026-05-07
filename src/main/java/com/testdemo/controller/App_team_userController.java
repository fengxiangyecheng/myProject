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
import com.testdemo.service.App_team_userService;
import com.testdemo.util.Tools;
@Api(value = "app_team_user", description = "团队用户关联", tags = "团队用户关联")
@Controller
@RequestMapping("/app_team_user")
public class App_team_userController extends BaseController {
@Autowired
private App_team_userService _service;
/** 获取团队用户关联页面 */
@RequestMapping(value = "/App_team_user", method = RequestMethod.POST)
@ApiOperation(value = "获取团队用户关联页面")
public String goApp_team_userPage() {  return ""; }
/**  添加团队用户关联 (重复数据不能添加) */
@RequestMapping(value = "/addApp_team_userNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加团队用户关联(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "team_id"  , value = "团队ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "用户ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_type"  , value = "加入类型：1申请加入 2邀请加入"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：0待审核 1已加入 2已拒绝"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_time"  , value = "加入时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_team_userNo() {
PageData pd = getPageData();
String formUserId = pd.getString("user_id");
pd.put("session_user", getUserPd());
putUserPd(pd);
if (!Tools.isEmpty(formUserId)) {
pd.put("user_id", formUserId.trim());
}
return _service.addApp_team_userNo(pd);
}
/**  添加团队用户关联 (重复数据可以添加) */
@RequestMapping(value = "/addApp_team_userAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加团队用户关联(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "team_id"  , value = "团队ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "用户ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_type"  , value = "加入类型：1申请加入 2邀请加入"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：0待审核 1已加入 2已拒绝"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_time"  , value = "加入时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_team_userAll() {
PageData pd = getPageData();
String formUserId = pd.getString("user_id");
pd.put("session_user", getUserPd());
putUserPd(pd);
if (!Tools.isEmpty(formUserId)) {
pd.put("user_id", formUserId.trim());
}
return _service.addApp_team_userAll(pd);
}
/** 删除团队用户关联 */
@RequestMapping(value = "/deleteApp_team_user", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除团队用户关联", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteApp_team_user() {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.deleteApp_team_user(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateApp_team_user", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新团队用户关联", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "team_id"  , value = "团队ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "用户ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_type"  , value = "加入类型：1申请加入 2邀请加入"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：0待审核 1已加入 2已拒绝"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_time"  , value = "加入时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateApp_team_user() {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.updateApp_team_user(pd);}
/** 获取团队用户关联数据(非分页,搜索功能) */
@RequestMapping(value = "/queryApp_team_userKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取团队用户关联数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "team_id"  , value = "团队ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "用户ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_type"  , value = "加入类型：1申请加入 2邀请加入"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：0待审核 1已加入 2已拒绝"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "join_time"  , value = "加入时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryApp_team_userKey() {
PageData pd = this.getPageData();
PageData login = getUserPd();
if (login != null) {
	Object rid = login.get("role_manage_id");
	if (rid != null) {
		pd.put("role_manage_id", String.valueOf(rid));
	}
	Object uid = login.get("id");
	if (uid == null) {
		uid = login.get("ID");
	}
	if (uid != null) {
		pd.put("login_user_id", String.valueOf(uid));
	}
}
return _service.queryApp_team_userKey(pd);}
/** 获取团队用户关联列表数据 */
@RequestMapping(value = "/queryPageApp_team_userKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取团队用户关联列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageApp_team_userKeyList() {
PageData pd1 = getPageData();
PageData pd = pd1;
PageData login = getUserPd();
if (login != null) {
    Object rid = login.get("role_manage_id");
    if (rid != null) {
        pd.put("role_manage_id", String.valueOf(rid));
    }
    Object uid = login.get("id");
    if (uid == null) {
        uid = login.get("ID");
    }
    if (uid != null) {
        pd.put("login_user_id", String.valueOf(uid));
    }
}
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageApp_team_userKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
