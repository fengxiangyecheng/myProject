package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface Permission_menuMapper {
/** 添加数据 */
int addPermission_menu(PageData pd);
/** 根据id删除数据 */
int deletePermission_menuId(PageData pd);
/** 根据id更新数据 */
int updatePermission_menu(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryPermission_menuKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPagePermission_menuKeyList(Page page);}
