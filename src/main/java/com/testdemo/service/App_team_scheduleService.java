package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_teamMapper;
import com.testdemo.mapper.App_schedule_taskMapper;
import com.testdemo.mapper.App_team_scheduleMapper;
import com.testdemo.mapper.App_team_userMapper;
import com.testdemo.mapper.App_noticeMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;
@Service
public class App_team_scheduleService extends BaseService {
@Autowired
private App_team_scheduleMapper _mapper;
@Autowired
private App_teamMapper teamMapper;
@Autowired
private App_team_userMapper teamUserMapper;
@Autowired
private App_schedule_taskMapper scheduleTaskMapper;
@Autowired
private App_noticeMapper noticeMapper;

/** 将登录上下文传入日程查询，使 queryApp_team_scheduleKey 的团队数据范围生效 */
private void copyScheduleQueryScope(PageData from, PageData to) {
	if (from == null || to == null) {
		return;
	}
	String lid = from.getString("login_user_id");
	if (!Tools.isEmpty(lid)) {
		to.put("login_user_id", lid);
	}
	Object r = from.get("role_manage_id");
	if (r != null) {
		to.put("role_manage_id", String.valueOf(r));
	}
}

/** 当前登录人是否可访问该团队日程（与 queryApp_team_scheduleKey 数据范围一致） */
private boolean scheduleVisibleInLoginScope(PageData pd, String scheduleId) {
	if (Tools.isEmpty(scheduleId) || pd == null) {
		return false;
	}
	PageData q = new PageData();
	q.put("id", scheduleId.trim());
	copyScheduleQueryScope(pd, q);
	List<PageData> schs = _mapper.queryApp_team_scheduleKey(q);
	return schs != null && !schs.isEmpty();
}

private ServerResponse<String> ensureTeamApproved(PageData pd){
if(pd==null){return ServerResponse.createByErrorMessage("参数错误");}
String teamId=pd.getString("team_id");
if(Tools.isEmpty(teamId)){return ServerResponse.createByErrorMessage("团队不能为空");}
PageData q=new PageData();
q.put("id",teamId);
q.put("audit_status","1");
q.put("status","1");
List<PageData> teams=teamMapper.queryApp_teamKey(q);
if(teams==null||teams.isEmpty()){
return ServerResponse.createByErrorMessage("团队未审核通过或已停用，无法创建/修改团队日程");
}
return null;
}

private Set<String> parseMemberIds(String raw){
Set<String> ids=new LinkedHashSet<>();
if(Tools.isEmpty(raw)){return ids;}
String[] arr=raw.split(",");
for(String it:arr){
if(it==null){continue;}
String v=it.trim();
if(v.isEmpty()||"null".equalsIgnoreCase(v)){continue;}
ids.add(v);
}
return ids;
}

private ServerResponse<String> ensureMemberInputs(PageData pd){
if(pd==null){return ServerResponse.createByErrorMessage("参数错误");}
String teamId=pd.getString("team_id");
if(Tools.isEmpty(teamId)){return ServerResponse.createByErrorMessage("团队不能为空");}
String vis=pd.getString("visibility_type");
if(Tools.isEmpty(vis)){vis="1";pd.put("visibility_type","1");}
String leaderId=pd.getString("leader_user_id");
if(!Tools.isEmpty(leaderId)){
PageData q=new PageData();
q.put("team_id",teamId);
q.put("user_id",leaderId);
q.put("status","1");
List<PageData> one=teamUserMapper.queryApp_team_userKey(q);
if(one==null||one.isEmpty()){
return ServerResponse.createByErrorMessage("负责人必须是该团队已加入成员");
}
}
String raw=pd.getString("member_user_ids");
Set<String> mids=parseMemberIds(raw);
if("1".equals(vis)){
PageData qAll=new PageData();
qAll.put("team_id",teamId);
qAll.put("status","1");
List<PageData> joined=teamUserMapper.queryApp_team_userKey(qAll);
if(joined!=null){
for(PageData row:joined){
Object uid=row.get("user_id");
if(uid!=null){
String sid=String.valueOf(uid).trim();
if(!sid.isEmpty() && !"null".equalsIgnoreCase(sid)){
mids.add(sid);
}}}
}
}
if("2".equals(vis) && mids.isEmpty()){
return ServerResponse.createByErrorMessage("可见范围为指定成员时，参与成员不能为空");
}
for(String uid:mids){
PageData q=new PageData();
q.put("team_id",teamId);
q.put("user_id",uid);
q.put("status","1");
List<PageData> one=teamUserMapper.queryApp_team_userKey(q);
if(one==null||one.isEmpty()){
return ServerResponse.createByErrorMessage("参与成员仅支持该团队已加入成员");
}
}
pd.put("parsed_member_ids",mids);
return null;
}

private void syncScheduleTasks(String scheduleId, PageData pd, Set<String> memberIds){
if(Tools.isEmpty(scheduleId) || pd==null || memberIds==null || memberIds.isEmpty()){return;}
String assignerId=pd.getString("creator_id");
if(Tools.isEmpty(assignerId)){assignerId=pd.getString("login_user_id");}
if(Tools.isEmpty(assignerId)){assignerId=pd.getString("user_id");}
if(Tools.isEmpty(assignerId)){return;}
String title=pd.getString("title");
String content=pd.getString("content");
String deadline=pd.getString("end_time");
PageData qAll=new PageData();
qAll.put("schedule_id",scheduleId);
List<PageData> all=scheduleTaskMapper.queryApp_schedule_taskKey(qAll);
if(all!=null){
for(PageData row:all){
String assignee=row.get("assignee_id")==null?"":String.valueOf(row.get("assignee_id")).trim();
String assigner=row.get("assigner_id")==null?"":String.valueOf(row.get("assigner_id")).trim();
String taskName=row.get("task_name")==null?"":String.valueOf(row.get("task_name"));
if(!memberIds.contains(assignee) && assignerId.equals(assigner) && title.equals(taskName)){
PageData del=new PageData();
del.put("id",String.valueOf(row.get("id")));
scheduleTaskMapper.deleteApp_schedule_taskId(del);
}
}
}
for(String uid:memberIds){
PageData q=new PageData();
q.put("schedule_id",scheduleId);
q.put("assignee_id",uid);
q.put("task_name",title);
List<PageData> existed=scheduleTaskMapper.queryApp_schedule_taskKey(q);
if(existed!=null && !existed.isEmpty()){
continue;
}
PageData add=new PageData();
add.put("schedule_id",scheduleId);
add.put("task_name",title);
add.put("task_content",content);
add.put("assigner_id",assignerId);
add.put("assignee_id",uid);
add.put("deadline",deadline);
add.put("status","0");
add.put("schedule_type_code","");
scheduleTaskMapper.addApp_schedule_task(add);
}
}

private void replaceScheduleMembers(String scheduleId, Set<String> memberIds){
if(Tools.isEmpty(scheduleId)){return;}
PageData del=new PageData();
del.put("schedule_id",scheduleId);
_mapper.deleteScheduleMembersByScheduleId(del);
if(memberIds==null||memberIds.isEmpty()){return;}
for(String uid:memberIds){
PageData one=new PageData();
one.put("schedule_id",scheduleId);
one.put("user_id",uid);
_mapper.addScheduleMember(one);
}
}

private static int intVal(PageData pd,String key){
if(pd==null||key==null){return 0;}
Object v=pd.get(key);
if(v==null){return 0;}
String s=String.valueOf(v).trim();
if(s.isEmpty()||"null".equalsIgnoreCase(s)){return 0;}
try{return Integer.parseInt(s);}catch (Exception e){return 0;}
}

private static String currentLoginUserId(PageData pd){
if(pd==null){return "";}
String uid=pd.getString("login_user_id");
if(Tools.isEmpty(uid)){uid=pd.getString("user_id");}
if(uid==null){return "";}
uid=uid.trim();
return uid.isEmpty()||"null".equalsIgnoreCase(uid)?"":uid;
}

public ServerResponse<PageData> queryScheduleProgressStats(PageData pd){
if(pd==null||Tools.isEmpty(pd.getString("schedule_id"))){
return ServerResponse.createByErrorMessage("日程ID不能为空");
}
if(!scheduleVisibleInLoginScope(pd,pd.getString("schedule_id"))){
return ServerResponse.createByErrorMessage("无权限查看该日程统计或日程不存在");
}
PageData stats=_mapper.queryScheduleTaskProgressStats(pd);
if(stats==null){stats=new PageData();}
int total=intVal(stats,"total_cnt");
int done=intVal(stats,"done_cnt");
double rate=total>0?(Math.round(done*1000.0/total)/10.0):0.0;
stats.put("rate",rate);
return ServerResponse.createBySuccess(stats);
}

public ServerResponse<List<PageData>> queryScheduleProgressDetails(PageData pd){
if(pd==null||Tools.isEmpty(pd.getString("schedule_id"))){
return ServerResponse.createByErrorMessage("日程ID不能为空");
}
if(!scheduleVisibleInLoginScope(pd,pd.getString("schedule_id"))){
return ServerResponse.createByErrorMessage("无权限查看该日程明细或日程不存在");
}
List<PageData> rows=_mapper.queryScheduleTaskProgressDetails(pd);
if(rows!=null){
for(PageData r:rows){
int total=intVal(r,"total_cnt");
int done=intVal(r,"done_cnt");
double rate=total>0?(Math.round(done*1000.0/total)/10.0):0.0;
r.put("rate",rate);
}
}
return ServerResponse.createBySuccess(rows);
}

/** 团队日程统计面板：与列表相同的筛选条件，统计全部匹配日程（不分页） */
public ServerResponse<JSONObject> queryTeamScheduleDashboard(PageData pd){
if(pd==null){return ServerResponse.badArgument();}
String rankMode=pd.getString("rank_mode");
if(Tools.isEmpty(rankMode)){rankMode="risk_top15";}
if("all".equalsIgnoreCase(rankMode)){
pd.remove("limit_size");
}else{
// LIMIT 参数必须为数值，避免数据库把字符串当作非法 LIMIT 导致 Top15 查询失败
pd.put("limit_size",15);
}
PageData agg=_mapper.queryTeamScheduleDashboardAggregate(pd);
if(agg==null){agg=new PageData();}
int scheduleCnt=intVal(agg,"schedule_count");
int taskTotal=intVal(agg,"task_total");
int done=intVal(agg,"done_cnt");
int active=intVal(agg,"active_cnt");
int overdue=intVal(agg,"overdue_cnt");
double completionRate=taskTotal>0?(Math.round(done*1000.0/taskTotal)/10.0):0.0;
double overdueRate=taskTotal>0?(Math.round(overdue*1000.0/taskTotal)/10.0):0.0;
List<PageData> rows=_mapper.queryTeamScheduleDashboardRates(pd);
JSONArray breakdown=new JSONArray();
if(rows!=null){
for(PageData r:rows){
int t=intVal(r,"progress_total1");
int d=intVal(r,"progress_done1");
double rate=t>0?(Math.round(d*1000.0/t)/10.0):0.0;
JSONObject item=new JSONObject();
item.put("title",r.get("title"));
item.put("rate",rate);
item.put("progress_total1",t);
item.put("progress_done1",d);
breakdown.add(item);
}}
JSONObject o=new JSONObject();
o.put("scheduleCount",scheduleCnt);
o.put("taskTotal",taskTotal);
o.put("doneCnt",done);
o.put("activeCnt",active);
o.put("overdueCnt",overdue);
o.put("completionRate",completionRate);
o.put("overdueRate",overdueRate);
o.put("breakdown",breakdown);
return ServerResponse.createBySuccess(o);
}

/** 团队日程共享给外部指定用户（本质：为外部用户生成同源任务） */
@Transactional
public ServerResponse<String> shareToExternalUsers(PageData pd){
if(pd==null){return ServerResponse.createByErrorMessage("参数错误");}
String scheduleId=pd.getString("schedule_id");
if(Tools.isEmpty(scheduleId)){return ServerResponse.createByErrorMessage("日程ID不能为空");}
Set<String> users=parseMemberIds(pd.getString("external_user_ids"));
if(users==null||users.isEmpty()){return ServerResponse.createByErrorMessage("外部用户不能为空");}
PageData q=new PageData();
q.put("id",scheduleId);
copyScheduleQueryScope(pd,q);
List<PageData> schedules=_mapper.queryApp_team_scheduleKey(q);
if(schedules==null||schedules.isEmpty()){return ServerResponse.createByErrorMessage("团队日程不存在");}
PageData sch=schedules.get(0);
String title=sch.getString("title");
String content=sch.getString("content");
String deadline=sch.getString("end_time");
String assignerId=currentLoginUserId(pd);
if(Tools.isEmpty(assignerId)){assignerId=sch.getString("creator_id");}
if(Tools.isEmpty(assignerId)){assignerId="1";}
int added=0;
int skipped=0;
for(String uid:users){
PageData v=new PageData();
v.put("assignee_id",uid);
if(scheduleTaskMapper.countValidTaskAssignee(v)<=0){
skipped++;
continue;
}
PageData qDup=new PageData();
qDup.put("schedule_id",scheduleId);
qDup.put("assignee_id",uid);
qDup.put("task_name",title);
List<PageData> dup=scheduleTaskMapper.queryApp_schedule_taskKey(qDup);
if(dup!=null&&!dup.isEmpty()){
skipped++;
continue;
}
PageData add=new PageData();
add.put("schedule_id",scheduleId);
add.put("task_name",title);
add.put("task_content",content);
add.put("assigner_id",assignerId);
add.put("assignee_id",uid);
add.put("deadline",deadline);
add.put("status","0");
add.put("schedule_type_code","");
scheduleTaskMapper.addApp_schedule_task(add);
added++;
}
if(added<=0){
return ServerResponse.createByErrorMessage("共享失败：目标用户无可共享对象（可能已存在或账号不可用）");
}
if(skipped>0){
return ServerResponse.createBySuccessMessage("共享完成：成功"+added+"个，跳过"+skipped+"个");
}
return ServerResponse.createBySuccessMessage("共享完成：成功"+added+"个");
}

@Transactional
public ServerResponse<String> urgeScheduleMember(PageData pd){
if(pd==null){return ServerResponse.createByErrorMessage("参数错误");}
String scheduleId=pd.getString("schedule_id");
String assigneeId=pd.getString("assignee_id");
if(Tools.isEmpty(scheduleId)||Tools.isEmpty(assigneeId)){
return ServerResponse.createByErrorMessage("日程ID和成员ID不能为空");
}
PageData q=new PageData();
q.put("schedule_id",scheduleId);
q.put("assignee_id",assigneeId);
List<PageData> tasks=scheduleTaskMapper.queryApp_schedule_taskKey(q);
if(tasks==null||tasks.isEmpty()){
return ServerResponse.createByErrorMessage("未找到该成员对应任务");
}
boolean hasTodo=false;
for(PageData t:tasks){
String st=t.getString("status");
if("0".equals(st)||"1".equals(st)){hasTodo=true;break;}
}
if(!hasTodo){
return ServerResponse.createByErrorMessage("该成员任务已完成，无需催办");
}
PageData qSch=new PageData();
qSch.put("id",scheduleId);
copyScheduleQueryScope(pd,qSch);
List<PageData> schs=_mapper.queryApp_team_scheduleKey(qSch);
String title="团队日程催办";
String content="请尽快推进并更新任务进度。";
if(schs!=null&&!schs.isEmpty()){
PageData sch=schs.get(0);
String t=sch.getString("title");
if(!Tools.isEmpty(t)){title="团队日程催办：" + t;}
content="日程【"+(Tools.isEmpty(t)?scheduleId:t)+"】存在未完成任务，请成员ID="+assigneeId+"尽快更新进度。";
}
PageData n=new PageData();
n.put("title",title);
n.put("content",content);
String publisher=currentLoginUserId(pd);
if(Tools.isEmpty(publisher) && schs!=null&&!schs.isEmpty()){
publisher=String.valueOf(schs.get(0).get("creator_id"));
}
n.put("publisher_id",publisher);
n.put("status","1");
n.put("publish_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
int rows=noticeMapper.addApp_notice(n);
if(rows>0){
return ServerResponse.createBySuccessMessage("催办已发送");
}
return ServerResponse.createByErrorMessage("催办发送失败");
}
/**  添加团队协作日程管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addApp_team_scheduleNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除团队协作日程管理参数错误"); }
if(Tools.isEmpty(pd.getString("creator_id"))){
String uid=pd.getString("login_user_id");
if(Tools.isEmpty(uid)){uid=pd.getString("user_id");}
if(!Tools.isEmpty(uid)){pd.put("creator_id",uid);}
}
ServerResponse<String> teamErr=ensureTeamApproved(pd);
if(teamErr!=null){return teamErr;}
ServerResponse<String> memberErr=ensureMemberInputs(pd);
if(memberErr!=null){return memberErr;}
List<PageData> list =_mapper.queryApp_team_scheduleKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("团队协作日程管理已存在");
int rowCount = _mapper.addApp_team_schedule(pd);
if(rowCount > 0){
int sid=_mapper.queryLastInsertId();
Set<String> mids=(Set<String>)pd.get("parsed_member_ids");
replaceScheduleMembers(String.valueOf(sid),mids);
syncScheduleTasks(String.valueOf(sid),pd,mids);
 return ServerResponse.createBySuccessMessage("添加团队协作日程管理成功"); }
return ServerResponse.createByErrorMessage("添加团队协作日程管理失败");}
/**  添加团队协作日程管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addApp_team_scheduleAll(PageData pd) {
if(Tools.isEmpty(pd.getString("creator_id"))){
String uid=pd.getString("login_user_id");
if(Tools.isEmpty(uid)){uid=pd.getString("user_id");}
if(!Tools.isEmpty(uid)){pd.put("creator_id",uid);}
}
ServerResponse<String> teamErr=ensureTeamApproved(pd);
if(teamErr!=null){return teamErr;}
ServerResponse<String> memberErr=ensureMemberInputs(pd);
if(memberErr!=null){return memberErr;}
int rowCount = _mapper.addApp_team_schedule(pd);
if(rowCount > 0){
int sid=_mapper.queryLastInsertId();
Set<String> mids=(Set<String>)pd.get("parsed_member_ids");
replaceScheduleMembers(String.valueOf(sid),mids);
syncScheduleTasks(String.valueOf(sid),pd,mids);
 return ServerResponse.createBySuccessMessage("添加团队协作日程管理成功");  }
return ServerResponse.createByErrorMessage("添加团队协作日程管理失败");}
/** 根据id删除团队协作日程管理数据 */
public ServerResponse<String> deleteApp_team_schedule(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除团队协作日程管理参数错误");  }
PageData qExist=new PageData();
qExist.put("id",String.valueOf(pd.get("id")));
copyScheduleQueryScope(pd,qExist);
List<PageData> exist=_mapper.queryApp_team_scheduleKey(qExist);
if(exist==null||exist.isEmpty()){
return ServerResponse.createByErrorMessage("删除失败或无权操作该日程");
}
PageData del=new PageData();
del.put("schedule_id",String.valueOf(pd.get("id")));
_mapper.deleteScheduleMembersByScheduleId(del);
int rowCount = _mapper.deleteApp_team_scheduleId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除团队协作日程管理成功"); }
return ServerResponse.createByErrorMessage("删除团队协作日程管理失败");}
/** 根据id更新团队协作日程管理数据 */
@Transactional
public ServerResponse<String> updateApp_team_schedule(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改团队协作日程管理参数错误");}
PageData qOld=new PageData();
qOld.put("id",pd.getString("id"));
copyScheduleQueryScope(pd,qOld);
List<PageData> olds=_mapper.queryApp_team_scheduleKey(qOld);
if(olds==null||olds.isEmpty()){return ServerResponse.createByErrorMessage("日程不存在");}
PageData old=olds.get(0);
if(Tools.isEmpty(pd.getString("team_id"))){pd.put("team_id",old.get("team_id"));}
if(Tools.isEmpty(pd.getString("creator_id"))){
String uid=pd.getString("login_user_id");
if(Tools.isEmpty(uid)){uid=pd.getString("user_id");}
if(!Tools.isEmpty(uid)){pd.put("creator_id",uid);}else{pd.put("creator_id",old.get("creator_id"));}
}
ServerResponse<String> teamErr=ensureTeamApproved(pd);
if(teamErr!=null){return teamErr;}
ServerResponse<String> memberErr=ensureMemberInputs(pd);
if(memberErr!=null){return memberErr;}
int rowCount = _mapper.updateApp_team_schedule(pd);
Set<String> mids=(Set<String>)pd.get("parsed_member_ids");
replaceScheduleMembers(pd.getString("id"),mids);
if(Tools.isEmpty(pd.getString("title"))){pd.put("title",old.get("title"));}
if(Tools.isEmpty(pd.getString("content"))){pd.put("content",old.get("content"));}
if(Tools.isEmpty(pd.getString("end_time"))){pd.put("end_time",old.get("end_time"));}
syncScheduleTasks(pd.getString("id"),pd,mids);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改团队协作日程管理成功");}
return ServerResponse.createBySuccessMessage("参与成员更新成功"); }
/** 获取团队协作日程管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryApp_team_scheduleKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryApp_team_scheduleKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取团队协作日程管理列表数据 分页*/
 public List<PageData> queryPageApp_team_scheduleKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageApp_team_scheduleKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageApp_team_scheduleKeyList(pd);
// }
List<PageData> list=_mapper.queryPageApp_team_scheduleKeyList(pd);
return list;}
}
