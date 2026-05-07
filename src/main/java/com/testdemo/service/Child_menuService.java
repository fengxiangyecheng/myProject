package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.Permission_menuMapper;
import com.testdemo.mapper.Child_menuMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class Child_menuService extends BaseService {
@Autowired
private Child_menuMapper _mapper;
@Autowired
private Permission_menuMapper permission_menuMapper;
/**  添加二级菜单 重复数据不能添加*/
@Transactional
public ServerResponse<String> addChild_menuNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除二级菜单参数错误"); }
List<PageData> list =_mapper.queryChild_menuKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("二级菜单已存在");
int rowCount = _mapper.addChild_menu(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加二级菜单成功"); }
return ServerResponse.createByErrorMessage("添加二级菜单失败");}
/**  添加二级菜单 重复数据可以添加*/
@Transactional
public ServerResponse<String> addChild_menuAll(PageData pd) {
int rowCount = _mapper.addChild_menu(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加二级菜单成功");  }
return ServerResponse.createByErrorMessage("添加二级菜单失败");}
/** 根据id删除二级菜单数据 */
public ServerResponse<String> deleteChild_menu(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除二级菜单参数错误");  }
int rowCount = _mapper.deleteChild_menuId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除二级菜单成功"); }
return ServerResponse.createByErrorMessage("删除二级菜单失败");}
/** 根据id更新二级菜单数据 */
@Transactional
public ServerResponse<String> updateChild_menu(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改二级菜单参数错误");}
List<PageData> list=_mapper.queryChild_menuKey(pd);
if(list.size()>0){
return ServerResponse.createBySuccessMessage("数据已经存在"); }
int rowCount = _mapper.updateChild_menu(pd);
if(rowCount > 0){
// 查询权限菜单中的二级菜单id，更新一级菜单id
PageData pd2=new PageData();
pd2.put("child_menu_id",pd.get("id"));
List<PageData> list2= permission_menuMapper.queryPermission_menuKey(pd2);
PageData pd1=new PageData();
 pd1.put("id",list2.get(0).get("id"));
pd1.put("menu_id",pd.get("menu_id"));
permission_menuMapper.updatePermission_menu(pd1);
return ServerResponse.createBySuccessMessage("修改二级菜单成功");}
return ServerResponse.createByErrorMessage("修改二级菜单失败"); }
/** 获取二级菜单数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryChild_menuKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryChild_menuKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取二级菜单列表数据 分页*/
 public List<PageData> queryPageChild_menuKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageChild_menuKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageChild_menuKeyList(pd);
// }
List<PageData> list=_mapper.queryPageChild_menuKeyList(pd);
return list;}
}
