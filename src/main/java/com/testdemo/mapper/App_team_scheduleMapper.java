package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface App_team_scheduleMapper {
/** 添加数据 */
int addApp_team_schedule(PageData pd);
int queryLastInsertId();
int deleteScheduleMembersByScheduleId(PageData pd);
int addScheduleMember(PageData pd);
List<PageData> queryScheduleMembersByScheduleId(PageData pd);
PageData queryScheduleTaskProgressStats(PageData pd);
List<PageData> queryScheduleTaskProgressDetails(PageData pd);
/** 统计面板：当前筛选条件下全部日程的任务汇总（不分页） */
PageData queryTeamScheduleDashboardAggregate(PageData pd);
/** 统计面板：各日程完成率明细（最多 limit 条，优先展示完成率低的） */
List<PageData> queryTeamScheduleDashboardRates(PageData pd);
/** 根据id删除数据 */
int deleteApp_team_scheduleId(PageData pd);
/** 根据id更新数据 */
int updateApp_team_schedule(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryApp_team_scheduleKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageApp_team_scheduleKeyList(Page page);}
