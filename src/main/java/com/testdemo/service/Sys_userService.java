package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.Sys_userMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class Sys_userService extends BaseService {
@Autowired
private Sys_userMapper _mapper;
@Autowired
private PasswordEncoder passwordEncoder;

/** 「个人」角色仅用于前台 app_user；后台 sys_user 勿绑定，否则与权限/任务里的用户 id 体系混淆 */
private ServerResponse<String> rejectSysUserPersonalRole(PageData pd) {
    if (pd == null) {
        return null;
    }
    String r = pd.getString("role_manage_id");
    if (r != null && "3".equals(r.trim())) {
        return ServerResponse.createByErrorMessage("后台账号不能选择「个人」角色。前台个人用户请在「用户管理」中维护。");
    }
    return null;
}

/**  添加管理员管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addSys_userNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除管理员管理参数错误"); }
ServerResponse<String> roleErr = rejectSysUserPersonalRole(pd);
if (roleErr != null) {
    return roleErr;
}
List<PageData> list =_mapper.querySys_userKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("管理员管理已存在");
int rowCount = _mapper.addSys_user(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加管理员管理成功"); }
return ServerResponse.createByErrorMessage("添加管理员管理失败");}
/**  添加管理员管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addSys_userAll(PageData pd) {
ServerResponse<String> roleErr = rejectSysUserPersonalRole(pd);
if (roleErr != null) {
    return roleErr;
}
int rowCount = _mapper.addSys_user(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加管理员管理成功");  }
return ServerResponse.createByErrorMessage("添加管理员管理失败");}
/** 根据id删除管理员管理数据 */
public ServerResponse<String> deleteSys_user(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除管理员管理参数错误");  }
int rowCount = _mapper.deleteSys_userId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除管理员管理成功"); }
return ServerResponse.createByErrorMessage("删除管理员管理失败");}
/** 根据id更新管理员管理数据 */
@Transactional
public ServerResponse<String> updateSys_user(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改管理员管理参数错误");}
ServerResponse<String> roleErr = rejectSysUserPersonalRole(pd);
if (roleErr != null) {
    return roleErr;
}
if(!Tools.isEmpty(pd.getString("USERNAME"))){
PageData q=new PageData();
q.put("USERNAME",pd.getString("USERNAME"));
for(PageData row:_mapper.querySys_userKey(q)){
if(!String.valueOf(row.get("id")).equals(String.valueOf(pd.get("id")))){
return ServerResponse.createByErrorMessage("用户名已存在");}}}
int rowCount = _mapper.updateSys_user(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改管理员管理成功");}
return ServerResponse.createByErrorMessage("修改管理员管理失败"); }

/** 系统管理员：禁用/启用账号 */
@Transactional
public ServerResponse<String> setAccountStatus(PageData pd,PageData adminPd){
if(adminPd==null||!"1".equals(String.valueOf(adminPd.get("role_manage_id")))){
return ServerResponse.createByErrorMessage("无权限");}
if(Tools.isEmpty(pd.getString("id"))||Tools.isEmpty(pd.getString("account_status"))){
return ServerResponse.createByErrorMessage("参数错误");}
int rowCount=_mapper.updateSys_user(pd);
return rowCount>0?ServerResponse.createBySuccessMessage("操作成功"):ServerResponse.createByErrorMessage("操作失败");}

/** 系统管理员：重置密码 */
@Transactional
public ServerResponse<String> adminResetPassword(PageData pd,PageData adminPd){
if(adminPd==null||!"1".equals(String.valueOf(adminPd.get("role_manage_id")))){
return ServerResponse.createByErrorMessage("无权限");}
if(Tools.isEmpty(pd.getString("id"))||Tools.isEmpty(pd.getString("PASSWORD"))){
return ServerResponse.createByErrorMessage("参数错误");}
PageData up=new PageData();
up.put("id",pd.getString("id"));
up.put("PASSWORD",passwordEncoder.encode(pd.getString("PASSWORD")));
int rowCount=_mapper.updateSys_user(up);
return rowCount>0?ServerResponse.createBySuccessMessage("密码已重置"):ServerResponse.createByErrorMessage("重置失败");}
/** 获取管理员管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> querySys_userKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.querySys_userKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取管理员管理列表数据 分页*/
 public List<PageData> queryPageSys_userKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageSys_userKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageSys_userKeyList(pd);
// }
List<PageData> list=_mapper.queryPageSys_userKeyList(pd);
return list;}
}
