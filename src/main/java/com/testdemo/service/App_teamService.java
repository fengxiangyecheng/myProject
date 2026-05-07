package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_teamMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class App_teamService extends BaseService {
@Autowired
private App_teamMapper _mapper;

private boolean isSysAdmin(PageData userPd){
return userPd!=null && "1".equals(String.valueOf(userPd.get("role_manage_id")));
}

private boolean isTeamAdminRole(PageData userPd){
if(userPd==null){return false;}
String role=String.valueOf(userPd.get("role_manage_id"));
return "1".equals(role) || "2".equals(role);
}

private String currentLoginUserId(PageData userPd){
if(userPd==null){return "";}
Object id=userPd.get("id");
if(id==null){id=userPd.get("ID");}
if(id==null){return "";}
String uid=String.valueOf(id).trim();
return "null".equalsIgnoreCase(uid)?"":uid;
}

private boolean canManageTeam(PageData userPd,String teamId){
if(Tools.isEmpty(teamId) || userPd==null){return false;}
if(isSysAdmin(userPd)){return true;}
PageData q=new PageData();
q.put("id",teamId);
List<PageData> teams=_mapper.queryApp_teamKey(q);
if(teams==null||teams.isEmpty()){return false;}
PageData t=teams.get(0);
String uid=currentLoginUserId(userPd);
if(Tools.isEmpty(uid)){return false;}
return uid.equals(String.valueOf(t.get("admin_id"))) || uid.equals(String.valueOf(t.get("creator_id")));
}

private ServerResponse<String> ensureCanCreateTeam(PageData pd){
PageData userPd=(PageData)pd.get("session_user");
if(userPd==null){return ServerResponse.createByErrorMessage("请先登录");}
if(!isTeamAdminRole(userPd)){return ServerResponse.createByErrorMessage("仅系统管理员或团队管理员可创建团队");}
String uid=currentLoginUserId(userPd);
if(Tools.isEmpty(uid)){return ServerResponse.createByErrorMessage("当前登录人无效");}
if(Tools.isEmpty(pd.getString("creator_id"))){pd.put("creator_id",uid);}
if(Tools.isEmpty(pd.getString("admin_id"))){pd.put("admin_id",uid);}
if(!isSysAdmin(userPd)){
// 非系统管理员创建团队时，强制自己为创建者与管理员，避免越权指定他人。
pd.put("creator_id",uid);
pd.put("admin_id",uid);
}
if(Tools.isEmpty(pd.getString("status"))){pd.put("status","1");}
return null;
}

private ServerResponse<String> ensureCanManageTeamById(PageData pd){
PageData userPd=(PageData)pd.get("session_user");
if(userPd==null){return ServerResponse.createByErrorMessage("请先登录");}
String teamId=pd.getString("id");
if(Tools.isEmpty(teamId)){return ServerResponse.createByErrorMessage("团队ID不能为空");}
if(!canManageTeam(userPd,teamId)){return ServerResponse.createByErrorMessage("无权限操作该团队");}
return null;
}
/**  添加团队管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addApp_teamNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除团队管理参数错误"); }
ServerResponse<String> deny=ensureCanCreateTeam(pd);
if(deny!=null){return deny;}
pd.put("audit_status","0");
List<PageData> list =_mapper.queryApp_teamKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("团队管理已存在");
int rowCount = _mapper.addApp_team(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加团队管理成功"); }
return ServerResponse.createByErrorMessage("添加团队管理失败");}
/**  添加团队管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addApp_teamAll(PageData pd) {
ServerResponse<String> deny=ensureCanCreateTeam(pd);
if(deny!=null){return deny;}
pd.put("audit_status","0");
int rowCount = _mapper.addApp_team(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加团队管理成功");  }
return ServerResponse.createByErrorMessage("添加团队管理失败");}
/** 根据id删除团队管理数据 */
public ServerResponse<String> deleteApp_team(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除团队管理参数错误");  }
ServerResponse<String> deny=ensureCanManageTeamById(pd);
if(deny!=null){return deny;}
PageData q=new PageData();
q.put("id",pd.getString("id"));
List<PageData> exist=_mapper.queryApp_teamKey(q);
if(exist==null||exist.isEmpty()){return ServerResponse.createByErrorMessage("团队不存在");}
String status=String.valueOf(exist.get(0).get("status"));
if("0".equals(status)){
return ServerResponse.createByErrorMessage("团队已解散，无需重复操作");
}
// 团队“解散”采用软删除：status=0，保留历史数据。
PageData up=new PageData();
up.put("id",pd.getString("id"));
up.put("status","0");
int rowCount = _mapper.updateApp_team(up);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("团队已解散"); }
return ServerResponse.createByErrorMessage("团队解散失败");}
/** 根据id更新团队管理数据 */
@Transactional
public ServerResponse<String> updateApp_team(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改团队管理参数错误");}
ServerResponse<String> deny=ensureCanManageTeamById(pd);
if(deny!=null){return deny;}
PageData userPd=(PageData)pd.get("session_user");
if(!isSysAdmin(userPd)){
// 非系统管理员不允许重置审核状态，避免绕过审核。
pd.remove("audit_status");
}
int rowCount = _mapper.updateApp_team(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改团队管理成功");}
return ServerResponse.createByErrorMessage("修改团队管理失败"); }

/** 系统管理员：团队创建审核 */
@Transactional
public ServerResponse<String> approveTeamAudit(PageData pd,PageData adminPd){
if(adminPd==null||!"1".equals(String.valueOf(adminPd.get("role_manage_id")))){
return ServerResponse.createByErrorMessage("无权限");}
if(Tools.isEmpty(pd.getString("id"))){
return ServerResponse.createByErrorMessage("参数错误");}
String action=pd.getString("audit_action");
if("reject".equalsIgnoreCase(action)){
pd.put("audit_status","2");
}else{
pd.put("audit_status","1");}
int rowCount=_mapper.updateApp_team(pd);
return rowCount>0?ServerResponse.createBySuccessMessage("审核已更新"):ServerResponse.createByErrorMessage("审核失败");}
/** 获取团队管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryApp_teamKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryApp_teamKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取团队管理列表数据 分页*/
 public List<PageData> queryPageApp_teamKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageApp_teamKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageApp_teamKeyList(pd);
// }
if(pd!=null&&pd.getPd()!=null){
PageData q=pd.getPd();
String role=q.get("role_manage_id")==null?"":String.valueOf(q.get("role_manage_id"));
String uid=q.get("user_id")==null?"":String.valueOf(q.get("user_id"));
if(!Tools.isEmpty(uid)&&!"1".equals(role)){
q.put("scope_user_id",uid);
}}
List<PageData> list=_mapper.queryPageApp_teamKeyList(pd);
return list;}
}
