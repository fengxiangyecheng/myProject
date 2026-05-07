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
import com.testdemo.service.App_schedule_commentService;
import com.testdemo.util.Tools;
@Api(value = "app_schedule_comment", description = "团队日程留言交互管理", tags = "团队日程留言交互管理")
@Controller
@RequestMapping("/app_schedule_comment")
public class App_schedule_commentController extends BaseController {
@Autowired
private App_schedule_commentService _service;
/** 获取团队日程留言交互管理页面 */
@RequestMapping(value = "/App_schedule_comment", method = RequestMethod.POST)
@ApiOperation(value = "获取团队日程留言交互管理页面")
public String goApp_schedule_commentPage() {  return ""; }
/**  添加团队日程留言交互管理 (重复数据不能添加) */
@RequestMapping(value = "/addApp_schedule_commentNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加团队日程留言交互管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "schedule_id"  , value = "日程ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "留言人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "留言内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "parent_id"  , value = "父留言ID（回复留言时使用）"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_schedule_commentNo() {
try {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.addApp_schedule_commentNo(this.putUserPdDataScope(pd));
} catch (Throwable t) {
return ServerResponse.createByErrorMessage("请求处理失败，请稍后重试");
}
}
/**  添加团队日程留言交互管理 (重复数据可以添加) */
@RequestMapping(value = "/addApp_schedule_commentAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加团队日程留言交互管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "schedule_id"  , value = "日程ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "留言人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "留言内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "parent_id"  , value = "父留言ID（回复留言时使用）"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_schedule_commentAll() {
try {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.addApp_schedule_commentAll(this.putUserPdDataScope(pd));
} catch (Throwable t) {
return ServerResponse.createByErrorMessage("请求处理失败，请稍后重试");
}
}
/** 删除团队日程留言交互管理 */
@RequestMapping(value = "/deleteApp_schedule_comment", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除团队日程留言交互管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteApp_schedule_comment() {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.deleteApp_schedule_comment(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateApp_schedule_comment", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新团队日程留言交互管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "schedule_id"  , value = "日程ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "留言人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "留言内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "parent_id"  , value = "父留言ID（回复留言时使用）"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateApp_schedule_comment() {
try {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.updateApp_schedule_comment(this.putUserPdDataScope(pd));
} catch (Throwable t) {
return ServerResponse.createByErrorMessage("请求处理失败，请稍后重试");
}
}
/** 获取团队日程留言交互管理数据(非分页,搜索功能) — 勿对此接口使用 putUserPd，否则未传 user_id 时会被注入为「仅本人」条件 */
@RequestMapping(value = "/queryApp_schedule_commentKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取团队日程留言交互管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "schedule_id"  , value = "日程ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "留言人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "留言内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "parent_id"  , value = "父留言ID（回复留言时使用）"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "create_time"  , value = "留言时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryApp_schedule_commentKey() {
PageData pd = this.getPageData();
String explicitUserFilter = pd.getString("user_id");
PageData userPd = getUserPd();
if (!Tools.isObjEmpty(userPd)) {
	Object uid = userPd.get("id");
	if (uid == null) {
		uid = userPd.get("ID");
	}
	if (uid != null) {
		String sid = String.valueOf(uid).trim();
		if (!sid.isEmpty() && !"null".equalsIgnoreCase(sid)) {
			pd.put("login_user_id", sid);
		}
	}
	if (userPd.get("role_manage_id") != null) {
		pd.put("role_manage_id", userPd.get("role_manage_id"));
	}
}
if (Tools.isEmpty(explicitUserFilter)) {
	pd.remove("user_id");
}
return _service.queryApp_schedule_commentKey(pd);}
/** 获取团队日程留言交互管理列表数据 */
@RequestMapping(value = "/queryPageApp_schedule_commentKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取团队日程留言交互管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageApp_schedule_commentKeyList() {
PageData pd1 = getPageData();
// 前端未选「留言人」筛选时，putUserPd 会注入当前登录人 user_id，导致 SQL 误加 su.user_id=?，
// 代发留言存的是被代发人 id，与登录人不一致，列表会空；系统管理员也无法看到全部记录。
// 必须在 putUserPd 之前取参：putUserPd 会把「未传的 user_id」填成当前登录人
String explicitUserFilter = pd1.getString("user_id");
PageData pd = this.putUserPdDataScope(pd1);
if (Tools.isEmpty(explicitUserFilter)) {
	pd.remove("user_id");
}
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageApp_schedule_commentKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
