package com.testdemo.service;

//import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_schedule_taskMapper;
import com.testdemo.service.App_team_scheduleService;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

@Service
public class App_schedule_taskService extends BaseService {
    @Autowired
    private App_schedule_taskMapper _mapper;
    @Autowired
    private App_team_scheduleService teamScheduleService;

    /** 前端、搜索条件使用 assignees_id，表字段为 assignee_id，统一映射 */
    private static int intVal(PageData row, String key) {
        if (row == null || row.get(key) == null) {
            return 0;
        }
        Object v = row.get(key);
        if (v instanceof Number) {
            return ((Number) v).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(v));
        } catch (Exception e) {
            return 0;
        }
    }

    private void syncAssigneeId(PageData pd) {
        if (pd == null) {
            return;
        }
        // 前端编辑场景会同时带 assignee_id(旧值) 与 assignees_id(当前选择值)，
        // 这里统一以 assignees_id 为准，避免校验命中旧值导致“被分配人无效”。
        if (!Tools.isEmpty(pd.getString("assignees_id"))) {
            pd.put("assignee_id", pd.get("assignees_id"));
            return;
        }
    }

    private static PageData sessionUser(PageData pd) {
        if (pd == null) {
            return null;
        }
        Object s = pd.get("session_user");
        return (s instanceof PageData) ? (PageData) s : null;
    }

    private static String sessionUserId(PageData pd) {
        PageData s = sessionUser(pd);
        if (s == null) {
            return null;
        }
        Object id = s.get("id");
        if (id == null) {
            id = s.get("ID");
        }
        if (id == null) {
            return null;
        }
        String sid = String.valueOf(id).trim();
        return sid.isEmpty() || "null".equalsIgnoreCase(sid) ? null : sid;
    }

    /** 仅前台 app_user 登录为个人会话；后台 sys_user 即使误选「个人」角色也不走个人任务 id 体系，避免与 app_user 主键混用 */
    private static boolean isPersonalSession(PageData pd) {
        PageData s = sessionUser(pd);
        if (s == null) {
            return false;
        }
        return "personal".equals(String.valueOf(s.get("login_user_type")));
    }

    /** 个人用户：任务归属强制到自己，查询也只看自己的任务 */
    private void applyPersonalScope(PageData pd) {
        if (!isPersonalSession(pd)) {
            return;
        }
        String uid = sessionUserId(pd);
        if (Tools.isEmpty(uid)) {
            return;
        }
        pd.put("participant_user_id", uid);
        pd.put("assignee_id", uid);
        pd.put("assignees_id", uid);
        if (Tools.isEmpty(pd.getString("assigner_id"))) {
            pd.put("assigner_id", "");
        }
        // 个人任务可能不挂团队日程，避免团队 SQL 范围把本人任务误过滤
        pd.put("skip_team_schedule_scope", "1");
    }

    /** 后台团队任务：当前登录人是否可看到该任务（与列表 SQL 团队范围一致） */
    private boolean taskVisibleInScope(PageData pd, String taskId) {
        if (Tools.isEmpty(taskId) || pd == null) {
            return false;
        }
        PageData k = new PageData();
        k.put("id", taskId.trim());
        String lid = pd.getString("login_user_id");
        if (!Tools.isEmpty(lid)) {
            k.put("login_user_id", lid);
        }
        Object r = pd.get("role_manage_id");
        if (r != null) {
            k.put("role_manage_id", String.valueOf(r));
        }
        List<PageData> rows = _mapper.queryApp_schedule_taskKey(k);
        return rows != null && !rows.isEmpty();
    }

    private boolean canPersonalAccessTask(String taskId, String uid) {
        if (Tools.isEmpty(taskId) || Tools.isEmpty(uid)) {
            return false;
        }
        PageData q = new PageData();
        q.put("id", taskId);
        q.put("skip_team_schedule_scope", "1");
        List<PageData> lst = _mapper.queryApp_schedule_taskKey(q);
        if (lst == null || lst.isEmpty()) {
            return false;
        }
        PageData one = lst.get(0);
        String a1 = one.get("assignee_id") == null ? "" : String.valueOf(one.get("assignee_id")).trim();
        String a2 = one.get("assigner_id") == null ? "" : String.valueOf(one.get("assigner_id")).trim();
        return uid.equals(a1) || uid.equals(a2);
    }

    /** 任务分配强约束：分配人=后台账号(角色1/2且启用)，被分配人=启用中的个人用户 */
    private ServerResponse<String> validateTaskParticipants(PageData pd) {
        if (pd == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        applyPersonalScope(pd);
        syncAssigneeId(pd);
        if (isPersonalSession(pd)) {
            if (Tools.isEmpty(pd.getString("assignee_id"))) {
                return ServerResponse.createByErrorMessage("个人用户缺少任务归属");
            }
            if (_mapper.countValidTaskAssignee(pd) <= 0) {
                return ServerResponse.createByErrorMessage("当前个人账号不可用，请联系管理员");
            }
            return null;
        }
        if (Tools.isEmpty(pd.getString("assigner_id"))) {
            return ServerResponse.createByErrorMessage("分配人不能为空");
        }
        if (Tools.isEmpty(pd.getString("assignee_id"))) {
            return ServerResponse.createByErrorMessage("被分配人不能为空");
        }
        if (_mapper.countValidTaskAssigner(pd) <= 0) {
            return ServerResponse.createByErrorMessage("分配人仅支持启用中的系统管理员/团队管理员");
        }
        if (_mapper.countValidTaskAssignee(pd) <= 0) {
            return ServerResponse.createByErrorMessage("被分配人仅支持启用中的个人用户");
        }
        return null;
    }

    /**
     * 添加日程任务分配管理 重复数据不能添加
     */
    @Transactional
    public ServerResponse<String> addApp_schedule_taskNo(PageData pd) {
        if (Tools.isObjEmpty(pd)) {
            return ServerResponse.createByErrorMessage("删除日程任务分配管理参数错误");
        }
        ServerResponse<String> invalid = validateTaskParticipants(pd);
        if (invalid != null) {
            return invalid;
        }
        List<PageData> list = _mapper.queryApp_schedule_taskKey(pd);
        if (list.size() > 0) return ServerResponse.createByErrorMessage("日程任务分配管理已存在");
        int rowCount = _mapper.addApp_schedule_task(pd);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加日程任务分配管理成功");
        }
        return ServerResponse.createByErrorMessage("添加日程任务分配管理失败");
    }

    /**
     * 添加日程任务分配管理 重复数据可以添加
     */
    @Transactional
    public ServerResponse<String> addApp_schedule_taskAll(PageData pd) {
        ServerResponse<String> invalid = validateTaskParticipants(pd);
        if (invalid != null) {
            return invalid;
        }
        int rowCount = _mapper.addApp_schedule_task(pd);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("添加日程任务分配管理成功");
        }
        return ServerResponse.createByErrorMessage("添加日程任务分配管理失败");
    }

    /**
     * 根据id删除日程任务分配管理数据
     */
    public ServerResponse<String> deleteApp_schedule_task(PageData pd) {
        if (Tools.isObjEmpty(pd.get("id"))) {
            return ServerResponse.createByErrorMessage("删除日程任务分配管理参数错误");
        }
        if (isPersonalSession(pd)) {
            String uid = sessionUserId(pd);
            String tid = String.valueOf(pd.get("id"));
            if (!canPersonalAccessTask(tid, uid)) {
                return ServerResponse.createByErrorMessage("无权限删除他人任务");
            }
        } else if (!taskVisibleInScope(pd, String.valueOf(pd.get("id")))) {
            return ServerResponse.createByErrorMessage("删除失败或无权操作该任务");
        }
        int rowCount = _mapper.deleteApp_schedule_taskId(pd);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("删除日程任务分配管理成功");
        }
        return ServerResponse.createByErrorMessage("删除日程任务分配管理失败");
    }

    /**
     * 根据id更新日程任务分配管理数据
     */
    @Transactional
    public ServerResponse<String> updateApp_schedule_task(PageData pd) {
        if (Tools.isEmpty(pd.getString("id"))) {
            return ServerResponse.createByErrorMessage("修改日程任务分配管理参数错误");
        }
        if (isPersonalSession(pd)) {
            String uid = sessionUserId(pd);
            if (!canPersonalAccessTask(pd.getString("id"), uid)) {
                return ServerResponse.createByErrorMessage("无权限修改他人任务");
            }
        } else if (!taskVisibleInScope(pd, pd.getString("id"))) {
            return ServerResponse.createByErrorMessage("修改失败或无权操作该任务");
        }
        ServerResponse<String> invalid = validateTaskParticipants(pd);
        if (invalid != null) {
            return invalid;
        }
        int rowCount = _mapper.updateApp_schedule_task(pd);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改日程任务分配管理成功");
        }
        return ServerResponse.createByErrorMessage("修改日程任务分配管理失败");
    }

    /**
     * 获取日程任务分配管理数据(非分页,搜索功能)
     */
    public ServerResponse<List<PageData>> queryApp_schedule_taskKey(PageData pd) {
        if (Tools.isObjEmpty(pd)) {
            return ServerResponse.badArgument();
        }
        applyPersonalScope(pd);
        syncAssigneeId(pd);
        List<PageData> list = _mapper.queryApp_schedule_taskKey(pd);
        return ServerResponse.createBySuccess(list);
    }

    /**
     * 获取日程任务分配管理列表数据 分页
     */
    public List<PageData> queryPageApp_schedule_taskKeyList(Page pd) {
        if (pd != null && pd.getPd() != null) {
            applyPersonalScope(pd.getPd());
            syncAssigneeId(pd.getPd());
        }
        List<PageData> list = _mapper.queryPageApp_schedule_taskKeyList(pd);
        return list;
    }


//JustinLi update
    /** 获取首页仪表盘数据 */
    public ServerResponse<JSONObject> queryDashboardData(PageData pd) {
        if (Tools.isObjEmpty(pd.get("user_id"))) {
            return ServerResponse.createByErrorMessage("用户ID不能为空");
        }

        JSONObject result = new JSONObject();

        try {
            String userId = pd.getString("user_id");

            PageData todayPd = new PageData();
            todayPd.put("assignees_id", userId);
            todayPd.put("date_type", "today");
            int todayCount = _mapper.countScheduleByDateType(todayPd);
            result.put("todayCount", todayCount);

            PageData weekPd = new PageData();
            weekPd.put("assignees_id", userId);
            weekPd.put("date_type", "week");
            int weekCount = _mapper.countScheduleByDateType(weekPd);
            result.put("weekCount", weekCount);

            PageData monthPd = new PageData();
            monthPd.put("assignees_id", userId);
            monthPd.put("date_type", "month");
            int monthCount = _mapper.countScheduleByDateType(monthPd);
            result.put("monthCount", monthCount);

            int teamCount = _mapper.countTeamMembers(userId);
            result.put("teamCount", teamCount);

            List<PageData> todaySchedules = _mapper.queryTodaySchedules(userId);
            result.put("todaySchedules", todaySchedules);

            PageData todoPd = new PageData();
            todoPd.put("participant_user_id", userId);
            List<PageData> todoList = _mapper.queryApp_schedule_taskKey(todoPd);
            int todoCount = 0;
            for (PageData task : todoList) {
                String status = task.getString("status");
                if ("0".equals(status) || "1".equals(status)) {
                    todoCount++;
                }
            }
            result.put("todoCount", todoCount);

            List<PageData> notices = _mapper.queryLatestNotices(3);
            List<PageData> invites = _mapper.queryPendingTeamInvites(userId);
            List<PageData> mergedNotices = new ArrayList<>();
            if (invites != null && !invites.isEmpty()) {
                mergedNotices.addAll(invites);
            }
            if (notices != null && !notices.isEmpty()) {
                mergedNotices.addAll(notices);
            }
            result.put("pendingInviteCount", invites == null ? 0 : invites.size());
            result.put("notices", mergedNotices);

            try {
                PageData pst = _mapper.queryPersonalScheduleStats(userId);
                int total = intVal(pst, "total_cnt");
                int done = intVal(pst, "done_cnt");
                double completionRate = total > 0 ? Math.round(done * 1000.0 / total) / 10.0 : 0;
                result.put("completionRate", completionRate);
                result.put("scheduleTotal", total);
                result.put("scheduleDone", done);
                result.put("scheduleActive", intVal(pst, "active_cnt"));
                result.put("scheduleOverdue", intVal(pst, "overdue_cnt"));
                JSONArray types = new JSONArray();
                for (PageData row : _mapper.queryScheduleTypeBreakdown(userId)) {
                    JSONObject o = new JSONObject();
                    o.put("type_code", row.get("type_code"));
                    o.put("cnt", intVal(row, "cnt"));
                    types.add(o);
                }
                result.put("typeBreakdown", types);
                PageData rts = _mapper.queryReminderOnTimeStats(userId);
                int doneForRate = intVal(rts, "done_cnt");
                int onTime = intVal(rts, "on_time_cnt");
                double reminderOnTimeRate = doneForRate > 0 ? Math.round(onTime * 1000.0 / doneForRate) / 10.0 : 0;
                result.put("reminderOnTimeRate", reminderOnTimeRate);
                result.put("reminderDoneTotal", doneForRate);
                result.put("reminderOnTimeCount", onTime);
            } catch (Exception ignore) {
                result.put("completionRate", 0);
                result.put("scheduleTotal", 0);
                result.put("scheduleDone", 0);
                result.put("scheduleActive", 0);
                result.put("scheduleOverdue", 0);
                result.put("typeBreakdown", new JSONArray());
                result.put("reminderOnTimeRate", 0);
                result.put("reminderDoneTotal", 0);
                result.put("reminderOnTimeCount", 0);
            }

            return ServerResponse.createBySuccess(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("获取仪表盘数据失败：" + e.getMessage());
        }
    }

    /** 个人日程共享到团队：复用团队日程创建逻辑，确保成员与任务同步一致 */
    @Transactional
    public ServerResponse<String> shareTaskToTeam(PageData pd) {
        if (pd == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        String taskId = pd.getString("task_id");
        String teamId = pd.getString("team_id");
        if (Tools.isEmpty(taskId) || Tools.isEmpty(teamId)) {
            return ServerResponse.createByErrorMessage("任务ID和团队ID不能为空");
        }
        PageData q = new PageData();
        q.put("id", taskId);
        if (isPersonalSession(pd)) {
            q.put("skip_team_schedule_scope", "1");
        } else {
            String lid = pd.getString("login_user_id");
            if (!Tools.isEmpty(lid)) {
                q.put("login_user_id", lid);
            }
            Object rm = pd.get("role_manage_id");
            if (rm != null) {
                q.put("role_manage_id", String.valueOf(rm));
            }
        }
        List<PageData> rows = _mapper.queryApp_schedule_taskKey(q);
        if (rows == null || rows.isEmpty()) {
            return ServerResponse.createByErrorMessage("任务不存在");
        }
        PageData task = rows.get(0);
        if (isPersonalSession(pd)) {
            String uid = sessionUserId(pd);
            if (!canPersonalAccessTask(taskId, uid)) {
                return ServerResponse.createByErrorMessage("无权限共享他人任务");
            }
        }
        PageData add = new PageData();
        add.put("team_id", teamId);
        add.put("title", task.getString("task_name"));
        add.put("content", task.getString("task_content"));
        add.put("start_time", pd.getString("start_time"));
        add.put("end_time", task.getString("deadline"));
        add.put("visibility_type", Tools.isEmpty(pd.getString("visibility_type")) ? "1" : pd.getString("visibility_type"));
        add.put("member_user_ids", pd.getString("member_user_ids"));
        add.put("leader_user_id", pd.getString("leader_user_id"));
        add.put("status", "1");
        add.put("login_user_id", pd.getString("login_user_id"));
        add.put("user_id", pd.getString("user_id"));
        String creatorId = pd.getString("login_user_id");
        if (Tools.isEmpty(creatorId)) {
            creatorId = pd.getString("user_id");
        }
        if (Tools.isEmpty(creatorId)) {
            creatorId = task.getString("assigner_id");
        }
        add.put("creator_id", creatorId);
        ServerResponse<String> shared = teamScheduleService.addApp_team_scheduleAll(add);
        if (!shared.isSuccess()) {
            return shared;
        }
        return ServerResponse.createBySuccessMessage("已共享到团队日程");
    }
}
