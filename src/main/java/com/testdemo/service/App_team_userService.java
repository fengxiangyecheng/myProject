package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_team_userMapper;
import com.testdemo.mapper.App_teamMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class App_team_userService extends BaseService {
@Autowired
private App_team_userMapper _mapper;
@Autowired
private App_teamMapper teamMapper;

private boolean isSysAdmin(PageData userPd){
return userPd!=null && "1".equals(String.valueOf(userPd.get("role_manage_id")));
}

private boolean canManageTeam(PageData userPd,String teamId){
if(Tools.isEmpty(teamId) || userPd==null){return false;}
if(isSysAdmin(userPd)){return true;}
PageData q=new PageData();
q.put("id",teamId);
List<PageData> teams=teamMapper.queryApp_teamKey(q);
if(teams==null||teams.isEmpty()){return false;}
PageData t=teams.get(0);
String uid=String.valueOf(userPd.get("id"));
return uid.equals(String.valueOf(t.get("admin_id"))) || uid.equals(String.valueOf(t.get("creator_id")));
}

private String currentLoginUserId(PageData userPd){
if(userPd==null){return "";}
Object id=userPd.get("id");
if(id==null){id=userPd.get("ID");}
if(id==null){return "";}
String uid=String.valueOf(id).trim();
return "null".equalsIgnoreCase(uid)?"":uid;
}

private boolean canUpdateOwnInviteStatus(PageData pd){
if(pd==null || Tools.isEmpty(pd.getString("id"))){return false;}
PageData userPd=(PageData)pd.get("session_user");
String uid=currentLoginUserId(userPd);
if(Tools.isEmpty(uid)){return false;}
PageData q=new PageData();
q.put("id",pd.getString("id"));
List<PageData> rels=_mapper.queryApp_team_userKey(q);
if(rels==null || rels.isEmpty()){return false;}
String inviteeId=String.valueOf(rels.get(0).get("user_id"));
return uid.equals(inviteeId);
}

private ServerResponse<String> checkManagePermission(PageData pd){
PageData userPd=(PageData)pd.get("session_user");
if(userPd==null){return ServerResponse.createByErrorMessage("请先登录");}
String teamId=pd.getString("team_id");
if(Tools.isEmpty(teamId) && !Tools.isEmpty(pd.getString("id"))){
PageData q=new PageData();
q.put("id",pd.getString("id"));
List<PageData> rels=_mapper.queryApp_team_userKey(q);
if(rels!=null&&!rels.isEmpty()){
teamId=rels.get(0).getString("team_id");
}
}
if(!canManageTeam(userPd,teamId)){
return ServerResponse.createByErrorMessage("无权限管理该团队成员");
}
return null;
}
/**  添加团队用户关联 重复数据不能添加*/
@Transactional
public ServerResponse<String> addApp_team_userNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除团队用户关联参数错误"); }
ServerResponse<String> deny=checkManagePermission(pd);
if(deny!=null){return deny;}
if(Tools.isEmpty(pd.getString("join_type"))){pd.put("join_type","2");}
if(Tools.isEmpty(pd.getString("status"))){pd.put("status","1");}
if(Tools.isEmpty(pd.getString("team_role"))){pd.put("team_role","1");}
PageData dupQ=new PageData();
dupQ.put("team_id",pd.getString("team_id"));
dupQ.put("user_id",pd.getString("user_id"));
List<PageData> list=_mapper.queryApp_team_userKey(dupQ);
if(list.size() > 0) return ServerResponse.createByErrorMessage("该用户已关联此团队");
int rowCount = _mapper.addApp_team_user(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加团队用户关联成功"); }
return ServerResponse.createByErrorMessage("添加团队用户关联失败");}
/**  添加团队用户关联 重复数据可以添加*/
@Transactional
public ServerResponse<String> addApp_team_userAll(PageData pd) {
ServerResponse<String> deny=checkManagePermission(pd);
if(deny!=null){return deny;}
if(Tools.isEmpty(pd.getString("join_type"))){pd.put("join_type","2");}
if(Tools.isEmpty(pd.getString("status"))){pd.put("status","1");}
if(Tools.isEmpty(pd.getString("team_role"))){pd.put("team_role","1");}
int rowCount = _mapper.addApp_team_user(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加团队用户关联成功");  }
return ServerResponse.createByErrorMessage("添加团队用户关联失败");}
/** 根据id删除团队用户关联数据 */
public ServerResponse<String> deleteApp_team_user(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除团队用户关联参数错误");  }
ServerResponse<String> deny=checkManagePermission(pd);
if(deny!=null){return deny;}
int rowCount = _mapper.deleteApp_team_userId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除团队用户关联成功"); }
return ServerResponse.createByErrorMessage("删除团队用户关联失败");}
/** 根据id更新团队用户关联数据 */
@Transactional
public ServerResponse<String> updateApp_team_user(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改团队用户关联参数错误");}
ServerResponse<String> deny=checkManagePermission(pd);
if(deny!=null){
if(!canUpdateOwnInviteStatus(pd)){return deny;}
if(Tools.isEmpty(pd.getString("status"))){
return ServerResponse.createByErrorMessage("请先选择状态");
}
PageData up=new PageData();
up.put("id",pd.getString("id"));
up.put("status",pd.getString("status"));
int rowSelf=_mapper.updateApp_team_user(up);
if(rowSelf>0){return ServerResponse.createBySuccessMessage("状态已更新");}
return ServerResponse.createByErrorMessage("状态更新失败");
}
int rowCount = _mapper.updateApp_team_user(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改团队用户关联成功");}
return ServerResponse.createByErrorMessage("修改团队用户关联失败"); }
/** 获取团队用户关联数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryApp_team_userKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryApp_team_userKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取团队用户关联列表数据 分页*/
 public List<PageData> queryPageApp_team_userKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageApp_team_userKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageApp_team_userKeyList(pd);
// }
List<PageData> list=_mapper.queryPageApp_team_userKeyList(pd);
return list;}
}
