package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.Child_menuMapper;
import com.testdemo.mapper.Permission_menuMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class Permission_menuService extends BaseService {
@Autowired
private Permission_menuMapper _mapper;
@Autowired
 private Child_menuMapper child_menuMapper;
/**  添加权限管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addPermission_menuNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除权限管理参数错误"); }
String menu_id=getMenuId(pd);
pd.put("menu_id",menu_id);
List<PageData> list =_mapper.queryPermission_menuKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("权限管理已存在");
int rowCount = _mapper.addPermission_menu(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加权限管理成功"); }
return ServerResponse.createByErrorMessage("添加权限管理失败");}
/**  添加权限管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addPermission_menuAll(PageData pd) {
String menu_id=getMenuId(pd);
pd.put("menu_id",menu_id);
int rowCount = _mapper.addPermission_menu(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加权限管理成功");  }
return ServerResponse.createByErrorMessage("添加权限管理失败");}
/** 根据id删除权限管理数据 */
public ServerResponse<String> deletePermission_menu(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除权限管理参数错误");  }
int rowCount = _mapper.deletePermission_menuId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除权限管理成功"); }
return ServerResponse.createByErrorMessage("删除权限管理失败");}
/** 根据id更新权限管理数据 */
@Transactional
public ServerResponse<String> updatePermission_menu(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改权限管理参数错误");}
String menu_id=getMenuId(pd);
pd.put("menu_id",menu_id);
List<PageData> list=_mapper.queryPermission_menuKey(pd);
if(list.size()>0){
return ServerResponse.createBySuccessMessage("数据已经存在"); }
int rowCount = _mapper.updatePermission_menu(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改权限管理成功");}
return ServerResponse.createByErrorMessage("修改权限管理失败"); }
/** 获取权限管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryPermission_menuKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
// 兼容历史前端：按钮权限接口把 child_menu_id 误传到 id
if(Tools.isEmpty(pd.getString("child_menu_id"))&&!Tools.isEmpty(pd.getString("id"))&&!Tools.isEmpty(pd.getString("role_manage_id"))){
pd.put("child_menu_id",pd.getString("id"));
pd.remove("id");
}
// 角色按钮权限查询若未定位到具体菜单，返回空，避免前端误取第一条造成权限错乱
if(!Tools.isEmpty(pd.getString("role_manage_id"))&&Tools.isEmpty(pd.getString("child_menu_id"))){
PageData deny=new PageData();
deny.put("name_add","0");
deny.put("name_edit","0");
deny.put("name_delete","0");
deny.put("name_query","0");
List<PageData> denys=new ArrayList<>();
denys.add(deny);
return ServerResponse.createBySuccess(denys);}
List<PageData> list=_mapper.queryPermission_menuKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取权限管理列表数据 分页*/
 public List<PageData> queryPagePermission_menuKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPagePermission_menuKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPagePermission_menuKeyList(pd);
// }
List<PageData> list=_mapper.queryPagePermission_menuKeyList(pd);
return list;}
/** 获取权限管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryPermission_menuKey1(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryPermission_menuKey(pd);
List<PageData> listAll = new ArrayList<>();
for1:
for (int i = 0; i < list.size(); i++) {
PageData pageData = list.get(i);
String menu_id = pageData.getString("menu_id");
if (menu_id.equals("")) {
// 二级菜单用于一级菜单
pageData.put("list", new ArrayList<>());
listAll.add(pageData);
} else {
// 一级菜单,先检查是否已经存在
if (listAll.size() == 0) {
listAll.add(getMap1(pageData));
continue for1;
} else {
int count2 = 0;
PageData pageDataA = new PageData();
 for (int j = 0; j < listAll.size(); j++) {
PageData pageData1 = listAll.get(j);
String menu_id1 = pageData1.getString("menu_id");
// 判断是否有符合的一级菜单，如有追加，如无新起
if (menu_id1.equals(menu_id)) {
count2 = 1;
pageDataA = pageData1;
 break; }}
if (count2 == 1) {
List<PageData> list11 = (List<PageData>) pageDataA.get("list");
list11.add(pageData);
} else {
listAll.add(getMap1(pageData));
 }} } }
 return ServerResponse.createBySuccess(listAll);}
private PageData getMap1(PageData pageData) {
List<PageData> count = new ArrayList<>();
count.add(pageData);
PageData pageData2 = new PageData();
pageData2.put("list", count);
pageData2.put("id", pageData.get("id"));
pageData2.put("menu_id", pageData.getString("menu_id"));
pageData2.put("name", pageData.getString("menu_name1"));
pageData2.put("icon", pageData.getString("menu_menu_icon1"));
return pageData2;}
private String getMenuId(PageData pageData){
 PageData pageData1 = new PageData();
pageData1.put("id",pageData.get("child_menu_id"));
List<PageData>list=child_menuMapper.queryChild_menuKey(pageData1);
 if(list.size()>0)return list.get(0).getString("menu_id");else return "";
 }
}
