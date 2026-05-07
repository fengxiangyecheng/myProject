package com.testdemo.mapper;

import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface App_schedule_taskMapper {
    /**
     * 添加数据
     */
    int addApp_schedule_task(PageData pd);

    /**
     * 根据id删除数据
     */
    int deleteApp_schedule_taskId(PageData pd);

    /**
     * 根据id更新数据
     */
    int updateApp_schedule_task(PageData pd);

    /**
     * 获取用户列表数据 不分页
     */
    List<PageData> queryApp_schedule_taskKey(PageData pd);

    /**
     * 获取用户列表数据 分页
     */
    List<PageData> queryPageApp_schedule_taskKeyList(Page page);

    /** 分配人必须是后台账号且角色为系统管理员/团队管理员（1/2）并处于启用状态 */
    int countValidTaskAssigner(PageData pd);

    /** 被分配人必须是启用中的个人用户 */
    int countValidTaskAssignee(PageData pd);

// justinLi update
    /** 根据日期类型统计日程数量 */
    int countScheduleByDateType(PageData pd);

    /** 统计团队成员数量 */
    int countTeamMembers(String userId);

    /** 查询今日详细日程列表 */
    List<PageData> queryTodaySchedules(String userId);

    /** 查询最新公告 */
    List<PageData> queryLatestNotices(int limit);

    /** 查询当前用户待处理的团队邀请（状态=0） */
    List<PageData> queryPendingTeamInvites(@Param("uid") String uid);

    PageData queryPersonalScheduleStats(@Param("uid") String uid);

    List<PageData> queryScheduleTypeBreakdown(@Param("uid") String uid);

    /** 已完成任务中「按时完成」数量（用于提醒/履约达标率近似指标） */
    PageData queryReminderOnTimeStats(@Param("uid") String uid);

}
