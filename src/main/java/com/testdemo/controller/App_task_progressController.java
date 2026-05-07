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
import  com.testdemo.service.App_task_progressService;
@Api(value = "app_task_progress", description = "日程任务进度记录管理", tags = "日程任务进度记录管理")
@Controller
@RequestMapping("/app_task_progress")
public class App_task_progressController extends BaseController {
@Autowired
private App_task_progressService _service;
/** 获取日程任务进度记录管理页面 */
@RequestMapping(value = "/App_task_progress", method = RequestMethod.POST)
@ApiOperation(value = "获取日程任务进度记录管理页面")
public String goApp_task_progressPage() {  return ""; }
/**  添加日程任务进度记录管理 (重复数据不能添加) */
@RequestMapping(value = "/addApp_task_progressNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加日程任务进度记录管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "task_id"  , value = "任务ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "提交人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "progress"  , value = "进度（百分比，如50）"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "进度描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "submit_time"  , value = "提交时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_task_progressNo() {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.addApp_task_progressNo(this.putUserPd(pd)); }
/**  添加日程任务进度记录管理 (重复数据可以添加) */
@RequestMapping(value = "/addApp_task_progressAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加日程任务进度记录管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "task_id"  , value = "任务ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "提交人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "progress"  , value = "进度（百分比，如50）"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "进度描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "submit_time"  , value = "提交时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_task_progressAll() {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.addApp_task_progressAll(this.putUserPd(pd));}
/** 删除日程任务进度记录管理 */
@RequestMapping(value = "/deleteApp_task_progress", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除日程任务进度记录管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteApp_task_progress() {
PageData pd =  getPageData();
return _service.deleteApp_task_progress(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateApp_task_progress", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新日程任务进度记录管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "task_id"  , value = "任务ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "提交人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "progress"  , value = "进度（百分比，如50）"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "进度描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "submit_time"  , value = "提交时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateApp_task_progress() {
PageData pd =  getPageData();
pd.put("session_user", getUserPd());
return _service.updateApp_task_progress(pd);}
/** 获取日程任务进度记录管理数据(非分页,搜索功能) */
@RequestMapping(value = "/queryApp_task_progressKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取日程任务进度记录管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "task_id"  , value = "任务ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "user_id"  , value = "提交人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "progress"  , value = "进度（百分比，如50）"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "进度描述"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "submit_time"  , value = "提交时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryApp_task_progressKey() {
PageData pd = this.putUserPdDataScope(this.getPageData());
return _service.queryApp_task_progressKey(pd);}
/** 获取日程任务进度记录管理列表数据 */
@RequestMapping(value = "/queryPageApp_task_progressKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取日程任务进度记录管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageApp_task_progressKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPdDataScope(pd1);
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageApp_task_progressKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
