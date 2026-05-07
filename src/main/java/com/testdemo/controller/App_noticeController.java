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
import  com.testdemo.service.App_noticeService;
@Api(value = "app_notice", description = "系统公告管理", tags = "系统公告管理")
@Controller
@RequestMapping("/app_notice")
public class App_noticeController extends BaseController {
@Autowired
private App_noticeService _service;
/** 获取系统公告管理页面 */
@RequestMapping(value = "/App_notice", method = RequestMethod.POST)
@ApiOperation(value = "获取系统公告管理页面")
public String goApp_noticePage() {  return ""; }
/**  添加系统公告管理 (重复数据不能添加) */
@RequestMapping(value = "/addApp_noticeNo", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加系统公告管理(重复数据不能添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "title"  , value = "公告标题"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "公告内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publisher_id"  , value = "发布人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1已发布 0未发布"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publish_time"  , value = "发布时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_noticeNo() {
PageData pd =  getPageData();
return _service.addApp_noticeNo(this.putUserPd(pd)); }
/**  添加系统公告管理 (重复数据可以添加) */
@RequestMapping(value = "/addApp_noticeAll", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "添加系统公告管理(重复数据可以添加)", notes = "添加不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "title"  , value = "公告标题"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "公告内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publisher_id"  , value = "发布人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1已发布 0未发布"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publish_time"  , value = "发布时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> addApp_noticeAll() {
PageData pd =  getPageData();
return _service.addApp_noticeAll(this.putUserPd(pd));}
/** 删除系统公告管理 */
@RequestMapping(value = "/deleteApp_notice", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "删除系统公告管理", notes = "删除不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
})
public ServerResponse<String> deleteApp_notice() {
PageData pd =  getPageData();
return _service.deleteApp_notice(pd);}
/** 根据id更新数据 */
@RequestMapping(value = "/updateApp_notice", method = RequestMethod.POST)
@ResponseBody
@ApiOperation(value = "更新系统公告管理", notes = "更新不为空的内容")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = true  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "title"  , value = "公告标题"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "公告内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publisher_id"  , value = "发布人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1已发布 0未发布"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publish_time"  , value = "发布时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<String> updateApp_notice() {
PageData pd =  getPageData();
return _service.updateApp_notice(pd);}
/** 获取系统公告管理数据(非分页,搜索功能) */
@RequestMapping(value = "/queryApp_noticeKey", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取系统公告管理数据(非分页,搜索功能)")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query"  , name = "id"  , value = "主键id"  , required = false  , dataType = "int"),
@ApiImplicitParam(paramType = "query"  , name = "title"  , value = "公告标题"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "content"  , value = "公告内容"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publisher_id"  , value = "发布人ID"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "status"  , value = "状态：1已发布 0未发布"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "publish_time"  , value = "发布时间"  , required = false  , dataType = "varchar"),
@ApiImplicitParam(paramType = "query"  , name = "update_time"  , value = "更新时间"  , required = false  , dataType = "varchar"),
})
public ServerResponse<List<PageData>> queryApp_noticeKey() {
PageData pd = this.getPageData();
return _service.queryApp_noticeKey(pd);}
/** 获取系统公告管理列表数据 */
@RequestMapping(value = "/queryPageApp_noticeKeyList", method = RequestMethod.GET)
@ResponseBody
@ApiOperation(value = "获取系统公告管理列表数据", notes = "分页")
@ApiImplicitParams({
@ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
@ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
})
public JSONObject queryPageApp_noticeKeyList() {
PageData pd1 = getPageData();
PageData pd =this.putUserPd(pd1);
Page page = getPage();
page.setPd(pd);
List<PageData> systemUserList = null;
try {
systemUserList = _service.queryPageApp_noticeKeyList(page);
} catch (Exception e) {
e.printStackTrace();}
return viewReturnPageData(page, systemUserList);
}
}
