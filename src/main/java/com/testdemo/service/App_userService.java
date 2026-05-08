package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.config.SecurityConfig.MigrationPasswordEncoder;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_userMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.List;
@Slf4j
@Service
public class App_userService extends BaseService {
@Autowired
private App_userMapper _mapper;
@Autowired
private PasswordEncoder passwordEncoder;

private static String shortUserMessage(Throwable e){
Throwable t=e;
while(t.getCause()!=null&&t.getCause()!=t){t=t.getCause();}
String m=t.getMessage();
if(m==null||m.isEmpty()){m=e.getClass().getSimpleName();}
return m.length()>240?m.substring(0,240)+"\u2026":m;}

/** 个人用户登录：与登录页 query 参数 USERNAME、PASSWORD 对齐（兼容误传 username/password） */
public PageData getLoginValidation(PageData pd) {
String u=trimLogin(pd.getString("USERNAME"),pd.getString("username"));
String p=trimLogin(pd.getString("PASSWORD"),pd.getString("password"));
if(Tools.isEmpty(u)||Tools.isEmpty(p)){return null;}
PageData q=new PageData();
q.put("USERNAME",u);
q.put("PASSWORD",p);
PageData user=_mapper.queryByUsername(q);
if(user==null){return null;}
String storedPwd=user.getString("password");
if(!passwordEncoder.matches(p,storedPwd)){return null;}
if(passwordEncoder instanceof MigrationPasswordEncoder
&&((MigrationPasswordEncoder)passwordEncoder).needsMigration(storedPwd)){
PageData upd=new PageData();
upd.put("USERNAME",u);
upd.put("PASSWORD",passwordEncoder.encode(p));
_mapper.updatePassword(upd);}
return user;}

private static String trimLogin(String a,String b){
String s=!Tools.isEmpty(a)?a:b;
return s==null?null:s.trim();}

private static boolean isSystemAdmin(PageData adminPd){
return adminPd!=null&&"1".equals(String.valueOf(adminPd.get("role_manage_id")));}

/** 表字段 sex 为数值型，表单常填「男/女」 */
private static void normalizeSexForDb(PageData pd){
if(pd==null){return;}
Object sx=pd.get("sex");
if(sx==null){return;}
if(sx instanceof Number){return;}
String s=String.valueOf(sx).trim();
if(s.isEmpty()){pd.remove("sex");return;}
if("男".equals(s)){pd.put("sex",1);return;}
if("女".equals(s)){pd.put("sex",0);return;}
try{pd.put("sex",Integer.parseInt(s));}catch(NumberFormatException e){pd.remove("sex");}}

/** 系统管理员：禁用/启用个人用户账号 */
@Transactional
public ServerResponse<String> setAccountStatus(PageData pd,PageData adminPd){
if(!isSystemAdmin(adminPd)){return ServerResponse.createByErrorMessage("无权限");}
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("参数错误");}
Object st=pd.get("account_status");
if(st==null){return ServerResponse.createByErrorMessage("参数错误");}
String status=String.valueOf(st).trim();
if(status.isEmpty()||(!"0".equals(status)&&!"1".equals(status))){
return ServerResponse.createByErrorMessage("参数错误");}
PageData up=new PageData();
up.put("id",pd.getString("id"));
up.put("account_status",status);
int rowCount=_mapper.updateApp_userAccountStatus(up);
return rowCount>0?ServerResponse.createBySuccessMessage("操作成功"):ServerResponse.createByErrorMessage("操作失败");}

/** 系统管理员：重置个人用户密码（参数 PASSWORD 与管理员接口一致） */
@Transactional
public ServerResponse<String> adminResetPassword(PageData pd,PageData adminPd){
if(!isSystemAdmin(adminPd)){return ServerResponse.createByErrorMessage("无权限");}
if(Tools.isEmpty(pd.getString("id"))||Tools.isEmpty(pd.getString("PASSWORD"))){
return ServerResponse.createByErrorMessage("参数错误");}
PageData up=new PageData();
up.put("id",pd.getString("id"));
up.put("password",passwordEncoder.encode(pd.getString("PASSWORD")));
int rowCount=_mapper.updateApp_user(up);
return rowCount>0?ServerResponse.createBySuccessMessage("密码已重置"):ServerResponse.createByErrorMessage("重置失败");}

/**  添加用户管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addApp_userNo(PageData pd) {
try{
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除用户管理参数错误"); }
if(Tools.isEmpty(pd.getString("username"))){ return ServerResponse.createByErrorMessage("用户名不能为空"); }
pd.put("username",pd.getString("username").trim());
PageData dupQ=new PageData();
dupQ.put("username",pd.getString("username"));
if(_mapper.countApp_userByUsername(dupQ)>0){ return ServerResponse.createByErrorMessage("用户名已存在"); }
normalizeSexForDb(pd);
int rowCount = _mapper.addApp_user(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加用户管理成功"); }
return ServerResponse.createByErrorMessage("添加用户管理失败");
}catch(Exception e){
log.error("addApp_userNo",e);
TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
return ServerResponse.createByErrorMessage("保存失败："+shortUserMessage(e));}}
/**  添加用户管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addApp_userAll(PageData pd) {
normalizeSexForDb(pd);
int rowCount = _mapper.addApp_user(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加用户管理成功");  }
return ServerResponse.createByErrorMessage("添加用户管理失败");}
/** 根据id删除用户管理数据 */
public ServerResponse<String> deleteApp_user(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除用户管理参数错误");  }
int rowCount = _mapper.deleteApp_userId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除用户管理成功"); }
return ServerResponse.createByErrorMessage("删除用户管理失败");}
/** 根据id更新用户管理数据 */
@Transactional
public ServerResponse<String> updateApp_user(PageData pd) {
try{
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改用户管理参数错误");}
if(!Tools.isEmpty(pd.getString("username"))){
pd.put("username",pd.getString("username").trim());
PageData q=new PageData();
q.put("username",pd.getString("username"));
q.put("id",pd.getString("id"));
if(_mapper.countApp_userUsernameOtherThanId(q)>0){
return ServerResponse.createByErrorMessage("用户名已存在");}}
normalizeSexForDb(pd);
int rowCount = _mapper.updateApp_user(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改用户管理成功");}
return ServerResponse.createByErrorMessage("修改用户管理失败");
}catch(Exception e){
log.error("updateApp_user",e);
TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
return ServerResponse.createByErrorMessage("保存失败："+shortUserMessage(e));}}
/** 获取用户管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryApp_userKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryApp_userKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取用户管理列表数据 分页*/
 public List<PageData> queryPageApp_userKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageApp_userKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageApp_userKeyList(pd);
// }
List<PageData> list=_mapper.queryPageApp_userKeyList(pd);
return list;}

private static boolean isPersonalSession(PageData sess){
return sess!=null&&"personal".equals(String.valueOf(sess.get("login_user_type")));}

private static String sessionUserId(PageData sess){
if(sess==null){return null;}
Object id=sess.get("id");
if(id==null){id=sess.get("ID");}
if(id==null){return null;}
String s=String.valueOf(id).trim();
return s.isEmpty()||"null".equalsIgnoreCase(s)?null:s;}

/** 当前登录个人用户拉取资料（不含密码） */
public ServerResponse<PageData> getMeForSession(PageData sess){
if(!isPersonalSession(sess)){return ServerResponse.createByErrorMessage("仅个人账号可使用个人中心资料接口");}
String id=sessionUserId(sess);
if(Tools.isEmpty(id)){return ServerResponse.createByErrorMessage("未登录");}
PageData q=new PageData();
q.put("id",id);
List<PageData> lst=_mapper.queryApp_userKey(q);
if(lst==null||lst.isEmpty()){return ServerResponse.createByErrorMessage("用户不存在");}
PageData one=new PageData();
one.putAll(lst.get(0));
one.remove("password");
return ServerResponse.createBySuccess(one);}

/** 当前登录个人用户修改资料（白名单字段） */
@Transactional
public ServerResponse<String> updateMeForSession(PageData sess,PageData body){
if(!isPersonalSession(sess)){return ServerResponse.createByErrorMessage("仅个人账号可保存");}
String sid=sessionUserId(sess);
if(Tools.isEmpty(sid)){return ServerResponse.createByErrorMessage("未登录");}
if(body==null||!sid.equals(String.valueOf(body.get("id")).trim())){
return ServerResponse.createByErrorMessage("参数错误");}
PageData up=new PageData();
up.put("id",sid);
if(!Tools.isEmpty(body.getString("username"))){
String nu=body.getString("username").trim();
PageData dup=new PageData();
dup.put("username",nu);
dup.put("id",sid);
if(_mapper.countApp_userUsernameOtherThanId(dup)>0){
return ServerResponse.createByErrorMessage("用户名已存在");}
up.put("username",nu);}
if(!Tools.isEmpty(body.getString("nickname"))){up.put("nickname",body.getString("nickname").trim());}
if(!Tools.isEmpty(body.getString("phone"))){up.put("phone",body.getString("phone").trim());}
if(body.get("sex")!=null&&!Tools.isEmpty(String.valueOf(body.get("sex")).trim())){
PageData sx=new PageData();
sx.put("sex",body.get("sex"));
normalizeSexForDb(sx);
if(sx.get("sex")!=null){up.put("sex",sx.get("sex"));}}
if(!Tools.isEmpty(body.getString("password"))){up.put("password",passwordEncoder.encode(body.getString("password")));}
if(body.get("user_prefs")!=null){
String raw=String.valueOf(body.get("user_prefs")).trim();
up.put("user_prefs",raw.isEmpty()?null:raw);}
if(up.size()<=1){return ServerResponse.createByErrorMessage("没有可保存的修改");}
normalizeSexForDb(up);
try{
int n=_mapper.updateApp_user(up);
return n>0?ServerResponse.createBySuccessMessage("保存成功"):ServerResponse.createByErrorMessage("保存失败");
}catch(Exception e){
log.error("updateMeForSession",e);
TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
return ServerResponse.createByErrorMessage("保存失败："+shortUserMessage(e));}}
}
