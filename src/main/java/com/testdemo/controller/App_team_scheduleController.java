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

import com.testdemo.service.App_team_scheduleService;

@Api(value = "app_team_schedule", description = "团队协作日程管理", tags = "团队协作日程管理")
@Controller
@RequestMapping("/app_team_schedule")
public class App_team_scheduleController extends BaseController {
    @Autowired
    private App_team_scheduleService _service;

    /**
     * 获取团队协作日程管理页面
     */
    @RequestMapping(value = "/App_team_schedule", method = RequestMethod.POST)
    @ApiOperation(value = "获取团队协作日程管理页面")
    public String goApp_team_schedulePage() {
        return "";
    }

    /**
     * 添加团队协作日程管理 (重复数据不能添加)
     */
    @RequestMapping(value = "/addApp_team_scheduleNo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加团队协作日程管理(重复数据不能添加)", notes = "添加不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "team_id", value = "团队ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "日程标题", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "日程内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "start_time", value = "开始时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "end_time", value = "结束时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "creator_id", value = "创建人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：1未开始 2进行中 3已完成 4已取消", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "update_time", value = "更新时间", required = false, dataType = "varchar"),
    })
    public ServerResponse<String> addApp_team_scheduleNo() {
        PageData pd = getPageData();
        return _service.addApp_team_scheduleNo(this.putUserPdDataScope(pd));
    }

    /**
     * 添加团队协作日程管理 (重复数据可以添加)
     */
    @RequestMapping(value = "/addApp_team_scheduleAll", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加团队协作日程管理(重复数据可以添加)", notes = "添加不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "team_id", value = "团队ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "日程标题", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "日程内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "start_time", value = "开始时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "end_time", value = "结束时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "creator_id", value = "创建人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：1未开始 2进行中 3已完成 4已取消", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "update_time", value = "更新时间", required = false, dataType = "varchar"),
    })
    public ServerResponse<String> addApp_team_scheduleAll() {
        PageData pd = getPageData();
        return _service.addApp_team_scheduleAll(this.putUserPdDataScope(pd));
    }

    /**
     * 删除团队协作日程管理
     */
    @RequestMapping(value = "/deleteApp_team_schedule", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除团队协作日程管理", notes = "删除不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
    })
    public ServerResponse<String> deleteApp_team_schedule() {
        PageData pd = getPageData();
        return _service.deleteApp_team_schedule(this.putUserPdDataScope(pd));
    }

    /**
     * 根据id更新数据
     */
    @RequestMapping(value = "/updateApp_team_schedule", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新团队协作日程管理", notes = "更新不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "team_id", value = "团队ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "日程标题", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "日程内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "start_time", value = "开始时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "end_time", value = "结束时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "creator_id", value = "创建人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：1未开始 2进行中 3已完成 4已取消", required = false, dataType = "varchar"),
    })
    public ServerResponse<String> updateApp_team_schedule() {
        PageData pd = getPageData();
        return _service.updateApp_team_schedule(this.putUserPdDataScope(pd));
    }

    /**
     * 获取团队协作日程管理数据(非分页,搜索功能)
     */
    @RequestMapping(value = "/queryApp_team_scheduleKey", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队协作日程管理数据(非分页,搜索功能)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "team_id", value = "团队ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "title", value = "日程标题", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "content", value = "日程内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "start_time", value = "开始时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "end_time", value = "结束时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "creator_id", value = "创建人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：1未开始 2进行中 3已完成 4已取消", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "create_time", value = "创建时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "update_time", value = "更新时间", required = false, dataType = "varchar"),
    })
    public ServerResponse<List<PageData>> queryApp_team_scheduleKey() {
        PageData pd = this.putUserPdDataScope(this.getPageData());
        return _service.queryApp_team_scheduleKey(pd);
    }

    @RequestMapping(value = "/queryScheduleProgressStats", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询团队日程完成统计")
    public ServerResponse<PageData> queryScheduleProgressStats() {
        PageData pd = this.putUserPdDataScope(this.getPageData());
        return _service.queryScheduleProgressStats(pd);
    }

    @RequestMapping(value = "/queryScheduleProgressDetails", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询团队日程成员完成明细")
    public ServerResponse<List<PageData>> queryScheduleProgressDetails() {
        PageData pd = this.putUserPdDataScope(this.getPageData());
        return _service.queryScheduleProgressDetails(pd);
    }

    @RequestMapping(value = "/urgeScheduleMember", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "催办成员任务")
    public ServerResponse<String> urgeScheduleMember() {
        PageData pd = this.getPageData();
        return _service.urgeScheduleMember(this.putUserPdDataScope(pd));
    }

    @RequestMapping(value = "/queryTeamScheduleDashboard", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "团队日程统计面板（当前筛选条件下全部日程，不分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "team_id", value = "团队ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "creator_id", value = "创建人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "visibility_type", value = "可见范围", required = false, dataType = "varchar"),
    })
    public ServerResponse<JSONObject> queryTeamScheduleDashboard() {
        PageData pd = this.getPageData();
        return _service.queryTeamScheduleDashboard(this.putUserPdDataScope(pd));
    }

    @RequestMapping(value = "/shareToExternalUsers", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "团队日程共享到外部指定用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "schedule_id", value = "团队日程ID", required = true, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "external_user_ids", value = "外部用户ID(逗号分隔)", required = true, dataType = "varchar"),
    })
    public ServerResponse<String> shareToExternalUsers() {
        PageData pd = this.getPageData();
        return _service.shareToExternalUsers(this.putUserPdDataScope(pd));
    }

    /**
     * 获取团队协作日程管理列表数据
     */
    @RequestMapping(value = "/queryPageApp_team_scheduleKeyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取团队协作日程管理列表数据", notes = "分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
    })
    public JSONObject queryPageApp_team_scheduleKeyList() {
        PageData pd1 = getPageData();
        PageData pd = this.putUserPdDataScope(pd1);
        Page page = getPage();
        page.setPd(pd);
        List<PageData> systemUserList = null;
        try {
            systemUserList = _service.queryPageApp_team_scheduleKeyList(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewReturnPageData(page, systemUserList);
    }
}
