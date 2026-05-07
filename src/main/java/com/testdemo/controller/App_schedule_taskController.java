package com.testdemo.controller;

import com.testdemo.base.BaseController;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_schedule_favoriteMapper;
import com.testdemo.mapper.App_schedule_taskMapper;
import com.testdemo.util.Tools;
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

import com.testdemo.service.App_schedule_taskService;

@Api(value = "app_schedule_task", description = "日程任务分配管理", tags = "日程任务分配管理")
@Controller
@RequestMapping("/app_schedule_task")
public class App_schedule_taskController extends BaseController {
    @Autowired
    private App_schedule_taskService _service;
    @Autowired
    private App_schedule_taskMapper _mapper; //new coding style
    @Autowired
    private App_schedule_favoriteMapper favoriteMapper;


    /**
     * 获取日程任务分配管理页面
     */
    @RequestMapping(value = "/App_schedule_task", method = RequestMethod.POST)
    @ApiOperation(value = "获取日程任务分配管理页面")
    public String goApp_schedule_taskPage() {
        return "";
    }

    /**
     * 添加日程任务分配管理 (重复数据不能添加)
     */
    @RequestMapping(value = "/addApp_schedule_taskNo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加日程任务分配管理(重复数据不能添加)", notes = "添加不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "schedule_id", value = "日程ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_name", value = "任务名称", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_content", value = "任务内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assigner_id", value = "分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assignees_id", value = "被分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "deadline", value = "截止时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：0待确认 1已确认 2已完成 3已逾期", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "update_time", value = "更新时间", required = false, dataType = "varchar"),
    })
    public ServerResponse<String> addApp_schedule_taskNo() {
        PageData pd = getPageData();
        pd.put("session_user", getUserPd());
        return _service.addApp_schedule_taskNo(this.putUserPdDataScope(pd));
    }

    /**
     * 添加日程任务分配管理 (重复数据可以添加)
     */
    @RequestMapping(value = "/addApp_schedule_taskAll", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加日程任务分配管理(重复数据可以添加)", notes = "添加不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "schedule_id", value = "日程ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_name", value = "任务名称", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_content", value = "任务内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assigner_id", value = "分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assignees_id", value = "被分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "deadline", value = "截止时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：0待确认 1已确认 2已完成 3已逾期", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "update_time", value = "更新时间", required = false, dataType = "varchar"),
    })
    public ServerResponse<String> addApp_schedule_taskAll() {
        PageData pd = getPageData();
        pd.put("session_user", getUserPd());
        return _service.addApp_schedule_taskAll(this.putUserPdDataScope(pd));
    }

    /**
     * 删除日程任务分配管理
     */
    @RequestMapping(value = "/deleteApp_schedule_task", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除日程任务分配管理", notes = "删除不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "xxx", required = true, dataType = "int"),
    })
    public ServerResponse<String> deleteApp_schedule_task() {
        PageData pd = getPageData();
        pd.put("session_user", getUserPd());
        return _service.deleteApp_schedule_task(this.putUserPdDataScope(pd));
    }

    /**
     * 根据id更新数据
     */
    @RequestMapping(value = "/updateApp_schedule_task", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新日程任务分配管理", notes = "更新不为空的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "schedule_id", value = "日程ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_name", value = "任务名称", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_content", value = "任务内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assigner_id", value = "分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assignees_id", value = "被分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "deadline", value = "截止时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：0待确认 1已确认 2已完成 3已逾期", required = false, dataType = "varchar"),
    })
    public ServerResponse<String> updateApp_schedule_task() {
        PageData pd = getPageData();
        pd.put("session_user", getUserPd());
        return _service.updateApp_schedule_task(this.putUserPdDataScope(pd));
    }

    /**
     * 获取日程任务分配管理数据(非分页,搜索功能)
     */
    @RequestMapping(value = "/queryApp_schedule_taskKey", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取日程任务分配管理数据(非分页,搜索功能)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键id", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "schedule_id", value = "日程ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_name", value = "任务名称", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "task_content", value = "任务内容", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assigner_id", value = "分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "assignees_id", value = "被分配人ID", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "deadline", value = "截止时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态：0待确认 1已确认 2已完成 3已逾期", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "create_time", value = "创建时间", required = false, dataType = "varchar"),
            @ApiImplicitParam(paramType = "query", name = "update_time", value = "更新时间", required = false, dataType = "varchar"),
    })
    public ServerResponse<List<PageData>> queryApp_schedule_taskKey() {
        PageData pd = this.putUserPdDataScope(this.getPageData());
        pd.put("session_user", getUserPd());
        return _service.queryApp_schedule_taskKey(pd);
    }

    /**JustinLI update 2026-04-04 09:05:09
     * 获取日程任务分配管理列表数据
     */
    @RequestMapping(value = "/queryPageApp_schedule_taskKeyList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取日程任务分配管理列表数据", notes = "分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "showCount", value = "每页记录数", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "currentPage", value = "当前页", required = true, dataType = "String"),
    })
    public JSONObject queryPageApp_schedule_taskKeyList() {
        PageData pd1 = getPageData();
        pd1.put("session_user", getUserPd());
        PageData pd = this.putUserPdDataScope(pd1);
        Page page = getPage();
        page.setPd(pd);
        List<PageData> systemUserList = null;
        try {
            systemUserList = _service.queryPageApp_schedule_taskKeyList(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewReturnPageData(page, systemUserList);
    }

    /** 获取首页仪表盘数据 */
    @RequestMapping(value = "/queryDashboardData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取首页仪表盘数据", notes = "返回今日/本周/本月日程统计及今日日程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "user_id", value = "用户ID", required = true, dataType = "String"),
    })
    public ServerResponse<JSONObject> queryDashboardData() {
        PageData pd = putUserPd(getPageData());
        // 安全：统计只允许查询当前登录用户，忽略外部传入 user_id，防止越权读取
        Object uid = pd.get("user_id");
        if (uid != null) {
            pd.put("user_id", String.valueOf(uid));
        }
        return _service.queryDashboardData(pd);
    }

    @RequestMapping(value = "/toggleScheduleFavorite", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "切换日程任务收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "task_id", value = "任务主键id", required = true, dataType = "String"),
    })
    public ServerResponse<JSONObject> toggleScheduleFavorite() {
        PageData pd = putUserPd(getPageData());
        String taskId = pageDataParamToString(pd, "task_id");
        String userId = pageDataParamToString(pd, "user_id");
        if (Tools.isEmpty(taskId) || Tools.isEmpty(userId)) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int c = favoriteMapper.countFavorite(userId, taskId);
        JSONObject data = new JSONObject();
        if (c > 0) {
            favoriteMapper.deleteFavorite(userId, taskId);
            data.put("favorited", false);
            return ServerResponse.createBySuccess("已取消收藏", data);
        }
        PageData ins = new PageData();
        ins.put("user_id", userId);
        ins.put("task_id", taskId);
        favoriteMapper.insertFavorite(ins);
        data.put("favorited", true);
        return ServerResponse.createBySuccess("已收藏", data);
    }

    @RequestMapping(value = "/shareTaskToTeam", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "个人日程共享到团队")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "task_id", value = "任务ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "team_id", value = "团队ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "visibility_type", value = "可见范围:1全体 2指定成员", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "member_user_ids", value = "指定成员ID(逗号分隔)", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "leader_user_id", value = "负责人ID", required = false, dataType = "String"),
    })
    public ServerResponse<String> shareTaskToTeam() {
        PageData pd = putUserPd(getPageData());
        pd.put("session_user", getUserPd());
        return _service.shareTaskToTeam(this.putUserPdDataScope(pd));
    }

    /** PageData 中 id 类字段常为 Integer，避免 getString 强转失败 */
    private static String pageDataParamToString(PageData pd, String key) {
        if (pd == null || key == null) {
            return null;
        }
        Object v = pd.get(key);
        if (v == null) {
            return null;
        }
        String s = String.valueOf(v).trim();
        return s.isEmpty() || "null".equalsIgnoreCase(s) ? null : s;
    }

}
