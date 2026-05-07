package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_noticeMapper;
import com.testdemo.service.BaseService;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class App_noticeService extends BaseService {
@Autowired
private App_noticeMapper _mapper;
/**  添加系统公告管理 重复数据不能添加*/
@Transactional
public ServerResponse<String> addApp_noticeNo(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.createByErrorMessage("删除系统公告管理参数错误"); }
List<PageData> list =_mapper.queryApp_noticeKey(pd);
if(list.size() > 0) return ServerResponse.createByErrorMessage("系统公告管理已存在");
int rowCount = _mapper.addApp_notice(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加系统公告管理成功"); }
return ServerResponse.createByErrorMessage("添加系统公告管理失败");}
/**  添加系统公告管理 重复数据可以添加*/
@Transactional
public ServerResponse<String> addApp_noticeAll(PageData pd) {
int rowCount = _mapper.addApp_notice(pd);
if(rowCount > 0){ return ServerResponse.createBySuccessMessage("添加系统公告管理成功");  }
return ServerResponse.createByErrorMessage("添加系统公告管理失败");}
/** 根据id删除系统公告管理数据 */
public ServerResponse<String> deleteApp_notice(PageData pd) {
if(Tools.isObjEmpty(pd.get("id"))){ return ServerResponse.createByErrorMessage("删除系统公告管理参数错误");  }
int rowCount = _mapper.deleteApp_noticeId(pd);
if(rowCount > 0){  return ServerResponse.createBySuccessMessage("删除系统公告管理成功"); }
return ServerResponse.createByErrorMessage("删除系统公告管理失败");}
/** 根据id更新系统公告管理数据 */
@Transactional
public ServerResponse<String> updateApp_notice(PageData pd) {
if(Tools.isEmpty(pd.getString("id"))){return ServerResponse.createByErrorMessage("修改系统公告管理参数错误");}
List<PageData> list=_mapper.queryApp_noticeKey(pd);
if(list.size()>0){
return ServerResponse.createBySuccessMessage("数据已经存在"); }
int rowCount = _mapper.updateApp_notice(pd);
if(rowCount > 0){
return ServerResponse.createBySuccessMessage("修改系统公告管理成功");}
return ServerResponse.createByErrorMessage("修改系统公告管理失败"); }
/** 获取系统公告管理数据(非分页,搜索功能) */
public ServerResponse<List<PageData>> queryApp_noticeKey(PageData pd) {
if(Tools.isObjEmpty(pd)){ return ServerResponse.badArgument(); }
List<PageData> list=_mapper.queryApp_noticeKey(pd);
return ServerResponse.createBySuccess(list);}
/** 获取系统公告管理列表数据 分页*/
 public List<PageData> queryPageApp_noticeKeyList(Page pd) {
// 根据权限账号获取数据，当前账号是super权限获取所有数据，否获取当前登录账号数据
// PageData pd1 = pd.getPd();
// Object user_id = pd1.get("user_id");
// List<PageData> list = null;
// if (user_id.equals(1)) {
// list =_mapper.queryPageApp_noticeKeyList(pd);
// } else {
// pd1.put("sys_user", user_id);
// list = _mapper.queryPageApp_noticeKeyList(pd);
// }
List<PageData> list=_mapper.queryPageApp_noticeKeyList(pd);
return list;}
}
