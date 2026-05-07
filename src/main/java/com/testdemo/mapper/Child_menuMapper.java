package com.testdemo.mapper;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface Child_menuMapper {
/** 添加数据 */
int addChild_menu(PageData pd);
/** 根据id删除数据 */
int deleteChild_menuId(PageData pd);
/** 根据id更新数据 */
int updateChild_menu(PageData pd);
/** 获取用户列表数据 不分页*/
List<PageData> queryChild_menuKey(PageData pd);
/** 获取用户列表数据 分页*/
List<PageData> queryPageChild_menuKeyList(Page page);}
