package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_task_progressMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class App_task_progressService extends BaseService {
@Autowired
private App_task_progressMapper _mapper;

private static boolean isPersonalSession(PageData pd){
if(pd==null){return false;}
Object s=pd.get("session_user");
if(!(s instanceof PageData)){return false;}
PageData u=(PageData)s;
return "personal".equals(String.valueOf(u.get("login_user_type")));
}

private static String sessionUserId(PageData pd){
if(pd==null){return "";}
Object s=pd.get("session_user");
if(!(s instanceof PageData)){return "";}
PageData u=(PageData)s;
Object id=u.get("id");
if(id==null){id=u.get("ID");}
if(id==null){return "";}
String sid=String.valueOf(id).trim();
return sid.isEmpty()||"null".equalsIgnoreCase(sid)?"":sid;
}

private void applySubmitterScope(PageData pd){
if(pd==null){return;}
if(isPersonalSession(pd)){
String uid=sessionUserId(pd);
if(!Tools.isEmpty(uid)){pd.put("user_id",uid);}
}
}
/**  添加日程任务进度记录管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addApp_task_progressNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除日程任务进度记录管理参数错误"); }
applySubmitterScope(pd);
int rowCount = _mapper.addApp_task_progress(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加日程任务进度记录管理成功"); }
return ServerResponse.createByErrorMessage("添加日程任务进度记录管理失败");}
/**  添加日程任务进度记录管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addApp_task_progressAll(PageData pd) {
applySubmitterScope(pd);
int rowCount = _mapper.addApp_task_progress(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加日程任务进度记录管理成功");  }
return ServerResponse.createByErrorMessage("添加日程任务进度记录管理失败");}
/** 根据id删除日程任务进度记录管理数据 */
public ServerResponse<String> deleteApp_task_progress(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除日程任务进度记录管理参数错误");  }
int rowCount = _mapper.deleteApp_task_progressId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除日程任务进度记录管理成功"); }
return ServerResponse.createByErrorMessage("删除日程任务进度记录管理失败");}
/** 根据id更新日程任务进度记录管理数据 */
@Transactional
public ServerResponse<String> updateApp_task_progress(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改日程任务进度记录管理参数错误");}
applySubmitterScope(pd);
int rowCount = _mapper.updateApp_task_progress(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改日程任务进度记录管理成功");}
return ServerResponse.createByErrorMessage("修改日程任务进度记录管理失败"); }
/** 获取日程任务进度记录管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryApp_task_progressKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryApp_task_progressKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取日程任务进度记录管理列表数据 分页*/
 public List<PageData> queryPageApp_task_progressKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageApp_task_progressKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageApp_task_progressKeyList(pd);
// }
List<PageData> list=_mapper.queryPageApp_task_progressKeyList(pd);
return list;}
}
