package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.Role_manageMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class Role_manageService extends BaseService {
@Autowired
private Role_manageMapper _mapper;
/**  添加角色管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addRole_manageNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除角色管理参数错误"); }
List<PageData> list =_mapper.queryRole_manageKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("角色管理已存在");
int rowCount = _mapper.addRole_manage(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加角色管理成功"); }
return ServerResponse.createByErrorMessage("添加角色管理失败");}
/**  添加角色管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addRole_manageAll(PageData pd) {
int rowCount = _mapper.addRole_manage(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加角色管理成功");  }
return ServerResponse.createByErrorMessage("添加角色管理失败");}
/** 根据id删除角色管理数据 */
public ServerResponse<String> deleteRole_manage(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除角色管理参数错误");  }
int rowCount = _mapper.deleteRole_manageId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除角色管理成功"); }
return ServerResponse.createByErrorMessage("删除角色管理失败");}
/** 根据id更新角色管理数据 */
@Transactional
public ServerResponse<String> updateRole_manage(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改角色管理参数错误");}
List<PageData> list=_mapper.queryRole_manageKey(pd);
if(list.size()>0){
return ServerResponse.createBySuccessMessage("数据已经存在"); }
int rowCount = _mapper.updateRole_manage(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改角色管理成功");}
return ServerResponse.createByErrorMessage("修改角色管理失败"); }
/** 获取角色管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryRole_manageKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryRole_manageKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取角色管理列表数据 分页*/
 public List<PageData> queryPageRole_manageKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageRole_manageKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageRole_manageKeyList(pd);
// }
List<PageData> list=_mapper.queryPageRole_manageKeyList(pd);
return list;}
}
