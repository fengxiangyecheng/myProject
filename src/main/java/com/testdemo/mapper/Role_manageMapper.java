package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface Role_manageMapper {
/** 添加数据 */
int addRole_manage(PageData pd);
/** 根据id删除数据 */
int deleteRole_manageId(PageData pd);
/** 根据id更新数据 */
int updateRole_manage(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryRole_manageKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageRole_manageKeyList(Page page);}
